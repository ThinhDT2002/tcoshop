package com.tcoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.Role;
import com.tcoshop.repository.RoleRepository;
import com.tcoshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleRepository roleRepository;
	@Override
	public Role getRole(String id) {
		return roleRepository.findById(id).get();
	}

}
