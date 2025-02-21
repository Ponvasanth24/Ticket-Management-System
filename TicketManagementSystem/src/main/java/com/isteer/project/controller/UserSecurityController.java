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
import com.isteer.project.dto.LoginDto;
import com.isteer.project.dto.SuccessResponseDto;
import com.isteer.project.entity.User;
import com.isteer.project.enums.ResponseMessageEnum;
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
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.USER_REGISTRATION_SUCCESS.getResponseCode(), ResponseMessageEnum.USER_REGISTRATION_SUCCESS.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
	@PostMapping("login")
	public ResponseEntity<?> loginUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		String token = userService.loginUser(userName);
		if(token != null) {
			LoginDto response = new LoginDto(userName, token, "User logged in successfully");
			return ResponseEntity.ok(response);
		}
		LoginDto response = new LoginDto(userName, token, "User not logged in");
		return ResponseEntity.badRequest().body(response);
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PostMapping("elevateUser")
	public ResponseEntity<?> elevateUser(@RequestBody AssignOrRemoveRoleDto userName) {
		int status = userService.eleveateUser(userName);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.ROLE_ELEVATION_SUCCESS.getResponseCode(), ResponseMessageEnum.ROLE_ELEVATION_SUCCESS.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PostMapping("demote")
	public ResponseEntity<?> removeUserRole(@RequestBody AssignOrRemoveRoleDto userName) {
		int status = userService.removeUserRole(userName);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.ROLE_DEMOTION_SUCCESS.getResponseCode(), ResponseMessageEnum.ROLE_DEMOTION_SUCCESS.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PutMapping("addRole")
	public ResponseEntity<?> addRole(@RequestParam String newRole) {
		int status = roleService.addRole(newRole);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.ADD_NEW_ROLE.getResponseCode(), ResponseMessageEnum.ADD_NEW_ROLE.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
//	@PreAuthorize("@permissionService.hasPermission()")
	@PutMapping("removeRole")
	public ResponseEntity<?> removeRole(@RequestParam String newRole) {
		int status = roleService.removeRole(newRole);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.REMOVE_EXISTING_ROLE.getResponseCode(), ResponseMessageEnum.REMOVE_EXISTING_ROLE.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
	}
	
	@PostMapping("addUrl")
	public ResponseEntity<?> addUrl(@RequestBody AddOrRemoveUrlDto url) {
		int status = permissionService.addUrl(url);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.ADD_URL_FOR_DYNAMIC_RBAC.getResponseCode(), ResponseMessageEnum.ADD_URL_FOR_DYNAMIC_RBAC.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
		}
	
	@PostMapping("removeUrl")
	public ResponseEntity<?> removeUrl(@RequestBody AddOrRemoveUrlDto url) {
		int status = permissionService.removeUrl(url);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.REMOVE_URL_FROM_DYNAMIC_RBAC.getResponseCode(), ResponseMessageEnum.REMOVE_URL_FROM_DYNAMIC_RBAC.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed");
		}
}
