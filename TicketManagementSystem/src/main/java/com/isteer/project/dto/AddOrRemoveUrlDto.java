package com.isteer.project.dto;

public class AddOrRemoveUrlDto {

	private String url;
	private String role;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "AddOrRemoveUrl [url=" + url + ", role=" + role + "]";
	}
	
}
