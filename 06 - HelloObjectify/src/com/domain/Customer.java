package com.domain;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Customer {

	@Id
	private Long id;
	@Index
	private String name;
	@Index
	private String surname01;
	@Index
	private String surname02;

	private List<Ref<Order>> orders = new ArrayList<Ref<Order>>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname01() {
		return surname01;
	}

	public void setSurname01(String surname01) {
		this.surname01 = surname01;
	}

	public String getSurname02() {
		return surname02;
	}

	public void setSurname02(String surname02) {
		this.surname02 = surname02;
	}

	
	
	public List<Ref<Order>> getOrders() {
		return orders;
	}

	public void setOrders(List<Ref<Order>> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
