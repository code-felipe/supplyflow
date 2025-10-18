package com.custodia.supply.item.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.custodia.supply.item.entity.Product;
import com.custodia.supply.item.entity.SupplyItem;

public interface ISupplyItemDao extends PagingAndSortingRepository<SupplyItem, Long>, CrudRepository<SupplyItem, Long> {

//	@Query("SELECT s FROM SupplyItem s JOIN s.product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))")
//	public List<SupplyItem> findByProductName(String term);

	@Query("""
			  SELECT s
			  FROM SupplyItem s
			  WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :term, '%'))
			""")
	List<SupplyItem> findAllByNameLikeIgnoreCase(@Param("term") String term);
	
	boolean existsByCode(String code);
	boolean existsByCodeAndIdNot(String code, Long id);
	public SupplyItem findByCode(String code);
	
	// Over the method
//	public List<SupplyItem> findByProduct_Name(String term);

//	public Optional<SupplyItem> findByProductId(Long productId);
}
