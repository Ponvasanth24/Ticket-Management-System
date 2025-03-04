package com.isteer.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.isteer.project.dto.AddOrRemoveUrlDto;
import com.isteer.project.entity.HttpMethod;
import com.isteer.project.entity.HttpMethodRolePermission;
import com.isteer.project.entity.Permission;
import com.isteer.project.entity.Roles;
import com.isteer.project.entity.Urls;
import com.isteer.project.entity.User;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.repository.PermissionRepo;
import com.isteer.project.repository.RoleSecurityRepo;
import com.isteer.project.repository.UserSecurityRepo;
import com.isteer.project.utility.UUIdGenerator;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PermissionService {

	@Autowired
	UserSecurityRepo userRepo;
	@Autowired
	RoleSecurityRepo roleRepo;
	@Autowired
	PermissionRepo permissionRepo;
	@Autowired
	HttpServletRequest request;
	@Autowired
	UUIdGenerator shortIdGenerator;
	
	public boolean hasPermission() {
		String userName = request.getUserPrincipal().getName();
		String urlPattern = request.getRequestURI();
		String httpMethod = request.getMethod();
		User user = userRepo.findByUserName(userName);
		List<Permission> urlPermission = permissionRepo.findByUrlPattern(urlPattern);
		List<HttpMethodRolePermission> methodPermission = permissionRepo.findHttpMethod(httpMethod);
		boolean urlPermissionStatus = user.getRoles()
				.stream()
				.map(Roles::getRoleId)
				.anyMatch(roleId -> urlPermission.stream()
						.map(Permission::getRoleId)
						.anyMatch(roleId::equals));
		boolean methodPermissionStatus = user.getRoles()
				.stream()
				.map(Roles::getRoleId)
				.anyMatch(roleId -> methodPermission.stream()
						.map(HttpMethodRolePermission::getRoleId)
						.anyMatch(roleId::equals));
		if(urlPermissionStatus && methodPermissionStatus)
			return true;
		throw new AccessDeniedException(ResponseMessageEnum.ACCESS_DENIED_EXCEPTION.getResponseMessage());
	}
	
	public int addUrl(Urls url) {
		url.setUrlid("URL" + shortIdGenerator.generateShortID());
		List<Roles> roles = roleRepo.getAllRoles();
		 url.setRoles(roles.stream().filter(r->r.getRole().equals("SUPER_ADMIN")).toList());
		int status = permissionRepo.addUrl(url);
		return status;
	}
	
	public int removeUrl(AddOrRemoveUrlDto url) {
		String roleId = roleRepo.getRoleId(url.getRole());
		int status = permissionRepo.removeUrl(url.getUrl(), roleId);
		return status;
	}

	public int addHttpMethod(HttpMethod method) {
		method.setMethodId("MTD" + shortIdGenerator.generateShortID());
		int status = permissionRepo.addHttpMethod(method);
		return status;
	}

	public int removeHttpMethod(HttpMethod httpMethod) {
		int status = permissionRepo.removeHttpMethod(httpMethod);
		return status;
	}
}
