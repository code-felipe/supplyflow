package com.custodia.supply.validation;


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
@Constraint(validatedBy = UniqueSupplyItemCodeValidator.class)
public @interface UniqueSupplyItemCode {
	String message() default "{supplyItem.code.unique}"; // clave en messages.properties
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
