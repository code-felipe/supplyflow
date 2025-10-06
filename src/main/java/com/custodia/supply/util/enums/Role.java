package com.custodia.supply.util.enums;

public enum Role {
	ADMIN("ADMIN"),
	CONCIERGE("CONCIERGE");
	
	private final String displayRole;
	
	Role(String role){
		this.displayRole = role;
	}
	
	public String getDisplay() {
		return this.displayRole;
	}
}
