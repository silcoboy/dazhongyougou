package com.pine.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.entity.UserMenu;
import com.pine.mapper.UserMenuMapper;
import com.pine.service.UserMenuService;
/**
 */
@Service("userMenuService")
public class UserMenuServiceImpl extends ServiceImpl<UserMenuMapper, UserMenu> implements UserMenuService {
}
