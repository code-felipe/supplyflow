package com.custodia.supply.models.dto.item;

import org.springframework.stereotype.Component;

import com.custodia.supply.models.dto.category.CategoryDTO;
import com.custodia.supply.models.entity.category.Category;
import com.custodia.supply.models.entity.item.Dimension;
import com.custodia.supply.models.entity.item.Packaging;
import com.custodia.supply.models.entity.item.SupplyItem;
import com.custodia.supply.util.mapper.IMapper;

@Component
public class SupplyFormMapperImpl implements IMapper<SupplyItemFormDTO, SupplyItem>{

	@Override
	public SupplyItem toEntity(SupplyItemFormDTO item) {
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

	@Override
	public SupplyItemFormDTO toDTO(SupplyItem item) {
		if (item == null)
			return null;

		SupplyItemFormDTO dto = new SupplyItemFormDTO();
		dto.setId(item.getId());
		dto.setCode(item.getCode());
		dto.setName(item.getName());
		dto.setSpecification(item.getSpecification());
		dto.setDescription(item.getDescription());
		dto.setCreateAt(item.getCreateAt());

		CategoryDTO cat = CategoryDTO.builder()
				.id(item.getCategory().getId())
				.name(item.getCategory().getName())
				.description(item.getCategory().getDescription())
				.build();
				
//		cat.setId(item.getCategory().getId());
//		cat.setName(item.getCategory().getName());
//		cat.setDescription(item.getCategory().getDescription());

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
	
	
	//=== For service ==
	@Override
	public void applyScalarFields(SupplyItem e, SupplyItemFormDTO dto) {
		// TODO Auto-generated method stub
		
	}

	

}
