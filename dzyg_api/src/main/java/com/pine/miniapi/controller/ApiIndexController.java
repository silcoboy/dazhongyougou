
package com.pine.miniapi.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pine.base.utils.BeanCopyConverter;
import com.pine.base.utils.DateUtil;
import com.pine.entity.GoodsCategory;
import com.pine.entity.GoodsInfo;
import com.pine.entity.StoreInfo;
import com.pine.entity.UserCart;
import com.pine.entity.UserInfo;
import com.pine.entity.UserOrder;
import com.pine.miniapi.enums.OrdStatusEnum;
import com.pine.miniapi.vo.ResultVO;
import com.pine.service.GoodsCategoryService;
import com.pine.service.GoodsInfoService;
import com.pine.service.StoreInfoService;
import com.pine.service.UserCartService;
import com.pine.service.UserOrderService;
import com.pine.service.UserService;
 
@RestController
@RequestMapping("/miniapi/")
public class ApiIndexController {
   
	@Autowired
	private GoodsInfoService goodsInfoService;
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserCartService userCartService;
	@Autowired
	private UserOrderService userOrderService;
	@Autowired
	private StoreInfoService storeInfoService;
	
	
	@GetMapping("/indexInit") 
	public ResultVO<Map<String, Object>> getIndexInit(HttpServletRequest request) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		 QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
    	 wrapper.orderByAsc("sort");
    	 wrapper.eq("is_show", 1);
		 data.put("iconList", goodsCategoryService.list(wrapper)); 
		 
		 //推荐商品
		 QueryWrapper<GoodsInfo> recommendWrapper = new QueryWrapper<>();
		 recommendWrapper.eq("is_recommend", 1);
		 data.put("recommendList", goodsInfoService.list(recommendWrapper));
		 
		//秒杀
		 QueryWrapper<GoodsInfo> skillWrapper = new QueryWrapper<>();
		 skillWrapper.eq("is_skill", 1);
		 data.put("skillList", goodsInfoService.list(skillWrapper));
		 
		//特价
		 QueryWrapper<GoodsInfo> specialWrapper = new QueryWrapper<>();
		 specialWrapper.eq("is_special", 1);
		 data.put("specialList", goodsInfoService.list(specialWrapper));
		 
		//全部
		 data.put("allList", goodsInfoService.list(null));
		 
	 
		return rs.success(data);
	}
	
	@GetMapping("/getListInfo") 
	public ResultVO<Map<String, Object>> getListInfo(HttpServletRequest request,Integer type) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		 QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<>();
    	 wrapper.orderByAsc("sort");
    	 List<GoodsCategory> typeList= goodsCategoryService.list(wrapper);
		 data.put("typeList",typeList ); 
		 
		 QueryWrapper<GoodsInfo> specialWrapper = new QueryWrapper<>();
		 specialWrapper.eq("category_id", type==null?typeList.get(0).getId():type);
		 data.put("goodsList", goodsInfoService.list(specialWrapper));
		 
		 
		 return rs.success(data);
	}
	

	@GetMapping("/getAllListInfo") 
	public ResultVO<Map<String, Object>> getAllListInfo(HttpServletRequest request
			,String name,Integer type) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		 
		 QueryWrapper<GoodsInfo> specialWrapper = new QueryWrapper<>();
		 if(!StringUtils.isEmpty(name)) {
			 specialWrapper.like("name",name);
		 }
		 if(type!=null) {
			 specialWrapper.eq("category_id",type);
		 }
		 data.put("goodsList", goodsInfoService.list(specialWrapper));
		 
		 
		 return rs.success(data);
	}
	
	//商品详情
	@GetMapping("/goodsDetail") 
	public ResultVO<Map<String, Object>> goodsDetail(HttpServletRequest request,Integer id) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		GoodsInfo goodsInfo= goodsInfoService.getById(id);
		
		Map<String, Object> info = BeanCopyConverter.parserToMap(goodsInfo);
		if(!StringUtils.isEmpty(goodsInfo.getImgs())) {
//			info.put("imgs", goodsInfo.getImgs().split(","));
		}
		if(!StringUtils.isEmpty(goodsInfo.getPicUrl())) {
//			info.put("picUrl", goodsInfo.getPicUrl().split(","));
		}
	 	data.put("info",info ); 
		 return rs.success(data);
	}
	
	
	//添加购物车
	@GetMapping("/addCart") 
	public ResultVO<Integer> addCart(HttpServletRequest request,Integer goodsId) {
		ResultVO<Integer> rs = new ResultVO<>();
		String userId = request.getHeader("token");
		
		 QueryWrapper<UserCart> cartWrapper = new QueryWrapper<>();
		 cartWrapper.eq("user_id", userId);
		 cartWrapper.eq("goods_id", goodsId);
		 cartWrapper.isNull("ord_id");
		UserCart selCart=userCartService.getOne(cartWrapper);
		
		GoodsInfo goods=goodsInfoService.getById(goodsId);
		if(selCart!=null) {
			UserCart cart=new UserCart();
			cart.setNum(selCart.getNum()+1);
			cart.setAmount(goods.getPrice().add(selCart.getAmount()));
			cart.setId(selCart.getId());
			cart.setUpdateTime(new Date());
			userCartService.updateById(cart);
		}else{
			UserCart cart=new UserCart();
			cart.setUserId(Integer.parseInt(userId));
			cart.setGoodsId(goodsId);
			cart.setNum(1);
			cart.setCreateTime(new Date());
			cart.setUpdateTime(new Date());
			cart.setAmount(goods.getPrice());
			userCartService.save(cart);
		}
		return rs.success();
	}
	
	//加减购物车数量
	@GetMapping("/addCartNum") 
	public ResultVO<Integer> addCartNum(HttpServletRequest request,Integer id,Integer num) {
		ResultVO<Integer> rs = new ResultVO<>();
		UserCart selCart=userCartService.getById(id);
		
		Integer curNum=selCart.getNum()+num;
		if(curNum==0) {
			userCartService.removeById(id);
		}else {
			selCart.setNum(curNum);
			userCartService.updateById(selCart);
		}
		
		
		return rs.success();
	}
	
	//购物车
	@GetMapping("userCartList")
	@ResponseBody
	public ResultVO<Map<String,Object>> userCartList(HttpServletRequest request) {
		ResultVO<Map<String,Object>> rs = new ResultVO<>();
		String userId = request.getHeader("token");
		Map<String,Object> data=new HashMap<>();
		 QueryWrapper<UserCart> cartWrapper = new QueryWrapper<>();
		 cartWrapper.eq("user_id", userId);
		 cartWrapper.isNull("ord_id");
		List<UserCart> list=userCartService.list(cartWrapper);
		BigDecimal amount=BigDecimal.ZERO;
		List<Map<String,Object>> listInfo=new ArrayList<>();
		for(UserCart info:list) {
			Map<String, Object> map = BeanCopyConverter.parserToMap(info); 
			map.put("createTime", DateUtil.formatToYYYYMMDDMMHHSS(info.getCreateTime()));
			GoodsInfo goods=goodsInfoService.getById(info.getGoodsId());
			map.put("goodsName", goods.getName());
			map.put("goodsPic", goods.getPicUrl());
			map.put("goodsPrice", goods.getPrice());
			map.put("takeTime", DateUtil.getEffectiveDate(new Date(),1));
			amount=amount.add(info.getAmount());
			listInfo.add(map);
		}
		data.put("amount", amount);
		data.put("list",listInfo);
		return rs.success(data);
	}
	
	//我的订单
	@GetMapping("/myOrder")
	public ResultVO<Map<String, Object>> myOrder(HttpServletRequest request,Integer status) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		String userId = request.getHeader("token");
		List<UserOrder> list=userOrderService.listOrd(userId,(status==null||status==0)?null:status);
		List<Map<String,Object>> listInfo=new ArrayList<>();
		for(UserOrder ord:list) {
			Map<String, Object> map = BeanCopyConverter.parserToMap(ord); 
			map.put("createTime", DateUtil.formatToYYYYMMDDMMHHSS(ord.getCreateTime()));
			map.put("statusName", OrdStatusEnum.getText(ord.getOrderStatus()).getValue());
			map.put("takeTime", ord.getTakeTime()==null?DateUtil.getEffectiveDate(new Date(),1):DateUtil.formatToYYYYMMDDMMHHSS(ord.getTakeTime()));
			
			//通过订单号获取商品信息
			 QueryWrapper<UserOrder> cartWrapper = new QueryWrapper<>();
			 cartWrapper.eq("order_sn", ord.getOrderSn());
				List<UserOrder> orders=userOrderService.list(cartWrapper);
//				List<GoodsInfo> goodsList=new ArrayList<GoodsInfo>();
				List<Map<String,Object>> goodsList=new ArrayList<>();
				for(UserOrder info:orders) {
					Map<String, Object> map2 = BeanCopyConverter.parserToMap(info);
					GoodsInfo goods=goodsInfoService.getById(info.getGoodsId());
					map2.put("goodsName", goods.getName());
					map2.put("goodsPic", goods.getPicUrl());
					map2.put("goodsPrice", goods.getPrice());
					goodsList.add(map2);
				}
			 	map.put("goodsList", goodsList);
			    listInfo.add(map);
			    data.put("list", listInfo);
		}
		return rs.success(data);
	}
	
	//订单详情
	@GetMapping("/orderDetail")
	public ResultVO<Map<String, Object>> orderDetail(HttpServletRequest request,
			String id) 
 	{
 		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		 QueryWrapper<UserOrder> cartWrapper = new QueryWrapper<>();
		 cartWrapper.eq("order_sn", id);
		List<UserOrder> orders=userOrderService.list(cartWrapper);
		Integer amount=0;
		String createTime="";
		Integer num=0;
		String statusName="";
		Integer status=0;
		List<Map<String,Object>> listInfo=new ArrayList<>();
		for(UserOrder info:orders) {
			status=info.getOrderStatus();
			Map<String, Object> map = BeanCopyConverter.parserToMap(info); 
			statusName= OrdStatusEnum.getText(info.getOrderStatus()).getValue();
			createTime=DateUtil.formatToYYYYMMDDMMHHSS(info.getCreateTime());
			map.put("createTime",createTime );
			map.put("statusName",statusName);
			GoodsInfo goods=goodsInfoService.getById(info.getGoodsId());
			map.put("goodsName", goods.getName());
			map.put("goodsPic", goods.getPicUrl());
			map.put("goodsPrice", goods.getPrice());
			map.put("takeTime", info.getTakeTime()==null?DateUtil.getEffectiveDate(new Date(),1):DateUtil.formatToYYYYMMDDMMHHSS(info.getTakeTime()));
			amount=amount+info.getAmount().intValue();
			num=num+info.getNum();
			listInfo.add(map);
		}
		data.put("createTime", createTime);
		data.put("amount", amount);
		data.put("status", status);
		data.put("statusName", statusName);
		data.put("num", num);
		data.put("ordSn", id);
		data.put("store", storeInfoService.getById(orders.get(0).getTakeStoreId()));
		data.put("list",listInfo);
		 
		
		return rs.success(data);
	}
			
	
	//提交购物车支付
	@GetMapping("submitCart")
	@ResponseBody
	public ResultVO<Integer> submitCart(HttpServletRequest request,
			Integer storeId,String ids,Integer status
			) {
		ResultVO<Integer> rs = new ResultVO<>();
		String userId = request.getHeader("token");
		UserInfo user=userService.getById(userId);
		 QueryWrapper<UserCart> cartWrapper = new QueryWrapper<>();
		 cartWrapper.isNull("ord_id");
		 cartWrapper.in("id", Arrays.asList(ids.split(",")));
		List<UserCart> list=userCartService.list(cartWrapper);
		 
		//获取自取点
		StoreInfo storeInfo= storeInfoService.getById(storeId);
		String ordId=System.currentTimeMillis()+"";
		for(UserCart cart:list) {
			UserOrder order=new UserOrder();
			order.setGoodsId(cart.getGoodsId());
			order.setUserId(Integer.parseInt(userId));
			order.setOrderSn(ordId);
			order.setOrderStatus(status);
			order.setConsignee(user.getNickname());
			order.setMobile(user.getMobile());
			order.setAmount(cart.getAmount().multiply(new BigDecimal(cart.getNum())));
			order.setFee(BigDecimal.ZERO);
			order.setSource(0);
			order.setTakeTime(DateUtil.getEffectiveDate(new Date(),1));
			order.setTakeStoreId(storeId);
			order.setTakeStoreName(storeInfo.getStoreName());
			order.setCreateTime(new Date());
			order.setCartId(cart.getId());
			order.setNum(cart.getNum());
			userOrderService.save(order);
			
			cart.setOrdId(order.getId());
			userCartService.updateById(cart);
			
		    //修改商品库存数量
			  GoodsInfo goods=  goodsInfoService.getById(order.getGoodsId());
			  goods.setTotalBuyNum(goods.getTotalBuyNum()+order.getNum());
			  goods.setCurNum(goods.getCurNum()-order.getNum());
			  goodsInfoService.updateById(goods);
		}
		return rs.success();
	}
	
	
	//待付支付订单
	@GetMapping("payOrd")
	@ResponseBody
	public ResultVO<Integer> payOrd(HttpServletRequest request,
			String ordSn,Integer status
			) {
		ResultVO<Integer> rs = new ResultVO<>();
		 QueryWrapper<UserOrder> cartWrapper = new QueryWrapper<>();
		 cartWrapper.eq("order_sn", ordSn);
		List<UserOrder> list=userOrderService.list(cartWrapper);
		for(UserOrder order:list) {
			if(status==2) {
				order.setTakeTime(DateUtil.getEffectiveDate(new Date(),1));
			    order.setOrderStatus(2);
			    userOrderService.updateById(order);
			    
			    //修改商品库存数量
			  GoodsInfo goods=  goodsInfoService.getById(order.getGoodsId());
			  goods.setTotalBuyNum(goods.getTotalBuyNum()+order.getNum());
			  goods.setCurNum(goods.getCurNum()-order.getNum());
			  goodsInfoService.updateById(goods);
			  
			  
			}else {
				userOrderService.removeById(order.getId());
			}
			
		}
		return rs.success();
	}
	
	//订单取货
	@GetMapping("takeOrd")
	@ResponseBody
	public ResultVO<Integer> takeOrd(HttpServletRequest request,
			String ordSn
			) {
		ResultVO<Integer> rs = new ResultVO<>();
		 QueryWrapper<UserOrder> cartWrapper = new QueryWrapper<>();
		 cartWrapper.eq("order_sn", ordSn);
		List<UserOrder> list=userOrderService.list(cartWrapper);
		for(UserOrder order:list) {
			    order.setOrderStatus(4);
			    userOrderService.updateById(order);
		}
		return rs.success();
	}
	
	  
	//自提点
	@GetMapping("/listStore") 
	public ResultVO<Map<String, Object>> listStore(HttpServletRequest request) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		data.put("storeList",storeInfoService.list() ); 
		 return rs.success(data);
	}
	
	
	//售后订单
	@GetMapping("/shouhouOrder")
	public ResultVO<Map<String, Object>> shouhouOrder(HttpServletRequest request,Integer status) {
		ResultVO<Map<String, Object>> rs = new ResultVO<>();
		Map<String, Object> data = new HashMap<>();
		String userId = request.getHeader("token");
		 QueryWrapper<UserOrder> cartWrapper = new QueryWrapper<>();
		 cartWrapper.eq("order_status", 4);
		 cartWrapper.eq("user_id", userId);
		 if(status!=null) {
			 cartWrapper.eq("refund_status", status);
		 }
		List<UserOrder> list=userOrderService.list(cartWrapper);
		List<Map<String,Object>> listInfo=new ArrayList<>();
		for(UserOrder ord:list) {
			Map<String, Object> map = BeanCopyConverter.parserToMap(ord); 
			map.put("createTime", DateUtil.formatToYYYYMMDDMMHHSS(ord.getCreateTime()));
			map.put("takeTime", DateUtil.formatToYYYYMMDDMMHHSS(ord.getTakeTime()));
			GoodsInfo goods=goodsInfoService.getById(ord.getGoodsId());
			map.put("goods", goods);
			listInfo.add(map);
		}
		data.put("list", listInfo);
		return rs.success(data);
	}
	
	//退回申请
	@GetMapping("refundApply")
	@ResponseBody
	public ResultVO<Integer> refundApply(HttpServletRequest request,
			Integer id,String content
			) {
		ResultVO<Integer> rs = new ResultVO<>();
		UserOrder order=userOrderService.getById(id);
		order.setRefundApplyTime(new Date());
		order.setRefundRemark(content);
		order.setRefundStatus(1);
		userOrderService.updateById(order);
		return rs.success();
	}
	
	  
	
}
