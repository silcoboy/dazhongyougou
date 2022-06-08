package com.pine.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 购物车 po
 *  
 */
public class UserCart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private Integer userId;
	/****/
	private Integer goodsId;
	/****/
	private Integer num;
	/****/
	private BigDecimal amount;
	/****/
	private Date createTime;
	/****/
	private Date updateTime;
	/****/
	private Integer ordId;
	/****/
	private Integer isDeleted;

	public void setId(Integer id) {
		this.id = id;
	}
	 
	public Integer getId() {
		return id;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	 
	public Integer getUserId() {
		return userId;
	}
	 
	public void setNum(Integer num) {
		this.num = num;
	}
	 
	public Integer getNum() {
		return num;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	 
	public BigDecimal getAmount() {
		return amount;
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
	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}
	 
	public Integer getOrdId() {
		return ordId;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	 
	public Integer getIsDeleted() {
		return isDeleted;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	
}
