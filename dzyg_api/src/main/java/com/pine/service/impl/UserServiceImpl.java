package com.pine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.entity.UserInfo;
import com.pine.mapper.UserMapper;
import com.pine.service.UserService;
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo>  
implements UserService {

    @Autowired
    UserMapper userMapper;

	@Override
	public UserInfo getByNickname(String no) {
		return  userMapper.getByNickname(no);
	}
}
