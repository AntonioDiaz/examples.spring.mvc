package com.forms;

import java.util.Date;

public class EntityForm {

	public EntityForm() {
		super();
	}
	
	public EntityForm(Long id, String name, String surname, Date birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
	}

	
	
	private Long id;
	private String name;
	private String surname;
	private Date birthdate;
	
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
