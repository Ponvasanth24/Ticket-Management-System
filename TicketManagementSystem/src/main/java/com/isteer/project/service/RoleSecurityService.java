package com.isteer.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isteer.project.entity.HttpMethod;
import com.isteer.project.entity.Roles;
import com.isteer.project.repository.PermissionRepo;
import com.isteer.project.repository.RoleSecurityRepo;
import com.isteer.project.utility.UUIdGenerator;

@Service
public class RoleSecurityService {

	@Autowired
	RoleSecurityRepo roleRepo;
	@Autowired
	UUIdGenerator uuIdGenerator;
	@Autowired
	PermissionRepo permissionRepo;
	
	public int addRole(String newRole) {
		Roles role = new Roles();
		role.setRoleId("ROLE"+uuIdGenerator.generateShortID());
		role.setRole(newRole);
		List<HttpMethod> methods = permissionRepo.getAllMethods();
		 role.setMethods(methods.stream().filter(r->r.getMethodName().equals("GET") || r.getMethodName().equals("POST")).toList());
		int status = roleRepo.addRole(role);
		return status;
	}
	
	public int removeRole(String newRole) {
		int status = roleRepo.removeRole(newRole);
		return status;
	}
}
