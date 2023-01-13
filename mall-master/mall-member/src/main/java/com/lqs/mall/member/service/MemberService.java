package com.lqs.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.to.auth.AccountLoginTo;
import com.lqs.mall.common.to.auth.AccountRegisterTo;
import com.lqs.mall.common.to.auth.SocialUserTo;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:01
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void commonAccountRegister(AccountRegisterTo accountRegisterTo);

    MemberEntity commonAccountLogin(AccountLoginTo accountLoginTo);

    MemberEntity socialAccountAutoLogin(SocialUserTo socialUserTo) throws Exception;

    void confirmOrder(String orderSn, String payDate);
}

