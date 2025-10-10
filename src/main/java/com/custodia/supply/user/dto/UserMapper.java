package com.custodia.supply.user.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.custodia.supply.authority.dto.AuthorityViewDTO;
import com.custodia.supply.authority.entity.Role;
import com.custodia.supply.customer.dto.CustomerAccountViewDTO;
import com.custodia.supply.customer.dto.CustomerSiteViewDTO;
import com.custodia.supply.customer.entity.CustomerAccount;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.request.dto.RequestMapper;
import com.custodia.supply.request.dto.RequestViewDTO;
import com.custodia.supply.user.entity.User;

public class UserMapper {
	
	public static UserViewDTO toDTO(User user) {
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

	
	public static UserFormDTO of(User u) {
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

	
//	public static UserFormDTO of(User u) {
//	    if (u == null) return null;
//
//	    UserFormDTO form = new UserFormDTO();
//	    form.setId(u.getId());
//	    form.setFirstName(u.getFirstName());
//	    form.setLastName(u.getLastName());
//	    form.setEmail(u.getEmail());
//	    form.setPhone(u.getPhone());
//
//	    // ⚠️ Evita autounboxing de Boolean -> boolean
//	    form.setIsActive(Boolean.TRUE.equals(u.getIsActive()));
//
//	    form.setCreateAt(u.getCreateAt());
//	    form.setInvitationCode(null);
//	    // NO cargues password aquí: usa newPassword/confirmPassword en el POST
//
//	    // --- Site y Customer (null-safe) ---
//	    CustomerSite site = u.getAssignedSite();
//	    if (site != null) {
//	        form.setAssignedSiteId(site.getId());
//	        form.setAssignedSiteAddress(site.getAddress());
//	        form.setAssignedSiteCode(site.getExternalCode());
//
//	        CustomerAccount cust = site.getCustomer(); // o la entidad que corresponda
//	        if (cust != null) {
//	            form.setAssignedCustomerCode(site.getCustomer().getExternalCode());
//	            form.setAssignedCustomerName(site.getCustomer().getExternalCode());
//	            form.setAssignedCustomerEmail(site.getCustomer().getEmail());
//	        } else {
//	            // opcional: limpia/omite campos de customer
//	            form.setAssignedCustomerCode(null);
//	            form.setAssignedCustomerName(null);
//	            form.setAssignedCustomerEmail(null);
//	        }
//	    } else {
//	        // opcional: limpia/omite campos de site/customer
//	        form.setAssignedSiteId(null);
//	        form.setAssignedSiteAddress(null);
//	        form.setAssignedSiteCode(null);
//	        form.setAssignedCustomerCode(null);
//	        form.setAssignedCustomerName(null);
//	        form.setAssignedCustomerEmail(null);
//	    }
//
//	    // --- Role (null-safe) ---
//	    Role r = u.getRole();
//	    if (r != null) {
//	        form.setRoleId(r.getId());
//	        form.setRole(r.getAuthority());
//	    } else {
//	        form.setRoleId(null);
//	        form.setRole(null);
//	    }
//
//	    return form;
//	}

	
}
