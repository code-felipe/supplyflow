package com.custodia.supply.item.embed;

import com.custodia.supply.item.enumerate.PackagingUom;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Packaging {

	@Column(name = "pkg_units_per_pack")
	private Integer unitsPerPack; // ej: 100 toallas por paquete

	@Column(name = "pkg_packs_per_case")
	private Integer packsPerCase; // ej: 12 paquetes por caja

	@Column(name = "pkg_cases_per_pallet")
	private Integer casesPerPallet; // opcional

	@Enumerated(EnumType.STRING)
	@Column(name = "pkg_uom", length = 10) // ROLL, BOX, CASE, BAG
	private PackagingUom uom;

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
