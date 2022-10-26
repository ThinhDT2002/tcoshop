package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.User;
import com.tcoshop.repository.UserRepository;
import com.tcoshop.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	@Override
	public User findByUsername(String username) {
		return userRepository.findById(username).get();
	}
	
	@Override
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
