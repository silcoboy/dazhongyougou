package com.pine.entity;

import lombok.Data;

public class UserRoles {
    private  Integer id;
    private  Integer userId;
    private  Integer roleId;
    private String roleStr;
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
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleStr() {
		return roleStr;
	}
	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}

}
