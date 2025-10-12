package com.custodia.supply.item.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.custodia.supply.item.entity.SupplyItem;

public interface ISupplyItemDao extends PagingAndSortingRepository<SupplyItem, Long>, CrudRepository<SupplyItem, Long> {
	
	@Query("SELECT s FROM SupplyItem s JOIN s.product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))")
	public List<SupplyItem> findByProductName(String term);
	
	// Over the method
	public List<SupplyItem> findByProduct_Name(String term);
	
	public Optional<SupplyItem> findByProductId(Long productId);
}
