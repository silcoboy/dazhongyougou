package com.pine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.entity.UserOrder;
import com.pine.mapper.AdminInfoMapper;
import com.pine.mapper.UserOrderMapper;
import com.pine.service.UserOrderService;
/**
 */
@Service("userOrderService")
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {
	 @Autowired
	   private UserOrderMapper userOrderMapper;
	@Override
	public List<UserOrder> listOrd(String userId, Integer status) {
		return userOrderMapper.listOrd(userId,status);
	}
}
