package com.custodia.supply.user.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.user.entity.User;

public interface IUserDao extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
	
	public Boolean existsByEmailIgnoreCase(String value);
	public Boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
	
	// Brings only User attributes plus CustomerSite -> assignedSite.customer
	@EntityGraph(attributePaths = {"assignedSite", "assignedSite.customer"})
	  Optional<User> findById(Long id);
	
	// Brings all requests that match user id.
	@Query("select u from User u left join fetch u.requests r where u.id=?1")
	public User fetchByIdWithRequests(Long id);
	
	//=== Allows authentication and permissions using email
	public User findByEmail(String email);

}
