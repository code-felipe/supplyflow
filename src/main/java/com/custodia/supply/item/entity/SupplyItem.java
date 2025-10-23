package com.custodia.supply.item.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.custodia.supply.category.entity.Category;
import com.custodia.supply.item.embed.Dimension;
import com.custodia.supply.item.embed.Packaging;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "supply_items")
public class SupplyItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
//	@ManyToOne(optional = false, fetch = FetchType.LAZY)// Bad implemented, it should be oneToOne since is one unique product per SupplyItem
	// Implementing embeded classes
//	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name = "product_id")
//	private Product product;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name; // Ultra towel
	
	private String code; // ACM-123
	
    private String description; // Ultra towel scot 
    
    @Column(length = 255)
    private String specification; // other details

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt; 
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id")
	private Category category; // TOWEL
	
    @Embedded
    private Dimension dimensions = new Dimension();
    
    @Embedded
    private Packaging packaging = new Packaging();
    
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Dimension getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimension dimensions) {
		this.dimensions = dimensions;
	}

	public Packaging getPackaging() {
		return packaging;
	}

	public void setPackaging(Packaging packaging) {
		this.packaging = packaging;
	}
	
		
	
}
