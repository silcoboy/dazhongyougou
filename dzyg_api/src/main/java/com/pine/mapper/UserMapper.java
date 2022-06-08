package com.pine.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pine.entity.UserInfo;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

    UserInfo getByNickname(String nickName);


}
