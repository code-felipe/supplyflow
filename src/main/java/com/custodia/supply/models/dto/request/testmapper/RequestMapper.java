package com.custodia.supply.models.dto.request.testmapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.custodia.supply.models.dto.customer.CustomerSiteViewDTO;
import com.custodia.supply.models.dto.request.RequestDTO;
import com.custodia.supply.models.dto.requestitem.RequestItemMapper;
import com.custodia.supply.models.dto.requestitem.RequestItemViewDTO;
import com.custodia.supply.models.dto.user.UserViewDTO;
import com.custodia.supply.models.dto.user.testmapper.UserMapper;
import com.custodia.supply.models.entity.customer.CustomerSite;
import com.custodia.supply.models.entity.request.Request;
import com.custodia.supply.models.entity.requestitem.RequestItem;
import com.custodia.supply.models.entity.user.User;

public class RequestMapper {

	public static RequestDTO toTree(Request request) {
		// TODO Auto-generated method stub
		if (request == null)
			return null;

		RequestDTO dto = new RequestDTO();
		dto.setId(request.getId());
		dto.setDescription(request.getDescription());
		dto.setAdditionalItems(request.getAdditionalItems());
		dto.setStatus(request.getStatus());

		User u = request.getUser();
		  if (u != null) {
		    UserViewDTO ref = UserMapper.toDetail(u);
		   
		    dto.setUser(ref); // <-- no vuelvas a mapear a UserViewDTO - bidireccional
		  } else {
		    dto.setUser(null);
		  }

		CustomerSite site = request.getShipTo();
		if (site != null) {
			CustomerSiteViewDTO dtoSite = new CustomerSiteViewDTO();
			dtoSite.setId(site.getId());
			dtoSite.setAddress(site.getAddress());
			dtoSite.setCode(site.getExternalCode());

			dto.setCustomerSite(dtoSite);
		}
		
		dto.setItems(RequestMapper.mapItems(request.getItems()));

		return dto;
	}

	public static List<RequestDTO> toDTOList(List<Request> requests) {
		if (requests == null)
			return new ArrayList<>();
		return requests.stream().map(RequestMapper::toTree).collect(Collectors.toList());
	}

	public static List<RequestItemViewDTO> mapItems(List<RequestItem> items) {
		if (items == null || items.isEmpty())
			return Collections.emptyList();
		List<RequestItemViewDTO> list = new ArrayList<>(items.size());
		for (int i = 0; i < items.size(); i++) {
			RequestItem it = items.get(i);
			list.add(RequestItemMapper.toDTO(it));
		}
		return list;
	}
}
