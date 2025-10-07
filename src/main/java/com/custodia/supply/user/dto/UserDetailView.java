package com.custodia.supply.user.dto;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.custodia.supply.authority.entity.Role;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.user.entity.User;

public class UserDetailView {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
//	private Role role;

	private Long roleId;
	private String roleAuthority;

	private String password;
	private Boolean isActive;
	private Date createAt;

	/** Nuevo: lo que realmente queremos persistir en User.assignedSite */
	// Opcional en registro (admin lo setea después); puedes exigirlo en un grupo de
	// validación distinto.
	private Long assignedSiteId;

	/** Solo lectura para mostrar en pantalla (opcionales) ---- */
	private String assignedCustomerName; // derivado vía site.customer.name
	private String assignedCustomerCode;
	private String assignedCustomerEmail; 

	private String assignedSiteAddress; // site.name (ShipTo name)
	private String assignedSiteCode; // site.externalCode (ShipTo code)

	public Long getIdLabel() {
		return id;
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
	
	public String getAssignedCustomerEmailLabel() {
		return (assignedCustomerEmail == null || assignedCustomerEmail.isBlank()) ? "No asigned" : assignedCustomerEmail;
	}

	public String getAssignedSiteAddressLabel() {
		return (assignedSiteAddress == null || assignedSiteAddress.isBlank()) ? "No asigned" : assignedSiteAddress;
	}

	public String getAssignedSiteCodeLabel() {
		return (assignedSiteCode == null || assignedSiteCode.isBlank()) ? "No asigned" : assignedSiteCode;
	}

	public static UserDetailView of(User u) {
		UserDetailView row = new UserDetailView();
		row.id = u.getId();
		row.firstName = u.getFirstName();
		row.lastName = u.getLastName();
		row.email = u.getEmail();
		row.phone = u.getPhone();
		row.password = u.getPassword();
		row.createAt = u.getCreateAt();
		row.isActive = u.getIsActive();

		Role r = u.getRole(); // ← aquí está el valor real
		if (r != null) {
			row.roleId = r.getId();
			row.roleAuthority = r.getAuthority(); // "ADMIN" / "SUPERVISOR" / "CONCIERGE"
		} else {
			row.roleId = null;
			row.roleAuthority = null; // getRoleLabel() mostrará "No asigned"
		}
		if (u.getAssignedSite() != null) {
			CustomerSite c = u.getAssignedSite();
			row.assignedSiteId = c.getId();
			row.assignedSiteAddress = c.getAddress();
			row.assignedSiteCode = c.getExternalCode();
			// ← derivado: assignedCustomer
			if (u.getAssignedSite().getCustomer() != null) {
				row.assignedCustomerCode = c.getCustomer().getExternalCode();
				row.assignedCustomerName = c.getCustomer().getName();
				row.assignedCustomerEmail = c.getCustomer().getEmail();
			}
		}

		System.out.println(row.getRoleLabel());
		return row;
	}
}
