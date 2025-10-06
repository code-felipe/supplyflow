package com.custodia.supply.web.util;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface IFlashGuards<T> {
	
	public Optional<String> require(T t, RedirectAttributes flash,
			String flashKey, String flashMsg, String redirectUrl);
		
	public Optional<String> requireUserBound(T t, RedirectAttributes flash,
	Function<Long, String> urlOnFail);
	
	public  Optional<String> requireActiveUser(T t, RedirectAttributes flash,
	Function<Long, String> urlOnFail);

}
