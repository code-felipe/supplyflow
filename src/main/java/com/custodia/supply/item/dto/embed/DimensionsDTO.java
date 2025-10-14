package com.custodia.supply.item.dto.embed;

import java.math.BigDecimal;

import com.custodia.supply.item.enumerate.UnitOfMeasure;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class DimensionsDTO {
	@DecimalMin("0.0")
	private BigDecimal length;
	@DecimalMin("0.0")
	private BigDecimal width;
	@DecimalMin("0.0")
	private BigDecimal height;
	@DecimalMin("0.0")
	private BigDecimal weight;
	@NotNull
	private UnitOfMeasure uom; // CM/IN/MM

	public DimensionsDTO() {

		// TODO Auto-generated constructor stub
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public UnitOfMeasure getUom() {
		return uom;
	}

	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}

}
