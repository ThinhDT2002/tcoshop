package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.User;

public interface UserService {
	User findByUsername(String username);
	List<User> getAll();
	User create(User user);
	User update(User user);
	User findByActivateCode(String activateCode);
	void deleteByUsername(String username);
}
