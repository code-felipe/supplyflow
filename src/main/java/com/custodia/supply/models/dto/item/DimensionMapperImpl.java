package com.custodia.supply.models.dto.item;

import org.springframework.stereotype.Component;

import com.custodia.supply.models.entity.item.Dimension;
import com.custodia.supply.util.mapper.IMapper;

@Component
public class DimensionMapperImpl implements IMapper<DimensionsDTO, Dimension>{

	@Override
	public Dimension toEntity(DimensionsDTO d) {
		if (d == null) return null;
	    Dimension dim = new Dimension();
	    dim.setHeight(d.getHeight());
	    dim.setLenght(d.getLength()); // ojo: "Lenght" en tu modelo
	    dim.setWeight(d.getWeight());
	    dim.setWidth(d.getWidth());
	    dim.setUom(d.getUom());
	    return dim;
	}

	@Override
	public DimensionsDTO toDTO(Dimension e) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// === For service ===
	@Override
	public void applyScalarFields(Dimension e, DimensionsDTO dto) {
		// TODO Auto-generated method stub
		
	}
	

}
