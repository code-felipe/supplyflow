package com.custodia.supply.util.paginator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPageableService<T> {
	public Page<T> findAll(Pageable pageable);
}
