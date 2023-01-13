package com.lqs.mall.member.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.member.entity.MemberReceiveAddressEntity;
import com.lqs.mall.member.service.MemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 会员收货地址
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:01
 */
@RestController
@RequestMapping("member/memberreceiveaddress")
public class MemberReceiveAddressController {
    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;



    /**
     * 获取指定的addrId的地址
     * @param addressId
     * @return
     */
    @GetMapping("{addressId}/address")
    public R getReceiveAddress(@PathVariable Long addressId){

        MemberReceiveAddressEntity memberReceiveAddress = memberReceiveAddressService.getAddrByAddrId(addressId);

        return R.ok().put(memberReceiveAddress);


    }


    /**
     * 根据memberId（用户id）获取用户收货地址的列表
     * @param memberId
     * @return
     */
    @GetMapping("{memberId}/addressList")
    public R getAddressList(@PathVariable Long memberId){
        try{
            // 程序代码
            List<MemberReceiveAddressEntity> memberReceiveAddressEntityList = memberReceiveAddressService.getAddressList(memberId);

            return R.ok(REnum.REQUEST_MEMBER_ADDRESS_SUCCESS.getStatusCode(),
                    REnum.REQUEST_MEMBER_ADDRESS_SUCCESS.getStatusMsg())
                    .put("memberReceiveAddressList", memberReceiveAddressEntityList);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_MEMBER_ADDRESS_FAIL.getStatusCode(),
                    REnum.REQUEST_MEMBER_ADDRESS_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberReceiveAddressService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberReceiveAddressEntity memberReceiveAddress = memberReceiveAddressService.getById(id);

        return R.ok().put("memberReceiveAddress", memberReceiveAddress);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberReceiveAddressEntity memberReceiveAddress){
		memberReceiveAddressService.save(memberReceiveAddress);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberReceiveAddressEntity memberReceiveAddress){
		memberReceiveAddressService.updateById(memberReceiveAddress);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberReceiveAddressService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
