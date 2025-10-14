package com.custodia.supply.category.service;

import java.util.List;

import com.custodia.supply.category.entity.Category;

public interface ICategoryService {
	
	public Category findOne(Long id);
	
	public List<Category> findAll();
	
}
