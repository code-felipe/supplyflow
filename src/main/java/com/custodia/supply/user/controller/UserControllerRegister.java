package com.custodia.supply.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.service.IUserService;

import jakarta.validation.Valid;

@Controller
public class UserControllerRegister {

	@Autowired
	private IUserService userService;

	@PreAuthorize("permitAll()")
	@RequestMapping(value = "/register")
	public String createForm(Map<String, Object> model) {

		model.put("user", new UserFormDTO());
		model.put("title", "User form");

		return "register";
	}

	@PreAuthorize("permitAll()")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("user") UserFormDTO user, BindingResult result, Model model,
			RedirectAttributes flash) {

		// 1) SOLO en registro público: exigir el código
		  if (user.getInvitationCode() == null || user.getInvitationCode().isBlank()) {
			  result.rejectValue("invitationCode", "invitation.required", "Invitation code is required");
		  }

		  if (result.hasErrors()) {
		    model.addAttribute("title", "Create Account");
		    return "register"; // ← quedarse en el formulario
		  }

		  try {
		    // 2) Crea el usuario y consume el código (is_used=true, used_by=<nuevo user>)
		    userService.registerWithInvitation(user);

		  } catch (IllegalArgumentException | IllegalStateException ex) {
		    // Ej: "Invitation code not found" o "already used"
			  result.rejectValue("invitationCode", "invitation.error", ex.getMessage());
		    model.addAttribute("title", "Create Account");
		    return "register";
		  }

		  // 3) Solo si todo salió bien → redirige al login
		  flash.addFlashAttribute("success", "Account created. You can log in now.");
		  return "redirect:/login";
	}
}
