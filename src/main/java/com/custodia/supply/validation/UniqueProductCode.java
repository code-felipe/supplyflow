package com.custodia.supply.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//@Retention(RUNTIME)
//@Target({ FIELD, METHOD })
//@Constraint(validatedBy = UniqueProductCodeValidator.class)
//@Documented

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueProductCodeValidator.class)
public @interface UniqueProductCode {
	String message() default "{product.code.unique}"; // clave en messages.properties
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
