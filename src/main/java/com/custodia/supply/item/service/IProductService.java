package com.custodia.supply.item.service;

import com.custodia.supply.item.entity.Product;

public interface IProductService {
	
	public Product findByCode(String code);
	
	public Product findById(Long id);
	
	public void save(Product product);
	
	public boolean existsByCode(String code);
	
	public boolean existsByCodeAndIdNot(String code, Long id);
}
