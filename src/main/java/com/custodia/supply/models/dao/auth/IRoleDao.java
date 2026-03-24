package com.custodia.supply.models.dao.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.custodia.supply.models.entity.auth.Role;

public interface IRoleDao extends PagingAndSortingRepository<Role, Long>, CrudRepository<Role, Long>{
	
	public Role findByAuthority(String authority);
}
