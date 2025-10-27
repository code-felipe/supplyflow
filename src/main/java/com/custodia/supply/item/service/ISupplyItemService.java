package com.custodia.supply.item.service;


import java.util.List;
import java.util.Optional;

import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;
import com.custodia.supply.item.entity.Product;
import com.custodia.supply.item.entity.SupplyItem;

public interface ISupplyItemService {
	
	public List<SupplyItem> findAll();
	
	public Optional<SupplyItem> findOne(Long id);
	
	public SupplyItem findById(Long id);// Using in new impl
	
	public SupplyItem save(SupplyItemFormDTO dto);
	
	public boolean existsByCode(String code);
	public boolean existsByCodeAndIdNot(String code, Long id);
	
	
//	Optional<SupplyItem> findByProductId(Long productId);
/*
 * Is not necessary to delete a SupplyItem, it will be
 *  part of active system. Is not a good practice delete this historical
 */
//	public Boolean delete(Long id);
	
}
