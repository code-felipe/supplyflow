package com.custodia.supply.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.custodia.supply.item.dto.SupplyItemForm;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.request.dto.RequestRow;
import com.custodia.supply.request.dto.RequestViewDTO;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.entity.User;

public interface IUserService {
	
	public List<User> findAll();
	
	public User findOne(Long id);
	
	// Brings all requests that match user id.
	public User fetchByIdWithRequests(Long id);
	
	public Boolean save(UserFormDTO userForm);
	
	public Boolean delete(Long id);
	
	//=== Allows authentication and permissions using email
	public User findByEmail(String email);
	
	/*=== Uses Custom query RequestDao ===
	 * Brings only the requestItems based on user Id -> helps to validate with UserRow DTO
	 */
//	public Page<RequestRow> findRequestsByUserId(Long id, Pageable page);
	public Page<RequestViewDTO> findRequestsByUserId(Long id, Pageable page);

	/*=== Uses Custom query UserDao ===
	 *  Brings only the user attributes and CustomerSite -> helps to validate with UserDetailView DTO
	 */
	public Optional<User> findById(Long id);
	
	/*=== Find the SupplyItem by product name using jQuery and JPA
	 */
	
	public List<SupplyItemForm> findAllByName(String term);
	
	/*Save the request in the user*/
	public Boolean saveRequest(Request request);
	
	public SupplyItem findSupplyItemById(Long id);
	
	public Request findRequestById(Long id);
	
	public void deleteRequest(Long id);
	
	// Optimization on request/view/id
	public Request fetchRequestByIdWithUserWithRequestItemWithSupplyItem(Long id);
	
	/*=== Toggle active no active ===*/
	public Boolean toggleUserActive(Long id);
	
	// == Register with invi ==
	public User registerWithInvitation(UserFormDTO form);
	
	public User updateUser(UserFormDTO form);
	
	
}
