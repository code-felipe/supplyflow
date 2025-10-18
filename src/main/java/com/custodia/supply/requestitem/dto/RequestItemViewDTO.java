package com.custodia.supply.requestitem.dto;

import com.custodia.supply.item.dto.supply.SupplyItemViewDTO;


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
