package com.custodia.supply.authority.dto;

public class AuthorityViewDTO {

	private Long id;
	private String roleAuthority;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleLabel() {
		return (roleAuthority == null || roleAuthority.isBlank()) ? "No asigned" : roleAuthority;
	}
	
	public void setRole(String roleAuthority) {
		this.roleAuthority = roleAuthority;
	}

}
