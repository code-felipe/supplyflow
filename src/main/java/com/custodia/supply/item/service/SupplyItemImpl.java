package com.custodia.supply.item.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custodia.supply.category.dao.ICategoryDao;
import com.custodia.supply.category.entity.Category;
import com.custodia.supply.category.service.ICategoryService;
import com.custodia.supply.item.dao.ISupplyItemDao;
import com.custodia.supply.item.dto.embed.DimensionsDTO;
import com.custodia.supply.item.dto.embed.PackagingDTO;
import com.custodia.supply.item.dto.embed.mapper.DimensionMapperImpl;
import com.custodia.supply.item.dto.embed.mapper.PackagingMapperImpl;
import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;
import com.custodia.supply.item.dto.supply.SupplyMapper;
import com.custodia.supply.item.embed.Dimension;
import com.custodia.supply.item.embed.Packaging;
import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.util.mapper.IMapper;
import com.custodia.supply.util.paginator.IPageableService;
import com.custodia.supply.validation.util.Exceptions;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SupplyItemImpl implements ISupplyItemService, IPageableService<SupplyItem> {

	@Autowired
	private ISupplyItemDao supplyItemDao;

	@Autowired
	private ICategoryDao categoryDao;
	
	@Autowired
	private DimensionMapperImpl dimensonM;
	
	@Autowired
	private PackagingMapperImpl packagingM;

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
	public Optional<SupplyItem> findOne(Long id) {
		return supplyItemDao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public SupplyItem findById(Long id) {
		// TODO Auto-generated method stub
		return supplyItemDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByCode(String code) {
		// TODO Auto-generated method stub
		return supplyItemDao.findByCode(code) != null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByCodeAndIdNot(String code, Long id) {
		// TODO Auto-generated method stub
		return supplyItemDao.existsByCodeAndIdNot(code, id);
	}

	@Override
	@Transactional
	public SupplyItem save(SupplyItemFormDTO dto) {
		SupplyItem item = this.supplyItemFromForm(dto);
		return supplyItemDao.save(item);

	}
// Keep this save Impl, in case.
//	@Override
//	@Transactional
//	public SupplyItem save(SupplyItemFormDTO dto) {
//		 final boolean isCreate = (dto.getId() == null);
//
//	        SupplyItem item;
//	        if (isCreate) {
//	            item = new SupplyItem();
//	        } else {
//	            Optional<SupplyItem> opt = this.findOne(dto.getId());
//	            if (!opt.isPresent()) {
//	                throw new EntityNotFoundException("SupplyItem " + dto.getId() + " not found");
//	            }
//	            item = opt.get();
//	        }
//
//	        // ---- Campos simples (ignora null) ----
//	        if (dto.getCode() != null)          item.setCode(emptyToNull(dto.getCode()));
//	        if (dto.getName() != null)          item.setName(emptyToNull(dto.getName()));
//	        if (dto.getSpecification() != null) item.setSpecification(emptyToNull(dto.getSpecification()));
//	        if(dto.getDescription() != null) item.setDescription(emptyToNull(dto.getDescription()));
//
//	        // ---- ManyToOne Category (lookup por repo; SELECT explícito) ----
//	        if (dto.getCategory() != null && dto.getCategory().getId() != null) {
//	            Optional<Category> catOpt = categoryService.findOne(dto.getCategory().getId());
//	            if (!catOpt.isPresent()) {
//	                throw new EntityNotFoundException("Category " + dto.getCategory().getId() + " not found");
//	            }
//	            item.setCategory(catOpt.get());
//	        }
//	        // Si viene CategoryDTO pero sin id, no toques la relación (PATCH)
//
//	        // ---- Embeddable: Dimensions (PATCH por campo) ----
//	        if (dto.getDimensions() != null) {
//	            if (item.getDimensions() == null) {
//	                item.setDimensions(new Dimension());
//	            }
//	            DimensionsDTO d = dto.getDimensions();
//	            Dimension t = item.getDimensions();
//
//	            if (d.getHeight() != null)  t.setHeight(d.getHeight());
//	            if (d.getLength() != null)  t.setLenght(d.getLength()); // ojo con el typo "Lenght" en tu entidad
//	            if (d.getWeight() != null)  t.setWeight(d.getWeight());
//	            if (d.getWidth() != null)   t.setWidth(d.getWidth());
//	            if (d.getUom() != null)     t.setUom(d.getUom());
//	        }
//
//	        // ---- Embeddable: Packaging (PATCH por campo) ----
//	        if (dto.getPackaging() != null) {
//	            if (item.getPackaging() == null) {
//	                item.setPackaging(new Packaging());
//	            }
//	            PackagingDTO p = dto.getPackaging();
//	            Packaging t = item.getPackaging();
//
//	            if (p.getCasesPerPallet() != null) t.setCasesPerPallet(p.getCasesPerPallet());
//	            if (p.getPacksPerCase() != null)   t.setPacksPerCase(p.getPacksPerCase());
//	            if (p.getUnitsPerPack() != null)   t.setUnitsPerPack(p.getUnitsPerPack());
//	            if (p.getUom() != null)            t.setUom(p.getUom());
//	        }
//
//	        return supplyItemDao.save(item);
//
//	}


	private SupplyItem supplyItemFromForm(SupplyItemFormDTO dto) {
		if (dto.getCategory() == null || dto.getCategory().getId() == null) {
			throw Exceptions.badRequest("category.id is required");
		}

		final SupplyItem target = (dto.getId() == null) 
				? new SupplyItem()
				: supplyItemDao.findById(dto.getId())
						.orElseThrow(() -> Exceptions.notFound(SupplyItem.class, "id", dto.getId()));
		
		target.setCode(dto.getCode());
		target.setName(dto.getName());
		target.setSpecification(dto.getSpecification());
		target.setDescription(dto.getDescription());
		
		 if (dto.getCreateAt() != null) {
		        target.setCreateAt(dto.getCreateAt());
		    }
		
		 // 4) Adjuntar Category gestionada por JPA (solo por ID)
		    Category managedCategory = categoryDao.findById(dto.getCategory().getId())
		            .orElseThrow(() -> Exceptions.notFound(Category.class, "id", dto.getCategory().getId()));
		    target.setCategory(managedCategory);

		    // 5) Embeddables (null-safe)
		    target.setDimensions(dimensonM.toEntity(dto.getDimensions()));
		    target.setPackaging(packagingM.toEntity(dto.getPackaging()));
		    
		    return target;
	}

}
