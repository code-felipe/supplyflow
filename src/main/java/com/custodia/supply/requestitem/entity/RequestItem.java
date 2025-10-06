package com.custodia.supply.requestitem.entity;

import java.io.Serializable;

import com.custodia.supply.item.entity.SupplyItem;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "request_items")
public class RequestItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer quantity;
	
	/*
	 No cascade because SupplyItem can be in multiples RequestItem, if RequestItem is deleted should not delete SupplyItem 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supply_item_id")
	private SupplyItem supplyItem;

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

	public SupplyItem getSupplyItem() {
		return supplyItem;
	}

	public void setSupplyItem(SupplyItem supplyItem) {
		this.supplyItem = supplyItem;
	}
	
}
