package com.custodia.supply.models.dto.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.custodia.supply.models.entity.auth.Role;
import com.custodia.supply.models.entity.customer.CustomerAccount;
import com.custodia.supply.models.entity.customer.CustomerSite;
import com.custodia.supply.models.entity.user.User;
import com.custodia.supply.util.mapper.IMapper;

@Component
public class UserMapperFormImpl implements IMapper<UserFormDTO, User>{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User toEntity(UserFormDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserFormDTO toDTO(User u) {
		if (u == null) return null;
	    UserFormDTO f = new UserFormDTO();

	    f.setId(u.getId());
	    f.setFirstName(u.getFirstName());
	    f.setLastName(u.getLastName());
	    f.setEmail(u.getEmail());
	    f.setPhone(u.getPhone());
	    f.setPassword(null);
	    f.setCreateAt(u.getCreateAt());
	    f.setIsActive(u.getIsActive());
	    f.setInvitationCode(null);

	    CustomerSite c = u.getAssignedSite();
	    if (c != null) {
	        f.setAssignedSiteId(c.getId());
	        f.setAssignedSiteAddress(c.getAddress());
	        f.setAssignedSiteCode(c.getExternalCode());
	    } else {
	        f.setAssignedSiteId(null);
	        f.setAssignedSiteAddress(null);
	        f.setAssignedSiteCode(null);
	    }

	    CustomerAccount ca = u.getAssignedCustomer(); // ← helper
	    if (ca != null) {
	        f.setAssignedCustomerCode(ca.getExternalCode());
	        f.setAssignedCustomerName(ca.getName());
	        f.setAssignedCustomerEmail(ca.getEmail());
	    } else {
	        f.setAssignedCustomerCode(null);
	        f.setAssignedCustomerName(null);
	        f.setAssignedCustomerEmail(null);
	    }

	    Role r = u.getRole();
	    if (r != null) {
	        f.setRoleId(r.getId());
	        f.setRole(r.getAuthority());
	    } else {
	        f.setRoleId(null);
	        f.setRole(null);
	    }

	    return f;
	}
	
	//=== For service 
	
	@Override
	public void applyScalarFields(User user, UserFormDTO form) {
		user.setFirstName(form.getFirstName());
	    user.setLastName(form.getLastName());
	    user.setEmail(form.getEmail());
	    user.setPhone(form.getPhone());
	    user.setIsActive(Boolean.TRUE.equals(form.getIsActive()));

	    if (hasText(form.getPassword())) {
	        user.setPassword(passwordEncoder.encode(form.getPassword()));
	    }
		
	    loadDate(user);
	}
	
	private static void loadDate(User user) {
		if(user.getCreateAt() == null) {
			user.setCreateAt(new Date());
		}
	}
	private static boolean hasText(String s) {
		return s != null && !s.trim().isEmpty();
	}

}
