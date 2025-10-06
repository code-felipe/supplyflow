package com.custodia.supply;

import org.springframework.stereotype.Component;

import com.custodia.supply.user.entity.User;
import com.custodia.supply.user.service.IUserService;

@Component("userSecurity") 
public class UserSecurity {
	
	private final IUserService userService;

    public UserSecurity(IUserService userService) {
        this.userService = userService;
    }

    public boolean isSelf(Long pathId, String usernameOrEmail) {
        User u = userService.findByEmail(usernameOrEmail);
        return u != null && u.getId().equals(pathId);
    }

}
