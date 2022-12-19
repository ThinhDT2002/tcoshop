package com.tcoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	@Query("select t from Transaction t where t.order.user.username=?1")
	List<Transaction> findByUser(String username);
}
