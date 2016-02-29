package com.daos;


public interface GenericDAO<T> {
	
	public void create(T item) throws Exception;

	public void update(T item) throws Exception;

	public void remove(T item) throws Exception;

}
