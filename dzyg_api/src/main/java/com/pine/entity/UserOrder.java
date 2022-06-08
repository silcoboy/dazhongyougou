package com.pine.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 订单表 po
 *  
 */
public class UserOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/***用户表的用户ID*/
	private Integer userId;
	/***商品ID*/
	private Integer goodsId;
 
	/***订单编号*/
	private String orderSn;
	/***订单状态 OrdStatusEnum*/
	private Integer orderStatus;
	/***收货人名称*/
	private String consignee;
	/***收货人手机号*/
	private String mobile;
	/***用户订单留言*/
	private String message;
	/***金额*/
	private BigDecimal amount;
	/***服务费*/
	private BigDecimal fee;
	/***支付状态*/
	private Integer payStatus;
 
	/***付款时间*/
	private Date payTime;
	/***用户确认收货时间*/
	private Date confirmTime;
	/***订单关闭时间*/
	private Date endTime;
	/***评价内容*/
	private String appraiseContent;
	/***评价时间*/
	private Date appraiseTime;
	/***创建时间*/
	private Date createTime;
	/***更新时间*/
	private Date updateTime;
	/***逻辑删除*/
	private Integer isDeleted;
	/***后台备注*/
	private String adminRemark;
	/***来源*/
	private Integer source;
	/***取货时间*/
    private Date takeTime;
    /***取货门店id*/
    private Integer takeStoreId;
    /***取货门店*/
    private String takeStoreName;
    /***退款状态 1退款申请2已退款*/
    private Integer refundStatus;
    /***退款原因*/
    private String refundRemark; 
    /***退款申请时间*/
    private Date refundApplyTime;
    /***退款处理时间*/
    private Date refundHandleTime;
    
    private Integer cartId;
    private Integer num;
    
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	 
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	 
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	 
	public String getAppraiseContent() {
		return appraiseContent;
	}
	public void setAppraiseContent(String appraiseContent) {
		this.appraiseContent = appraiseContent;
	}
	public Date getAppraiseTime() {
		return appraiseTime;
	}
	public void setAppraiseTime(Date appraiseTime) {
		this.appraiseTime = appraiseTime;
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
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getAdminRemark() {
		return adminRemark;
	}
	public void setAdminRemark(String adminRemark) {
		this.adminRemark = adminRemark;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Date getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
	}
	public Integer getTakeStoreId() {
		return takeStoreId;
	}
	public void setTakeStoreId(Integer takeStoreId) {
		this.takeStoreId = takeStoreId;
	}
	public String getTakeStoreName() {
		return takeStoreName;
	}
	public void setTakeStoreName(String takeStoreName) {
		this.takeStoreName = takeStoreName;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getRefundRemark() {
		return refundRemark;
	}
	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}
	public Date getRefundApplyTime() {
		return refundApplyTime;
	}
	public void setRefundApplyTime(Date refundApplyTime) {
		this.refundApplyTime = refundApplyTime;
	}
	public Date getRefundHandleTime() {
		return refundHandleTime;
	}
	public void setRefundHandleTime(Date refundHandleTime) {
		this.refundHandleTime = refundHandleTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
