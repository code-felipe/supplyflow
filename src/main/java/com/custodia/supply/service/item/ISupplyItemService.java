package com.custodia.supply.service.item;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.custodia.supply.models.dto.item.SupplyItemFormDTO;
import com.custodia.supply.models.dto.item.SupplyItemViewDTO;
import com.custodia.supply.models.entity.item.SupplyItem;

public interface ISupplyItemService {
	
	public List<SupplyItem> findAll();
	
	public Optional<SupplyItem> findOne(Long id);
	
	public SupplyItem findById(Long id);// Using in new impl
	
	public SupplyItem save(SupplyItemFormDTO dto);
	
	public boolean existsByCode(String code);
	public boolean existsByCodeAndIdNot(String code, Long id);
	public Page<SupplyItemViewDTO> findPageByCategory(Long id, Pageable pageable);

	
//	Optional<SupplyItem> findByProductId(Long productId);
/*
 * Is not necessary to delete a SupplyItem, it will be
 *  part of active system. Is not a good practice delete this historical
 */
//	public Boolean delete(Long id);
	
}
