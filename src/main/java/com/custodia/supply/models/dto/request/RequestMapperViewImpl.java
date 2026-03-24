package com.custodia.supply.models.dto.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.custodia.supply.models.dto.auth.AuthorityViewDTO;
import com.custodia.supply.models.dto.customer.CustomerAccountViewDTO;
import com.custodia.supply.models.dto.customer.CustomerSiteViewDTO;
import com.custodia.supply.models.dto.requestitem.RequestItemMapper;
import com.custodia.supply.models.dto.requestitem.RequestItemViewDTO;
import com.custodia.supply.models.dto.user.UserMapperViewImpl;
import com.custodia.supply.models.dto.user.UserViewDTO;
import com.custodia.supply.models.entity.auth.Role;
import com.custodia.supply.models.entity.customer.CustomerAccount;
import com.custodia.supply.models.entity.customer.CustomerSite;
import com.custodia.supply.models.entity.request.Request;
import com.custodia.supply.models.entity.requestitem.RequestItem;
import com.custodia.supply.models.entity.user.User;
import com.custodia.supply.util.mapper.IMapper;

@Component
public class RequestMapperViewImpl implements IMapper<RequestDTO, Request> {
	
	@Autowired
	private UserMapperViewImpl userMapper;
	
	@Override
	public Request toEntity(RequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDTO toDTO(Request request) {
		// TODO Auto-generated method stub
		if (request == null)
			return null;

		RequestDTO dto = new RequestDTO();
		dto.setId(request.getId());
		dto.setDescription(request.getDescription());
		dto.setAdditionalItems(request.getAdditionalItems());
		dto.setStatus(request.getStatus());

		UserViewDTO user = userMapper.toDTO(request.getUser());
		dto.setUser(user);

		CustomerSite site = request.getShipTo();
		if (site != null) {
			CustomerSiteViewDTO dtoSite = new CustomerSiteViewDTO();
			dtoSite.setId(site.getId());
			dtoSite.setAddress(site.getAddress());
			dtoSite.setCode(site.getExternalCode());

			dto.setCustomerSite(dtoSite);
		}
		
		dto.setItems(mapItems(request.getItems()));

		return dto;
	}

	@Override
	public void applyScalarFields(Request e, RequestDTO dto) {
		// TODO Auto-generated method stub

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

}
