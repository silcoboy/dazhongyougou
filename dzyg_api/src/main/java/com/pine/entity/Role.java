package com.pine.entity;

import java.sql.Timestamp;

import lombok.Data;

public class Role {

    /**
     * id
     */
    private Integer id;

    /**
     * rloe_name
     */
    private String roleName;

    /**
     * updatetime
     */
    private Timestamp updatetime;

    /**
     * role_desc
     */
    private String roleDesc;

    /**
     * 权限对应的功能菜单
     */
    private String menuInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getMenuInfo() {
		return menuInfo;
	}

	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}
    
}
