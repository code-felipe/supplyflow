package com.custodia.supply.category.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.category.dao.ICategoryDao;
import com.custodia.supply.category.dto.CategoryDTO;
import com.custodia.supply.category.entity.Category;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	@Transactional
	public Optional<Category> findOne(Long id) {
		// TODO Auto-generated method stub
		return categoryDao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return (List<Category>) categoryDao.findAll();
	}

	@Override
	@Transactional
	public void save(CategoryDTO dto) {
		Category category = dto.getId() == null
				? new Category()
				: this.findOne(dto.getId()).get();
		
		
		category.setId(dto.getId());
		category.setName(dto.getName());
		category.setDescription(dto.getDescription());
		
		categoryDao.save(category);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existsById(Long id) {
		return categoryDao.existsById(id);
	}
	
	
}
