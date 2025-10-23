package com.custodia.supply.requestitem.dto;

import com.custodia.supply.category.dto.CategoryDTO;
import com.custodia.supply.item.dto.embed.DimensionsDTO;
import com.custodia.supply.item.dto.embed.PackagingDTO;
import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;
import com.custodia.supply.item.dto.supply.SupplyMapper;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.requestitem.entity.RequestItem;
import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.dto.UserMapper;

public class RequestItemMapper {
	 public static RequestItemViewDTO toDTO(RequestItem item) {
	        if (item == null) return null;

	        RequestItemViewDTO dto = new RequestItemViewDTO();
	        dto.setId(item.getId());
	        dto.setQuantity(item.getQuantity());
	    

	        // Si el RequestItem tiene referencia a SupplyItem:
	        SupplyItem supply = item.getSupplyItem();
	        if (supply != null) {
	            // Reutiliza tu SupplyMapper para mantener consistencia
	            dto.setSupplyItem(SupplyMapper.toView(supply)); 
	        }

	        return dto;
	    }
	 
	 public static RequestItem toEntity(RequestItemFormDTO item) {
	        if (item == null) return null;

	        RequestItem dto = new RequestItem();
	        dto.setId(item.getId());
	        dto.setQuantity(item.getQuantity());
	    

	        // Si el RequestItem tiene referencia a SupplyItem:
	        // Using builder
//	        SupplyItemFormDTO supply =  SupplyItemFormDTO.builder()
//	        		.category(new CategoryDTO())
//	        		.dimensions(new DimensionsDTO())
//	        		.packaging(new PackagingDTO())
//	        		.build();
	        // Works without BUilder
	        SupplyItemFormDTO supply = new SupplyItemFormDTO();
	        if (supply != null) {
	            // Reutiliza tu SupplyMapper para mantener consistencia
	            dto.setSupplyItem(SupplyMapper.toEntity(supply)); 
	        }

	        return dto;
	    }
	 
//	 public static RequestItemFormDTO of(Request item) {
//		 if(item == null) return null;
//		 
//		 RequestItemFormDTO dto = new RequestItemFormDTO();
//		 dto.setId(item.getId());
//		 dto.setQuantity(item.getQuantity());
//		 
//		 SupplyItemFormDTO supply = SupplyMapper.of(item.getSupplyItem());
//		 UserFormDTO user = UserMapper.of(item.get)
//		 
//		 
//	 }
}
