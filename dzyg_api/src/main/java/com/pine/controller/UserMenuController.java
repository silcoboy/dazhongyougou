package com.pine.controller;

import java.util.HashMap;
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
import com.pine.entity.UserMenu;
import com.pine.service.UserMenuService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/userMenu")
public class UserMenuController {
    @Autowired
    UserMenuService userMenuService; 
     
    
    
    /**
     * 
     * @param pageNo    查询页数
     * @param pageSize  页面大小
     * @return
     */
    @GetMapping("/getUserMenu")
    public ResultJson<Object> getUserMenu(@RequestParam(required = false) String userName,
    		String title,
                                        Integer pageNo, Integer pageSize) throws InterruptedException {
        //参数一是当前页，参数二是每页个数
        IPage<UserMenu> userPage = new Page<>(pageNo, pageSize);
        //查询条件(可选)
        QueryWrapper<UserMenu> wrapper = new QueryWrapper<>();
        if (!Objects.equals(userName, "") && userName != null) wrapper.like("user_name", userName);
        if (!Objects.equals(title, "") && title != null) wrapper.like("title", title);
        wrapper.orderByDesc("update_time");
        userPage = userMenuService.page(userPage, wrapper);
        // 创建分页结果集
        Map<Object, Object> result = new HashMap<>();
        result.put("list", userPage.getRecords());
        result.put("total", userPage.getTotal());
   	 return ResultJson.ok(result);
    }
    

    /**
     * 批量删除 
     * @return
     */
    @GetMapping("/delete")
    public ResultJson<Object> delete(Integer id) {
    	userMenuService.removeById(id);
        return ResultJson.ok(true);
    }
    
     
  
  
}
