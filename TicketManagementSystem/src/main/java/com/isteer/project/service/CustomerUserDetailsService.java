package com.isteer.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isteer.project.entity.User;
import com.isteer.project.repository.UserSecurityRepo;
import com.isteer.project.utility.UserSecurityContextUtil;

@Service
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	UserSecurityRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUserName(username);
		if(user != null) {
			return new UserSecurityContextUtil(user);
		}
		throw new UsernameNotFoundException("User not Found");
	}

}
