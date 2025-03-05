package com.isteer.project.entity;

public class Permission {

	private String urlId;
	private String roleId;
		
	public String getUrlId() {
		return urlId;
	}
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "Permission [urlId=" + urlId + ", roleId=" + roleId + "]";
	}
}
