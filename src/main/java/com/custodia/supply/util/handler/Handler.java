package com.custodia.supply.util.handler;

public interface Handler<E, D> {
	Handler<E,D> setNext(Handler<E, D> handler);
	void handle(E e, D dto);
}
