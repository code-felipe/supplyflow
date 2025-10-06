package com.custodia.supply.item.dto;

import java.util.Date;

import com.custodia.supply.item.entity.Product;
import com.custodia.supply.item.entity.SupplyItem;

import jakarta.validation.Valid;

public class SupplyItemForm {
	private Long id;

	private String packagingCode;

	private String unitOfMeasure;

	private String specification;
    
    private Date createAt;
    
    @Valid
    private ProductForm product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPackagingCode() {
		return packagingCode;
	}

	public void setPackagingCode(String packagingCode) {
		this.packagingCode = packagingCode;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
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

	public ProductForm getProduct() {
		return product;
	}

	public void setProduct(ProductForm product) {
		this.product = product;
	}
    
	public static SupplyItemForm to(SupplyItem e) {
	    SupplyItemForm f = new SupplyItemForm();
	    f.setId(e.getId());
	    f.setPackagingCode(e.getPackagingCode());
	    f.setUnitOfMeasure(e.getUnitOfMeasure());
	    f.setSpecification(e.getSpecification());
	    f.setCreateAt(e.getCreateAt());

	    ProductForm pf = new ProductForm();
	    pf.setId(e.getProduct().getId());
	    pf.setCode(e.getProduct().getCode());
	    pf.setName(e.getProduct().getName());
	    
	    f.setProduct(pf);
	    return f;
	}
    
}
