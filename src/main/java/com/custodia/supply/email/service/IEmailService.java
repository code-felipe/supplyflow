package com.custodia.supply.email.service;

public interface IEmailService {
	
	public Boolean sendEmailWithHtml(String to, String subject, String boddy, Long userId);
}
