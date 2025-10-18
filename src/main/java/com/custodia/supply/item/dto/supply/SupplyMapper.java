package com.custodia.supply.item.dto.supply;

import com.custodia.supply.category.dto.CategoryDTO;
import com.custodia.supply.category.entity.Category;
import com.custodia.supply.item.dto.embed.DimensionsDTO;
import com.custodia.supply.item.dto.embed.PackagingDTO;
import com.custodia.supply.item.embed.Dimension;
import com.custodia.supply.item.embed.Packaging;
import com.custodia.supply.item.entity.SupplyItem;

public class SupplyMapper {

	public static SupplyItemViewDTO toView(SupplyItem item) {
		if (item == null)
			return null;

		SupplyItemViewDTO dto = new SupplyItemViewDTO();
		dto.setId(item.getId());
		dto.setCode(item.getCode());
		dto.setName(item.getName());
		dto.setSpecification(item.getSpecification());
		dto.setDescription(item.getDescription());
		dto.setCategory(item.getCategory().getName());
		dto.setCreateAt(item.getCreateAt());

		dto.setDimLength(item.getDimensions().getLenght());
		dto.setDimWeight(item.getDimensions().getWeight());
		dto.setDimHeight(item.getDimensions().getHeight());
		dto.setDimUom(item.getDimensions().getUom().name());

		dto.setUnitsPerPack(item.getPackaging().getUnitsPerPack());
		dto.setPacksPerCase(item.getPackaging().getPacksPerCase());
		dto.setCasesPerPallet(item.getPackaging().getCasesPerPallet());
		dto.setPkgUom(item.getPackaging().getUom().name());

		return dto;
	}

	public static SupplyItemFormDTO toForm(SupplyItem item) {
		if (item == null)
			return null;

		SupplyItemFormDTO dto = new SupplyItemFormDTO();
		dto.setId(item.getId());
		dto.setCode(item.getCode());
		dto.setName(item.getName());
		dto.setSpecification(item.getSpecification());
		dto.setDescription(item.getDescription());
		dto.setCreateAt(item.getCreateAt());

		CategoryDTO cat = new CategoryDTO();
		cat.setId(item.getCategory().getId());
		cat.setName(item.getCategory().getName());
		cat.setDescription(item.getCategory().getDescription());

		DimensionsDTO dim = new DimensionsDTO();
		dim.setHeight(item.getDimensions().getHeight());
		dim.setLength(item.getDimensions().getLenght());
		dim.setWeight(item.getDimensions().getWeight());
		dim.setWidth(item.getDimensions().getWidth());
		dim.setUom(item.getDimensions().getUom());

		PackagingDTO pkg = new PackagingDTO();
		pkg.setCasesPerPallet(item.getPackaging().getCasesPerPallet());
		pkg.setPacksPerCase(item.getPackaging().getPacksPerCase());
		pkg.setUnitsPerPack(item.getPackaging().getUnitsPerPack());
		pkg.setUom(item.getPackaging().getUom());

		dto.setCategory(cat);
		dto.setDimensions(dim);
		dto.setPackaging(pkg);

		return dto;

	}

	public static SupplyItem toEntity(SupplyItemFormDTO item) {
		if (item == null)
			return null;

		SupplyItem entity = new SupplyItem();
		entity.setId(item.getId());
		entity.setCode(item.getCode());
		entity.setName(item.getName());
		entity.setSpecification(item.getSpecification());
		entity.setDescription(item.getDescription());
		entity.setCreateAt(item.getCreateAt());

		Category cat = new Category();
		cat.setId(item.getCategory().getId());
		cat.setName(item.getCategory().getName());
		cat.setDescription(item.getCategory().getDescription());

		Dimension dim = new Dimension();
		dim.setHeight(item.getDimensions().getHeight());
		dim.setLenght(item.getDimensions().getLength());
		dim.setWeight(item.getDimensions().getWeight());
		dim.setWidth(item.getDimensions().getWidth());
		dim.setUom(item.getDimensions().getUom());

		Packaging pkg = new Packaging();
		pkg.setCasesPerPallet(item.getPackaging().getCasesPerPallet());
		pkg.setPacksPerCase(item.getPackaging().getPacksPerCase());
		pkg.setUnitsPerPack(item.getPackaging().getUnitsPerPack());
		pkg.setUom(item.getPackaging().getUom());

		entity.setCategory(cat);
		entity.setDimensions(dim);
		entity.setPackaging(pkg);
		
		return entity;

	}

}
