package com.lqs.mall.member.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.to.auth.AccountLoginTo;
import com.lqs.mall.common.to.auth.AccountRegisterTo;
import com.lqs.mall.common.to.auth.AccountRespTo;
import com.lqs.mall.common.to.auth.SocialUserTo;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.member.entity.MemberEntity;
import com.lqs.mall.member.exception.MobileCodeExistException;
import com.lqs.mall.member.exception.UserNameExistException;
import com.lqs.mall.member.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 会员
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:01
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    /**
     * 普通用户注册
     * @param accountRegisterTo
     * @return
     */
    @PostMapping("commonAccountRegister")
    public R commonAccountRegister(@RequestBody AccountRegisterTo accountRegisterTo){

        try{
            // 程序代码
            memberService.commonAccountRegister(accountRegisterTo);

            return R.ok(REnum.COMMON_ACCOUNT_REGISTER_SUCCESS.getStatusCode(),
                    REnum.COMMON_ACCOUNT_REGISTER_SUCCESS.getStatusMsg());
        }catch(UserNameExistException userNameExistException){
            // 程序代码

            return R.error(REnum.COMMON_ACCOUNT_USERNAME_EXIST.getStatusCode(),
                    REnum.COMMON_ACCOUNT_USERNAME_EXIST.getStatusMsg())
                    .put("errorKey", "userName");
        }catch (MobileCodeExistException mobileCodeExistException){
            // 程序代码

            return R.error(REnum.COMMON_ACCOUNT_MOBILE_CODE_EXIST.getStatusCode(),
                    REnum.COMMON_ACCOUNT_MOBILE_CODE_EXIST.getStatusMsg())
                    .put("errorKey", "mobileCode");
        } finally{
            // 程序代码

        }
    }


    /**
     * 普通用户登录
     * @param accountLoginTo
     * @return
     */
    @PostMapping("commonAccountLogin")
    public R commonAccountLogin(@RequestBody AccountLoginTo accountLoginTo){

        System.out.println(accountLoginTo);

        try{
            // 程序代码
            MemberEntity commonAccount = memberService.commonAccountLogin(accountLoginTo);

            AccountRespTo responseCommonAccount = new AccountRespTo();
            BeanUtils.copyProperties(commonAccount, responseCommonAccount);

            return commonAccount != null ?
                    R.ok(REnum.COMMON_ACCOUNT_LOGIN_SUCCESS.getStatusCode(),
                            REnum.COMMON_ACCOUNT_LOGIN_SUCCESS.getStatusMsg()).put("account", commonAccount)
                            .put("account", responseCommonAccount)
                    :
                    R.error(REnum.COMMON_ACCOUNT_LOGIN_PASSWORD_OR_ACCOUNT_ERROR.getStatusCode(),
                            REnum.COMMON_ACCOUNT_LOGIN_PASSWORD_OR_ACCOUNT_ERROR.getStatusMsg());
        }catch(Exception e){
            // 程序代码
            e.printStackTrace();
            return R.error(REnum.COMMON_ACCOUNT_LOGIN_FAIL.getStatusCode(),
                            REnum.COMMON_ACCOUNT_LOGIN_FAIL.getStatusMsg());
        } finally{
            // 程序代码

        }
    }


    /**
     * 社交用户登录（判断是是否已经存在，存在则登录，不存在就注册+登录）
     * @param socialUserTo
     * @return
     */
    @PostMapping("oauth2/socialAccountAutoLogin")
    public R socialAccountAutoLogin(@RequestBody SocialUserTo socialUserTo){

        try{
            // 程序代码
            MemberEntity socialAccount = memberService.socialAccountAutoLogin(socialUserTo);

            AccountRespTo responseSocialAccount = new AccountRespTo();
            BeanUtils.copyProperties(socialAccount, responseSocialAccount);


            return R.ok(REnum.OAUTH2_SOCIAL_LOGIN_SUCCESS.getStatusCode(),
                    REnum.OAUTH2_SOCIAL_LOGIN_SUCCESS.getStatusMsg())
                    .put("account", responseSocialAccount);
        }catch(Exception e){
            // 程序代码
            e.printStackTrace();
            return R.error(REnum.OAUTH2_SOCIAL_LOGIN_FAIL.getStatusCode(),
                    REnum.OAUTH2_SOCIAL_LOGIN_FAIL.getStatusMsg());
        } finally{
            // 程序代码

        }
    }




    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
