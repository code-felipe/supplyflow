package com.custodia.supply.item.service;

import java.util.Optional;

import com.custodia.supply.item.entity.Product;

public interface IProductService {
	
	public Product findByCode(String code);
	
	public Product findById(Long id);
	
	public Product save(Product product);
	
	public boolean existsByCode(String code);
	
	public boolean existsByCodeAndIdNot(String code, Long id);
}
