package com.isteer.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isteer.project.dto.AddOrRemoveUrlDto;
import com.isteer.project.dto.AssignOrRemoveRoleDto;
import com.isteer.project.entity.User;
import com.isteer.project.service.PermissionService;
import com.isteer.project.service.RoleSecurityService;
import com.isteer.project.service.UserSecurityService;


@RestController
@RequestMapping("/tms")
public class UserSecurityController {

	@Autowired
	UserSecurityService userService;
	@Autowired
	RoleSecurityService roleService;
	@Autowired
	PermissionService permissionService;
	
	@PostMapping("register")
	public ResponseEntity<?> registerUser(@RequestBody User	user) {
		int status = userService.registerUser(user);
		if(status > 0)
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
	@PostMapping("login")
	public ResponseEntity<?> loginUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		String token = userService.loginUser(userName);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PostMapping("elevateUser")
	public ResponseEntity<?> elevateUser(@RequestBody AssignOrRemoveRoleDto userName) {
		int status = userService.eleveateUser(userName);
		if(status > 0)
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PostMapping("demote")
	public ResponseEntity<?> removeUserRole(@RequestBody AssignOrRemoveRoleDto userName) {
		int status = userService.removeUserRole(userName);
		if(status > 0)
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PutMapping("addRole")
	public ResponseEntity<?> addRole(@RequestParam String newRole) {
		int status = roleService.addRole(newRole);
		if(status > 0)
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
//	@PreAuthorize("@permissionService.hasPermission()")
	@PutMapping("removeRole")
	public ResponseEntity<?> removeRole(@RequestParam String newRole) {
		int status = roleService.removeRole(newRole);
		if(status > 0)
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
	@PostMapping("addUrl")
	public ResponseEntity<?> addUrl(@RequestBody AddOrRemoveUrlDto url) {
		int status = permissionService.addUrl(url);
		if(status > 0)
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
		}
	
	@PostMapping("removeUrl")
	public ResponseEntity<?> removeUrl(@RequestBody AddOrRemoveUrlDto url) {
		int status = permissionService.removeUrl(url);
		if(status > 0)
			return ResponseEntity.status(HttpStatus.OK).body("Success");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
		}
}
