package com.custodia.supply.customer.dto;

public class CustomerAccountViewDTO {

	private Long id;

	private String code;

	private String name;

	private String email;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeLabel() {
		return (code == null || code.isBlank()) ? "No asigned" : code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getNameLabel() {
		return (name == null || name.isBlank()) ? "No asigned" : name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getEmailLabel() {
		return (email == null || email.isBlank()) ? "No asigned" : email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

}
