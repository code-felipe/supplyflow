package com.custodia.supply.requestitem.dto;

import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;


public class RequestItemFormDTO {
	
private Long id;
	
	private Integer quantity;
	
	private SupplyItemFormDTO supplyItem;

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

	public SupplyItemFormDTO getSupplyItem() {
		return supplyItem;
	}

	public void setSupplyItem(SupplyItemFormDTO supplyItem) {
		this.supplyItem = supplyItem;
	}
	
	
}
