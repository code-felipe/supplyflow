package com.custodia.supply.request.dto.rdto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.custodia.supply.customer.dto.CustomerSiteFormDTO;
import com.custodia.supply.customer.dto.CustomerSiteMapper;
import com.custodia.supply.customer.dto.CustomerSiteViewDTO;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.requestitem.dto.RequestItemFormDTO;
import com.custodia.supply.requestitem.dto.RequestItemMapper;
import com.custodia.supply.requestitem.dto.RequestItemViewDTO;
import com.custodia.supply.requestitem.entity.RequestItem;
import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.dto.UserMapper;
import com.custodia.supply.user.dto.UserViewDTO;
import com.custodia.supply.user.entity.User;


public class RequestMapper {
	
	public static RequestDTO toView(Request request) {
		if(request == null) return null;
		
		RequestDTO dto = new RequestDTO();
		dto.setId(request.getId());
		dto.setDescription(request.getDescription());
		dto.setAdditionalItems(request.getAdditionalItems());
		dto.setStatus(request.getStatus());
		
		
		UserViewDTO user = UserMapper.toView(request.getUser());
		dto.setUser(user);
		
		CustomerSite site = request.getShipTo();
		if(site != null) {
			CustomerSiteViewDTO dtoSite = new CustomerSiteViewDTO(); 
			dtoSite.setId(site.getId());
			dtoSite.setAddress(site.getAddress());
			dtoSite.setCode(site.getExternalCode());
			
			dto.setCustomerSite(dtoSite);
		}
		dto.setItems(mapItems(request.getRequests()));
		
		return dto;
		
	}
	
	
	public static RequestFormDTO of(User user) {
		UserFormDTO userDTO = UserMapper.toForm(user);
		
		RequestFormDTO reDTO = new RequestFormDTO();
		reDTO.setUser(userDTO);
	
		
		return reDTO;
		
	}
    private static List<RequestItemViewDTO> mapItems(List<RequestItem> items) {
        if (items == null || items.isEmpty()) return Collections.emptyList();
        List<RequestItemViewDTO> list = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            RequestItem it = items.get(i);
            list.add(RequestItemMapper.toDTO(it));
        }
        return list;
    }
    
//	public static Request toEntity(RequestFormDTO request) {
//	Request r = new Request();
//	r.setId(request.getId());
//	r.setAdditionalItems(request.getAdditionalItems());
//	r.setDescription(request.getDescription());
//	r.setCreateAt(request.getCreateAt());
//	
//	r.setShipTo(CustomerSiteMapper.toEntity(request.getShipTo()));
//	r.setUser(request.getUser());
//	
//}
    
}
