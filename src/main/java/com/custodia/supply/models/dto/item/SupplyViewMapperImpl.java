package com.custodia.supply.models.dto.item;

import org.springframework.stereotype.Component;

import com.custodia.supply.models.entity.item.SupplyItem;
import com.custodia.supply.util.mapper.IMapper;

@Component
public class SupplyViewMapperImpl implements IMapper<SupplyItemViewDTO, SupplyItem> {

	@Override
	public SupplyItem toEntity(SupplyItemViewDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SupplyItemViewDTO toDTO(SupplyItem item) {
		if (item == null)
			return null;

		SupplyItemViewDTO dto = SupplyItemViewDTO.builder().id(item.getId()).code(item.getCode()).name(item.getName())
				.specification(item.getSpecification()).description(item.getDescription())
				.category(item.getCategory().getName()).createAt(item.getCreateAt())
				.dimLength(item.getDimensions().getLenght()).dimWeight(item.getDimensions().getWeight())
				.dimHeight(item.getDimensions().getHeight()).dimUom(item.getDimensions().getUom().name())
				.unitsPerPack(item.getPackaging().getUnitsPerPack()).packsPerCase(item.getPackaging().getPacksPerCase())
				.casesPerPallet(item.getPackaging().getCasesPerPallet()).pkgUom(item.getPackaging().getUom().name())
				.build();
		return dto;
	}
	
	
	// ==== For service 
	@Override
	public void applyScalarFields(SupplyItem e, SupplyItemViewDTO dto) {
		// TODO Auto-generated method stub
		
	}

}
