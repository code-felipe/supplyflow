package com.custodia.supply.customer.dto;

import com.custodia.supply.customer.entity.CustomerAccount;

public class CustomerAccountMapper {
	
	public static CustomerAccountFormDTO toForm(CustomerAccount acc) {
		if(acc == null) return null;
		
		CustomerAccountFormDTO cusAcc = new CustomerAccountFormDTO();
		cusAcc.setId(acc.getId());
		cusAcc.setCode(acc.getExternalCode());
		cusAcc.setName(acc.getEmail());
		cusAcc.setEmail(acc.getEmail());
		
		return cusAcc;
	}
	
	public static CustomerAccount toEntity(CustomerAccountFormDTO acc) {
		CustomerAccount account = new CustomerAccount();
		account.setId(acc.getId());
		account.setEmail(acc.getEmail());
		account.setExternalCode(acc.getCode());
		account.setName(acc.getName());
		
		return account;
	}
}
