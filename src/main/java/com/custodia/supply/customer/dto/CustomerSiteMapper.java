package com.custodia.supply.customer.dto;

import com.custodia.supply.customer.entity.CustomerAccount;
import com.custodia.supply.customer.entity.CustomerSite;

public class CustomerSiteMapper {
	
	public static CustomerSiteFormDTO toForm(CustomerSite site) {
		if(site == null) return null;
		
		CustomerSiteFormDTO dto = new CustomerSiteFormDTO();
		dto.setId(site.getId());
		dto.setCode(site.getExternalCode());
		dto.setAddress(site.getAddress());
		
		CustomerAccountFormDTO acc = CustomerAccountMapper.toForm(site.getCustomer());
		
		dto.setCustomer(acc);
		
		return dto;
		
		
	}
	
	public static CustomerSite toEntity(CustomerSiteFormDTO site) {
		CustomerSite s = new CustomerSite();
		s.setId(site.getId());
		s.setAddress(site.getAddress());
		s.setExternalCode(site.getCode());
		
		CustomerAccount acc = CustomerAccountMapper.toEntity(site.getCustomer());
		s.setCustomer(acc);
		
		return s;
		
	}
}
