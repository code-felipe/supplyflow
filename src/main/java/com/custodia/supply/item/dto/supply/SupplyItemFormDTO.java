package com.custodia.supply.item.dto.supply;

import java.util.Date;

import com.custodia.supply.category.dto.CategoryDTO;
import com.custodia.supply.item.dto.embed.DimensionsDTO;
import com.custodia.supply.item.dto.embed.PackagingDTO;
import com.custodia.supply.validation.UniqueSupplyItemCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@UniqueSupplyItemCode
public class SupplyItemFormDTO {
	
	private Long id;

	private String code;

	@NotBlank
	private String name;

	@NotNull
	private CategoryDTO category = new CategoryDTO();


	private String specification;
	
	private String description;
	
	private Date createAt; 

	@Valid
	@NotNull
	private DimensionsDTO dimensions = new DimensionsDTO();

	@Valid
	@NotNull
	private PackagingDTO packaging = new PackagingDTO();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public DimensionsDTO getDimensions() {
		return dimensions;
	}

	public void setDimensions(DimensionsDTO dimensions) {
		this.dimensions = dimensions;
	}

	public PackagingDTO getPackaging() {
		return packaging;
	}

	public void setPackaging(PackagingDTO packaging) {
		this.packaging = packaging;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	

//	public static SupplyItemFormDTO to(SupplyItem e) {
//	    SupplyItemFormDTO f = new SupplyItemFormDTO();
//	    f.setId(e.getId());
//	    f.setPackagingCode(e.getPackagingCode());
//	    f.setUnitOfMeasure(e.getUnitOfMeasure());
//	    f.setSpecification(e.getSpecification());
//	    f.setCreateAt(e.getCreateAt());
//
//	    ProductForm pf = new ProductForm();
//	    pf.setId(e.getProduct().getId());
//	    pf.setCode(e.getProduct().getCode());
//	    pf.setName(e.getProduct().getName());
//	    
//	    f.setProduct(pf);
//	    return f;
//	}

}
