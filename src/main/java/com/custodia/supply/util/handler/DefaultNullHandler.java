package com.custodia.supply.util.handler;

public interface DefaultNullHandler<E, D> {
	void handle(E e, D dto);
}
