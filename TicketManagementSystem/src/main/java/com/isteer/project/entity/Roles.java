package com.isteer.project.entity;

public class Roles {

	private Integer roleId;
	private String role;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "Roles [roleId=" + roleId + ", role=" + role + "]";
	}
}
