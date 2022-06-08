package com.pine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.base.exception.CustomException;
import com.pine.base.result.ResultCode;
import com.pine.base.result.ResultJson;
import com.pine.base.utils.JwtUtils;
import com.pine.base.utils.ResponseUserToken;
import com.pine.entity.AdminInfo;
import com.pine.entity.UserDetail;
import com.pine.mapper.AdminInfoMapper;
import com.pine.service.AdminInfoService;
@Service("adminInfoService")
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo>  
implements AdminInfoService {

    @Autowired
    AdminInfoMapper adminInfoMapper;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public ResponseUserToken login(String username, String password) {
        //用户验证
        final Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token ，查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        //通过工具类生成token
         final String token = "pine "+jwtUtils.generateAccessToken(userDetail);

        //存储token
        jwtUtils.putToken(username, token);
        // 学习 测试用,把用户的信息也返回了
        return new ResponseUserToken(token, userDetail);
    }


    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
        	System.out.println(e.getMessage());
            throw new CustomException(ResultJson.failure(ResultCode.LOGIN_ERROR, e.getMessage()));
        }
    }

	@Override
	public AdminInfo getByUserName(String username) {
		return  adminInfoMapper.findByUserName(username);
	}
}
