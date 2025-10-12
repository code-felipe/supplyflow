package com.custodia.supply.request.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.item.dto.SupplyItemForm;
import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;
import com.custodia.supply.item.dto.supply.SupplyItemViewDTO;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.request.dto.UserRequestFormDTO;
import com.custodia.supply.request.dto.UserRequestMapper;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.requestitem.entity.RequestItem;
import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.dto.UserMapper;
import com.custodia.supply.user.entity.User;
import com.custodia.supply.user.service.IUserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/request")
@SessionAttributes("request")
public class RequestController {

	@Autowired
	private IUserService userService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/form/{userId}")
	public String make(@PathVariable(value = "userId") Long userId, Map<String, Object> model,
			RedirectAttributes flash) {

		User u = userService.findOne(userId);
//		User u = userService.fetchByIdWithRequests(userId);
		if (u == null) {
			flash.addAttribute("error", "The user does not exists in the data base");
			return "redirect:/list";
		}

		if (!u.getIsActive()) {
			flash.addFlashAttribute("error", "Sorry the user is not active");
			return "redirect:/user/list";
		}
		
//		UserRequestFormDTO request = UserRequestMapper.toDTO(u);
		
		Request request = new Request();
		
		request.setUser(u);

		model.put("request", request);
		model.put("title", "Create Request");

		return "request/form";
	}

	// Load supplyItems in user view - table
	@GetMapping(value = "/load-items/{term}", produces = { "application/json" })
	public @ResponseBody List<SupplyItemForm> loadSupplyItems(@PathVariable String term) {
		return userService.findAllByName(term);
	}

	@GetMapping("/view/{id}")
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
//		Request request = userService.findRequestById(id);
		Request request = userService.fetchRequestByIdWithUserWithRequestItemWithSupplyItem(id);

		if (request == null) {
			flash.addFlashAttribute("error", "The request does not exists on the data base!");
			return "redirect:/user/list";
		}

		if (!request.getUser().getIsActive()) {
			flash.addFlashAttribute("error", "Sorry the user is not active");
			return "redirect:/user/list";
		}

		model.put("request", request);
		model.put("title", "Request: ".concat(request.getDescription()));

		return "request/view";
	}

	@PostMapping("/form")
	public String save(@Valid Request request, BindingResult result, Map<String, Object> model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "quantity[]", required = false) Integer[] quantity, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.put("title", "Create Request");
			return "request/form";
		}

		if (itemId == null || itemId.length == 0) {

			model.put("title", "Create Request");
			model.put("error", "Error: The request NEEDS to have items!");
			return "request/form";
		}

		if (request == null || request.getUser().getId() == null) {
			flash.addFlashAttribute("warning", "No user bound to the request.");
			return "redirect:/user/view/" + request.getUser().getId();
		}

		if (request.getUser().getAssignedSite() == null || request.getUser().getAssignedSite().getCustomer() == null) {
			flash.addFlashAttribute("warning", "You must to assign a customer to the request");
			return "redirect:/user/view/" + request.getUser().getId();
		}

		CustomerSite site = new CustomerSite();
		site.setId(request.getUser().getAssignedSite().getId());

		for (int i = 0; i < itemId.length; i++) {
			SupplyItem item = userService.findSupplyItemById(itemId[i]);

			RequestItem line = new RequestItem();
			line.setQuantity(quantity[i]);
			line.setSupplyItem(item);

			request.addRequestItem(line);

			log.info("ID: " + itemId[i].toString() + ", quantity: " + quantity[i].toString());
		}
		request.setShipTo(site);

		userService.saveRequest(request);

		status.setComplete();

		flash.addFlashAttribute("success", "Request created successfully!");

		return "redirect:/user/view/" + request.getUser().getId();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Request request = userService.findRequestById(id);

		if (request != null) {
			userService.deleteRequest(id);
			flash.addFlashAttribute("success", "Request deleted succesfully!");
			return "redirect:/user/view/" + request.getUser().getId();
		}

		flash.addFlashAttribute("error", "Request can not be deleted!");

		return "redirect:/user/list";
	}



}
