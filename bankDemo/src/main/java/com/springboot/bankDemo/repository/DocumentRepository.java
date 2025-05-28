package com.springboot.bankDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankDemo.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer>{

	@Query("select d from Document d where d.customer.id=?1")
	Optional<Document> getByCustomerId(int customerId);		// user written JPQL 
	Optional<Document> findByCustomerId(int customerId);	// Jpq writes JPQL to fetch document by customer id
}
