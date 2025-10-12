package com.custodia.supply.request.dto;

import jakarta.validation.constraints.NotEmpty;

public class UserRequestFormDTO {
	
	private Long id;
	
	private String firstName;

	private String lastName;
	
    @NotEmpty
	private String description;
	private String additionalItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdditionalItems() {
		return additionalItems;
	}

	public void setAdditionalItems(String additionalItems) {
		this.additionalItems = additionalItems;
	}

}