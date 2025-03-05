package com.isteer.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isteer.project.dto.AssignOrRemoveRoleDto;
import com.isteer.project.entity.Roles;
import com.isteer.project.entity.User;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.exception.RoleNotFoundException;
import com.isteer.project.repository.RoleSecurityRepo;
import com.isteer.project.repository.UserSecurityRepo;
import com.isteer.project.utility.JwtUtil;
import com.isteer.project.utility.UUIdGenerator;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserSecurityService {

	@Autowired
	UserSecurityRepo userRepo;
	@Autowired
	RoleSecurityRepo roleRepo;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	HttpServletRequest request;
	@Autowired
	UUIdGenerator uuIdGenerator;
	
	public int registerUser(User user) {
		user.setUserId("USR"+uuIdGenerator.generateShortID());
		user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
		List<Roles> roles = roleRepo.getAllRoles();
		 user.setRoles(roles.stream().filter(r->r.getRole().equals("USER")).toList());
		int status = userRepo.registerUser(user);
		
		return status;
	}
	
	public String loginUser(String userName) {
		String token = null;
		token = jwtUtil.generateToken(userName);
		return token;
	}
	
	public User getDetails() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByUserName(userName);
		return user;
	}
	
	public int elevateUser(AssignOrRemoveRoleDto role) {
		List<Roles> roles = roleRepo.getAllRoles();
		String roleId = roles.stream()
				.filter(
						r->r.getRole()
						.equalsIgnoreCase(role.getRole())
						).map(Roles::getRoleId)
				.findFirst()
				.orElse("null");
		if(roleId == "null") {
			throw new RoleNotFoundException(ResponseMessageEnum.ROLE_NOT_FOUND_EXCEPTION);
		}
		int status = userRepo.elevateRole(role.getUserName(), roleId);
		return status;
	}
	
	public int removeUserRole(AssignOrRemoveRoleDto role) {
		List<Roles> roles = roleRepo.getAllRoles();
		String roleId = roles.stream()
				.filter(
						r->r.getRole()
						.equalsIgnoreCase(role.getRole())
						).map(Roles::getRoleId)
				.findFirst()
				.orElse("null");
		if(roleId == "null") {
			throw new RoleNotFoundException(ResponseMessageEnum.ROLE_NOT_FOUND_EXCEPTION);
		}
		int status = userRepo.deleteRole(role.getUserName(), roleId);
		return status;
	}
}
