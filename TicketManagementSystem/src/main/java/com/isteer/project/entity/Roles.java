package com.isteer.project.entity;

import java.util.List;

public class Roles {

	private String roleId;
	private String role;
	private List<HttpMethod> methods;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}	
	public List<HttpMethod> getMethods() {
		return methods;
	}
	public void setMethods(List<HttpMethod> methods) {
		this.methods = methods;
	}
	
	@Override
	public String toString() {
		return "Roles [roleId=" + roleId + ", role=" + role + ", methods=" + methods + "]";
	}
}
