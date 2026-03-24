package com.custodia.supply.service.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.mapper.Mapper;
import com.custodia.supply.models.dao.category.ICategoryDao;
import com.custodia.supply.models.dto.category.CategoryDTO;
import com.custodia.supply.models.entity.category.Category;

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
	public List<CategoryDTO> findAll() {
	    return StreamSupport.stream(categoryDao.findAll().spliterator(), false)
	            .map(Mapper::toDTO)
	            .toList();
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
