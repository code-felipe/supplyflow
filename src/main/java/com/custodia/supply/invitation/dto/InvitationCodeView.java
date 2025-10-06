package com.custodia.supply.invitation.dto;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.custodia.supply.authority.entity.Role;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.invitation.entity.InvitationCode;
import com.custodia.supply.user.entity.User;

public class InvitationCodeView {
	private Long idInvitationCode;
	private String code;
	private Boolean isUsed;
	private String createdByUserFullName;
	private String isUsedByUserFullName;
	private Date createAt;
//	private Role role;
	
	// User
	private Long idUser;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Long roleId;
	private String roleAuthority;
	private String password;
	private Boolean isActive;


	/** Nuevo: lo que realmente queremos persistir en User.assignedSite */
	// Opcional en registro (admin lo setea después); puedes exigirlo en un grupo de
	// validación distinto.
	private Long assignedSiteId;

	/** Solo lectura para mostrar en pantalla (opcionales) ---- */
	private String assignedCustomerName; // derivado vía site.customer.name
	private String assignedCustomerCode;

	private String assignedSiteAddress; // site.name (ShipTo name)
	private String assignedSiteCode; // site.externalCode (ShipTo code)
	
	
	public Long getIdInvitationCodeLabel() {
		return idInvitationCode;
	}
	
	public String getCodeLabel() {
		return (code == null || code.isBlank())? "No generated" : code;
	}
	
	public Boolean getIsUsedLabel() {
		return isUsed;
	}
	
	public String getCreatedByUserFullNameLabel() {
		return (createdByUserFullName == null || createdByUserFullName.isBlank()) ? "No asigned" : createdByUserFullName;
	}
	
	public String getIsUsedByUserFullNameLabel() {
		return (isUsedByUserFullName == null || isUsedByUserFullName.isBlank()) ? "No asigned": isUsedByUserFullName;
	}
	
	public Long getIdLabel() {
		return idUser;
	}

	public String getFirstNameLabel() {
		return (firstName == null || firstName.isBlank()) ? "No asigned" : firstName;
	}

	public String getLastNameLabel() {
		return (lastName == null || lastName.isBlank()) ? "No asigned" : lastName;
	}

	public String getPhoneLabel() {
		return (phone == null || phone.isBlank()) ? "No asigned" : phone;
	}

	public String getEmailLabel() {
		return (email == null || email.isBlank()) ? "No asigned" : email;
	}

	public String getRoleLabel() {
		return (roleAuthority == null || roleAuthority.isBlank()) ? "No asigned" : roleAuthority;
	}

	public String getRoleIdLabel() {
		return (roleId == null || roleId <= 0) ? "No asigned" : String.valueOf(roleId);
	}

	public String getPasswordLabel() {
		return (password == null || password.isBlank()) ? "No asigned" : password;
	}

	public Date getCreateAtLabel() {
		return createAt;
	}

	public String getIsActiveLabel() {
		return (isActive == null ? "No active" : isActive.toString());
	}

	public String getAssignedSiteIdLabel() {
		return (assignedSiteId == null || assignedSiteId <= 0) ? "No asigned" : String.valueOf(assignedSiteId);
	}

	public String getAssignedCustomerNameLabel() {
		return (assignedCustomerName == null || assignedCustomerName.isBlank()) ? "No asigned" : assignedCustomerName;
	}

	public String getAssignedCustomerCodeLabel() {
		return (assignedCustomerCode == null || assignedCustomerCode.isBlank()) ? "No asigned" : assignedCustomerCode;
	}

	public String getAssignedSiteAddressLabel() {
		return (assignedSiteAddress == null || assignedSiteAddress.isBlank()) ? "No asigned" : assignedSiteAddress;
	}

	public String getAssignedSiteCodeLabel() {
		return (assignedSiteCode == null || assignedSiteCode.isBlank()) ? "No asigned" : assignedSiteCode;
	}

	public static InvitationCodeView of(InvitationCode c) {
		
		InvitationCodeView v = new InvitationCodeView();
	    v.idInvitationCode = c.getId();
	    v.code = c.getCode();
	    v.isUsed = c.getIsUsed();
	    v.createAt = c.getCreateAt();
	    
	    User createdBy = c.getCreatedBy();
	    v.createdByUserFullName = (createdBy != null) ? createdBy.getFirstName() + " " + createdBy.getLastName() : null;

	    User usedBy = c.getIsUsedBy();
	    v.isUsedByUserFullName = (usedBy != null) ? usedBy.getFirstName()+ " " + createdBy.getLastName() : null;

	    return v;
	}

	private static String toDisplayName(User u) {
	    if (u == null) return null;
	    String first = safe(u.getFirstName());
	    String last  = safe(u.getLastName());
	    String full  = (first + " " + last).trim();
	    if (!full.isBlank()) return full;
	    String email = safe(u.getEmail());
	    return email.isBlank() ? null : email;
	}

	private static String safe(String s) {
	    return (s == null) ? "" : s;
	}

}
