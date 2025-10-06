package com.custodia.supply.authority.service;

import java.util.List;

import com.custodia.supply.authority.entity.Role;

public interface IRoleService {
	
	public List<Role> findAll();
	
	public Role findOne(Long id);
	
	public Role findByAuthority(String authority);
}
