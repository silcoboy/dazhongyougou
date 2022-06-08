package com.pine.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 门店自提点 po
 *  
 */
public class StoreInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private String storeNo;
	/****/
	private String storeName;
	/****/
	private String storeAddress;
	/****/
	private Date createTime;
 
	/****/
	private String storePhone;
	/****/
	private String linkName;
	/****/
	private String latitude;
	/****/
	private String longitude;
	/****/
	private String storePic;

	public String getStorePic() {
		return storePic;
	}

	public void setStorePic(String storePic) {
		this.storePic = storePic;
	}

	/****/
	private Integer status;



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
	public String getStorePhone() {
		return storePhone;
	}
	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
