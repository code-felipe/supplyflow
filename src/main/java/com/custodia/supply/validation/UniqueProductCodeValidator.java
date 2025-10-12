package com.custodia.supply.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.custodia.supply.item.dto.ProductForm;
import com.custodia.supply.item.dto.product.ProductFormDTO;
import com.custodia.supply.item.service.IProductService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueProductCodeValidator implements ConstraintValidator<UniqueProductCode, ProductFormDTO> {
	@Autowired
    private IProductService productService;

	@Override
	public boolean isValid(ProductFormDTO form, ConstraintValidatorContext context) {
		if (form == null) return true;

	    String code = form.getCode();
	    if (code == null || code.isBlank()) return true; // @NotBlank se encarga

	    Long id = form.getId();
	    boolean ok;

	    if (id == null) { // CREATE
	        ok = !productService.existsByCode(code);
	    } else {          // UPDATE
	        ok = !productService.existsByCodeAndIdNot(code, id);
	    }

	    if (!ok) {
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("{product.code.unique}")
	               .addPropertyNode("code")   // ← apunta al campo de ProductForm
	               .addConstraintViolation();
	    }

	    return ok;
    }
	

}
