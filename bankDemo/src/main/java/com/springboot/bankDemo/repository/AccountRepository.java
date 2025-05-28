package com.springboot.bankDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.bankDemo.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

}
