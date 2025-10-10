package com.custodia.supply.validation.customer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCustomerAccountEmailValidator.class)
public @interface UniqueCustomerAccountEmail {
	String message() default "{customer.email.unique}";
	  Class<?>[] groups() default {};
	  Class<? extends Payload>[] payload() default {};
	  String emailField() default "assignedCustomerEmail";
	  String siteIdField() default "assignedSiteId";
}
