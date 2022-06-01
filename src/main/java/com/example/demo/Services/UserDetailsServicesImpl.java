package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.MyUserDetails;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.UserRepository;
public class UserDetailsServicesImpl implements UserDetailsService {

	@Autowired
	UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = repository.findByUsername(username);
		if (users == null) {
			throw new UsernameNotFoundException("Could find usename");
		}
		return new MyUserDetails(users);
	}
   
	
}
