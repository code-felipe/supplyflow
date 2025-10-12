package com.custodia.supply.request.dto;


import com.custodia.supply.user.entity.User;

public class UserRequestMapper {
	  public static UserRequestFormDTO toDTO(User u) {
	    if (u == null) return null;

	    UserRequestFormDTO dto = new UserRequestFormDTO();
	    dto.setId(u.getId());
	    dto.setFirstName(u.getFirstName());
	    dto.setLastName(u.getLastName());
	    String description = "";
	    String addtionalItems = "";
	    u.getRequests().stream().forEach(r -> {
	    	description.concat(r.getDescription());
	    	addtionalItems.concat(r.getAdditionalItems());
	    });
	    dto.setAdditionalItems(addtionalItems);
	    dto.setDescription(description);
	    
	

	    return dto;
	  }
	}

