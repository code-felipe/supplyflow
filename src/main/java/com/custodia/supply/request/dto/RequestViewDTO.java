package com.custodia.supply.request.dto;

import java.util.Date;

import com.custodia.supply.customer.dto.CustomerSiteViewDTO;
import com.custodia.supply.user.dto.UserViewDTO;

public class RequestViewDTO {
	private Long id;
	private String description;
	private String additionalItems;
	private Boolean status;
	private Date createAt;
	
    private String siteAddress;     
    private String siteCode;
	
	private Long itemsCount;
	private Long totalQuantity;
	private UserViewDTO user;
	private CustomerSiteViewDTO customerSite;

	public RequestViewDTO() {

		// TODO Auto-generated constructor stub
	}

	
	public RequestViewDTO(Long id, String description, String additionalItems, Boolean status, Date createAt, String siteAddress,
			String siteCode, Long itemsCount, Long totalQuantity) {
		
		this.id = id;
		this.description = description;
		this.additionalItems = additionalItems;
		this.status = status;
		this.createAt = createAt;
		this.siteAddress = siteAddress;
		this.siteCode = siteCode;
		this.itemsCount = itemsCount;
		this.totalQuantity = totalQuantity;
	}


	public String getSiteAddress() {
		return siteAddress;
	}


	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}


	public String getSiteCode() {
		return siteCode;
	}


	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return (description == null || description.isBlank()) ? "No assigned" : description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdditionalItems() {
		return (additionalItems == null || additionalItems.isBlank()) ? "No assigned" : additionalItems;
	}

	public void setAdditionalItems(String additionalItems) {
		this.additionalItems = additionalItems;
	}
	
	public String getStatus() {
		if(status == true) {
			return "Sent";
		}else {
			return "Pending";
		}
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createdAt) {
		this.createAt = createdAt;
	}

	public Long getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(Long items) {
		this.itemsCount = items;
	}

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

//    public Integer getTotalQuantity() { return totalQuantity; }
//    public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }

	public UserViewDTO getUser() {
		return user;
	}

	public void setUser(UserViewDTO user) {
		this.user = user;
	}

	public CustomerSiteViewDTO getCustomerSite() {
		return customerSite;
	}

	public void setCustomerSite(CustomerSiteViewDTO customerSite) {
		this.customerSite = customerSite;
	}
}
