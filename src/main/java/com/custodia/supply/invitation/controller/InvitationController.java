package com.custodia.supply.invitation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.custodia.supply.invitation.dto.InvitationCodeView;
import com.custodia.supply.invitation.entity.InvitationCode;
import com.custodia.supply.invitation.service.IInvitationCodeService;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.user.dto.suspended.UserDetailView;
import com.custodia.supply.util.paginator.IPageableService;
import com.custodia.supply.util.paginator.PageRender;

@Controller
@RequestMapping("/invitation_code")
public class InvitationController {
	
	@Autowired
	private IInvitationCodeService codeService;
	
	@Autowired
	private IPageableService<InvitationCode> codePageable;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0")int page, Model model) {
		/*		Page<UserDetailView> rows = users.map(UserDetailView::of);

		PageRender<UserDetailView> pageRender = new PageRender<>("/user/list", rows);
		 */
		Pageable pageRequest = PageRequest.of(page, 5, Sort.by("createAt").descending());
		
		Page<InvitationCode> invitationCodes = codePageable.findAll(pageRequest);
		
		Page<InvitationCodeView> invitations = invitationCodes.map(InvitationCodeView::of);
		
		PageRender<InvitationCodeView> pageRender = new PageRender<>("/invivation_code/list", invitations);
		
		
		model.addAttribute("title", "Invitation Code");
		model.addAttribute("invitations", invitations);
		model.addAttribute("page", pageRender);
		
		return "invitation_code/list";
		
	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String save(Map<String, Object> model,
			Authentication auth, RedirectAttributes flash) {

		InvitationCode code = codeService.generateUniqueCode(auth);
		System.out.println("code: " + code.getCreatedBy());
		if(code == null) {
			flash.addFlashAttribute("error", "The User not found");		
		}
		
		return "redirect:/invitation_code/list";
	}
}
