package com.example.demo.Authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Services.JwtServices;
import com.example.demo.Services.UserDetailsServicesImpl;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
	  @Autowired
	  JwtServices jwtServices;
	  
	  @Autowired
	  UserDetailsServicesImpl servicesImpl;
	  
	  private String getJwt(HttpServletRequest request) {
		  String auther = request.getHeader("Authorization");
		  if (auther != null && auther.startsWith("Bearer ")) {
			return auther.replace("Bearer ", "");
		}
		  return null;
	  }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwt(request);
			if ( jwt != null && jwtServices.validateJwtToken(jwt) ) {
				String username = jwtServices.getUserNameFromJwtToken(jwt);
				
				UserDetails userDetails = servicesImpl.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	filterChain.doFilter(request, response);
	}



	


}
