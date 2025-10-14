package com.custodia.supply.item.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.custodia.supply.item.dao.ISupplyItemDao;


import com.custodia.supply.item.dto.supply.SupplyItemFormDTO;

import com.custodia.supply.item.entity.SupplyItem;
import com.custodia.supply.util.paginator.IPageableService;

@Service
public class SupplyItemImpl implements ISupplyItemService, IPageableService<SupplyItem> {

	@Autowired
	private ISupplyItemDao supplyItemDao;
	
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
	public SupplyItem save(SupplyItemFormDTO form) {
		return null;
//	@Override
//	@Transactional
//	public SupplyItem save(SupplyItemFormDTO form) {		
//		 // --- 1) Validaciones simples ---
//	    if (form == null || form.getProduct() == null) {
//	        throw new IllegalArgumentException("Formulario o producto nulo.");
//	    }
//	    String code = form.getProduct().getCode();
//	    String name = form.getProduct().getName();
//	    
//	    if (code == null || code.trim().isEmpty()) {
//	        throw new IllegalArgumentException("El código del producto es obligatorio.");
//	    }
//	    if (name == null || name.trim().isEmpty()) {
//	        throw new IllegalArgumentException("El nombre del producto es obligatorio.");
//	    }
//
//	    // --- 2) Resolver o crear Product por code (sin lambdas) ---
//	    Product product = productService.findById(form.getProduct().getId());
//	   
//	    boolean exists = productService.existsByCodeAndIdNot(code, product.getId());
//	    if (exists) {
//	        if (!name.equals(product.getName())) {
//	            product.setName(name);
////	            product.setCode(code);
//	            product = productService.save(product);
//	        }
//	    } else {
//	        Product newP = new Product();
//	        newP.setCode(code);
//	        newP.setName(name);
//	        product = productService.save(newP); // simple: guarda y usa el devuelto
//	    }
//
//	    // --- 3) Resolver SupplyItem (editar o crear) ---
//	    SupplyItem item;
//	    if (form.getId() != null) {
//	        Optional<SupplyItem> itemOpt = supplyItemDao.findById(form.getId());
//	        if (itemOpt.isPresent()) {
//	            item = itemOpt.get();
//	        } else {
//	            throw new EntityNotFoundException("No existe SupplyItem con id=" + form.getId());
//	        }
//	    } else {
//	        item = new SupplyItem();
//	        if(item.getCreateAt() == null) {
//	        	item.setCreateAt(new Date());
//	        }
//	    }
//
//	    // --- 4) Copiar datos del form ---
//	    item.setProduct(product);
//	    item.setPackagingCode(form.getPackagingCode());
//	    item.setUnitOfMeasure(form.getUnitOfMeasure());
//	    item.setSpecification(form.getSpecification());
//	   
//
//	    // --- 5) Guardar y devolver ---
//	    return supplyItemDao.save(item);
//	}
	
	// Change Product class to be a Father instead of using association.

//	    if (form == null) return null;
//
//	    // ------- 1) Cargar o crear SupplyItem --------
//	    SupplyItem item;
//	    if (form.getId() != null) {
//	        item = supplyItemDao.findById(form.getId()).orElse(null);
//	        if (item == null) return null; // no existe el SupplyItem a editar
//	    } else {
//	        item = new SupplyItem();
//	        if (form.getCreateAt() != null) {
//	            item.setCreateAt(form.getCreateAt());
//	        } else {
//	            item.setCreateAt(new java.util.Date());
//	        }
//	    }
//
//	    // ------- 2) Resolver y ACTUALIZAR Product (con validación de code) -------
//	    ProductFormDTO pDto = form.getProduct();
//	    if (pDto == null) return null; // necesitamos datos del product
//	    String newCode = pDto.getCode() != null ? pDto.getCode().trim() : null;
//	    String newName = pDto.getName() != null ? pDto.getName().trim() : null;
//
//	    Product product = null;
//
//	    if (item.getId() != null) {
//	        // ----- UPDATE SUPPLYITEM: editar el Product asociado -----
//	        product = item.getProduct();
//	        if (product == null) return null; // SupplyItem roto: sin product
//
//	        // Validar unicidad del code si viene uno nuevo (permitiendo su propio id)
//	        if (newCode != null && newCode.length() > 0) {
//	            boolean existsElsewhere = productService.existsByCodeAndIdNot(newCode, product.getId());
//	            if (existsElsewhere) {
//	                // code pertenece a otro product → no guardar
//	                return null;
//	            }
//	            product.setCode(newCode);
//	        }
//	        if (newName != null && newName.length() > 0) {
//	            product.setName(newName);
//	        }
//	        // Si quieres permitir limpiar name/code con vacío, elimina los "if (...)"
//
//	        // Guardar cambios del Product
//	        productDao.save(product);
//	        // Asociación ya existe; no la tocamos
//	    } else {
//	        // ----- CREATE SUPPLYITEM: resolver/crear Product por id o code -----
//	        if (pDto.getId() != null) {
//	            product = productService.findById(pDto.getId());
//	            if (product == null) return null; // id inválido
//	            // Actualiza campos si vinieron
//	            if (newCode != null && newCode.length() > 0) {
//	                boolean existsElsewhere = productService.existsByCodeAndIdNot(newCode, product.getId());
//	                if (existsElsewhere) return null;
//	                product.setCode(newCode);
//	            }
//	            if (newName != null && newName.length() > 0) {
//	                product.setName(newName);
//	            }
//	            productDao.save(product);
//	        } else {
//	            // Sin id → intentar por code
//	            if (newCode != null && newCode.length() > 0) {
//	                  Product existing = productService.findByCode(newCode);
//	                if (existing != null) {
//	                    // Reutilizar product existente (y opcionalmente actualizar nombre)
//	                    product = existing;
//	                    if (newName != null && newName.length() > 0) {
//	                        product.setName(newName);
//	                        productDao.save(product);
//	                    }
//	                } else {
//	                    // Crear nuevo Product con code (y name si viene)
//	                    product = new Product();
//	                    product.setCode(newCode);
//	                    if (newName != null && newName.length() > 0) {
//	                        product.setName(newName);
//	                    }
//	                    product = productDao.save(product);
//	                }
//	            } else {
//	                // Ni id ni code -> no podemos determinar/crear product
//	                return null;
//	            }
//	        }
//
//	        // Asociar en CREATE
//	        item.setProduct(product);
//	    }
//
//	    // ------- 3) Mapear campos de SupplyItem (no tocamos la asociación) -------
//	    item.setPackagingCode(form.getPackagingCode());
//	    item.setUnitOfMeasure(form.getUnitOfMeasure());
//	    item.setSpecification(form.getSpecification());
//	    if (form.getCreateAt() != null) {
//	        item.setCreateAt(form.getCreateAt()); // opcional: comenta si no quieres editar createAt
//	    }
//
//	    // ------- 4) Guardar SupplyItem -------
//	    item = supplyItemDao.save(item);
//	    return item;
	}

	





//	@Override
//	@Transactional
//	public Boolean save(SupplyItemFormDTO form) {
//		SupplyItem item;
//
//		if (form.getId() != null) {
//			// Editing existing SupplyItem
//			item = supplyItemDao.findById(form.getId()).orElse(new SupplyItem());
//			if (item.getCreateAt() == null) {
//				item.setCreateAt(new Date());
//			}
//		} else {
//			// New SupplyItem
//			item = new SupplyItem();
//			item.setCreateAt(new Date());
//		}
//
//		// map common fields
//		item.setPackagingCode(form.getPackagingCode());
//		item.setUnitOfMeasure(form.getUnitOfMeasure());
//		item.setSpecification(form.getSpecification());
//
//		// --- Product ---
//		Product product;
//		if (item.getProduct() != null) {
//			// SupplyItem already has a product -> edit it
//			product = item.getProduct();
//			product.setCode(form.getProduct().getCode()); // Si quieres permitir cambiar código
//			product.setName(form.getProduct().getName());
//		} else {
//			// New product
//			product = productService.findByCode(form.getProduct().getCode());
//			if (product == null) {
//				product = new Product();
//				product.setCode(form.getProduct().getCode());
//			}
//			product.setName(form.getProduct().getName());
//		}
//		product = productDao.save(product);
//		item.setProduct(product);
//
//		// Save SupplyItem
//		supplyItemDao.save(item);
//		return true;
//	}

}
