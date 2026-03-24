package com.custodia.supply.models.dao.invitation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.custodia.supply.models.entity.invitation.InvitationCode;


public interface IInvitationCodeDao extends PagingAndSortingRepository<InvitationCode, Long>, CrudRepository<InvitationCode, Long> {
	
	public InvitationCode findByCode(String code);
}
