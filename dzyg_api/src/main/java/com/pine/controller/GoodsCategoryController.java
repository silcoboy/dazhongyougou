package com.pine.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pine.base.result.ResultJson;
import com.pine.entity.GoodsCategory;
import com.pine.service.AdminInfoService;
import com.pine.service.GoodsCategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/category")
public class GoodsCategoryController {
   
    @Autowired
    AdminInfoService adminInfoService;

    @Autowired
    GoodsCategoryService goodsCategoryService;
    
    /**
     * @param content  分类模糊查询
     * @param pageNo   查询页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/getAllCategory")
    public ResultJson<Object> getAllCategory(
    		@RequestParam(required = false, name = "name") String name,Integer pid,
                                             Integer pageNo, Integer pageSize ) {
        //参数一是当前页，参数二是每页个数
        IPage<GoodsCategory> infoIPage = new Page<>(pageNo, pageSize);
        //查询条件(可选)
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        if (!Objects.equals(name, "") && name != null) wrapper.like("name", name);
        if(pid!=null) {
        	wrapper.eq("pid", pid);
        }else {
        	wrapper.eq("pid", 0);
        }
        
        infoIPage = goodsCategoryService.page(infoIPage, wrapper);
        // 创建分页结果集
        Map<Object, Object> result = new HashMap<>();
        result.put("list", infoIPage.getRecords());
        result.put("total", infoIPage.getTotal());
        return ResultJson.ok(result);
    }
 
    /**
     * 批量删除
     */
    @GetMapping("/deleteCategory")
    public ResultJson<Object> deleteCategory(Integer id) {
        //转换成数组 需要操作的用户的id数组
        goodsCategoryService.removeById(id);
        return ResultJson.ok(true);
    }

    @PostMapping("/saveCategory")
    public ResultJson<Object> saveCategory(@RequestBody GoodsCategory info) {
    	info.setCreateTime(new Date());
    	  if(info.getId()!=null) {
    		  boolean save = goodsCategoryService.updateById(info);
        	  return ResultJson.ok(save);
    	  }else {
    		  info.setPid(0);
    		  boolean save = goodsCategoryService.save(info);
        	  return ResultJson.ok(save);
    	  }
    	  
    }
    
    @GetMapping("/getList")
    public List<GoodsCategory> getList(){
    	 QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
    	 wrapper.orderByAsc("sort");
    	return goodsCategoryService.list(wrapper);
    }
    
}
