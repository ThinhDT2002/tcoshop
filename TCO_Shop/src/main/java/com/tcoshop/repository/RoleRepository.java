package com.tcoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

}
