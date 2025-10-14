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

	/* === Find the SupplyItem by product name using jQuery and JPA */
	@Autowired
	private ISupplyItemDao supplyDao;
	
	@Autowired
	private IRoleService rolseService;

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
	public List<SupplyItemFormDTO> findAllByName(String term) {
		// TODO Auto-generated method stub
		return supplyDao.findAllByCodeLikeIgnoreCase(term).stream().map(s -> SupplyMapper.of(s)).toList();
	}

	/*
	 * === NEEDS REFACTOR ===
	 */
	@Transactional
	public Boolean save(UserFormDTO form) {

	    User user = (form.getId() != null)
	        ? userDao.findById(form.getId()).orElse(new User())
	        : new User();

	    if (user.getCreateAt() == null) user.setCreateAt(new Date());

	    user.setFirstName(form.getFirstName());
	    user.setLastName(form.getLastName());
	    user.setEmail(form.getEmail());
	    user.setPhone(form.getPhone());
	    user.setIsActive(Boolean.TRUE.equals(form.getIsActive()));

	    if (hasText(form.getPassword())) {
	        user.setPassword(passwordEncoder.encode(form.getPassword()));
	    }

	    // Role por ID (o null)
	    if (form.getRoleId() != null) {
	        Role role = roleDao.findById(form.getRoleId())
	            .orElseThrow(() -> new IllegalArgumentException("Invalid roleId"));
	        user.setRole(role);
	    } else {
	        user.setRole(null);
	    }

	    // --- SITE / CUSTOMER ---
	    // A) Selección de site existente
	    if (form.getAssignedSiteId() != null) {
	        CustomerSite site = customerSiteDao.findById(form.getAssignedSiteId())
	            .orElseThrow(() -> new IllegalArgumentException("Invalid siteId"));
	        user.setAssignedSite(site);

	    // B) Alta inline de CustomerAccount + CustomerSite
	    } else if (allInlinePresent(form)) {
	        // Normaliza/recorta
	        String custCode    = trimOrNull(form.getAssignedCustomerCode());
	        String custName    = trimOrNull(form.getAssignedCustomerName());
	        String custEmail   = trimOrNull(form.getAssignedCustomerEmail()); // asegúrate que el DTO NO tenga el typo
	        String siteCode    = trimOrNull(form.getAssignedSiteCode());
	        String siteAddress = trimOrNull(form.getAssignedSiteAddress());

	        // 1) Buscar o crear CustomerAccount por externalCode (único)
	        CustomerAccount acc = customerAccountDao.findByExternalCode(custCode).orElseGet(() -> {
	            CustomerAccount n = new CustomerAccount();
	            n.setExternalCode(custCode);
	            n.setName(custName);
	            n.setEmail(custEmail);
	            return customerAccountDao.save(n);   // guarda primero la cuenta
	        });

	        // 2) Buscar o crear CustomerSite por (customer, externalCode)
	        CustomerSite site = customerSiteDao.findByCustomerAndExternalCode(acc, siteCode).orElseGet(() -> {
	            CustomerSite s = new CustomerSite();
	            s.setCustomer(acc);                  // vincula al account
	            s.setExternalCode(siteCode);
	            s.setAddress(siteAddress);
	            return customerSiteDao.save(s);      // guarda el site
	        });

	        // 3) Asignar el site recién obtenido/creado al user
	        user.setAssignedSite(site);

	    // C) Nada seleccionado y sin inline completo: desasignar
	    } else {
	        user.setAssignedSite(null);
	    }

	    userDao.save(user);
	    return true;
	}


	/*
	@Override
	@Transactional
	public Boolean save(UserFormDTO form) {
		User user;

		if (form.getId() != null) {
			user = userDao.findById(form.getId()).orElse(new User());
			if (user.getCreateAt() == null) {
				user.setCreateAt(new Date());
			}
		} else {
			user = new User();
			user.setCreateAt(new Date());
		}

		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		user.setPhone(form.getPhone());
		user.setIsActive(form.getIsActive());

		// Only codify is password is new
		 if (hasText(form.getPassword())) {
		        user.setPassword(passwordEncoder.encode(form.getPassword()));
		   }
		 if(form.getIsActive() != null) {
			 user.setIsActive(form.getIsActive());
		 }
		
	
        if (form.getRoleId() != null) {
            Role role = rolseService.findOne(form.getRoleId());
            user.setRole(role);
        } else {
            user.setRole(null);
        }

		// Set Customer to User
		// Asignar CustomerSite (SE PERSISTE)
		// Opción A: respetar exactamente lo que venga (si es null -> desasignar)
		if (form.getAssignedSiteId() != null) {
			CustomerSite site = customerSiteDao.getReferenceById(form.getAssignedSiteId());
			user.setAssignedSite(site);
		} else if (allInlinePresent(form)) {
			// Alta en línea: crear/buscar Customer y luego Site por código
			String custCode = trimOrNull(form.getAssignedCustomerCode());
			String custName = trimOrNull(form.getAssignedCustomerName());
			String custEmail = trimOrNull(form.getAssignedCustomerEmail());
			String siteCode = trimOrNull(form.getAssignedSiteCode());
			String siteAddress = trimOrNull(form.getAssignedSiteAddress());

			CustomerAccount acc = customerAccountDao.findByExternalCode(custCode).orElseGet(() -> {
				CustomerAccount n = new CustomerAccount();
				n.setExternalCode(custCode);
				n.setName(custName);
				n.setEmail(custEmail);
				return customerAccountDao.save(n);
			});

			CustomerSite site = customerSiteDao.findByCustomerAndExternalCode(acc, siteCode).orElseGet(() -> {
				CustomerSite s = new CustomerSite();
				s.setCustomer(acc);
				s.setExternalCode(siteCode);
				s.setAddress(siteAddress);
				
				return customerSiteDao.save(s);
			});

			user.setAssignedSite(site);

		} else {
			// Ni seleccionó existente ni completó inline → desasigna
			user.setAssignedSite(null);
		}

		userDao.save(user);
		return true;
	}
*/
	@Override
	@Transactional
	public Boolean delete(Long id) {
		userDao.deleteById(id);
		return true;
	}
	//==== USER AND REQUESTS ===
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
		Request r =  requestDao.save(request);
		
		if(r != null) {
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
		if(user == null) return null;
		
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
	    if (inv == null) throw new IllegalArgumentException("Invitation code not found");
	    if (Boolean.TRUE.equals(inv.getIsUsed())) throw new IllegalStateException("Invitation code already used");

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
	    if (concierge != null) u.setRole(concierge);

	    u = userDao.save(u);

	    // 4) Consumir ese MISMO código
	    inv.setIsUsed(true);
	    inv.setIsUsedBy(u);
	    codeDao.save(inv);

	    return u;
	}
	
	@Override
	@Transactional
	public User updateUser(UserFormDTO form) {
		 User u = userDao.findById(form.getId())
		            .orElseThrow(() -> new IllegalArgumentException("User not found"));

		    u.setFirstName(form.getFirstName());
		    u.setLastName(form.getLastName());
		    u.setPhone(form.getPhone());
		    // si permites cambiar email/rol, agrégalo con tus reglas
		    if (form.getPassword() != null && !form.getPassword().isBlank()) {
		        u.setPassword(passwordEncoder.encode(form.getPassword()));
		    }
		    return userDao.save(u);
	}

	
	//====Helpers

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
