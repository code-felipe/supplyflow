package com.custodia.supply.login;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;


@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Model model, Principal principal,
			RedirectAttributes flash) {
	    
		if(principal != null){
			//if true, is because have already signed in
			flash.addFlashAttribute("info", "You have already logged in before ");
			return "redirect:/user/list";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error in the login: User name or password are incorrect, please try again");
		}
		
		if(logout != null) {
			model.addAttribute("success", "You have successfully logged out ");
		}
		
		return "login";
	}
}
