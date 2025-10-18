package com.custodia.supply.customer.dto;

public class CustomerSiteFormDTO {
	private Long id;

	private String code; // ShipTo code

	private String address; // ShipTo name
	
	private CustomerAccountFormDTO customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CustomerAccountFormDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerAccountFormDTO customer) {
		this.customer = customer;
	}
	
	
}
