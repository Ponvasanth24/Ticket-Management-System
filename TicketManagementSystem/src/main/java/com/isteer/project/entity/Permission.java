package com.isteer.project.entity;

public class Permission {

	private String urlPattern;
	private String roleId;
	
	public String getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "Permission [urlPattern=" + urlPattern + ", roleId=" + roleId + "]";
	}
}
