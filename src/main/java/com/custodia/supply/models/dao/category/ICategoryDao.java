package com.custodia.supply.models.dao.category;

import org.springframework.data.repository.CrudRepository;

import com.custodia.supply.models.entity.category.Category;

public interface ICategoryDao extends CrudRepository<Category, Long>{

}
