package com.custodia.supply.invitation.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.custodia.supply.invitation.entity.InvitationCode;
import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.entity.User;

public interface IInvitationCodeService {
	
	public List<InvitationCode> findAll();
	
	public InvitationCode findOne(Long id);
	
	public InvitationCode save (InvitationCode code);
	
	public InvitationCode findByCode(String code);
	
	
	
	public InvitationCode generateUniqueCode(Authentication auth);
}
