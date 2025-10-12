package com.custodia.supply.item.dto.supply;
import com.custodia.supply.authority.entity.Role;
import com.custodia.supply.customer.entity.CustomerAccount;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.item.dto.product.ProductFormDTO;
import com.custodia.supply.item.dto.product.ProductViewDTO;
import com.custodia.supply.item.entity.Product;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.entity.User;

import jakarta.validation.constraints.NotEmpty;

public class SupplyMapper {
	
	public static SupplyItemViewDTO toDTO(SupplyItem item) {
		if(item == null)return null;
		
		SupplyItemViewDTO dto = new SupplyItemViewDTO();
		dto.setId(item.getId());
		dto.setPackagingCode(item.getPackagingCode());
		dto.setSpecification(item.getSpecification());
		dto.setUnitOfMeasure(item.getUnitOfMeasure());
		dto.setCreateAt(item.getCreateAt());
		
		Product product = item.getProduct();
		if(product != null) {
			ProductViewDTO p = new ProductViewDTO();
			p.setId(product.getId());
			p.setCode(product.getCode());
			p.setName(product.getName());
			dto.setProduct(p);
		}else {
			dto.setProduct(null);
		}
		return dto;
	}
	
	public static SupplyItemFormDTO of(SupplyItem item) {
		if(item == null) return null;
		SupplyItemFormDTO dto = new SupplyItemFormDTO();
		dto.setId(item.getId());
		dto.setPackagingCode(item.getPackagingCode());
		dto.setSpecification(item.getSpecification());
		dto.setUnitOfMeasure(item.getUnitOfMeasure());
		dto.setCreateAt(item.getCreateAt());
		
		
		Product product = item.getProduct();
		if(product != null) {
			ProductFormDTO p = new ProductFormDTO();
			p.setId(product.getId());
			p.setCode(product.getCode());
			p.setName(product.getName());
			dto.setProduct(p);
		}else {
			dto.setProduct(null);
		}
		return dto;
	}
	
}
	/*
	 *public static UserFormDTO of(User u) {
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
	 */

