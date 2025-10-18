package com.custodia.supply.category.service;

import java.util.List;
import java.util.Optional;

import com.custodia.supply.category.dto.CategoryDTO;
import com.custodia.supply.category.entity.Category;

public interface ICategoryService {
	
	public Optional<Category> findOne(Long id);
	
	public List<Category> findAll();
	
	public void save(CategoryDTO dto);
	
}
