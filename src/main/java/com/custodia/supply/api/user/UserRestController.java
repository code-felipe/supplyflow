package com.custodia.supply.api.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import com.custodia.supply.models.dto.user.UserViewDTO;
import com.custodia.supply.service.user.IUserService;


@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private IUserService userService;

	// == In use ==
		@PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
		@GetMapping(value = "/list-rest-tree")
		public List<UserViewDTO> listTreeRest() {
			return userService.findAllTreeRest();
		}

		// == In use ==
		@PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
		@GetMapping(value = "/list-rest")
		public List<UserViewDTO> listRest() {
			return userService.findAllRest();
		}
}
