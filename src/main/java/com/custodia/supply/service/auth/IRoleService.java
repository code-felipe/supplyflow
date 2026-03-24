package com.custodia.supply.service.auth;

import java.util.List;

import com.custodia.supply.models.entity.auth.Role;

public interface IRoleService {
	
	public List<Role> findAll();
	
	public Role findOne(Long id);
	
	public Role findByAuthority(String authority);
}
