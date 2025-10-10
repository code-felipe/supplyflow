package com.custodia.supply.user.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.custodia.supply.authority.entity.Role;
import com.custodia.supply.customer.entity.CustomerAccount;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.request.entity.Request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(unique = true, length = 30)
	private String email;

	private String phone;

//	@Enumerated(EnumType.STRING)
//	private Role role;
	@ManyToOne(fetch = FetchType.LAZY, optional = true) // puede ser null mientras el admin asigna
	@JoinColumn(name = "role_id")
	private Role role;

	@Column(length = 60)
	private String password;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "site_id")
	private CustomerSite assignedSite; // Se persiste (FK en users.site_id)

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	@Column(name = "is_active")
	private Boolean isActive;

	/*
	 * Ensure it gets delete or update. Not recommended. If a User is delete, all
	 * Requests and its RequestItems is delete in cascade.
	 */
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Optimal for historicals.
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Request> requests = new ArrayList<Request>();// bidireccional

	public User() {
		requests = new ArrayList<Request>();
	}

	// Set active when is a new user
	@PrePersist
	void prePersist() {
		if (isActive == null)
			isActive = true; // ← activa por defecto si no viene seteado
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	 public List<Request> getRequests() {
	        return requests == null ? Collections.emptyList() : requests;
	    }

	 public void setRequests(List<Request> requests) {
	        this.requests = (requests == null) ? new ArrayList<>() : requests;
	    }
	public void addRequest(Request request) {
		requests.add(request);
	}

	public CustomerSite getAssignedSite() {
		return assignedSite;
	}

	public void setAssignedSite(CustomerSite assignedSite) {
		this.assignedSite = assignedSite;
	}

	@Transient
	public CustomerAccount getAssignedCustomer() {
		return assignedSite != null ? assignedSite.getCustomer() : null;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

}
