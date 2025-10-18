package com.custodia.supply.request.dto.rdto;

import java.util.Date;
import java.util.List;

import com.custodia.supply.customer.dto.CustomerSiteViewDTO;
import com.custodia.supply.requestitem.dto.RequestItemViewDTO;
import com.custodia.supply.user.dto.UserViewDTO;

public class RequestDTO {
	
	private Long id;
	
	private String description;
	
	private String additionalItems;
	
	private Date createAt;
	
	private UserViewDTO user;
	
	private CustomerSiteViewDTO customerSite;
	
	private List<RequestItemViewDTO> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

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

	public List<RequestItemViewDTO> getItems() {
		return items;
	}

	public void setItems(List<RequestItemViewDTO> items) {
		this.items = items;
	}
	
	public Integer getTotal() {
		return items.stream().map(request -> request.getQuantity()).reduce(0, Integer::sum);
	}

	
}
