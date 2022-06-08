package com.pine.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pine.entity.UserOrder;

public interface UserOrderService extends IService<UserOrder>{

	List<UserOrder> listOrd(String userId, Integer status);

    




}
