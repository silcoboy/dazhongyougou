package com.pine.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pine.base.result.ResultJson;
import com.pine.entity.UserInfo;
import com.pine.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService; 
     
    
    
    /**
     * @param nickName  昵称
     * @param pageNo    查询页数
     * @param pageSize  页面大小
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/getUser")
    public ResultJson<Object> getUser(@RequestParam(required = false) String nickName,
                                        Integer pageNo, Integer pageSize) throws InterruptedException {
        log.info("执行了===>AdminController中的getUser方法");
        //参数一是当前页，参数二是每页个数
        IPage<UserInfo> userPage = new Page<>(pageNo, pageSize);
        //查询条件(可选)
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        if (!Objects.equals(nickName, "") && nickName != null) wrapper.like("nickname", nickName);
	 
      //  wrapper.orderByDesc("update_time");
        userPage = userService.page(userPage, wrapper);
        List<UserInfo> userInfos = userPage.getRecords();
        // 创建分页结果集
        Map<Object, Object> result = new HashMap<>();
        result.put("users", userInfos);
        result.put("total", userPage.getTotal());
   	 return ResultJson.ok(result);
    }
    

    /**
     * 批量删除 
     * @return
     */
    @GetMapping("/delete")
    public ResultJson<Object> delete(@RequestParam(name = "ids") String idsList) {
        log.info("执行了===>UserController中的delete方法");
        //转换成数组 需要操作的用户的id数组
        String[] ids = idsList.split(",");
        for (String id : ids) {
        	userService.removeById(Integer.parseInt(id));
        }
        return ResultJson.ok(true);
    }
    
    /**
     * 修改状态
     * @return
     */
    @GetMapping("/updateStatus")
    public ResultJson<Object> updateStatus(Integer id,Integer status) {
        log.info("执行了===>UserController中的updateStatus方法");
        //转换成数组 需要操作的用户的id数组
        UserInfo userInfo =new UserInfo();
    	userInfo.setId(id);
    	userInfo.setStatus(status);
    	userInfo.setUpdateTime(new Date());
    	userService.updateById(userInfo);
        return ResultJson.ok(true);
    }
  
  
}
