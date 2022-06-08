package com.pine.controller;

import java.util.ArrayList;
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
import com.pine.base.utils.BeanCopyConverter;
import com.pine.base.utils.DateUtil;
import com.pine.entity.GoodsInfo;
import com.pine.entity.UserCart;
import com.pine.entity.UserInfo;
import com.pine.entity.UserOrder;
import com.pine.service.AdminInfoService;
import com.pine.service.GoodsInfoService;
import com.pine.service.UserCartService;
import com.pine.service.UserOrderService;
import com.pine.service.UserService;

import lombok.extern.slf4j.Slf4j;
/**
 * 用户订单
 *
 */
@RestController
@Slf4j
@RequestMapping("/api/userOrder")
public class UserOrderController {

    @Autowired
    AdminInfoService adminInfoService;
    @Autowired
    UserOrderService userOrderService;
    @Autowired
    UserService userService;
    @Autowired
   GoodsInfoService goodsInfoService;
    @Autowired
    UserCartService userCartService;
    
    /**
     * @param name  模糊查询
     * @param pageNo   查询页码
     * @param pageSize 页面大小
     * @return
     */
    @GetMapping("/getAllOrder")
    public ResultJson<Object> getAllOrder(
    		@RequestParam(required = false, name = "consignee") String consignee,
    		String orderSn,
    		Integer status,
                                             Integer pageNo, Integer pageSize ) {
        //参数一是当前页，参数二是每页个数
        IPage<UserOrder> infoIPage = new Page<>(pageNo, pageSize);
        //查询条件(可选)
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        if (!Objects.equals(orderSn, "") && orderSn != null) wrapper.like("order_sn", orderSn);
        if (!Objects.equals(consignee, "") && consignee != null) wrapper.like("consignee", consignee);
        if(status!=null) {
        	wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        infoIPage = userOrderService.page(infoIPage, wrapper);
        List<Map<String, Object>> list = new ArrayList< >();
         for(UserOrder info:infoIPage.getRecords()){
 			Map<String, Object> map = BeanCopyConverter.parserToMap(info); 
 			map.put("createTime", info.getCreateTime());
 			
 			
 			if(info.getUserId()!=null) {
 				UserInfo user=userService.getById(info.getUserId());
 				if(user!=null) {
 					map.put("userName",user.getNickname());
 				}
 			}
 			
 			if(info.getTakeTime()!=null) {
				 map.put("takeTime",DateUtil.formatToyyyyMMddHHmmss(info.getTakeTime()));
			}
 			
 			
 			if(info.getGoodsId()!=null) {
 				GoodsInfo room=goodsInfoService.getById(info.getGoodsId());
 				if(room!=null) {
 					map.put("goodsName",room.getName());
 				}
 			}
 			
 			 QueryWrapper<UserCart> cartwrapper = new QueryWrapper<>();
 			cartwrapper.eq("ord_id", info.getId());
 			cartwrapper.eq("goods_id", info.getGoodsId());
 			UserCart  userCart=userCartService.getOne(cartwrapper);
 			if(userCart!=null) {
					map.put("num",userCart.getNum());
			}
 			 
 			list.add(map);
         }
        // 创建分页结果集
        Map<Object, Object> result = new HashMap<>();
        result.put("list",list);
        result.put("total", infoIPage.getTotal());
        return ResultJson.ok(result);
    }
 

    /**
     * 删除
     */
    @GetMapping("/deleteUserOrder")
    public ResultJson<Object> deleteUserOrder(Integer id) {
    	userOrderService.removeById(id);
        return ResultJson.ok(true);
    }
 
    /**
     * 修改状态
     */
    @GetMapping("/updateOrdStatus")
    public ResultJson<Object> updateOrdStatus(Integer id,Integer status) {
    	UserOrder order=new UserOrder();
    	order.setId(id);
    	order.setOrderStatus(status);
    	order.setTakeTime(DateUtil.getEffectiveDate(new Date(),1));
    	userOrderService.updateById(order);
        return ResultJson.ok(true);
    }
     
    /**
     * 修改退款状态
     */
    @GetMapping("/updateRefundStatus")
    public ResultJson<Object> updateRefundStatus(Integer id) {
    	UserOrder order=new UserOrder();
    	order.setId(id);
    	order.setRefundStatus(2);
    	order.setRefundHandleTime(new Date());
    	userOrderService.updateById(order);
        return ResultJson.ok(true);
    }
     
}
