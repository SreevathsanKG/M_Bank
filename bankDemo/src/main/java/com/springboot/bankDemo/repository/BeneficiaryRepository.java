package com.springboot.bankDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.bankDemo.model.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer>{

}
