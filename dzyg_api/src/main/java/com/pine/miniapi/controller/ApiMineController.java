package com.pine.miniapi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pine.entity.MenuComment;
import com.pine.entity.StoreInfo;
import com.pine.entity.UserInfo;
import com.pine.entity.UserMenu;
import com.pine.entity.UserOrder;
import com.pine.miniapi.vo.ResultVO;
import com.pine.service.MenuCommentService;
import com.pine.service.StoreInfoService;
import com.pine.service.UserMenuService;
import com.pine.service.UserOrderService;
import com.pine.service.UserService;

@RestController
@RequestMapping("/miniapi/")
public class ApiMineController {
	@Autowired
	private UserService userService;
	 
	@Autowired
	private UserOrderService userOrderService;
	@Autowired
	private StoreInfoService storeInfoService;
	@Autowired
	private UserMenuService userMenuService;
	@Autowired
	private MenuCommentService menuCommentService;
	
	
	@GetMapping("/mine")
	public ResultVO<Map<String, Object>> mine(HttpServletRequest request) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		String userId = request.getHeader("token");
		UserInfo userInfo=userService.getById(userId);
		Map<String, Object> data = new HashMap<>();
		data.put("nickname", userInfo.getNickname());
		data.put("headImg", userInfo.getAvatar()==null?"":userInfo.getAvatar());
		data.put("mobile", userInfo.getMobile());
		
		//获取门店
		 QueryWrapper<UserOrder> ordWrapper = new QueryWrapper<>();
		 ordWrapper.eq("user_id", userId); 
		 ordWrapper.last("limit 1");
		 UserOrder order= userOrderService.getOne(ordWrapper);
		 if(order==null) {
			 QueryWrapper<StoreInfo> storeWrapper = new QueryWrapper<>();
			 storeWrapper.orderByAsc("id");
			 storeWrapper.last("limit 1");
			 data.put("storeInfo", storeInfoService.getOne(storeWrapper));
		 }else {
			 data.put("storeInfo", storeInfoService.getById(order.getTakeStoreId()));
		 }
		return rs.success(data);
	}
	
	 
			
	
	@GetMapping("/updateInfo")
	public ResultVO<Integer> updateInfo(
			@NotNull(message = "手机号不能为空") String mobile,
			String avatar ,HttpServletRequest request) {
		String userId = request.getHeader("token");
		ResultVO<Integer> rs = new ResultVO<>();
		 UserInfo addUser=new UserInfo();
		 addUser.setMobile(mobile);
		 addUser.setAvatar(avatar);
		 addUser.setId(Integer.parseInt(userId));
		 userService.updateById(addUser);
		return rs.success(addUser.getId());
	}
	 
	
	//菜谱列表
	@GetMapping("/menuList") 
	public ResultVO<Map<String, Object>> menuList(HttpServletRequest request,String name) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		 QueryWrapper<UserMenu> wrapper = new QueryWrapper<>();
    	 if(!StringUtils.isEmpty(name)) {
    		 wrapper.like("title", name);
    	 }
    	 wrapper.orderByDesc("create_time");
    	 List<UserMenu> userMenuList= userMenuService.list(wrapper);
		 data.put("userMenuList",userMenuList ); 
		 return rs.success(data);
	}
	
	//发布菜谱
	@GetMapping("/addMenu") 
	public ResultVO<Integer> addMenu(HttpServletRequest request,
			String title,String picUrl,String content) {
		ResultVO<Integer> rs = new ResultVO<>();
		String userId = request.getHeader("token");
		UserInfo userInfo=userService.getById(userId);
		UserMenu menu=new UserMenu();
		menu.setTitle(title);
		menu.setPicUrl(picUrl);
		menu.setContent(content);
		menu.setBrowse(0);
		menu.setUserId(userInfo.getId());
		menu.setUserAvatar(userInfo.getAvatar());
		menu.setUserName(userInfo.getNickname());
		menu.setComment(0);
		menu.setCreateTime(new Date());
		menu.setUpdateTime(new Date());
		userMenuService.save(menu);
		 return rs.success();
	}
	
	
	@GetMapping("/menuDetail") 
	public ResultVO<Map<String, Object>> menuDetail(HttpServletRequest request,Integer id) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		UserMenu menu=userMenuService.getById(id);
		menu.setBrowse(menu.getBrowse()+1);
		userMenuService.updateById(menu);
		 data.put("info",menu ); 
		 
		 Integer selfFlag=0;
		 String userId = request.getHeader("token");
		 if(userId!=null && menu!=null && userId.equals(menu.getUserId().toString())) {
			 selfFlag=1;
		 }
		 data.put("selfFlag",selfFlag ); 
		 
		 //获取评论信息
		 
		 
		 QueryWrapper<MenuComment> wrapper = new QueryWrapper<>();
		 wrapper.eq("menu_id", id);
    	 wrapper.orderByDesc("create_time");
    	 List<MenuComment> list= menuCommentService.list(wrapper);
    	 data.put("list",list); 
		 
		 return rs.success(data);
	}
	
	@GetMapping("/addMenuComment") 
	public ResultVO<Integer> addMenuComment(HttpServletRequest request, Integer id,String content) {
		ResultVO<Integer> rs = new ResultVO<>();
		String userId = request.getHeader("token");
		UserInfo userInfo=userService.getById(userId);
		MenuComment menu=new MenuComment();
		menu.setContent(content);
		menu.setMenuId(id);
		menu.setUserId(userInfo.getId());
		menu.setUserAvatar(userInfo.getAvatar());
		menu.setUserName(userInfo.getNickname());
		menu.setCreateTime(new Date()); 
		menuCommentService.save(menu);
		
		//修改评论数
		UserMenu menuInfo=userMenuService.getById(id);
		menuInfo.setComment(menuInfo.getComment()+1);
		userMenuService.updateById(menuInfo);
		 return rs.success();
	}
	
	@GetMapping("/delMenuComment") 
	public ResultVO<Integer> delMenuComment(HttpServletRequest request, Integer id,String content) {
		ResultVO<Integer> rs = new ResultVO<>();
		MenuComment menu= menuCommentService.getById(id);
		menuCommentService.removeById(id);
		//修改评论数
		UserMenu menuInfo=userMenuService.getById(menu.getMenuId());
		menuInfo.setComment(menuInfo.getComment()-1);
		userMenuService.updateById(menuInfo);
		 return rs.success();
	}
	
	
	
	
	
}
