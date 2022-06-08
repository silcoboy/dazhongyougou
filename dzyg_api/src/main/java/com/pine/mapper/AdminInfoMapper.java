package com.pine.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pine.entity.AdminInfo;

@Mapper
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {

	@Select("select * from admin_info where user_name = #{value}")
	AdminInfo findByUserName(String userName);


}
