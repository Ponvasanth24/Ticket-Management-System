package com.isteer.project.dto;

public class AddOrRemoveHttpMethodDto {
	
	private String method;
	private String role;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "AddOrRemoveHttpMethodDto [method=" + method + ", role=" + role + "]";
	}
}
