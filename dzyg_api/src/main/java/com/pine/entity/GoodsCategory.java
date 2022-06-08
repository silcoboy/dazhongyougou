package com.pine.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 商品分类表 po
 *  
 */
public class GoodsCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/***商品分类表ID*/
	private Integer id;
	/***商品分类表PID*/
	private Integer pid;
	/***分类名称*/
	private String name;
	/***图标*/
	private String icon;
	/***排序*/
	private Integer sort;
	/***是否推荐*/
	private Integer isShow;
	/***添加时间*/
	private Date createTime;
	/****/
	private Date updateTime;
	/***删除状态*/
	private Integer isDeleted;

	public void setId(Integer id) {
		this.id = id;
	}
	 
	public Integer getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
	public String getName() {
		return name;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	 
	public Integer getSort() {
		return sort;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	 
	public Integer getIsShow() {
		return isShow;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
	public Date getCreateTime() {
		return createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	 
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	 
	public Integer getIsDeleted() {
		return isDeleted;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}
