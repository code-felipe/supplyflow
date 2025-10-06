package com.custodia.supply.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.custodia.supply.user.entity.User;
import com.custodia.supply.user.service.IUserService;



@PreAuthorize("hasAnyRole('ADMIN')")
@Controller
@RequestMapping("/user")
public class UserActiveToggleController {
	
	@Autowired
	private IUserService userService;
	
	
	@RequestMapping(value = "/toggle/{id}", method = RequestMethod.POST)
	public String save(@PathVariable(value = "id") Long id,
			Map<String, Object> model, RedirectAttributes flash) {

		Boolean isActive = userService.toggleUserActive(id);
		
		if(isActive == null) {
			flash.addFlashAttribute("error", "The User not found");			
		}else if(isActive) {
			flash.addFlashAttribute("success", "The user is now active!");
		}
		else {
			flash.addFlashAttribute("warning", "The user is now disable!");
		}
		
		return "redirect:/user/list";
	}
}
