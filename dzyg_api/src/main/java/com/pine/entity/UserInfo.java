package com.pine.entity;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
 
	private static final long serialVersionUID = 2517466091701442372L;

	/**
     * id
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;
 

    /**
     * 头像
     */
    private String avatar;

    /**
     * updatetime
     */
    private Date updateTime;
    private Date createTime;

    /**
     * status 0正常1已冻结
     */
    private Integer status;
    //手机号
    private String mobile;
    private String openid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	 
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	} 
      
    
}
