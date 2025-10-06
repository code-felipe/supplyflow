package com.custodia.supply.validation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
	String message() default "{user.email.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String emailField() default "email";
    String idField() default "id";
}
