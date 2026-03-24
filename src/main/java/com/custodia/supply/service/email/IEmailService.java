package com.custodia.supply.service.email;

public interface IEmailService {
	
	public Boolean sendEmailWithHtml(String to, String subject, String boddy, Long userId);
}
