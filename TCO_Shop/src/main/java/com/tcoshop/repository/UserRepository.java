package com.tcoshop.repository;

import java.util.List;

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
    
    @Query(value = "select count(*) from Users\r\n"
            + "where Month(Create_Date) = ?1 and Year(Create_Date) = ?2 and Role_Id = 'USER'",
            nativeQuery = true)
    Integer getUserRegister(int month, int year);

    @Query(value = "select * from Users where YEAR(Create_Date) = ?1 and Role_Id = 'USER'\r\n"
            + "and Month(Create_Date) = ?2", nativeQuery = true)
    List<User> getUserByYearAndMonth(int year, int month);
}
