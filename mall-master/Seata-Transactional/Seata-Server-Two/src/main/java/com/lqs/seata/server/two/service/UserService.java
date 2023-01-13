package com.lqs.seata.server.two.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.seata.server.two.domain.UserEntity;

/**
 * @author 李奇凇
 * @moduleName UserService
 * @date 2023/1/8 下午6:42
 * @do 第一个微服务得服务层接口
 */
public interface UserService extends IService<UserEntity> {
    void alterUser();
}
