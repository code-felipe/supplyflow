package com.custodia.supply.service.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.models.dao.auth.IRoleDao;
import com.custodia.supply.models.dao.customer.ICustomerAccountDao;
import com.custodia.supply.models.dao.customer.ICustomerSiteDao;
import com.custodia.supply.models.dao.invitation.IInvitationCodeDao;
import com.custodia.supply.models.dao.item.ISupplyItemDao;
import com.custodia.supply.models.dao.request.IRequestDao;
import com.custodia.supply.models.dao.user.IUserDao;
import com.custodia.supply.models.dto.item.SupplyItemFormDTO;
import com.custodia.supply.models.dto.item.SupplyItemViewDTO;
import com.custodia.supply.models.dto.item.SupplyMapper;
import com.custodia.supply.models.dto.request.RequestViewDTO;
import com.custodia.supply.models.dto.user.UserFormDTO;
import com.custodia.supply.models.dto.user.UserMapperFormImpl;
import com.custodia.supply.models.dto.user.UserViewDTO;
import com.custodia.supply.models.dto.user.testmapper.UserMapper;
import com.custodia.supply.models.entity.auth.Role;
import com.custodia.supply.models.entity.customer.CustomerAccount;
import com.custodia.supply.models.entity.customer.CustomerSite;
import com.custodia.supply.models.entity.invitation.InvitationCode;
import com.custodia.supply.models.entity.item.SupplyItem;
import com.custodia.supply.models.entity.request.Request;
import com.custodia.supply.models.entity.user.User;
import com.custodia.supply.service.auth.IRoleService;
import com.custodia.supply.util.handler.Handler;
import com.custodia.supply.util.paginator.IPageableService;
import com.custodia.supply.validation.util.Exceptions;

@Service
public class UserImpl implements IUserService, IPageableService<User> {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRequestDao requestDao;

	@Autowired
	private ICustomerAccountDao customerAccountDao;

	@Autowired
	private ICustomerSiteDao customerSiteDao;

	@Autowired
	private ISupplyItemDao supplyDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IInvitationCodeDao codeDao;
//
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserMapperFormImpl userMapper;

	@Override
	@Transactional(readOnly = true)
	public List<UserViewDTO> findAllTreeRest() {
		List<User> users = (List<User>) userDao.findAll();
		return UserMapper.toDTOList(users);

	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserViewDTO> findAllRest() {
		List<User> users = (List<User>) userDao.findAll();
		return UserMapper.toDTOAll(users);

	}
	
	
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return (List<User>) userDao.findAll();
	}
	

	@Override
	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		return userDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public User findOne(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.findByEmail(email);
	}

	// Brings all user requests for the user id
	@Override
	@Transactional(readOnly = true)
	public User fetchByIdWithRequests(Long id) {
		// TODO Auto-generated method stub
		return userDao.fetchByIdWithRequests(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SupplyItemViewDTO> findAllByName(String term) {
		// TODO Auto-generated method stub
		return supplyDao.findAllByNameLikeIgnoreCase(term).stream().map(s -> SupplyMapper.toView(s)).toList();
	}
	
	//== Not in use, is replaced by simple findAllRest
	@Override
	@Transactional(readOnly = true)
	public List<UserViewDTO> exportUsersTree() {
		// TODO Auto-generated method stub
		List<User> users = userDao.findAllWithRoleSiteCustomerAndRequests();

	    // 2) Cargar items (y supplyItems) de TODOS los requests de esos usuarios en una sola query
	    List<Long> userIds = users.stream().map(User::getId).toList();
	    if (!userIds.isEmpty()) {
	    	requestDao.fetchItemsForRequests(userIds);
	      // Esta consulta "enriquece" el Persistence Context: ahora r.getItems() ya viene cargado.
	    }

	    // 3) Mapear a DTOs (sin más SQL) y devolver
	    return UserMapper.toDTOList(users);
	}

	/*
	 * === Refactoring the save ===
	 */
	@Transactional
	public Boolean save(UserFormDTO form) {

		User user = this.loadOrCreate(form);

		userMapper.applyScalarFields(user, form);

		assignedRole(user, form);
	    if (form.getAssignedSiteId() != null) {
	        assignBySelect(user, form);
	    } else if (hasInlineSite(form)) {
	        assignByInline(user, form);
	    } else {
	        user.setAssignedSite(null);
	    }

		userDao.save(user);
		return true;
	}

	/*
	 * === REFACTOR THIS MONSTER ===
	 */
//	@Transactional
//	public Boolean save(UserFormDTO form) {
//
//		// Cargar/crear usuario
//		User user;
//		if (form.getId() != null) {
//			Optional<User> opt = userDao.findById(form.getId());
//			user = opt.orElse(new User());
//		} else {
//			user = new User();
//		}
//
//		if (user.getCreateAt() == null)
//			user.setCreateAt(new Date());
//
//		user.setFirstName(form.getFirstName());
//		user.setLastName(form.getLastName());
//		user.setEmail(form.getEmail());
//		user.setPhone(form.getPhone());
//		user.setIsActive(Boolean.TRUE.equals(form.getIsActive()));
//
//		if (hasText(form.getPassword())) {
//			user.setPassword(passwordEncoder.encode(form.getPassword()));
//		}
//
//		// ----- ROLE (estricto) -----
//		user = this.assignRoleFromForm(user, form);
////		if (form.getRoleId() != null) {
////			Role role = roleDao.findById(form.getRoleId()).orElse(null); // sin Supplier
////			if (role == null) {
////				throw new IllegalArgumentException("Invalid roleId: " + form.getRoleId());
////			}
////			user.setRole(role);
////		} else {
////			user.setRole(null);
////		}
//
//		// ----- SITE / CUSTOMER -----
//		if (form.getAssignedSiteId() != null) {
//			CustomerSite site = customerSiteDao.findById(form.getAssignedSiteId()).orElse(null);
//			if (site == null) {
//				throw new IllegalArgumentException("Invalid siteId: " + form.getAssignedSiteId());
//			}
//			user.setAssignedSite(site);
//
//		} else if (allInlinePresent(form)) {
//			String custCode = trimOrNull(form.getAssignedCustomerCode());
//			String custName = trimOrNull(form.getAssignedCustomerName());
//			String custEmail = trimOrNull(form.getAssignedCustomerEmail());
//			String siteCode = trimOrNull(form.getAssignedSiteCode());
//			String siteAddress = trimOrNull(form.getAssignedSiteAddress());
//
//			CustomerAccount acc = customerAccountDao.findByExternalCode(custCode).orElse(null);
//			if (acc == null) {
//				CustomerAccount n = new CustomerAccount();
//				n.setExternalCode(custCode);
//				n.setName(custName);
//				n.setEmail(custEmail);
//				acc = customerAccountDao.save(n);
//			}
//
//			CustomerSite site = customerSiteDao.findByCustomerAndExternalCode(acc, siteCode).orElse(null);
//			if (site == null) {
//				CustomerSite s = new CustomerSite();
//				s.setCustomer(acc);
//				s.setExternalCode(siteCode);
//				s.setAddress(siteAddress);
//				site = customerSiteDao.save(s);
//			}
//
//			user.setAssignedSite(site);
//
//		} else {
//			user.setAssignedSite(null);
//		}
//
//		userDao.save(user);
//		return true;
//	}

	@Override
	@Transactional
	public Boolean delete(Long id) {
		userDao.deleteById(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<RequestViewDTO> findRequestsByUserId(Long id, Pageable page) {
		return requestDao.findRowsByUserId(id, page);
	}

	@Override
	@Transactional(readOnly = true)
	public Request findRequestById(Long id) {
		// TODO Auto-generated method stub
		return requestDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Boolean saveRequest(Request request) {
		// TODO Auto-generated method stub
		Request r = requestDao.save(request);

		if (r != null) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public SupplyItem findSupplyItemById(Long id) {
		// TODO Auto-generated method stub
		return supplyDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	@Transactional
	public void deleteRequest(Long id) {
		// TODO Auto-generated method stub
		requestDao.deleteById(id);
	}

	// Optimization on request/view/id
	@Override
	@Transactional(readOnly = true)
	public Request fetchRequestByIdWithUserWithRequestItemWithSupplyItem(Long id) {
		// TODO Auto-generated method stub
		return requestDao.fetchByIdWithUserWithRequestItemWithSupplyItem(id);
	}

	@Override
	@Transactional
	public Boolean toggleUserActive(Long id) {
		// TODO Auto-generated method stub
		User user = userDao.findById(id).orElse(null);
		if (user == null)
			return null;

		boolean next = !Boolean.TRUE.equals(user.getIsActive());
		user.setIsActive(next);

		return next;
	}

	@Override
	@Transactional
	public User registerWithInvitation(UserFormDTO form) {
		String raw = form.getInvitationCode();

		if (raw == null || raw.isBlank()) {
			throw new IllegalArgumentException("Invitation code is required");
		}
		String codeStr = raw.trim().toUpperCase();

		// Searcg the row on invitation_code
		InvitationCode inv = codeDao.findByCode(codeStr);
		if (inv == null)
			throw new IllegalArgumentException("Invitation code not found");
		if (Boolean.TRUE.equals(inv.getIsUsed()))
			throw new IllegalStateException("Invitation code already used");

		// Create user
		User user = loadOrCreate(form);
		userMapper.applyScalarFields(user, form);
		assignedRole(user, form);
//		User u = new User();
//		u.setFirstName(form.getFirstName());
//		u.setLastName(form.getLastName());
//		u.setEmail(form.getEmail());
//		u.setPhone(form.getPhone());
//		u.setIsActive(Boolean.TRUE);
//		if (form.getPassword() != null && !form.getPassword().isBlank()) {
//			u.setPassword(passwordEncoder.encode(form.getPassword()));
//		}
//		u.setCreateAt(u.getCreateAt() == null ? new Date() : u.getCreateAt());

		// Rol por authority (o getReferenceById si prefieres)
//		Role concierge = roleDao.findByAuthority("ROLE_CONCIERGE");
//		if (concierge != null)
//			u.setRole(concierge);

		userDao.save(user);

		// 4) Consumir ese MISMO código
		inv.setIsUsed(true);
		inv.setIsUsedBy(user);
		codeDao.save(inv);

		return user;
	}

	// ====Helpers ====

	private void assignedRole(User user, UserFormDTO form) {
		if (form.getRoleId() == null) {
			user.setRole(null);
			return;
		}
		Role role = roleDao.findById(form.getRoleId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid roleId: " + form.getRoleId()));

		user.setRole(role);
	}

	private void assignBySelect(User user, UserFormDTO form) {
	    Long siteId = form.getAssignedSiteId();
	    if (siteId == null) {
	        user.setAssignedSite(null);
	        return;
	    }
	    CustomerSite site = customerSiteDao.findById(siteId)
	        .orElseThrow(() -> new IllegalArgumentException("Invalid siteId: " + siteId));
	    user.setAssignedSite(site);
	}
	private void assignByInline(User user, UserFormDTO form) {
	    if (user.getAssignedSite() != null) return;
	    if (!hasInlineSite(form)) {
	        user.setAssignedSite(null);
	        return;
	    }

	    String custCode  = trimOrNull(form.getAssignedCustomerCode());
	    String custName  = trimOrNull(form.getAssignedCustomerName());
	    String custEmail = trimOrNull(form.getAssignedCustomerEmail());
	    String siteCode  = trimOrNull(form.getAssignedSiteCode());
	    String siteAddr  = trimOrNull(form.getAssignedSiteAddress());

	    // Search or create, but always update
	    CustomerAccount acc = customerAccountDao.findByExternalCode(custCode)
	            .orElseGet(CustomerAccount::new);

	    acc.setExternalCode(custCode);
	    acc.setName(custName);
	    acc.setEmail(custEmail);         // persists email
	    customerAccountDao.save(acc);    // insert o update

	    CustomerSite site = customerSiteDao.findByCustomerAndExternalCode(acc, siteCode)
	            .orElseGet(CustomerSite::new);

	    site.setCustomer(acc);
	    site.setExternalCode(siteCode);
	    site.setAddress(siteAddr);
	    customerSiteDao.save(site);      // insert or update

	    user.setAssignedSite(site);
	}
//	private void assignByInline(User user, UserFormDTO form) {
//	    // No hagas nada si alguien ya asignó el site por select
//	    if (user.getAssignedSite() != null) return;
//
//	    if (!hasInlineSite(form)) {
//	        // Nada que crear/ligar; se deja en null
//	        user.setAssignedSite(null);
//	        return;
//	    }
//
//	    String custCode = trimOrNull(form.getAssignedCustomerCode());
//	    String custName = trimOrNull(form.getAssignedCustomerName());
//	    String custEmail = trimOrNull(form.getAssignedCustomerEmail());
//	    String siteCode = trimOrNull(form.getAssignedSiteCode());
//	    String siteAddr = trimOrNull(form.getAssignedSiteAddress()); // NOT NULL en BD
//
//	    // Por seguridad, evita queries con parámetros null
//	    CustomerAccount acc = customerAccountDao.findByExternalCode(custCode).orElseGet(() -> {
//	        CustomerAccount n = new CustomerAccount();
//	        n.setExternalCode(custCode); // Asegúrate que esta columna permita NOT NULL o valida aquí
//	        n.setName(custName);
//	        n.setEmail(custEmail);
//	        return customerAccountDao.save(n);
//	    });
//
//	    CustomerSite site = customerSiteDao.findByCustomerAndExternalCode(acc, siteCode).orElseGet(() -> {
//	        CustomerSite s = new CustomerSite();
//	        s.setCustomer(acc);
//	        s.setExternalCode(siteCode);
//	        s.setAddress(siteAddr); // ← ya garantizamos que no es null con hasInlineSite(...)
//	        return customerSiteDao.save(s);
//	    });
//
//	    user.setAssignedSite(site);
//	}
	

	private User loadOrCreate(UserFormDTO dto) {
		if (dto.getId() == null) {
			User u = new User();
			u.setCreateAt(new Date());
			return u;
		}
		// Update estricto: si no existe → error
		return userDao.findById(dto.getId())
				.orElseThrow(() -> new IllegalArgumentException("User not found id=" + dto.getId()));

	}

//	private void setAssigmentChaing(User user, UserFormDTO dto) {
//		Handler<User, UserFormDTO> head = new ByIdSiteHandler(customerSiteDao);
//		head.setNext(new ByCustomerAccountHandler(customerAccountDao, customerSiteDao))
//				.setNext(new DefaultNullUserHandler());
//
//		head.handle(user, dto);
//
//	}

//	private boolean allInlinePresent(UserFormDTO f) {
//		return notBlank(f.getAssignedCustomerCode()) && notBlank(f.getAssignedCustomerName())
//				&& notBlank(f.getAssignedSiteCode()) && notBlank(f.getAssignedSiteAddress());
//	}

//	private boolean notBlank(String s) {
//		return s != null && !s.isBlank();
//	}

	private String trimOrNull(String s) {
		if (s == null)
			return null;
		String t = s.trim();
		return t.isEmpty() ? null : t;
	}
	
	private boolean hasInlineSite(UserFormDTO f) {
	    return trimOrNull(f.getAssignedCustomerEmail()) != null
	    	&&trimOrNull(f.getAssignedCustomerName()) != null 
	    	&& trimOrNull(f.getAssignedCustomerCode()) != null
	        && trimOrNull(f.getAssignedSiteCode()) != null
	        && trimOrNull(f.getAssignedSiteAddress()) != null;
	}



}
