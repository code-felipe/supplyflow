package com.custodia.supply.invitation.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.custodia.supply.invitation.entity.InvitationCode;


public interface IInvitationCodeDao extends PagingAndSortingRepository<InvitationCode, Long>, CrudRepository<InvitationCode, Long> {
	
	public InvitationCode findByCode(String code);
}
