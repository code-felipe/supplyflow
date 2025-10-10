package com.custodia.supply.request.dto;

import com.custodia.supply.customer.dto.CustomerAccountViewDTO;
import com.custodia.supply.customer.dto.CustomerSiteViewDTO;
import com.custodia.supply.customer.entity.CustomerAccount;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.user.dto.UserViewDTO;
import com.custodia.supply.user.entity.User;

public class RequestMapper {
	  public static RequestViewDTO toDTO(Request r) {
	    if (r == null) return null;

	    RequestViewDTO dto = new RequestViewDTO();
	    dto.setId(r.getId());
	    dto.setAdditionalItems(r.getAdditionalItems());
	    dto.setDescription(r.getDescription()); // no lo olvides
	    dto.setCreateAt(r.getCreateAt()); 
	    dto.setSiteAddress(r.getShipTo().getAddress());
	    dto.setSiteCode(r.getShipTo().getExternalCode());
	    // <-- alinea nombres (o renombra getCreateAt a getCreatedAt en la entidad)
	
	    // dto.setTotalQuantity(r.getTotalQuantity()); // si existe en la entidad

	    // Site: preferir site propio del request si lo tiene
	    CustomerSite site = (r.getShipTo() != null)
	        ? r.getShipTo()
	        : (r.getUser() != null ? r.getUser().getAssignedSite() : null);

	    if (site != null) {
	      CustomerSiteViewDTO s = new CustomerSiteViewDTO();
	      s.setId(site.getId());
	      s.setAddress(site.getAddress());
	      s.setCode(site.getExternalCode());
	      dto.setCustomerSite(s);
	    } else {
	      dto.setCustomerSite(null);
	    }

	    // User del request (null-safe)
	    User userEntity = r.getUser();
	    if (userEntity != null) {
	      UserViewDTO u = new UserViewDTO();
	      u.setId(userEntity.getId());
	      u.setFirstName(userEntity.getFirstName());
	      u.setLastName(userEntity.getLastName());
	      u.setEmail(userEntity.getEmail());
	      u.setPhone(userEntity.getPhone());
	      u.setCreateAt(userEntity.getCreateAt());
	      u.setIsActive(userEntity.getIsActive());
	      dto.setUser(u);
	    } else {
	      dto.setUser(null);
	    }

	    return dto;
	  }
	}

