package com.custodia.supply.validation.util;

public class NotFoundException extends DomainException{
	
	private final String resource;
	private final String identifier;
	
	protected NotFoundException(String resource, String identifier) {
		super(resource + "Not found: " + identifier);
		this.resource = resource;
		this.identifier = identifier;
		// TODO Auto-generated constructor stub
	}
	
	public String getResource() {return resource;}
	public String getIdentifier() {return identifier;}

}
