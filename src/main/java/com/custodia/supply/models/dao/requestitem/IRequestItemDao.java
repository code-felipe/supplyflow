package com.custodia.supply.models.dao.requestitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.models.entity.requestitem.RequestItem;

public interface IRequestItemDao  extends JpaRepository<RequestItem, Long> {
		// Delete childs from supplyItem on RequestItem
//		@Transactional
//	    long deleteBySupplyItem_Id(Long supplyItemId);

}
