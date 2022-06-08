package com.pine.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.entity.UserCart;
import com.pine.mapper.UserCartMapper;
import com.pine.service.UserCartService;
/**
 */
@Service("userCartService")
public class UserCartServiceImpl extends ServiceImpl<UserCartMapper, UserCart> implements UserCartService {
}
