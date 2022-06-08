package com.pine.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;


/**
 * 管理员信息
 *
 */
public class AdminInfo implements Serializable {
 
	private static final long serialVersionUID = 2517466091701442372L;

	/**
     * id
     */
    private Integer id;

    /**
     * username
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    
    @NotBlank(message = "密码不能为空")
    private String pwd;
 
    
    private Integer roleId;


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


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
 
}
