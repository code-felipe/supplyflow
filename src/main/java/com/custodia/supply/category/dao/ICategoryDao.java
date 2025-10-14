package com.custodia.supply.category.dao;

import org.springframework.data.repository.CrudRepository;

import com.custodia.supply.category.entity.Category;

public interface ICategoryDao extends CrudRepository<Category, Long>{

}
