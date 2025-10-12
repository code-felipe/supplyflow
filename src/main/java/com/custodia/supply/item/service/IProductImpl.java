package com.custodia.supply.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.item.dao.IProductDao;
import com.custodia.supply.item.entity.Product;

@Service
public class IProductImpl implements IProductService {
	
	@Autowired
	private IProductDao productDao;
	
	@Override
	@Transactional
	public void save(Product product) {
		// TODO Auto-generated method stub
		productDao.save(product);
	}

	@Override
	@Transactional(readOnly = true)
	public Product findByCode(String code) {
		return productDao.findByCode(code);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByCode(String code) {
		return productDao.findByCode(code) != null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByCodeAndIdNot(String code, Long id) {
		// TODO Auto-generated method stub
		return productDao.existsByCodeAndIdNot(code, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		// TODO Auto-generated method stub
		return productDao.findById(id).orElse(null);
	}
	
	
}
