package com.lqs.seata.server.one.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lqs.seata.server.one.domain.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李奇凇
 * @moduleName UserMapper
 * @date 2023/1/8 下午7:02
 * @do mapper接口
 */

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
