package com.lqs.seata.server.two.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.seata.server.two.domain.UserEntity;
import com.lqs.seata.server.two.mapper.UserMapper;
import com.lqs.seata.server.two.service.UserService;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName UserServiceImpl
 * @date 2023/1/8 下午6:43
 * @do 第一个微服务得服务层实现
 */


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void alterUser() {

        System.out.println(RootContext.getXID());
        List<UserEntity> userEntityList = this.baseMapper.selectList(null);

        userEntityList.stream().map(userEntity -> {
            userEntity.setAge(userEntity.getAge() + 10L);
            return userEntity;
        }).forEach(this.baseMapper::updateById);

        int i = 10/0;
    }
}
