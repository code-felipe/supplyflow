package com.custodia.supply.user.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.custodia.supply.authority.service.IRoleService;
import com.custodia.supply.customer.dao.ICustomerSiteDao;
import com.custodia.supply.request.dao.IRequestDao;
import com.custodia.supply.request.dto.RequestRow;
import com.custodia.supply.user.dto.UserDetailView;
import com.custodia.supply.user.dto.UserForm;
import com.custodia.supply.user.entity.User;
import com.custodia.supply.user.service.IUserService;
import com.custodia.supply.util.country.CountryPhone;
import com.custodia.supply.util.enums.Role;
import com.custodia.supply.util.paginator.IPageableService;
import com.custodia.supply.util.paginator.PageRender;
import com.custodia.supply.util.phone.ICountryPhone;
import com.custodia.supply.web.util.FlashGuardsUser;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

@Controller
@RequestMapping("/user")
public class UserController {

	// For debug in authentication.
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private IPageableService<User> pageableEmployee;

	@Autowired
	private IUserService userService;

	@Autowired
	private ICustomerSiteDao customerDao;

	@Autowired
	private IRoleService roleService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); // "" -> null
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Manual way to implement this check using hasRole().
		if (auth != null) {
			logger.info(
					"Using static way SecurityContextHolder.getContext().getAuthentication(); Hello user authenticated, your user name is: "
							.concat(authentication.getName()));
		}

		if (hasRole("ROLE_ADMIN")) {
			logger.info("Hello ".concat(auth.getName().concat(" have access")));
		} else {
			logger.info("Hello ".concat(auth.getName().concat(" do NOT have access")));
		}

		/*
		 * Another way to authenticate by HttpServlet. Not need to implement private
		 * hasRole()
		 * 
		 */

		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,
				"ROLE_");// <--Prefix
		if (securityContext.isUserInRole("ADMIN")) {
			logger.info("Using SecurityContextHolderAwareRequestWrapper, Hello "
					.concat(auth.getName().concat(" have access")));
		} else {
			logger.info("Using SecurityContextHolderAwareRequestWrapper, Hello "
					.concat(auth.getName().concat(" do NOT have access")));

		}

		// Using request
		if (request.isUserInRole("ROLE_ADMIN")) {// <--Prefix is must added.
			logger.info("Using HttpServletRequest, Hello ".concat(auth.getName().concat(" have access")));
		} else {
			logger.info("Using HttpServletRequest, Hello ".concat(auth.getName().concat(" do NOT have access")));

		}

		Pageable pageRequest = PageRequest.of(page, 5, Sort.by("createAt").descending());

		Page<User> users = pageableEmployee.findAll(pageRequest);
		// Map to handle nulls in the list
		Page<UserDetailView> rows = users.map(UserDetailView::of);

		PageRender<UserDetailView> pageRender = new PageRender<>("/user/list", rows);

		model.addAttribute("title", "Users");
		model.addAttribute("users", rows);
		model.addAttribute("page", pageRender);

		return "user/list";

	}

	@PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR') or (hasRole('CONCIERGE') and @userSecurity.isSelf(#id, principal.username))")
	@GetMapping(value = "/view/{id}")
	public String view(@RequestParam(name = "page", defaultValue = "0") int page, @PathVariable(value = "id") Long id,
			Map<String, Object> model,
			RedirectAttributes flash) {

		// Requests from the user
		Pageable pageRequest = PageRequest.of(page, 5, Sort.by("createAt").descending());
//		User user = userService.findOne(id);
		Page<RequestRow> rows = userService.findRequestsByUserId(id, pageRequest);
		PageRender<RequestRow> pageRender = new PageRender<>("/user/view/" + id, rows);

		// Single consult brings all requests that match user id - Optimized with Left
		// join!.
		User u = userService.fetchByIdWithRequests(id);

		if (u == null) {
			flash.addFlashAttribute("error", "The user does not exists in the data base");
			return "redirect:/user/list";
		}

		if (!u.getIsActive()) {
			flash.addFlashAttribute("error", "Sorry the user is not active");
			return "redirect:/user/list";
		}
		UserDetailView user = UserDetailView.of(u); // Validates empties fields and set to No assigned.

		model.put("user", user);
		model.put("requests", rows);
		model.put("page", pageRender);
		model.put("title", "User Detail " + user.getFirstNameLabel());

		return "user/view";

		// Double consult, brings user and with getRequests brings requests
//		Optional<User> userOpt = userService.findById(id);

//		if (!userOpt.isPresent()) {
//			flash.addFlashAttribute("error", "The user does not exists in the data base");
//			return "redirect:/user/list";
//		}
//
//		if (!userOpt.get().getIsActive()) {
//			flash.addFlashAttribute("error", "Sorry the user is not active");
//			return "redirect:/user/list";
//		}
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/form")
	public String createForm(Map<String, Object> model) {

		model.put("user", new UserForm());
		model.put("roles", roleService.findAll());// <---No longer using Role.values()
		model.put("sites", customerDao.findAllForSelect());
		model.put("title", "User form");

		return "user/form";
	}


	// More robust than Secured
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("user") UserForm user, BindingResult result, Model model,
			RedirectAttributes flash) {


		if (result.hasErrors()) {
			model.addAttribute("title", user.getId() != null ? "Edit User" : "Create User");
			model.addAttribute("roles", roleService.findAll());// ensures display again in the select - not longer using
																// enumerate
			model.addAttribute("sites", customerDao.findAllForSelect());

			return "user/form";// verify this
		}

		String mensajeFlash = (user.getId() != null) ? "User was successfully edited!"
				: "User was created succesfully!";

		userService.save(user);

		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/user/list";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		User u = userService.findOne(id);

		// === Validates - Create Request - Edit - view
		if (u == null) {
			flash.addFlashAttribute("error", "The user does not exists");
			return "redirect:/user/list";
		}

		if (!u.getIsActive()) {
			flash.addFlashAttribute("error", "Sorry the user is not active");
			return "redirect:/user/list";
		}

		UserForm user = UserForm.of(u);

		model.put("user", user);
		model.put("roles", roleService.findAll());// not longer using enumerate
		model.put("sites", customerDao.findAllForSelect());
		model.put("title", "Edit User");
		return "user/form";
	}

	// Delete is not in use, is replace by active/no active
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			User user = userService.findOne(id);

			userService.delete(id);
			flash.addFlashAttribute("success", "User " + user.getFirstName() + " was successfully delete!");

		}
		return "redirect:/user/list";
	}
	
	// Redirects to my self page, for all roles.
//	@PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR') or "
//			+ "(hasRole('CONCIERGE') and @userSecurity.isSelf(#id, principal.username))")
	@GetMapping("/me")
	public String me(Authentication auth) {
		if (auth == null) {
			return "redirect:/login";
		}

		String email = auth.getName();
		Long userId = null;
		try {
			userId = userService.findByEmail(email).getId();
		} catch (Exception ignored) {
		}

		return (userId != null) ? "redirect:/user/view/" + userId : "redirect:/user/list";
	}

	// check for authentication based on security role default.
	private boolean hasRole(String role) {

		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return false;
		}

		Authentication auth = context.getAuthentication();

		if (auth == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		// Improve version
		return authorities.contains(new SimpleGrantedAuthority(role));
		// Old version
		/*
		 * for(GrantedAuthority authority : authorities) {
		 * if(role.equals(authority.getAuthority())) {
		 * logger.info("Hello user ".concat(auth.getName()).concat(" your role is: ").
		 * concat(authority.getAuthority())); return true; }
		 * 
		 * } return false;
		 */
	}
	

}
