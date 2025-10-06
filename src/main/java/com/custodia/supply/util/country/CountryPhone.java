package com.custodia.supply.util.country;

public class CountryPhone {
	private String name;
	private String flag;
	private String dialCode;
	private String code;

	public CountryPhone() {
	}

	public CountryPhone(String name, String flag, String code, String dialCode) {
		this.name = name;
		this.flag = flag;
		this.code = code;
		this.dialCode = dialCode;
	}

	public String getName() {
		return name;
	}

	public String getFlag() {
		return flag;
	}

	public String getDialCode() {
		return dialCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setDialCode(String dialCode) {
		this.dialCode = dialCode;
	}

	public String getCode() {
		return code;
	}


	
	
}
