package com.custodia.supply.customer.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custodia.supply.customer.entity.CustomerAccount;


public interface ICustomerAccountDao extends JpaRepository<CustomerAccount, Long>{
	Optional<CustomerAccount> findByExternalCode(String externalCode);
	boolean existsByEmailIgnoreCase(String email);
}
