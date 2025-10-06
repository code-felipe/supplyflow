package com.custodia.supply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class CustodianSupplyManagerApplication implements CommandLineRunner {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(CustodianSupplyManagerApplication.class, args);
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
	    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
	    source.setBasename("messages");
	    source.setDefaultEncoding("UTF-8");
	    return source;
	}
	
	// For upload files and generate passwords
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	
		String password = "12345";
		
		for(int i = 0; i<3; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
			
		}
	}
	
	
	
	
}
