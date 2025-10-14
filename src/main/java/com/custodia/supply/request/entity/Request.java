package com.custodia.supply.request.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.custodia.supply.customer.entity.CustomerAccount;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.requestitem.entity.RequestItem;
import com.custodia.supply.user.entity.User;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	private List<RequestItem> requests;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	public Request() {
		this.requests = new ArrayList<RequestItem>();
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

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<RequestItem> getRequests() {
		return requests;
	}

	public void setRequests(List<RequestItem> requests) {
		this.requests = requests;
	}

	public void addRequestItem(RequestItem requestItem) {
		this.requests.add(requestItem);
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
		return requests.stream().map(request -> request.getQuantity()).reduce(0, Integer::sum);
	}

}
