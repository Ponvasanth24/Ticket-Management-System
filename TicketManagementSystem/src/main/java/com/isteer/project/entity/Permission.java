package com.isteer.project.entity;

public class Permission {

	private String urlPattern;
	private int roleId;
	
	public String getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public String toString() {
		return "Permission [urlPattern=" + urlPattern + ", roleId=" + roleId + "]";
	}
}
