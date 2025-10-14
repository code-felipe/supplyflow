package com.custodia.supply.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.category.dao.ICategoryDao;
import com.custodia.supply.category.entity.Category;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	@Transactional
	public Category findOne(Long id) {
		// TODO Auto-generated method stub
		return categoryDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return (List<Category>) categoryDao.findAll();
	}

}
