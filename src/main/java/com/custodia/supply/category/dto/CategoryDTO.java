package com.custodia.supply.category.dto;


public class CategoryDTO {
	
	private Long id;


	private String name; // TISSUE, LINER, SOAP, BAG, TOWEL…


	private String description;


	public CategoryDTO() {

		// TODO Auto-generated constructor stub
	}


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
}
