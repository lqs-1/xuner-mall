package com.lqs.seata.server.one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.seata.server.one.domain.UserEntity;
import io.seata.core.exception.TransactionException;

/**
 * @author 李奇凇
 * @moduleName UserService
 * @date 2023/1/8 下午6:42
 * @do 第一个微服务得服务层接口
 */
public interface UserService extends IService<UserEntity> {
    void alterUser() throws TransactionException;

}
