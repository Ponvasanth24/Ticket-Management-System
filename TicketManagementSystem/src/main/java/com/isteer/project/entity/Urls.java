package com.isteer.project.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Urls {

	private String urlid;
	private String urlPattern;
	private List<Roles> roles = new ArrayList<>();
	public String getUrlid() {
		return urlid;
	}
	public void setUrlid(String urlid) {
		this.urlid = urlid;
	}
	public String getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	public List<Roles> getRoles() {
		return roles;
	}
	public void setRoles(List<Roles> roles) {
		Optional.ofNullable(roles)
        .ifPresentOrElse(
                this.roles::addAll,
                () -> this.roles = new ArrayList<>()
        );
	}
	@Override
	public String toString() {
		return "Urls [urlid=" + urlid + ", urlPattern=" + urlPattern + ", roles=" + roles + "]";
	}
	
}
