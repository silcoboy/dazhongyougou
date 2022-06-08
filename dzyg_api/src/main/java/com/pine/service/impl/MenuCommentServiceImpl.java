package com.pine.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pine.entity.MenuComment;
import com.pine.mapper.MenuCommentMapper;
import com.pine.service.MenuCommentService;
/**
 */
@Service("menuCommentService")
public class MenuCommentServiceImpl extends ServiceImpl<MenuCommentMapper, MenuComment> implements MenuCommentService {
}
