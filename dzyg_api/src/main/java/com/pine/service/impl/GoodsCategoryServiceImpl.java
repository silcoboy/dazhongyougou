package com.pine.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.entity.GoodsCategory;
import com.pine.mapper.GoodsCategoryMapper;
import com.pine.service.GoodsCategoryService;
/**
 */
@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {
}
