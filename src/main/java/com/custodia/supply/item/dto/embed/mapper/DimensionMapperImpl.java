package com.custodia.supply.item.dto.embed.mapper;

import org.springframework.stereotype.Component;

import com.custodia.supply.item.dto.embed.DimensionsDTO;
import com.custodia.supply.item.embed.Dimension;
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
	

}
