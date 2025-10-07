package com.custodia.supply.user.dto;

import java.util.Date;

import com.custodia.supply.authority.entity.Role;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.user.entity.User;
import com.custodia.supply.user.util.OnRegister;
import com.custodia.supply.validation.UniqueEmail;
import com.custodia.supply.validation.ValidPhone;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@UniqueEmail // Valida a nivel clase
public class UserForm {

	private Long id;

	@Size(min = 2, max = 15, message = "{user.firstName.size}")
	@Pattern(regexp = "^[\\p{L}][\\p{L}'\\-\\s]{1,49}$", message = "{user.firstName.chars}")
	private String firstName;

	@Size(min = 2, max = 15, message = "{user.lastName.size}")
	@Pattern(regexp = "^[\\p{L}][\\p{L}'\\-\\s]{1,49}$", message = "{user.lastName.chars}")
	private String lastName;

	@Email
	@Size(max = 120)
	private String email;

	@ValidPhone
	private String phone;

//	@Enumerated(EnumType.STRING)
//	private Role role;

	private String role;
	private Long roleId;

	// obligatorio SOLO al crear
	private String password;

	private Date createAt;

	private Boolean isActive;

	/** Nuevo: lo que realmente queremos persistir en User.assignedSite */
	// Opcional en registro (admin lo setea después); puedes exigirlo en un grupo de
	// validación distinto.
	private Long assignedSiteId;

	/** Solo lectura para mostrar en pantalla (opcionales) ---- */
	private String assignedCustomerName; // derivado vía site.customer.name
	private String assignedCustomerCode;
	private String assginedCustomerEmail;

	private String assignedSiteAddress; // site.name (ShipTo name)
	private String assignedSiteCode; // site.externalCode (ShipTo code)
	
	private String invitationCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getAssignedSiteId() {
		return assignedSiteId;
	}

	public void setAssignedSiteId(Long assignedSiteId) {
		this.assignedSiteId = assignedSiteId;
	}

	public String getAssignedCustomerName() {
		return assignedCustomerName;
	}

	public void setAssignedCustomerName(String assignedCustomerName) {
		this.assignedCustomerName = assignedCustomerName;
	}

	public String getAssignedCustomerCode() {
		return assignedCustomerCode;
	}

	public void setAssignedCustomerCode(String assignedCustomerCode) {
		this.assignedCustomerCode = assignedCustomerCode;
	}
	
	public String getAssignedCustomerEmail() {
		return assginedCustomerEmail;
	}
	public void setAssignedCustomerEmail(String assignedCustomerEmail) {
		this.assginedCustomerEmail = assignedCustomerEmail;
	}

	public String getAssignedSiteAddress() {
		return assignedSiteAddress;
	}

	public void setAssignedSiteAddress(String assignedSiteAddress) {
		this.assignedSiteAddress = assignedSiteAddress;
	}

	public String getAssignedSiteCode() {
		return assignedSiteCode;
	}

	public void setAssignedSiteCode(String assignedSiteCode) {
		this.assignedSiteCode = assignedSiteCode;
	}
	
	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	// In case I need to map.
	public static UserForm of(User u) {
		UserForm form = new UserForm();
		form.id = u.getId();
		form.firstName = u.getFirstName();
		form.lastName = u.getLastName();
		form.email = u.getEmail();
		form.phone = u.getPhone();
		form.password = null;
		form.createAt = u.getCreateAt();
		form.isActive = u.getIsActive();
		
		form.setInvitationCode(null);

		if (u.getAssignedSite() != null) {
			CustomerSite c = u.getAssignedSite();
			form.setAssignedSiteId(c.getId());
			form.setAssignedSiteAddress(c.getAddress());
			form.setAssignedSiteCode(c.getExternalCode());
			// ← derivado: assignedCustomer
			if (u.getAssignedSite().getCustomer() != null) {
				form.setAssignedCustomerCode(c.getCustomer().getExternalCode());
				form.setAssignedCustomerName(c.getCustomer().getName());
				form.setAssignedCustomerEmail(c.getCustomer().getEmail());
			}
		}
		
		if(u.getRole() != null) {
			Role r = u.getRole();
			form.setRoleId(r.getId());
			form.setRole(r.getAuthority());
			
		}
		return form;
	}

}
