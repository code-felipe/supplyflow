package com.custodia.supply;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	 @GetMapping({"/", ""})
	  public String root() {
	    return "redirect:/user/list"; // o "/user/" si prefieres
	  }
}
