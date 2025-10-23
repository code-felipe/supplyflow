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

	private SupplyItemViewDTO(Builder builder) {
		this.id = builder.id;
		this.code = builder.code;
		this.name = builder.name;
		this.description = builder.description;
		this.specification = builder.specification;
        this.createAt = (builder.createAt == null) ? null : new Date(builder.createAt.getTime()); // copia defensiva
		this.category = builder.category;
		this.dimLength = builder.dimLength;
		this.dimWidth = builder.dimWidth;
		this.dimHeight = builder.dimHeight;
		this.dimWeight = builder.dimWeight;
		this.dimUom = builder.dimUom;
		this.unitsPerPack = builder.unitsPerPack;
		this.packsPerCase = builder.packsPerCase;
		this.casesPerPallet = builder.casesPerPallet;
		this.pkgUom = builder.pkgUom;
	}
	
	public static class Builder{
		private Long id;
		private String code;
		private String name;
		private String description;
		private String specification = "No assigned";
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
		

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder code(String code) {
			this.code = code;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder specification(String specification) {
			this.specification = specification;
			return this;
		}

		public Builder createAt(Date createAt) {
			this.createAt = createAt;
			return this;
		}

		public Builder category(String category) {
			this.category = category;
			return this;
		}

		public Builder dimLength(BigDecimal dimLength) {
			this.dimLength = dimLength;
			return this;
		}

		public Builder dimWidth(BigDecimal dimWidth) {
			this.dimWidth = dimWidth;
			return this;
		}

		public Builder dimHeight(BigDecimal dimHeight) {
			this.dimHeight = dimHeight;
			return this;
		}

		public Builder dimWeight(BigDecimal dimWeight) {
			this.dimWeight = dimWeight;
			return this;
		}

		public Builder dimUom(String dimUom) {
			this.dimUom = dimUom;
			return this;
		}

		public Builder unitsPerPack(Integer unitsPerPack) {
			this.unitsPerPack = unitsPerPack;
			return this;
		}

		public Builder packsPerCase(Integer packsPerCase) {
			this.packsPerCase = packsPerCase;
			return this;
		}

		public Builder casesPerPallet(Integer casesPerPallet) {
			this.casesPerPallet = casesPerPallet;
			return this;
		}

		public Builder pkgUom(String pkgUom) {
			this.pkgUom = pkgUom;
			return this;
		}
		
		public SupplyItemViewDTO build() {
			 if (code == null || code.isBlank()) {
	                throw new IllegalStateException("code is required");
	            }
	            if (name == null || name.isBlank()) {
	                throw new IllegalStateException("name is required");
	            }

	            // Defaults seguros
	            if (specification == null || specification.isBlank()) {
	                specification = "No assigned";
	            }

	            // Normalización de strings vacíos -> null
	            if (description != null && description.isBlank()) description = null;
	            if (category    != null && category.isBlank())    category    = null;
	            if (dimUom      != null && dimUom.isBlank())      dimUom      = null;
	            if (pkgUom      != null && pkgUom.isBlank())      pkgUom      = null;
	            
			return new SupplyItemViewDTO(this);
		}
		
	}
	
	public static Builder builder() {
		return new Builder();
	}
	public Long getId() {
		return id;
	}

//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getCode() {
		return code;
	}

//	public void setCode(String code) {
//		this.code = code;
//	}

	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

	public String getCategory() {
		return category;
	}

//	public void setCategory(String category) {
//		this.category = category;
//	}

	public BigDecimal getDimLength() {
		return dimLength;
	}

//	public void setDimLength(BigDecimal dimLength) {
//		this.dimLength = dimLength;
//	}

	public BigDecimal getDimWidth() {
		return dimWidth;
	}

//	public void setDimWidth(BigDecimal dimWidth) {
//		this.dimWidth = dimWidth;
//	}

	public BigDecimal getDimHeight() {
		return dimHeight;
	}

//	public void setDimHeight(BigDecimal dimHeight) {
//		this.dimHeight = dimHeight;
//	}

	public BigDecimal getDimWeight() {
		return dimWeight;
	}

//	public void setDimWeight(BigDecimal dimWeight) {
//		this.dimWeight = dimWeight;
//	}

	public String getDimUom() {
		return dimUom;
	}

//	public void setDimUom(String dimUom) {
//		this.dimUom = dimUom;
//	}

	public Integer getUnitsPerPack() {
		return unitsPerPack;
	}

//	public void setUnitsPerPack(Integer unitsPerPack) {
//		this.unitsPerPack = unitsPerPack;
//	}

	public Integer getPacksPerCase() {
		return packsPerCase;
	}

//	public void setPacksPerCase(Integer packsPerCase) {
//		this.packsPerCase = packsPerCase;
//	}

	public Integer getCasesPerPallet() {
		return casesPerPallet;
	}

//	public void setCasesPerPallet(Integer casesPerPallet) {
//		this.casesPerPallet = casesPerPallet;
//	}

	public String getPkgUom() {
		return pkgUom;
	}

//	public void setPkgUom(String pkgUom) {
//		this.pkgUom = pkgUom;
//	}

	public String getSpecification() {
		return (specification == null || specification.isBlank()) ? "No asigned" : specification;
	}

//	public void setSpecification(String specification) {
//		this.specification = specification;
//	}
	
	public String getDescription() {
		return description;
	}

//	public void setDescription(String description) {
//		this.description = description;
//	}

	public Date getCreateAt() {
		return  (createAt == null) ? null : new Date(createAt.getTime()); 
	}

//	public void setCreateAt(Date createAt) {
//		this.createAt = createAt;
//	}
	
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
