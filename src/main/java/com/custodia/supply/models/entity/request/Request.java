package com.custodia.supply.models.entity.request;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.custodia.supply.models.entity.customer.CustomerAccount;
import com.custodia.supply.models.entity.customer.CustomerSite;
import com.custodia.supply.models.entity.requestitem.RequestItem;
import com.custodia.supply.models.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
// Needs to use DTO in controller.
@Entity
@Table(name = "requests")
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String description;

	@Column(name = "additional_items")
	private String additionalItems;
	
	private Boolean status;
	
	@ManyToOne(fetch = FetchType.LAZY)
//	@JsonBackReference
	private User user;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id")
	private CustomerSite shipTo; // ← destino logístico

	/*
	 * Deletes all RequestItems, means sense because RequestItem does not exists
	 * without its Request
	 */
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)// Optimal for historicals.
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "request_id") // unidericcional
	private List<RequestItem> items;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	public Request() {
		this.items = new ArrayList<RequestItem>();
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
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

	public void setAdditionalItems(String additionaItems) {
		this.additionalItems = additionaItems;
	}

	public Date getCreateAt() {
		return createAt;
	}
	

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RequestItem> getItems() {
		return items;
	}

	public void seItems(List<RequestItem> items) {
		this.items = items;
	}

	public void addRequestItem(RequestItem items) {
		this.items.add(items);
	}

	@Transient
	public CustomerAccount getCustomer() {
		return shipTo != null ? shipTo.getCustomer() : null;
	}

	public CustomerSite getShipTo() {
		return shipTo;
	}

	public void setShipTo(CustomerSite shipTo) {
		this.shipTo = shipTo;
	}

	public Integer getTotal() {
		return items.stream().map(request -> request.getQuantity()).reduce(0, Integer::sum);
	}

}
