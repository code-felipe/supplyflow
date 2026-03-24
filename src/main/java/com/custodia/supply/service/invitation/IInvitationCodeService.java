package com.custodia.supply.service.invitation;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.custodia.supply.models.dto.user.UserFormDTO;
import com.custodia.supply.models.entity.invitation.InvitationCode;
import com.custodia.supply.models.entity.user.User;

public interface IInvitationCodeService {
	
	public List<InvitationCode> findAll();
	
	public InvitationCode findOne(Long id);
	
	public InvitationCode save (InvitationCode code);
	
	public InvitationCode findByCode(String code);
	
	
	
	public InvitationCode generateUniqueCode(Authentication auth);
}
