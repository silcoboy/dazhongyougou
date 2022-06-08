package com.pine.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *商品信息
 *
 */
public class GoodsInfo implements Serializable {
 
	private static final long serialVersionUID = 8795269000612683640L;

	private Integer id;
	//分类id
    private String categoryId;
    //分类名称
    private String categoryName;
    //商品名称
    private String name;
    //商品页面商品图片  多个以,隔开
    private String picUrl;
    //概要
    private String resume;
    //商品价格
    private BigDecimal oldPrice;
    //出售价格
    private BigDecimal price;
    
    private Date createTime;

    private Date updateTime; 
	//总库存数量
	private Integer totalNum;
	//当前剩余
	private Integer curNum;
	//总销售数量
	 private Integer totalBuyNum;
	//商品详细介绍
	 private String detail;
	 
	//状态，是否上架
	private Integer status;
	//浏览量
	private Integer browse;
	//是否特价
	private Integer isSpecial;
	//是否秒杀
	private Integer isSkill; 
	//是否推荐
	private Integer isRecommend;
	//是否删除
	private Integer isDeleted;
	
	//明细图片多个以逗号隔开
	private String imgs;
	
	//产地
	private String address;
	
	
	
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	 
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public BigDecimal getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getCurNum() {
		return curNum;
	}
	public void setCurNum(Integer curNum) {
		this.curNum = curNum;
	}
	public Integer getTotalBuyNum() {
		return totalBuyNum;
	}
	public void setTotalBuyNum(Integer totalBuyNum) {
		this.totalBuyNum = totalBuyNum;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getBrowse() {
		return browse;
	}
	public void setBrowse(Integer browse) {
		this.browse = browse;
	}
	public Integer getIsSpecial() {
		return isSpecial;
	}
	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}
	public Integer getIsSkill() {
		return isSkill;
	}
	public void setIsSkill(Integer isSkill) {
		this.isSkill = isSkill;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
 
	
}
