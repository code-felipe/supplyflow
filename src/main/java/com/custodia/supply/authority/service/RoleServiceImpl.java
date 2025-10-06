package com.custodia.supply.authority.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.authority.dao.IRoleDao;
import com.custodia.supply.authority.entity.Role;



@Service
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private IRoleDao roleDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return (List<Role>) roleDao.findAll();
	}

	@Override
	@Transactional
	public Role findOne(Long id) {
		// TODO Auto-generated method stub
		return roleDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Role findByAuthority(String authority) {
		// TODO Auto-generated method stub
		return roleDao.findByAuthority(authority);
	}

}
