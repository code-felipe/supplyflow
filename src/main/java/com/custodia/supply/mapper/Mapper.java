package com.custodia.supply.mapper;

import com.custodia.supply.models.dto.category.CategoryDTO;
import com.custodia.supply.models.entity.category.Category;

public class Mapper {
	
	public static CategoryDTO toDTO(Category category) {
		if(category == null) return null;
		
		return CategoryDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();
	}
	


}
