package com.custodia.supply.authority.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.custodia.supply.authority.entity.Role;

public interface IRoleDao extends PagingAndSortingRepository<Role, Long>, CrudRepository<Role, Long>{
	
	public Role findByAuthority(String authority);
}
