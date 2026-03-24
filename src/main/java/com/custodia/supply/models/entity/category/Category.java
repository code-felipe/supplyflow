package com.custodia.supply.models.entity.category;

import java.util.ArrayList;
import java.util.List;

import com.custodia.supply.models.entity.item.SupplyItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String name; // TISSUE, LINER, SOAP, BAG, TOWEL…

	@Column(length = 120)
	private String description;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<SupplyItem> products = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SupplyItem> getProducts() {
		return products;
	}

	public void setProducts(List<SupplyItem> products) {
		this.products = products;
	}
	
	

}
