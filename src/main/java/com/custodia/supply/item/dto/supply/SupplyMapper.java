package com.custodia.supply.item.dto.supply;
import com.custodia.supply.category.dto.CategoryDTO;
import com.custodia.supply.item.dto.embed.DimensionsDTO;
import com.custodia.supply.item.dto.embed.PackagingDTO;
import com.custodia.supply.item.entity.SupplyItem;


public class SupplyMapper {
	
	public static SupplyItemViewDTO toDTO(SupplyItem item) {
		if(item == null)return null;
		
		SupplyItemViewDTO dto = new SupplyItemViewDTO();
		dto.setId(item.getId());
		dto.setCode(item.getCode());
		dto.setName(item.getName());
		dto.setSpecification(item.getSpecification());
		dto.setCategory(item.getCategory().getName());
		dto.setCreateAt(item.getCreateAt());
		
		dto.setDimLength(item.getDimensions().getLenght());
		dto.setDimWeight(item.getDimensions().getWeight());
		dto.setDimHeight(item.getDimensions().getHeight());
		dto.setDimUom(item.getDimensions().getUom().name());
		
		dto.setUnitsPerPack(item.getPackaging().getUnitsPerPack());
		dto.setPacksPerCase(item.getPackaging().getPacksPerCase());
		dto.setCasesPerPallet(item.getPackaging().getCasesPerPallet());
		dto.setPkgUom(item.getPackaging().getUom().name());
		
		return dto;
	}
	
	public static SupplyItemFormDTO of(SupplyItem item) {
		if(item == null) return null;
		
		SupplyItemFormDTO dto = new SupplyItemFormDTO();
		dto.setId(item.getId());
		dto.setCode(item.getCode());
		dto.setName(item.getName());
		dto.setSpecification(item.getSpecification());
		dto.setCreateAt(item.getCreateAt());
		
		CategoryDTO cat = new CategoryDTO();
		cat.setId(item.getCategory().getId());
		cat.setName(item.getCategory().getName());
		cat.setDescription(item.getCategory().getDescription());
		
		DimensionsDTO dim = new DimensionsDTO();
		dim.setHeight(item.getDimensions().getHeight());
		dim.setLength(item.getDimensions().getLenght());
		dim.setWeight(item.getDimensions().getWeight());
		dim.setWidth(item.getDimensions().getWidth());
		dim.setUom(item.getDimensions().getUom());
		
		PackagingDTO pkg = new PackagingDTO();
		pkg.setCasesPerPallet(item.getPackaging().getCasesPerPallet());
		pkg.setPacksPerCase(item.getPackaging().getPacksPerCase());
		pkg.setUnitsPerPack(item.getPackaging().getUnitsPerPack());
		pkg.setUom(item.getPackaging().getUom());
		
		dto.setCategory(cat);
		dto.setDimensions(dim);
		dto.setPackaging(pkg);
		
		return dto;
		
	}
	
//	public static SupplyItemFormDTO of(SupplyItem item) {
//		if(item == null) return null;
//		SupplyItemFormDTO dto = new SupplyItemFormDTO();
//		dto.setId(item.getId());
//		dto.setPackagingCode(item.getPackagingCode());
//		dto.setSpecification(item.getSpecification());
//		dto.setUnitOfMeasure(item.getUnitOfMeasure());
//		dto.setCreateAt(item.getCreateAt());
//		
//		
//		Product product = item.getProduct();
//		if(product != null) {
//			ProductFormDTO p = new ProductFormDTO();
//			p.setId(product.getId());
//			p.setCode(product.getCode());
//			p.setName(product.getName());
//			dto.setProduct(p);
//		}else {
//			dto.setProduct(null);
//		}
//		return dto;
//	}
	
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

