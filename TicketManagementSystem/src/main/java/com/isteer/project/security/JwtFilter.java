package com.isteer.project.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.isteer.project.service.CustomerUserDetailsService;
import com.isteer.project.utility.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	CustomerUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = "";
		String userName = "";
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			userName = jwtUtil.extractUserName(token);
		}
		 if (!userName.isBlank() && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails user = userDetailsService.loadUserByUsername(userName);
	            if (jwtUtil.validateToken(token, user)) {
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }
	        filterChain.doFilter(request, response);
	}

}
