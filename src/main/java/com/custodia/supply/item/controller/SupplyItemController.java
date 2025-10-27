package com.custodia.supply.item.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.custodia.supply.category.dto.CategoryDTO;
import com.custodia.supply.category.service.ICategoryService;
import com.custodia.supply.item.dto.embed.DimensionsDTO;
import com.custodia.supply.item.dto.embed.PackagingDTO;
import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;
import com.custodia.supply.item.dto.supply.SupplyItemViewDTO;
import com.custodia.supply.item.dto.supply.SupplyMapper;
import com.custodia.supply.item.dto.supply.mapper.SupplyFormMapperImpl;
import com.custodia.supply.item.dto.supply.mapper.SupplyViewMapperImpl;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.item.service.ISupplyItemService;
import com.custodia.supply.util.mapper.IMapper;
import com.custodia.supply.util.paginator.IPageableService;
import com.custodia.supply.util.paginator.PageRender;
import com.custodia.supply.validation.util.Exceptions;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/supply_item")
public class SupplyItemController {
	
	@Autowired
	private IPageableService<SupplyItem> pageableSupplyItem;
	
	@Autowired
	private ISupplyItemService supplyItemService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private SupplyViewMapperImpl supplyView;
	
	@Autowired
	private SupplyFormMapperImpl supplyForm;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0")int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5, Sort.by("createAt").descending());
		
		Page<SupplyItem> items = pageableSupplyItem.findAll(pageRequest);
		
		Page<SupplyItemViewDTO> supplies = items.map(supplyView::toDTO);// New version of Mapper - before  (SupplyMapper)
		PageRender<SupplyItemViewDTO> pageRender = new PageRender<>("/supply_item/list", supplies);
		
	
		model.addAttribute("title", "Custodian Order Supply");
		model.addAttribute("items", supplies);
		model.addAttribute("page", pageRender);
		
		return "supply_item/list";
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/form")
	public String createForm(Map<String, Object> model) {
		
		model.put("title", "Supply Item form");
		model.put("supplyItem", new SupplyItemFormDTO());
		model.put("categories", categoryService.findAll());

		return "supply_item/form";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(
	        @Valid @ModelAttribute("supplyItem") SupplyItemFormDTO item,
	        BindingResult result,
	        Model model,
	        RedirectAttributes flash) {
	
	    if (result.hasErrors()) {
	        model.addAttribute("title", item.getId() != null ? "Edit Supply Item" : "Create Supply Item");
	        model.addAttribute("categories", categoryService.findAll());
	        return "supply_item/form"; // get back to form with errors.
	    }

	    try {
	        SupplyItem saved = supplyItemService.save(item);
	     
	        if (saved != null) {
	            flash.addFlashAttribute("success", "Supply Item saved successfully!");
	        } else {
	            flash.addFlashAttribute("error", "Could not save Supply Item.");
	        }

	    } catch (Exception e) {
	        flash.addFlashAttribute("error", "Unexpected error: " + e.getMessage());
	    }

	    return "redirect:/supply_item/list";
	}
	

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String edit(@PathVariable(value = "id") Long id,
			Map<String, Object> model, RedirectAttributes flash) {
		Optional<SupplyItem> supplyItem = supplyItemService.findOne(id);

	    if (!supplyItem.isPresent()) {
	        flash.addFlashAttribute("error", "The item does not exists");
	        return "redirect:/supply_item/list";
	    }
	    //==Old version of Mapper: No in use==
//	    SupplyItemFormDTO item = SupplyMapper.toCreate(supplyItem.get());
	    SupplyItemFormDTO item = supplyForm.toDTO(supplyItem.get());//Testing current Mapper.
	    
	    model.put("supplyItem", item);
		model.put("categories", categoryService.findAll());
	    model.put("title", "Edit Supply Item");
	    return "supply_item/form";
	}
	/*
	 * Is not necessary to delete a SupplyItem, it will be
	 *  part of active system. Is not a good practice delete this historical
	 */
//	@RequestMapping(value = "/delete/{id}")
//	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
//		if( id > 0) {
//			
//			SupplyItemDTO item = supplyItemService.findOne(id);
//			supplyItemService.delete(id);
//			flash.addFlashAttribute("success", "Supply item: " + item.getProduct().getName() + " has been succesfully delete!");
//
//		}
//		return "redirect:/supply_item/list";
//	}
	
}
