package com.custodia.supply.requestitem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.requestitem.entity.RequestItem;

public interface IRequestItemDao  extends JpaRepository<RequestItem, Long> {
		// Delete childs from supplyItem on RequestItem
//		@Transactional
//	    long deleteBySupplyItem_Id(Long supplyItemId);

}
