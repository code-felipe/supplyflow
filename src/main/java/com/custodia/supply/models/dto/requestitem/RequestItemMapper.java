package com.custodia.supply.models.dto.requestitem;
import com.custodia.supply.models.dto.item.SupplyItemFormDTO;
import com.custodia.supply.models.dto.item.SupplyMapper;
import com.custodia.supply.models.entity.item.SupplyItem;
import com.custodia.supply.models.entity.requestitem.RequestItem;



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
	        SupplyItemFormDTO supply = new SupplyItemFormDTO();
	        if (supply != null) {
	            // Reutiliza  SupplyMapper para mantener consistencia
	            dto.setSupplyItem(SupplyMapper.toEntity(supply)); 
	        }

	        return dto;
	    }
	 
}
