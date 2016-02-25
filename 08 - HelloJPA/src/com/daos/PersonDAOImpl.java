package com.daos;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.persistence.Person;

@Repository
@Transactional
public class PersonDAOImpl implements PersonDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void create(Person person) throws Exception {
		entityManager.persist(person);
	}

	@Override
	public void update(Person person) throws Exception {
		entityManager.merge(person);
	}

	@Override
	public void remove(Person person) throws Exception {
		entityManager.remove(entityManager.contains(person) ? person : entityManager.merge(person));
	}

	@Override
	public List<Person> getAllPerson() {
		return entityManager.createQuery("Select a From Person a", Person.class).getResultList();
	}
}