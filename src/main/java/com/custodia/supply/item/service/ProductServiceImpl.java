package com.custodia.supply.item.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.item.dao.IProductDao;
import com.custodia.supply.item.entity.Product;

@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private IProductDao productDao;
	
	@Override
	@Transactional
	public Product save(Product product) {
		// TODO Auto-generated method stub
		return productDao.save(product);
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
