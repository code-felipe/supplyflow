package com.custodia.supply.item.service;


import java.util.List;

import com.custodia.supply.item.dto.SupplyItemForm;
import com.custodia.supply.item.entity.SupplyItem;

public interface ISupplyItemService {
	
	public List<SupplyItem> findAll();
	
	public SupplyItem findOne(Long id);
	
	public Boolean save(SupplyItemForm supplyItem);
/*
 * Is not necessary to delete a SupplyItem, it will be
 *  part of active system. Is not a good practice delete this historical
 */
//	public Boolean delete(Long id);
	
}
