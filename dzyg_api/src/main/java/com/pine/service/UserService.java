package com.pine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pine.entity.UserInfo;


public interface UserService extends IService<UserInfo> {

	UserInfo getByNickname(String nickname);





}
