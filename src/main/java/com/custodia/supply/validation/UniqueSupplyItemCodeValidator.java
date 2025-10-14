package com.custodia.supply.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;
import com.custodia.supply.item.service.ISupplyItemService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueSupplyItemCodeValidator implements ConstraintValidator<UniqueSupplyItemCode, SupplyItemFormDTO> {
	@Autowired
    private ISupplyItemService supplyService;

	@Override
	public boolean isValid(SupplyItemFormDTO form, ConstraintValidatorContext context) {
		if (form == null) return true;

	    String code = form.getCode();
	    if (code == null || code.isBlank()) return true; // @NotBlank se encarga

	    Long id = form.getId();
	    boolean ok;

	    if (id == null) { // CREATE
	        ok = !supplyService.existsByCode(code);
	    } else {          // UPDATE
	        ok = !supplyService.existsByCodeAndIdNot(code, id);
	    }

	    if (!ok) {
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("{supplyItem.code.unique}")
	               .addPropertyNode("code")   // ← apunta al campo de ProductForm
	               .addConstraintViolation();
	    }

	    return ok;
    }
	

}
