package com.custodia.supply.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.custodia.supply.user.dao.IUserDao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, Object> {

	@Autowired
	private IUserDao userDao;

	private String emailField;
	private String idField;

	@Override
	public void initialize(UniqueEmail anno) {
		this.emailField = anno.emailField();
		this.idField = anno.idField();
	}

	// Does not shows error message below the input
//		@Override
//		public boolean isValid(Object value, ConstraintValidatorContext target) {
//			BeanWrapper  wrapper = new BeanWrapperImpl(value);
//	        String email = (String) wrapper.getPropertyValue(emailField);
//	        Long id = (Long) wrapper.getPropertyValue(idField);
//
//	        if (email == null || email.isBlank()) return true; // opcional, no valida vacíos
//
//	        String normalized = email.trim().toLowerCase();
//
//	        // CREATE: id == null
//	        if (id == null) {
//	            return !userDao.existsByEmailIgnoreCase(normalized);
//	        }
//
//	        // UPDATE: excluir mi propio id
//	        return !userDao.existsByEmailIgnoreCaseAndIdNot(normalized, id);
//		}

	// Shows error below the input

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper wrapper = new BeanWrapperImpl(value);
		String email = (String) wrapper.getPropertyValue(emailField);
		Long id = (Long) wrapper.getPropertyValue(idField);

		if (email == null || email.isBlank())
			return true; // deja que @Email/@Size manejen vacíos

		String normalized = email.trim().toLowerCase();

		boolean unique = (id == null) ? !userDao.existsByEmailIgnoreCase(normalized)
				: !userDao.existsByEmailIgnoreCaseAndIdNot(normalized, id);

		if (!unique) {
			// Enlaza el error al campo "email" en vez de global
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode(emailField) // <- clave: liga al input email
					.addConstraintViolation();
		}

		return unique;
	}

}
