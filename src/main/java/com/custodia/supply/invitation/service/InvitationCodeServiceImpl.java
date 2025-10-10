package com.custodia.supply.invitation.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.authority.dao.IRoleDao;
import com.custodia.supply.authority.entity.Role;
import com.custodia.supply.invitation.dao.IInvitationCodeDao;
import com.custodia.supply.invitation.entity.InvitationCode;
import com.custodia.supply.user.dao.IUserDao;
import com.custodia.supply.user.dto.UserFormDTO;
import com.custodia.supply.user.entity.User;
import com.custodia.supply.util.paginator.IPageableService;

@Service
public class InvitationCodeServiceImpl implements IInvitationCodeService, IPageableService<InvitationCode> {

	@Autowired
	private IInvitationCodeDao codeDao;

	@Autowired
	private IUserDao userDao;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	  private static final int CODE_LENGTH = 10;
	  private final SecureRandom random = new SecureRandom();

	@Override
	@Transactional(readOnly = true)
	public Page<InvitationCode> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return codeDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InvitationCode> findAll() {
		// TODO Auto-generated method stub
		return (List<InvitationCode>) codeDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public InvitationCode findOne(Long id) {
		// TODO Auto-generated method stub
		return codeDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public InvitationCode save(InvitationCode code) {
		// TODO Auto-generated method stub
		if (code != null) {
			codeDao.save(code);
			return code;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public InvitationCode findByCode(String code) {
		// TODO Auto-generated method stub
		return codeDao.findByCode(code);
	}
	
	
	@Override
	@Transactional
	public InvitationCode generateUniqueCode(Authentication auth) {
		User admin = userDao.findByEmail(auth.getName());
		System.out.println("from generateUniCode " + admin.getEmail());
		if (admin == null) {
			throw new IllegalStateException("Authenticated user not found");
		}

		// 2) intentar varios códigos por si hay colisión
		for (int attempt = 0; attempt < 5; attempt++) {
			String code = randomCode(CODE_LENGTH);

			InvitationCode ic = new InvitationCode();
			ic.setCode(code);
			ic.setCreatedBy(admin);
			ic.setCreateAt(new Date());
			ic.setIsUsed(false);

			try {
				return this.save(ic); // si el code es duplicado, saltará DataIntegrityViolation
			} catch (DataIntegrityViolationException ex) {
				// colisión por unique constraint → reintentar
			}
		}
		throw new IllegalStateException("Could not generate a unique invitation code after several attempts");
	}

	private String randomCode(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			int idx = random.nextInt(ALPHABET.length());
			sb.append(ALPHABET.charAt(idx));
		}
		return sb.toString();
	}

	

	

}
