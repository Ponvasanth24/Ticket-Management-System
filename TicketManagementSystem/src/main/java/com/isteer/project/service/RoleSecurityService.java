package com.isteer.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isteer.project.repository.RoleSecurityRepo;

@Service
public class RoleSecurityService {

	@Autowired
	RoleSecurityRepo roleRepo;
	
	public int addRole(String newRole) {
		int status = roleRepo.addRole(newRole);
		return status;
	}
	
	public int removeRole(String newRole) {
		int status = roleRepo.removeRole(newRole);
		return status;
	}
}
