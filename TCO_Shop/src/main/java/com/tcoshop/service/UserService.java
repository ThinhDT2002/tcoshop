package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.User;

public interface UserService {
	User findByUsername(String username);
	List<User> getAll();
}
