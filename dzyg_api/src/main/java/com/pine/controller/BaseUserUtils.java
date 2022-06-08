package com.pine.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pine.base.utils.JwtUtils;
import com.pine.entity.AdminInfo;
import com.pine.service.AdminInfoService;
import com.pine.service.impl.JwtAdminDetailsServiceImpl;

@Component
public class BaseUserUtils {
	   

    @Autowired
    AdminInfoService adminInfoService;
	@Autowired
	private JwtUtils jwtTokenUtil;
	@Autowired
	private JwtAdminDetailsServiceImpl jwtUserDetailsService;
		
	public boolean checkTokenInfo(HttpServletRequest request) {
		String username = null;
		String jwtToken = null;
		 String requestTokenHeader = request.getHeader("authorization");
		// JWT报文表头的格式是"Bearer token". 去除"Bearer ",直接获取token
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("pine ")) {
			jwtToken = requestTokenHeader.substring(5);
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		}
		UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
    	Boolean b=jwtTokenUtil.validateToken(jwtToken, userDetails);
    	return b;
	}

	
	public AdminInfo getUserInfo(HttpServletRequest request) {
		String username = null;
		String jwtToken = null;
		 String requestTokenHeader = request.getHeader("authorization");
		// JWT报文表头的格式是"Bearer token". 去除"Bearer ",直接获取token
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("pine ")) {
			jwtToken = requestTokenHeader.substring(5);
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		}
		  QueryWrapper<AdminInfo> wrapper = new QueryWrapper<>();
	        wrapper.eq("user_name", username);
	        AdminInfo user = adminInfoService.getOne(wrapper);
		return user;
	}
 
 

}
