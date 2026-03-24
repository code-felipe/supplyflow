package com.custodia.supply.models.dto.requestitem;

import com.custodia.supply.models.dto.item.SupplyItemViewDTO;


public class RequestItemViewDTO {
	
	private Long id;
	
	private Integer quantity;
	
	private SupplyItemViewDTO supplyItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public SupplyItemViewDTO getSupplyItem() {
		return supplyItem;
	}

	public void setSupplyItem(SupplyItemViewDTO supplyItem) {
		this.supplyItem = supplyItem;
	}
	

	
}
