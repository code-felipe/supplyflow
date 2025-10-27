package com.custodia.supply.user.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.authority.dao.IRoleDao;
import com.custodia.supply.authority.entity.Role;
import com.custodia.supply.authority.service.IRoleService;
import com.custodia.supply.customer.dao.ICustomerAccountDao;
import com.custodia.supply.customer.dao.ICustomerSiteDao;
import com.custodia.supply.customer.entity.CustomerAccount;
import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.invitation.dao.IInvitationCodeDao;
import com.custodia.supply.invitation.entity.InvitationCode;
import com.custodia.supply.item.dao.ISupplyItemDao;
import com.custodia.supply.item.dto.SupplyItemForm;
import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;
import com.custodia.supply.item.dto.supply.SupplyItemViewDTO;
import com.custodia.supply.item.dto.supply.SupplyMapper;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.request.dao.IRequestDao;
import com.custodia.supply.request.dto.RequestViewDTO;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.user.dao.IUserDao;
import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.entity.User;
import com.custodia.supply.util.paginator.IPageableService;

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

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
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

	/*
	 * === NEEDS REFACTOR THIS MONSTER ===
	 */
	@Transactional
	public Boolean save(UserFormDTO form) {

		// Cargar/crear usuario
		User user;
		if (form.getId() != null) {
			Optional<User> opt = userDao.findById(form.getId());
			user = opt.orElse(new User());
		} else {
			user = new User();
		}

		if (user.getCreateAt() == null)
			user.setCreateAt(new Date());

		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		user.setPhone(form.getPhone());
		user.setIsActive(Boolean.TRUE.equals(form.getIsActive()));

		if (hasText(form.getPassword())) {
			user.setPassword(passwordEncoder.encode(form.getPassword()));
		}

		// ----- ROLE (estricto) -----
		user = this.assignRoleFromForm(user, form);
//		if (form.getRoleId() != null) {
//			Role role = roleDao.findById(form.getRoleId()).orElse(null); // sin Supplier
//			if (role == null) {
//				throw new IllegalArgumentException("Invalid roleId: " + form.getRoleId());
//			}
//			user.setRole(role);
//		} else {
//			user.setRole(null);
//		}

		// ----- SITE / CUSTOMER -----
		if (form.getAssignedSiteId() != null) {
			CustomerSite site = customerSiteDao.findById(form.getAssignedSiteId()).orElse(null);
			if (site == null) {
				throw new IllegalArgumentException("Invalid siteId: " + form.getAssignedSiteId());
			}
			user.setAssignedSite(site);

		} else if (allInlinePresent(form)) {
			String custCode = trimOrNull(form.getAssignedCustomerCode());
			String custName = trimOrNull(form.getAssignedCustomerName());
			String custEmail = trimOrNull(form.getAssignedCustomerEmail());
			String siteCode = trimOrNull(form.getAssignedSiteCode());
			String siteAddress = trimOrNull(form.getAssignedSiteAddress());

			CustomerAccount acc = customerAccountDao.findByExternalCode(custCode).orElse(null);
			if (acc == null) {
				CustomerAccount n = new CustomerAccount();
				n.setExternalCode(custCode);
				n.setName(custName);
				n.setEmail(custEmail);
				acc = customerAccountDao.save(n);
			}

			CustomerSite site = customerSiteDao.findByCustomerAndExternalCode(acc, siteCode).orElse(null);
			if (site == null) {
				CustomerSite s = new CustomerSite();
				s.setCustomer(acc);
				s.setExternalCode(siteCode);
				s.setAddress(siteAddress);
				site = customerSiteDao.save(s);
			}

			user.setAssignedSite(site);

		} else {
			user.setAssignedSite(null);
		}

		userDao.save(user);
		return true;
	}

	@Override
	@Transactional
	public Boolean delete(Long id) {
		userDao.deleteById(id);
		return true;
	}

	// ==== USER AND REQUESTS ===
	// all requests from the user mapped to the user detail supply items
//	@Override
//	@Transactional(readOnly = true)
//	public Page<RequestRow> findRequestsByUserId(Long id, Pageable page) {
//		return requestDao.findRowsByUserId(id, page);
//	}
//	
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

		// 2) Buscar fila en invitation_code
		InvitationCode inv = codeDao.findByCode(codeStr);
		if (inv == null)
			throw new IllegalArgumentException("Invitation code not found");
		if (Boolean.TRUE.equals(inv.getIsUsed()))
			throw new IllegalStateException("Invitation code already used");

		// 3) Crear user
		User u = new User();
		u.setFirstName(form.getFirstName());
		u.setLastName(form.getLastName());
		u.setEmail(form.getEmail());
		u.setPhone(form.getPhone());
		u.setIsActive(Boolean.TRUE);
		if (form.getPassword() != null && !form.getPassword().isBlank()) {
			u.setPassword(passwordEncoder.encode(form.getPassword()));
		}
		u.setCreateAt(u.getCreateAt() == null ? new Date() : u.getCreateAt());

		// Rol por authority (o getReferenceById si prefieres)
		Role concierge = roleDao.findByAuthority("ROLE_CONCIERGE");
		if (concierge != null)
			u.setRole(concierge);

		u = userDao.save(u);

		// 4) Consumir ese MISMO código
		inv.setIsUsed(true);
		inv.setIsUsedBy(u);
		codeDao.save(inv);

		return u;
	}

//	@Override
//	@Transactional
//	public User updateUser(UserFormDTO form) {
//		 User u = userDao.findById(form.getId())
//		            .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//		    u.setFirstName(form.getFirstName());
//		    u.setLastName(form.getLastName());
//		    u.setPhone(form.getPhone());
//		    // si permites cambiar email/rol, agrégalo con tus reglas
//		    if (form.getPassword() != null && !form.getPassword().isBlank()) {
//		        u.setPassword(passwordEncoder.encode(form.getPassword()));
//		    }
//		    return userDao.save(u);
//	}

	// ====Helpers ====

	private User assignRoleFromForm(User user, UserFormDTO form) {

		Long roleId = form.getRoleId();
		if (roleId == null) {
			user.setRole(null);
			return user;
		}
		Role role = roleDao.findById(roleId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid roleId: " + roleId));
		user.setRole(role);
		return user;
	}

	private boolean allInlinePresent(UserFormDTO f) {
		return notBlank(f.getAssignedCustomerCode()) && notBlank(f.getAssignedCustomerName())
				&& notBlank(f.getAssignedSiteCode()) && notBlank(f.getAssignedSiteAddress());
	}

	private boolean notBlank(String s) {
		return s != null && !s.isBlank();
	}

	private String trimOrNull(String s) {
		return s == null ? null : s.trim();
	}

	private static boolean hasText(String s) {
		return s != null && !s.trim().isEmpty();
	}

}
