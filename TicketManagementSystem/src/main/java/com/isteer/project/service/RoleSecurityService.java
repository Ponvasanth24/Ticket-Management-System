package com.isteer.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isteer.project.repository.RoleSecurityRepo;
import com.isteer.project.utility.UUIdGenerator;

@Service
public class RoleSecurityService {

	@Autowired
	RoleSecurityRepo roleRepo;
	@Autowired
	UUIdGenerator uuIdGenerator;
	
	public int addRole(String newRole) {
		String roleId = "ROLE"+uuIdGenerator.generateShortID();
		int status = roleRepo.addRole(roleId, newRole);
		return status;
	}
	
	public int removeRole(String newRole) {
		int status = roleRepo.removeRole(newRole);
		return status;
	}
}
