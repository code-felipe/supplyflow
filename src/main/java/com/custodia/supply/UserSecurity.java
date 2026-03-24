package com.custodia.supply;

import org.springframework.stereotype.Component;

import com.custodia.supply.models.entity.user.User;
import com.custodia.supply.service.user.IUserService;

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
