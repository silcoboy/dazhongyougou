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
import com.pine.entity.GoodsInfo;
import com.pine.service.AdminInfoService;
import com.pine.service.GoodsCategoryService;
import com.pine.service.GoodsInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 商品
 *
 */
@RestController
@Slf4j
@RequestMapping("/api/goodsInfo")
public class GoodsInfoController {

    @Autowired
    AdminInfoService adminInfoService;

    @Autowired
    GoodsCategoryService goodsCategoryService;
    @Autowired
    GoodsInfoService goodsInfoService;
    
    /**
     * @param content  商品模糊查询
     * @param pageNo   查询页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/getAllGoods")
    public ResultJson<Object> getAllGoods(
    		@RequestParam(required = false, name = "name") String name,
                                             Integer pageNo, Integer pageSize ) {
        //参数一是当前页，参数二是每页个数
        IPage<GoodsInfo> infoIPage = new Page<>(pageNo, pageSize);
        //查询条件(可选)
        QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        if (!Objects.equals(name, "") && name != null) wrapper.like("name", name);
        infoIPage = goodsInfoService.page(infoIPage, wrapper);
        // 创建分页结果集
        Map<Object, Object> result = new HashMap<>();
        result.put("list", infoIPage.getRecords());
        result.put("total", infoIPage.getTotal());
        return ResultJson.ok(result);
    }
 

    /**
     * 批量删除
     */
    @GetMapping("/deleteGoods")
    public ResultJson<Object> deleteGoods(Integer id) {
        //转换成数组 需要操作的用户的id数组
        goodsInfoService.removeById(id);
        return ResultJson.ok(true);
    }
 
    
    @PostMapping("/saveGoods")
    public ResultJson<Object> save(@RequestBody GoodsInfo info) {
    	info.setCreateTime(new Date());
    	GoodsCategory goodsCategory =goodsCategoryService.getById(info.getCategoryId());
    	info.setCategoryName(goodsCategory.getName());
    	
    	  if(info.getId()!=null) {
    		  GoodsInfo goodsInfo=goodsInfoService.getById(info.getId());
    		  info.setTotalNum(goodsInfo.getTotalNum()-goodsInfo.getCurNum()+info.getCurNum());
    		  boolean save = goodsInfoService.updateById(info);
        	  return ResultJson.ok(save);
    	  }else {
    		  info.setTotalBuyNum(0);
    		  info.setTotalNum(info.getCurNum());
    		  boolean save = goodsInfoService.save(info);
        	  return ResultJson.ok(save);
    	  }
    }
    

    @GetMapping("/getList")
    public List<GoodsInfo> getList(){
    	 QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
    	 wrapper.eq("status", 1);
    	return goodsInfoService.list(wrapper);
    }
}
