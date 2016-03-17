package com.daos;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

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
	public Person getPersonByIdJoin(Long id) {
		TypedQuery<Person> typedQuery = entityManager.createQuery("Select a From Person a left join fetch a.phones s Where s.id=:person_id", Person.class);
		Person person = null;
		try {
			person = typedQuery.setParameter("person_id", id).getSingleResult();
		} catch (NoResultException e) {	}
		return person;
	}

	@Override
	public List<Person> getPersonByNameEqual(String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> personRoot = query.from(Person.class);
		query.where(builder.equal(personRoot.get("firstName"), name));
		return entityManager.createQuery(query).getResultList();
	}
	
	@Override
	public List<Person> getPersonByNameLike(String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> personRoot = query.from(Person.class);

		/* create like expresion. */ 
		EntityType<Person> type = entityManager.getMetamodel().entity(Person.class);
		SingularAttribute<Person, String> declaredSingularAttribute = type.getDeclaredSingularAttribute("firstName", String.class);
		Path<String> path = personRoot.get(declaredSingularAttribute);
		Expression<String> expresion = builder.lower(path);

		query.where(builder.like(expresion, name + "%"));
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<Person> getPersonsByPhone(String myPhone) {
		List<Person> resultList = null;
		TypedQuery<Person> typedQuery = entityManager.createQuery("Select a From Person a left join fetch a.phones s Where s.number=:my_number", Person.class);
		resultList = typedQuery.setParameter("my_number", myPhone).getResultList();
		return resultList;
	}
	
	@Override
	public List<Person> getPersonsByPhoneCriteria(String myPhone) {
		List<Person> resultList = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> personRoot = query.from(Person.class);
		Join<Object, Object> join = personRoot.join("phones",  JoinType.LEFT);
		query.where(builder.equal(join.get("number"), myPhone));
//		Path<Object> path = join.get("number");
//		path.
//		query.where(path)
		//query.where(builder.equal(personRoot.get("firstName"), myPhone));
		
		//query.where(builder.equal(myFetch.get("firstName"), myPhone));
		resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}
	
	
	public List<Person> getPersonsByPhoneCriteriaAux(String myPhone) {
		List<Person> resultList = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> personRoot = query.from(Person.class);		
		personRoot.fetch("phones",  JoinType.LEFT);
		query.where(builder.equal(personRoot.get("firstName"), myPhone));
		//query.where(builder.equal(myFetch.get("firstName"), myPhone));
		resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}
}