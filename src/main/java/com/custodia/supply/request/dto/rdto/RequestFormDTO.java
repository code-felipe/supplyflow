package com.custodia.supply.request.dto.rdto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.custodia.supply.customer.dto.CustomerSiteFormDTO;
import com.custodia.supply.requestitem.dto.RequestItemFormDTO;
import com.custodia.supply.user.dto.UserFormDTO;

public class RequestFormDTO {

	private Long id;

	private String description;

	private String additionalItems;

	private Boolean status;

	private Date createAt;

	private UserFormDTO user;

	private CustomerSiteFormDTO shipTo;

	private List<RequestItemFormDTO> items;

	public RequestFormDTO() {
		this.items = new ArrayList<RequestItemFormDTO>();
	}

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public UserFormDTO getUser() {
		return user;
	}

	public void setUser(UserFormDTO user) {
		this.user = user;
	}

	public CustomerSiteFormDTO getShipTo() {
		return shipTo;
	}

	public void setShipTo(CustomerSiteFormDTO shipTo) {
		this.shipTo = shipTo;
	}

	public List<RequestItemFormDTO> getItems() {
		return items;
	}

	public void setItems(List<RequestItemFormDTO> items) {
		this.items = items;
	}

	public void addRequestItem(RequestItemFormDTO requestItem) {
		this.items.add(requestItem);
	}

}
