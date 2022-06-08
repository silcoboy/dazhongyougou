package com.pine.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pine.entity.UserCart;

@Mapper
public interface UserCartMapper extends BaseMapper<UserCart> {

}
