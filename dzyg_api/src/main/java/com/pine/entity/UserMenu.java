package com.pine.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 菜单 po
 *  
 */
public class UserMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private String UserAvatar;
	/****/
	private String userName;
	/****/
	private Integer userId;
	/****/
	private String title;
	/****/
	private Date createTime;
	/****/
	private Date updateTime;
	/****/
	private String content;
	/****/
	private String picUrl;

	public String getUserAvatar() {
		return UserAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		UserAvatar = userAvatar;
	}

	/****/
	private Integer browse;
	/****/
	private Integer comment;

	public UserMenu() {
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getBrowse() {
		return browse;
	}
	public void setBrowse(Integer browse) {
		this.browse = browse;
	}
	public Integer getComment() {
		return comment;
	}
	public void setComment(Integer comment) {
		this.comment = comment;
	}
	 
}
