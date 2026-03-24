package com.custodia.supply.models.dto.item;

import com.custodia.supply.models.enumerate.item.PackagingUom;

public class PackagingDTO {
	private Integer unitsPerPack;
	private Integer packsPerCase;
	private Integer casesPerPallet;
	private PackagingUom uom; // ROLL/BOX/CASE/BAG

	public PackagingDTO() {

		// TODO Auto-generated constructor stub
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

	public PackagingUom getUom() {
		return uom;
	}

	public void setUom(PackagingUom uom) {
		this.uom = uom;
	}

}
