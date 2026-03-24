package com.custodia.supply.controllers.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.custodia.supply.models.dto.category.CategoryDTO;
import com.custodia.supply.service.category.ICategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private ICategoryService category;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		
		List<CategoryDTO> categories = category.findAll();
		
		model.addAttribute("categories", categories);
		
		return "category/list";
	}

}
