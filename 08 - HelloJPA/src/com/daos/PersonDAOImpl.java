package com.daos;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	public List<Person> getPersonAll() {
		return entityManager.createQuery("Select a From Person a", Person.class).getResultList();
	}

	@Override
	public Person getPersonById(Long id) {
		TypedQuery<Person> typedQuery = entityManager.createQuery("Select a From Person a Where a.id=:person_id", Person.class);
		Person person = null;
		try {
			person = typedQuery.setParameter("person_id", id).getSingleResult();
		} catch (NoResultException e) {	}
		return person;
	}

	@Override
	public List<Person> getPersonByName(String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> personRoot = query.from(Person.class);
		query.where(builder.equal(personRoot.get("firstName"), name));
		return entityManager.createQuery(query).getResultList();
	}
}