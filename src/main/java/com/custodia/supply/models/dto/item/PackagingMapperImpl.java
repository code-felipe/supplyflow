package com.custodia.supply.models.dto.item;

import org.springframework.stereotype.Component;

import com.custodia.supply.models.entity.item.Packaging;
import com.custodia.supply.util.mapper.IMapper;

@Component
public class PackagingMapperImpl implements IMapper<PackagingDTO, Packaging>{

	@Override
	public Packaging toEntity(PackagingDTO p) {
		if (p == null) return null;
	    Packaging pkg = new Packaging();
	    pkg.setCasesPerPallet(p.getCasesPerPallet());
	    pkg.setPacksPerCase(p.getPacksPerCase());
	    pkg.setUnitsPerPack(p.getUnitsPerPack());
	    pkg.setUom(p.getUom());
	    return pkg;
	}

	@Override
	public PackagingDTO toDTO(Packaging e) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// === For sevice ===

	@Override
	public void applyScalarFields(Packaging e, PackagingDTO dto) {
		// TODO Auto-generated method stub
		
	}

}
