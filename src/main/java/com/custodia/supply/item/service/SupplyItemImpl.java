package com.custodia.supply.item.service;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.item.dao.IProductDao;
import com.custodia.supply.item.dao.ISupplyItemDao;
import com.custodia.supply.item.dto.SupplyItemForm;
import com.custodia.supply.item.entity.Product;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.util.paginator.IPageableService;


@Service
public class SupplyItemImpl implements ISupplyItemService, IPageableService<SupplyItem> {

	@Autowired
	private ISupplyItemDao supplyItemDao;

	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IProductService productService;
    
    @Override
    @Transactional(readOnly = true)
    public List<SupplyItem> findAll() {
        return (List<SupplyItem>) supplyItemDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<SupplyItem> findAll(Pageable pageable) {
        return supplyItemDao.findAll(pageable);
                          
    }
    
    @Override
    @Transactional(readOnly = true)
    public SupplyItem findOne(Long id) {
        return supplyItemDao.findById(id).orElse(null);
    }
   

	@Override
	@Transactional
	public Boolean save(SupplyItemForm form) {
		SupplyItem item;

		if (form.getId() != null) {
			// Editing existing SupplyItem
			item = supplyItemDao.findById(form.getId()).orElse(new SupplyItem());
			if (item.getCreateAt() == null) {
				item.setCreateAt(new Date());
			}
		} else {
			// New SupplyItem
			item = new SupplyItem();
			item.setCreateAt(new Date());
		}

		// map common fields
		item.setPackagingCode(form.getPackagingCode());
		item.setUnitOfMeasure(form.getUnitOfMeasure());
		item.setSpecification(form.getSpecification());

		// --- Product ---
		Product product;
		if (item.getProduct() != null) {
			// SupplyItem already has a product -> edit it
			product = item.getProduct();
			product.setCode(form.getProduct().getCode()); // Si quieres permitir cambiar código
			product.setName(form.getProduct().getName());
		} else {
			// New product
			product = productService.findByCode(form.getProduct().getCode());
			if (product == null) {
				product = new Product();
				product.setCode(form.getProduct().getCode());
			}
			product.setName(form.getProduct().getName());
		}
		product = productDao.save(product);
		item.setProduct(product);

		// Save SupplyItem
		supplyItemDao.save(item);
		return true;
	}
	
	/*
	 * Is not necessary to delete a SupplyItem, it will be
	 *  part of active system. Is not a good practice delete this historical
	 */
//	@Override
//	@Transactional
//	public Boolean delete(Long id) {
//	    if (!supplyItemDao.existsById(id)) return false;
////	    requestItemDao.deleteBySupplyItem_Id(id); // Delete childs first
//	    supplyItemDao.deleteById(id);  // delete Father
//	    return true;
//	}

	


}
