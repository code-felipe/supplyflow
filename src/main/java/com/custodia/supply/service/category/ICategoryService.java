package com.custodia.supply.service.category;

import java.util.List;
import java.util.Optional;

import com.custodia.supply.models.dto.category.CategoryDTO;
import com.custodia.supply.models.entity.category.Category;

public interface ICategoryService {
	
	public Optional<Category> findOne(Long id);

	public List<CategoryDTO> findAll();
	
	public void save(CategoryDTO dto);
	
	public Boolean existsById(Long id);
	
}
