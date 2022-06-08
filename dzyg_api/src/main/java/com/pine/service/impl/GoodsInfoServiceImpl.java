package com.pine.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.entity.GoodsInfo;
import com.pine.mapper.GoodsInfoMapper;
import com.pine.service.GoodsInfoService;
/**
 */
@Service("goodsInfoService")
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {
}
