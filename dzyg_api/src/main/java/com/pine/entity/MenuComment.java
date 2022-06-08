package com.pine.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 评论 po
 *  
 */
public class MenuComment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private String userName;
	/****/
	private String UserAvatar;

	public String getUserAvatar() {
		return UserAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		UserAvatar = userAvatar;
	}

	/****/
	private Integer userId;
	/****/
	private Date createTime;
	/****/
	private String content;
	/****/
	private Integer menuId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	
	 
}
