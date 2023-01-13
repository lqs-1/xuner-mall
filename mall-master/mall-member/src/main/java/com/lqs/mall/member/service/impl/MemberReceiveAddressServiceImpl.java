package com.lqs.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.member.dao.MemberReceiveAddressDao;
import com.lqs.mall.member.entity.MemberReceiveAddressEntity;
import com.lqs.mall.member.service.MemberReceiveAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity> implements MemberReceiveAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberReceiveAddressEntity> page = this.page(
                new QueryPage<MemberReceiveAddressEntity>().getPage(params),
                new QueryWrapper<MemberReceiveAddressEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(readOnly = true)
    // 根据memberId（用户id）获取用户收货地址的列表
    @Override
    public List<MemberReceiveAddressEntity> getAddressList(Long memberId) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<MemberReceiveAddressEntity>().eq(MemberReceiveAddressEntity::getMemberId, memberId));
    }

    /**
     * 获取指定得地址
     * @param addressId
     * @return
     */
    @Override
    public MemberReceiveAddressEntity getAddrByAddrId(Long addressId) {

        return this.baseMapper.selectOne(new LambdaQueryWrapper<MemberReceiveAddressEntity>().eq(MemberReceiveAddressEntity::getId, addressId));

    }
}