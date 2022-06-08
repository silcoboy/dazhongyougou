	package com.pine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pine.base.utils.ResponseUserToken;
import com.pine.entity.AdminInfo;


public interface AdminInfoService extends IService<AdminInfo> {

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    ResponseUserToken login(String username, String password);


    AdminInfo getByUserName(String username);





}
