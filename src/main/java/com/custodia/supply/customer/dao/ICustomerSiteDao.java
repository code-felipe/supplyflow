package com.custodia.supply.customer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.custodia.supply.customer.entity.CustomerAccount;
import com.custodia.supply.customer.entity.CustomerSite;

public interface ICustomerSiteDao extends JpaRepository<CustomerSite, Long> {
	 // Trae cada site con su customer para poder mostrarlo en el <select>
	  @Query("""
	         select s
	         from CustomerSite s
	         join fetch s.customer c
	         order by c.name, s.address
	         """)
	  List<CustomerSite> findAllForSelect();
	  
	  Optional<CustomerSite> findByCustomerAndExternalCode(CustomerAccount customer, String externalCode);
}
