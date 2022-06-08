package com.pine.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pine.base.result.ResultCode;
import com.pine.base.result.ResultJson;
import com.pine.base.utils.ResponseUserToken;
import com.pine.entity.AdminInfo;
import com.pine.entity.Role;
import com.pine.service.AdminInfoService;
import com.pine.service.RoleService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/api")
public class CommonController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    AdminInfoService adminInfoService;
    
    @Autowired
    RoleService roleService;
     
    @Resource
    BaseUserUtils baseUserUtils; 

    /**
     * 获取token
     * @param user
     * @return
     */
    @SuppressWarnings("unchecked")
	@PostMapping("/login")
    public ResultJson<ResponseUserToken> login(String username,String password) {

        final ResponseUserToken response = adminInfoService.login(username, password);
        
       
        AdminInfo user =adminInfoService.getByUserName( response.getUserDetail().getUsername());
        return ResultJson.ok(response);
    }



    /**
     * @param request
     * @return
     */
  @GetMapping("/checkToken")
    public ResultJson checkToken(HttpServletRequest request) {
    	if(baseUserUtils.checkTokenInfo(request)) {
    		
    		AdminInfo user =baseUserUtils.getUserInfo(request);
    		return ResultJson.ok("成功");
    	}else {
    		return ResultJson.failure(ResultCode.LOGIN_ERROR);
    	}
    } 

    @SuppressWarnings("unchecked")
	@GetMapping("/getMenu")
    public ResultJson<String> getMenu(HttpServletRequest request) {
    	   
    	 AdminInfo user =baseUserUtils.getUserInfo(request);
    	 Role role=roleService.getById(user.getRoleId());
    	 return ResultJson.ok(role.getMenuInfo());
    	 
    } 
    
    /**
     * 查询系统存在的所有角色信息
     * @return
     */
    @GetMapping("/getRole")
    public ResultJson<Object> getRole() {
        log.info("执行了===>CommonController中的getRole方法");
        List<Role> userRoles = roleService.list(new QueryWrapper<>());
        return ResultJson.ok(userRoles);
    }
    
    
    
}
