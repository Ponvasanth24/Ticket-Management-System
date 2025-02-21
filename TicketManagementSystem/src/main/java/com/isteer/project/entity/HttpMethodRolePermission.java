package com.isteer.project.entity;

public class HttpMethodRolePermission {

	private String httpMethod;
	private int roleId;
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "HttpMethodRolePermission [httpMethod=" + httpMethod + ", roleId=" + roleId + "]";
	}
	
}
