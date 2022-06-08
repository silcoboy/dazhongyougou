package com.pine.miniapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pine.entity.UserInfo;
import com.pine.miniapi.vo.ResultVO;
import com.pine.service.UserService;
 

@RestController
@RequestMapping("/miniapi/")
public class ApiLoginController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/loginWx")
	public ResultVO<Integer> loginWx(String nickname,String avatar) {
		ResultVO<Integer> rs = new ResultVO<>();
		UserInfo userInfo = userService.getByNickname(nickname);
		 
		if (userInfo == null) {
			UserInfo user=new UserInfo();
			user.setAvatar(avatar);
			user.setNickname(nickname);
			user.setStatus(1);
			userService.save(user);
			return rs.success(user.getId());
		}
		return rs.success(userInfo.getId());
	}
	

}
