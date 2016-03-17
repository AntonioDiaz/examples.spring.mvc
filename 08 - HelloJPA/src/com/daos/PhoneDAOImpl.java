package com.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.persistence.Phone;

@Repository
@Transactional
public class PhoneDAOImpl implements PhoneDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public void create(Phone phone) throws Exception {
		entityManager.persist(phone);
	}

	@Override
	public void update(Phone phone) throws Exception {
		entityManager.merge(phone);
	}

	@Override
	public void remove(Phone phone) throws Exception {
		entityManager.remove(entityManager.contains(phone) ? phone : entityManager.merge(phone));
	}

	@Override
	public List<Phone> getPhoneAll() {
		return entityManager.createQuery("Select a From Phone a", Phone.class).getResultList();
	}
}
