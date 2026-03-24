package com.custodia.supply.models.dao.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.custodia.supply.models.entity.item.SupplyItem;

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
	
	@Query("""
			   select si
			   from SupplyItem si
			   where si.category.id = :categoryId
			""")
			Page<SupplyItem> findPageByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);


	
	// Over the method
//	public List<SupplyItem> findByProduct_Name(String term);

//	public Optional<SupplyItem> findByProductId(Long productId);
}
