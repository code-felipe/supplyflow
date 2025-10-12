package com.custodia.supply.item.service;


import java.util.List;
import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;
import com.custodia.supply.item.entity.Product;
import com.custodia.supply.item.entity.SupplyItem;

public interface ISupplyItemService {
	
	public List<SupplyItem> findAll();
	
	public SupplyItem findOne(Long id);
	
	public SupplyItem save(SupplyItemFormDTO dto);
	
//	Optional<SupplyItem> findByProductId(Long productId);
/*
 * Is not necessary to delete a SupplyItem, it will be
 *  part of active system. Is not a good practice delete this historical
 */
//	public Boolean delete(Long id);
	
}
