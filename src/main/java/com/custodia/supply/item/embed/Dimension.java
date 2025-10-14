package com.custodia.supply.item.embed;

import java.math.BigDecimal;

import com.custodia.supply.item.enumerate.UnitOfMeasure;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Dimension {

	@Column(name = "dim_length")
	private BigDecimal lenght;
	@Column(name = "dim_width")
	private BigDecimal width;

	@Column(name = "dim_height")
	private BigDecimal height;

	@Column(name = "dim_weight")
	private BigDecimal weight;

	@Enumerated(EnumType.STRING)
	@Column(name = "dim_uom", length = 10) // cm, in, mm
	private UnitOfMeasure uom;

	public BigDecimal getLenght() {
		return lenght;
	}

	public void setLenght(BigDecimal lenght) {
		this.lenght = lenght;
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
