package com.isteer.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isteer.project.dto.AssignOrRemoveRoleDto;
import com.isteer.project.entity.Roles;
import com.isteer.project.entity.User;
import com.isteer.project.repository.RoleSecurityRepo;
import com.isteer.project.repository.UserSecurityRepo;
import com.isteer.project.utility.JwtUtil;

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
	
	public int registerUser(User user) {
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
	
	public int eleveateUser(AssignOrRemoveRoleDto role) {
		List<Roles> roles = roleRepo.getAllRoles();
		int roleId = roles.stream()
				.filter(
						r->r.getRole()
						.equalsIgnoreCase(role.getRole())
						).mapToInt(Roles::getRoleId)
				.findFirst()
				.orElse(-1);
		if(roleId == -1)
			return 0;
		int status = userRepo.elevateRole(role.getUserName(), roleId);
		return status;
	}
	
	public int removeUserRole(AssignOrRemoveRoleDto role) {
		List<Roles> roles = roleRepo.getAllRoles();
		int roleId = roles.stream()
				.filter(
						r->r.getRole()
						.equalsIgnoreCase(role.getRole())
						).mapToInt(Roles::getRoleId)
				.findFirst()
				.orElse(-1);
		if(roleId == -1)
			return 0;
		int status = userRepo.deleteRole(role.getUserName(), roleId);
		return status;
	}
}
