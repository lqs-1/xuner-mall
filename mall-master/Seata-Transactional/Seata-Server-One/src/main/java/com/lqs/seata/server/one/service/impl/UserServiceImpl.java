package com.lqs.seata.server.one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.R;
import com.lqs.seata.server.one.domain.UserEntity;
import com.lqs.seata.server.one.feign.TwoFeignClientService;
import com.lqs.seata.server.one.mapper.UserMapper;
import com.lqs.seata.server.one.service.UserService;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TwoFeignClientService twoFeignClientService;


    /**
     * 这里是TM,只要TM出现异常,不管异常之前执行了多少个RM,都会回滚
     *
     * 如果是RM出现异常,如果不在远程调用得时候传递一个类似成功失败得状态信息,那么RM回滚,TM不回滚,如果传了状态,那么可以在TM中抛异常来回滚所有
     * 或者手动回滚
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(readOnly = false)
    @Override
    public void alterUser() throws TransactionException {

        System.out.println(RootContext.getXID());

        List<UserEntity> userEntityList = this.baseMapper.selectList(null);

        userEntityList.stream().map(userEntity -> {
            userEntity.setAge(userEntity.getAge() + 10L);
            return userEntity;
        }).forEach(userMapper::updateById);

        R result = twoFeignClientService.alter();

        int i = 10/0;

//        if (result.parseCode() > 20000){
//            // 手动回滚
//            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
//        }


    }
}
