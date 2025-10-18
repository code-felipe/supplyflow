package com.custodia.supply.item.dto.supply;

import java.math.BigDecimal;
import java.util.Date;

public class SupplyItemViewDTO {
	private Long id;
	private String code;
	private String name;
	private String description;
	private String specification;
	private Date createAt;
	private String category;

	// Dimensions (flatten)
	private BigDecimal dimLength;
	private BigDecimal dimWidth;
	private BigDecimal dimHeight;
	private BigDecimal dimWeight;
	private String dimUom; // "CM", "IN", Enum.

	// Packaging (flatten)
	private Integer unitsPerPack;
	private Integer packsPerCase;
	private Integer casesPerPallet;
	private String pkgUom; // Enum

	

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getDimLength() {
		return dimLength;
	}

	public void setDimLength(BigDecimal dimLength) {
		this.dimLength = dimLength;
	}

	public BigDecimal getDimWidth() {
		return dimWidth;
	}

	public void setDimWidth(BigDecimal dimWidth) {
		this.dimWidth = dimWidth;
	}

	public BigDecimal getDimHeight() {
		return dimHeight;
	}

	public void setDimHeight(BigDecimal dimHeight) {
		this.dimHeight = dimHeight;
	}

	public BigDecimal getDimWeight() {
		return dimWeight;
	}

	public void setDimWeight(BigDecimal dimWeight) {
		this.dimWeight = dimWeight;
	}

	public String getDimUom() {
		return dimUom;
	}

	public void setDimUom(String dimUom) {
		this.dimUom = dimUom;
	}

	public Integer getUnitsPerPack() {
		return unitsPerPack;
	}

	public void setUnitsPerPack(Integer unitsPerPack) {
		this.unitsPerPack = unitsPerPack;
	}

	public Integer getPacksPerCase() {
		return packsPerCase;
	}

	public void setPacksPerCase(Integer packsPerCase) {
		this.packsPerCase = packsPerCase;
	}

	public Integer getCasesPerPallet() {
		return casesPerPallet;
	}

	public void setCasesPerPallet(Integer casesPerPallet) {
		this.casesPerPallet = casesPerPallet;
	}

	public String getPkgUom() {
		return pkgUom;
	}

	public void setPkgUom(String pkgUom) {
		this.pkgUom = pkgUom;
	}

	public String getSpecification() {
		return (specification == null || specification.isBlank()) ? "No asigned" : specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
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
	
	public String getCatalogString() {
	    String cat = (category != null && !category.isBlank()) ? " | " + category : "";
	    String l = dimLength  != null ? dimLength.stripTrailingZeros().toPlainString() : "";
	    String w = dimWidth   != null ? dimWidth.stripTrailingZeros().toPlainString()  : "";
	    String h = dimHeight  != null ? dimHeight.stripTrailingZeros().toPlainString() : "";
	    String u = dimUom     != null ? dimUom.toLowerCase() : "";
	    String dims = (!l.isEmpty() || !w.isEmpty() || !h.isEmpty())
	        ? " | " + l + "x" + w + "x" + h + (u.isEmpty() ? "" : " " + u)
	        : "";
	    String up = unitsPerPack != null ? unitsPerPack.toString() : "";
	    String pc = packsPerCase != null ? packsPerCase.toString() : "";
	    String pkg = (!up.isEmpty() || !pc.isEmpty())
	        ? " | " + (up.isEmpty() ? "" : up + "u/pk") + (pc.isEmpty() ? "" : " " + pc + "pk/case")
	        : "";

	    return (code != null ? code : "") + " | " + (name != null ? name : "") + cat + dims + pkg;
	    // Ej: ACM-123 | Ultra Towel | TOWEL | 20x20x25 cm | 100u/pk 12pk/case
	  }

}
