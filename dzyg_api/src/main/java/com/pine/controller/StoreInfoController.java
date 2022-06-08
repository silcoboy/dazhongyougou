package com.pine.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.pine.entity.StoreInfo;
import com.pine.service.AdminInfoService;
import com.pine.service.StoreInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 门店
 *
 */
@RestController
@Slf4j
@RequestMapping("/api/storeInfo")
public class StoreInfoController {

    @Autowired
    AdminInfoService adminInfoService;
    @Autowired
    StoreInfoService storeInfoService;
    
    /**
     * @param name  模糊查询
     * @param pageNo   查询页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/getAllStore")
    public ResultJson<Object> getAllStore(
    		@RequestParam(required = false, name = "name") String name,
                                             Integer pageNo, Integer pageSize ) {
        //参数一是当前页，参数二是每页个数
        IPage<StoreInfo> infoIPage = new Page<>(pageNo, pageSize);
        //查询条件(可选)
        QueryWrapper<StoreInfo> wrapper = new QueryWrapper<>();
        if (!Objects.equals(name, "") && name != null) wrapper.like("store_name", name);
        infoIPage = storeInfoService.page(infoIPage, wrapper);
        // 创建分页结果集
        Map<Object, Object> result = new HashMap<>();
        result.put("list", infoIPage.getRecords());
        result.put("total", infoIPage.getTotal());
        return ResultJson.ok(result);
    }
 

    /**
     * 删除
     */
    @GetMapping("/deleteStore")
    public ResultJson<Object> deleteStore(Integer id) {
    	storeInfoService.removeById(id);
        return ResultJson.ok(true);
    }
 
    
    @PostMapping("/saveStore")
    public ResultJson<Object> saveStore(@RequestBody StoreInfo info) {
    	info.setCreateTime(new Date());
    	  if(info.getId()!=null) {
    		  boolean save = storeInfoService.updateById(info);
        	  return ResultJson.ok(save);
    	  }else {
    		  info.setCreateTime(new Date());
    		  boolean save = storeInfoService.save(info);
        	  return ResultJson.ok(save);
    	  }
    }
     
}
