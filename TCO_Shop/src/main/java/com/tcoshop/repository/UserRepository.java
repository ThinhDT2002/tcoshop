package com.tcoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    @Query("Select u from User u Where u.activateCode =?1")
    User findByActivateCode(String activadeCode);

    @Query(value = "select count(*) from Users where Users.Role_Id = ?1", nativeQuery = true)
    Integer getUserCount(String roleId);
}
