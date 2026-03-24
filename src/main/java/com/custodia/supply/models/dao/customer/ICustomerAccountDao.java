package com.custodia.supply.models.dao.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custodia.supply.models.entity.customer.CustomerAccount;


public interface ICustomerAccountDao extends JpaRepository<CustomerAccount, Long>{
	Optional<CustomerAccount> findByExternalCode(String externalCode);
//	boolean existsByEmailIgnoreCase(String email);
	public Boolean existsByEmailIgnoreCase(String value);
	public Boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
	
}
