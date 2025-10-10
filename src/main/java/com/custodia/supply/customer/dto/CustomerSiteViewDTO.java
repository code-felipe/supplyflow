package com.custodia.supply.customer.dto;


public class CustomerSiteViewDTO {

	private Long id;

	private String code; // ShipTo code

	private String address; // ShipTo name

	private CustomerAccountViewDTO customer;

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

	public String getAddressLabel() {
		return (address == null || address.isBlank()) ? "No asigned" : address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}


	public CustomerAccountViewDTO getCustomer() {
		return customer;
	}

}
