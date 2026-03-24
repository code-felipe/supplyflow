package com.custodia.supply.controllers.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custodia.supply.models.dto.category.CategoryDTO;
import com.custodia.supply.models.dto.item.SupplyItemViewDTO;
import com.custodia.supply.service.category.ICategoryService;
import com.custodia.supply.service.item.ISupplyItemService;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
	
	@Autowired
	private ICategoryService category;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> listCategories(){
		return ResponseEntity.ok(category.findAll());
	}
	

}
