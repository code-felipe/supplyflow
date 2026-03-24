package com.custodia.supply.controllers.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custodia.supply.models.dto.item.SupplyItemViewDTO;
import com.custodia.supply.service.item.ISupplyItemService;

@RestController
@RequestMapping("/api/supply_item")
public class SupplyItemRestController {
	
	@Autowired
	private ISupplyItemService supplyItemService;

//	  @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
//	  @GetMapping("/{categoryId}/items")
//	  public ResponseEntity<List<SupplyItemViewDTO>> itemsByCategory(@PathVariable Long categoryId) {
//	      return ResponseEntity.ok(supplyItemService.findAllWithCategory(categoryId));
//	  }

}
