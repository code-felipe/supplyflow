package com.custodia.supply.user.dto;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.custodia.supply.authority.dto.AuthorityViewDTO;
import com.custodia.supply.customer.dto.CustomerAccountViewDTO;
import com.custodia.supply.customer.dto.CustomerSiteViewDTO;
import com.custodia.supply.request.dto.RequestViewDTO;


public class UserViewDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String password;
	private Boolean isActive;
	private Date createAt;

	private AuthorityViewDTO roleAuthority;

	private CustomerAccountViewDTO customerAccount;

	private CustomerSiteViewDTO customerSite;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstNameLabel() {
		return (firstName == null || firstName.isBlank()) ? "No asigned" : firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastNameLabel() {
		return (lastName == null || lastName.isBlank()) ? "No asigned" : lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailLabel() {
		return (email == null || email.isBlank()) ? "No asigned" : email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneLabel() {
		return (phone == null || phone.isBlank()) ? "No asigned" : phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPasswordLabel() {
		return (password == null || password.isBlank()) ? "No asigned" : password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return Boolean.TRUE.equals(isActive);
	}

	public String getIsActiveLabel() {
		return (isActive == null ? "No active" : isActive.toString());
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreateAtLabel() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public AuthorityViewDTO getRoleAuthority() {
		return roleAuthority;
	}

	public String getRoleLabel() {
		return (roleAuthority != null) ? roleAuthority.getRoleLabel() : "Not assigned";
	}

	public void setAuthority(AuthorityViewDTO authority) {
		this.roleAuthority = authority;
	}

	public CustomerAccountViewDTO getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccountViewDTO customerAccount) {
		this.customerAccount = customerAccount;
	}

	public CustomerSiteViewDTO getCustomerSite() {
		return customerSite;
	}

	public void setCustomerSite(CustomerSiteViewDTO customerSite) {
		this.customerSite = customerSite;
	}

}
