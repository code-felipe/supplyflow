package com.custodia.supply.item.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.custodia.supply.item.entity.Product;
import com.custodia.supply.item.entity.SupplyItem;

public interface IProductDao extends PagingAndSortingRepository<Product, Long>, CrudRepository<Product, Long> {
	boolean existsByCode(String code);
	boolean existsByCodeAndIdNot(String code, Long id);
	public Product findByCode(String code);
	
}
