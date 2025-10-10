package com.custodia.supply.validation.customer;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.custodia.supply.user.dao.IUserDao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueCustomerAccountEmailValidator implements ConstraintValidator<UniqueCustomerAccountEmail, Object> {

	@Autowired
	private IUserDao userDao;

	private String emailField;
	private String siteIdField;

	@Override
	public void initialize(UniqueCustomerAccountEmail a) {
		this.emailField = a.emailField();
		this.siteIdField = a.siteIdField();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext ctx) {
		// Usa BeanWrapper para evitar errores de reflexión
		BeanWrapper bw = new BeanWrapperImpl(value);
		String email = (String) bw.getPropertyValue(emailField);
		Long siteId = (Long) bw.getPropertyValue(siteIdField);

		// Si seleccionaron un site existente, no validamos inline
		if (siteId != null)
			return true;

		// Sin email → que lo validen @Email/@NotBlank si corresponde
		if (email == null || email.trim().isEmpty())
			return true;

		boolean exists = userDao.existsByEmailIgnoreCase(email.trim());
		if (!exists)
			return true;

		// Si existe, marca el campo 'assignedCustomerEmail'
		ctx.disableDefaultConstraintViolation();
		ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate()).addPropertyNode(emailField)
				.addConstraintViolation();
		return false;
	}
}
