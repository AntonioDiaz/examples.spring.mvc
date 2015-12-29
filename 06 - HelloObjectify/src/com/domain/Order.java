package com.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Order {

	@Id
	private Long id;
	@Index
	private String name;
	@Index
	private Date createDate;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
