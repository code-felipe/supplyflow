package com.custodia.supply.item.dto.supply;

import java.util.Date;

import com.custodia.supply.item.dto.product.ProductViewDTO;

public class SupplyItemViewDTO {
	private Long id;

	private String packagingCode;

	private String unitOfMeasure;

	private String specification;

	private Date createAt;

	private ProductViewDTO product;

	public Long getIdLabel() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPackagingCodeLabel() {
		return (packagingCode == null || packagingCode.isBlank()) ? "No asigned" : packagingCode;
	}

	public void setPackagingCode(String packagingCode) {
		this.packagingCode = packagingCode;
	}

	public String getUnitOfMeasureLabel() {
		return (unitOfMeasure == null || unitOfMeasure.isBlank()) ? "No asigned" : unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getSpecificationLabel() {
		return (specification == null || specification.isBlank()) ? "No asigned" : specification;
	};

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public ProductViewDTO getProduct() {
		return product;
	}

	public void setProduct(ProductViewDTO product) {
		this.product = product;
	}

}
