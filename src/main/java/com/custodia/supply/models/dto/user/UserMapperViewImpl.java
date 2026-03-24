package com.custodia.supply.models.dto.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.custodia.supply.models.dto.auth.AuthorityViewDTO;
import com.custodia.supply.models.dto.customer.CustomerAccountViewDTO;
import com.custodia.supply.models.dto.customer.CustomerSiteViewDTO;
import com.custodia.supply.models.dto.request.RequestMapperViewImpl;
import com.custodia.supply.models.dto.request.RequestViewDTO;
import com.custodia.supply.models.dto.requestitem.RequestItemMapper;
import com.custodia.supply.models.dto.requestitem.RequestItemViewDTO;
import com.custodia.supply.models.entity.auth.Role;
import com.custodia.supply.models.entity.customer.CustomerAccount;
import com.custodia.supply.models.entity.customer.CustomerSite;
import com.custodia.supply.models.entity.request.Request;
import com.custodia.supply.models.entity.requestitem.RequestItem;
import com.custodia.supply.models.entity.user.User;
import com.custodia.supply.util.mapper.IMapper;

@Component
public class UserMapperViewImpl implements IMapper<UserViewDTO, User>{
	
	
	@Override
	public User toEntity(UserViewDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserViewDTO toDTO(User user) {
		if (user == null) return null;

	    UserViewDTO dto = new UserViewDTO();
	    dto.setId(user.getId());
	    dto.setFirstName(user.getFirstName());
	    dto.setLastName(user.getLastName());
	    dto.setEmail(user.getEmail());
	    dto.setPhone(user.getPhone());
	    dto.setCreateAt(user.getCreateAt());
	    dto.setIsActive(user.getIsActive());

	    // Role (null-safe)
	    Role role = user.getRole();
	    if (role != null) {
	        AuthorityViewDTO auth = new AuthorityViewDTO();
	        auth.setId(role.getId());
	        auth.setRole(role.getAuthority());
	        dto.setAuthority(auth);
	    } else {
	        dto.setAuthority(null);
	    }

	    // Site (null-safe)
	    CustomerSite site = user.getAssignedSite(); // ← una sola lectura
	    if (site != null) {
	        CustomerSiteViewDTO s = new CustomerSiteViewDTO();
	        s.setId(site.getId());
	        s.setAddress(site.getAddress());
	        s.setCode(site.getExternalCode());
	        dto.setCustomerSite(s);
	    } else {
	        dto.setCustomerSite(null);
	    }

	    // Customer (elige una de estas dos formas)
	    // A) usando tu helper @Transient:
	    CustomerAccount cust = user.getAssignedCustomer();
	    // B) o desde la variable 'site':
	    // CustomerAccount cust = (site != null) ? site.getCustomer() : null;

	    if (cust != null) {
	        CustomerAccountViewDTO a = new CustomerAccountViewDTO();
	        a.setId(cust.getId());
	        a.setName(cust.getName());
	        a.setCode(cust.getExternalCode());
	        a.setEmail(cust.getEmail());
	        dto.setCustomerAccount(a);
	    } else {
	        dto.setCustomerAccount(null);
	    }

	    return dto;
	}
	
	
	//=== For service NO USE IS A VIEW!-====
	@Override
	public void applyScalarFields(User e, UserViewDTO dto) {
		// TODO Auto-generated method stub
		
	}
	
	    

}
