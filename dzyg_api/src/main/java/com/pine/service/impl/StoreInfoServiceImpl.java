package com.pine.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.entity.StoreInfo;
import com.pine.mapper.StoreInfoMapper;
import com.pine.service.StoreInfoService;
/**
 */
@Service("storeInfoService")
public class StoreInfoServiceImpl extends ServiceImpl<StoreInfoMapper, StoreInfo> implements StoreInfoService {
}
