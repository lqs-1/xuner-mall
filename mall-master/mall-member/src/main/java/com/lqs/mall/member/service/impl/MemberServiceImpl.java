package com.lqs.mall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.auth.AccountLoginTo;
import com.lqs.mall.common.to.auth.AccountRegisterTo;
import com.lqs.mall.common.to.auth.AccountRespTo;
import com.lqs.mall.common.to.auth.SocialUserTo;
import com.lqs.mall.common.utils.HttpUtils;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.SmsCodeGenerateUtils;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.member.dao.MemberDao;
import com.lqs.mall.member.entity.MemberEntity;
import com.lqs.mall.member.exception.MobileCodeExistException;
import com.lqs.mall.member.exception.UserNameExistException;
import com.lqs.mall.member.fiegn.CartOpenFeignClientService;
import com.lqs.mall.member.fiegn.OrderOpenFeignClientService;
import com.lqs.mall.member.fiegn.WareOpenFeignClientService;
import com.lqs.mall.member.interceptor.UserLoginInterceptor;
import com.lqs.mall.member.service.MemberService;
import com.lqs.mall.member.vo.OrderVo;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {


    @Autowired
    private OrderOpenFeignClientService orderOpenFeignClientService;


    @Autowired
    private WareOpenFeignClientService wareOpenFeignClientService;

    @Autowired
    private CartOpenFeignClientService cartOpenFeignClientService;


    private final BCryptPasswordEncoder passWordEncoder = new BCryptPasswordEncoder();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new QueryPage<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * ????????????????????????
     * @param accountRegisterTo
     */
    @Transactional(readOnly = false)
    @Override
    public void commonAccountRegister(AccountRegisterTo accountRegisterTo) {
        // ?????????????????????????????????????????????
        userNameExist(accountRegisterTo.getUserName());
        mobileCodeExist(accountRegisterTo.getMobileCode());

        // ???????????????????????????????????????
        MemberEntity account = new MemberEntity();
        account.setUsername(accountRegisterTo.getUserName());
        account.setMobile(accountRegisterTo.getMobileCode());

        // ???????????????nickName
        account.setNickname(Constant.COMMON_REGISTER_NICKNAME_PREFIX + passWordEncoder.encode(SmsCodeGenerateUtils.generateLongSmsCode()).substring(0,8));

        // ?????????????????????
        String encodePassWord = passWordEncoder.encode(accountRegisterTo.getPassWord());

        account.setPassword(encodePassWord);

        account.setCreateTime(new Date());

        this.save(account);

    }



    /**
     * ????????????????????????
     * @param accountLoginTo
     */
    @Override
    public MemberEntity commonAccountLogin(AccountLoginTo accountLoginTo) {
        MemberEntity commonAccount = this.baseMapper.selectOne(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getMobile, accountLoginTo.getLoginAccount()));

        // ????????????
        return passWordEncoder.matches(accountLoginTo.getPassword(), commonAccount.getPassword()) ? commonAccount : null;



    }


    /**
     * ???????????????????????????????????????????????????????????????????????????????????????+?????????
     * @param socialUserTo
     * @return
     */
    @Override
    public MemberEntity socialAccountAutoLogin(SocialUserTo socialUserTo) throws Exception {


        String socialUid = socialUserTo.getUid();
        // ???????????????????????????????????????????????????
        MemberEntity socialAccount = this.baseMapper.selectOne(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getSocialUid, socialUid));
        if (socialAccount != null){
            // ????????????????????????
            MemberEntity account = new MemberEntity();

            account.setId(socialAccount.getId());
            account.setAccessToken(socialUserTo.getAccess_token());
            account.setExpiresIn(socialUserTo.getExpires_in());

            this.updateById(account);

            socialAccount.setAccessToken(socialUserTo.getAccess_token());
            socialAccount.setExpiresIn(socialUserTo.getExpires_in());


        }else {
            // ??????????????????????????????????????????????????????
            MemberEntity registerSocialAccount = new MemberEntity();

            try{
                // ???????????????????????????????????????
                Map<String, String> queryParam = new HashMap<>();
                queryParam.put(Constant.WEIBO_ACCESS_TOKEN, socialUserTo.getAccess_token());
                queryParam.put(Constant.WEIBO_U_ID, socialUid);
                HttpResponse response = HttpUtils.doGet(Constant.OAUTH2_WEIBO_HOST, Constant.OAUTH2_WEIBO_REQUEST_USER_INFO_PATH, "get", new HashMap<String, String>(), queryParam);

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    // ????????????
                    String jsonString = EntityUtils.toString(response.getEntity());

                    JSONObject jsonObject = JSON.parseObject(jsonString);
                    // ??????
                    String accountName = jsonObject.getString("name");
                    // ??????
                    String accountGender = jsonObject.getString("gender");

                    registerSocialAccount.setNickname(accountName);
                    registerSocialAccount.setGender(accountGender.equals("m") ? 1 : 0);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            // ????????????????????????????????????????????????
            registerSocialAccount.setSocialUid(socialUid);
            registerSocialAccount.setAccessToken(socialUserTo.getAccess_token());
            registerSocialAccount.setExpiresIn(socialUserTo.getExpires_in());

            this.save(registerSocialAccount);

            socialAccount = registerSocialAccount;

        }
        return socialAccount;
    }

    /**
     * ????????????????????????????????????????????????????????? ????????????????????????????????? ?????????????????????
     * @param orderSn
     * @param payDate
     */
    @Override
    public void confirmOrder(String orderSn, String payDate) {

        // ?????????????????????
        AccountRespTo account = UserLoginInterceptor.user.get();

        // ?????????????????? ???????????????????????? ???????????????
        R requestAndAlterOrderResponse = orderOpenFeignClientService.requestAndAlterOrder(orderSn);

        // ??????????????????order?????????????????????????????????
        if (requestAndAlterOrderResponse.parseCode() < 20000){
            OrderVo order = requestAndAlterOrderResponse.parseType(new TypeReference<OrderVo>(){}, "order");

            // ????????????????????????????????????
            MemberEntity member = new MemberEntity();
            member.setId(account.getId());

            member.setGrowth(order.getGrowth());
            member.setIntegration(order.getIntegration());

            this.baseMapper.updateById(member);

        }

        // ?????????????????? ????????????????????? ????????????skuIds
        R deductionWareStockResponse = wareOpenFeignClientService.deductionWareStock(orderSn);

        if (deductionWareStockResponse.parseCode() < 20000){
            List<Long> skuIds = deductionWareStockResponse.parseType(new TypeReference<List<Long>>(){}, "skuIds");

            cartOpenFeignClientService.deleteCartItemBySkuIds(account.getId().toString(), skuIds);
        }

    }


    private void userNameExist(String userName) throws UserNameExistException{
        Integer accountUserNameCount = this.baseMapper.selectCount(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getUsername, userName));

        if (accountUserNameCount > 0){

            throw new UserNameExistException();

        }
    }

    private void mobileCodeExist(String mobileCode) throws MobileCodeExistException{
        Integer mobileCodeCount = this.baseMapper.selectCount(new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getMobile, mobileCode));

        if (mobileCodeCount > 0){

            throw new MobileCodeExistException();

        }

    }

}