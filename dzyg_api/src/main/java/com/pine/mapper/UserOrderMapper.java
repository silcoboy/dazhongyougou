package com.pine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pine.entity.UserOrder;

@Mapper
public interface UserOrderMapper extends BaseMapper<UserOrder> {


		@Select(" <script>"+
		"  select take_store_name,order_sn,cart_id,sum(amount)amount,sum(num)num,create_time,order_status,take_time from user_order    "
		+ "<where>  "
		+ "<if test='userId != null'> AND user_id=#{userId} </if>"
		+ "<if test='status != null'> AND order_status=#{status} </if>"
		+ "</where>  group by order_sn  "
		+"order by id desc " 
		+ "</script>")
		List<UserOrder> listOrd(@Param("userId") String userId,
				@Param("status")Integer status);

}
