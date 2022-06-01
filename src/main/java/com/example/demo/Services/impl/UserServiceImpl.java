package com.example.demo.Services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Users;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Services.UserServices;
@Service
public class UserServiceImpl implements UserServices {
	@Autowired
     UserRepository userRepository;
    
	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public Boolean login(Users user) {
		
		Users users = findByusername(user.getUsername());
		if (user != null) {
			if (encoder.matches(user.getPassword(), users.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Users findByusername(String name) {
		return userRepository.findByUsername(name);
	}
    
	@Override
	public void delete(Users entity) {
		userRepository.delete(entity);
	}



	@Override
	public <S extends Users> S save(S entity) {
		Users users = findByusername(entity.getUsername());
		if (users != null) {
		 return	userRepository.save(entity);
		}else {
			entity.setPassword(encoder.encode(entity.getPassword()));
			return userRepository.save(entity);
		}
		
	}

	@Override
	public List<Users> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<Users> findById(Integer id) {
		return userRepository.findById(id);
	}
	
	
}
