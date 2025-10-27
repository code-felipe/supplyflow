package com.custodia.supply.util.mapper;

public interface IMapper<D, E> {
	public E toEntity(D dto);
	public D toDTO(E e);
}
