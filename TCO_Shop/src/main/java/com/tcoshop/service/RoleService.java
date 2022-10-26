package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.Role;

public interface RoleService {
	Role getRole(String id);
	List<Role> getRoles();
}
