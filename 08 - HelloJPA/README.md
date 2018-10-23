## Spring MVC project with JPA

Proof of concept of a Spring MVC project using JPA (Java Persistence API) to access to a MySQL database.  
Thanks to: <a href="http://www.javacodegeeks.com/2015/02/jpa-tutorial.html" target="_blank">www.javacodegeeks.com/2015/02/jpa-tutorial.html</a>

* [Project structure](#project-structure)
* [pom.xml](#pomxml)  
* [applicationContext.xml](#applicationcontextxml)
* [Entities](#entities)  
* [DAOs](#daos)  
* [Services](#services)  
* [Controllers](#controllers)
* [Avoid lazy-loading exception](#lazy-loading)
* [Adding HSQLDB](#adding-hsqldb)  
* [Run HSQLDB](#run-hsqldb)
* [Query with JPQL](#query-with-jpql)

***
#### Project structure  
![](https://antoniodiaz.github.io/images/spring_mvc/08_structure.jpg)

***
#### pom.xml
``` xml  
<properties>
		<spring.version>4.1.4.RELEASE</spring.version>
		<log4j.version>1.2.17</log4j.version>
		<jstl.version>1.2</jstl.version>
		<apache.commons>3.4</apache.commons>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
			<version>${jstl.version}</version>
		</dependency>
		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>${log4j.version}</version>
		</dependency>
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
			<version>${apache.commons}</version>
		</dependency>
		<!-- persistence dependences. -->

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-orm</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-core</artifactId>
			<version>4.0.1.Final</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>4.2.0.Final</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate.common</groupId>
			<artifactId>hibernate-commons-annotations</artifactId>
			<version>4.0.1.Final</version>
			<classifier>tests</classifier>
		</dependency>

		<dependency>
			<groupId>javax.validation</groupId>
			<artifactId>validation-api</artifactId>
			<version>1.0.0.GA</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate.javax.persistence</groupId>
			<artifactId>hibernate-jpa-2.0-api</artifactId>
			<version>1.0.1.Final</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-entitymanager</artifactId>
			<version>4.0.1.Final</version>
		</dependency>

		<dependency>
			<groupId>org.jboss.logging</groupId>
			<artifactId>jboss-logging</artifactId>
			<version>3.1.0.CR2</version>
		</dependency>

		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>1.6.4</version>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.10</version>
		</dependency>
	</dependencies>
````

***
#### applicationContext.xml
``` xml
	<!-- This produces a container-managed EntityManagerFactory; rather than application-managed EntityManagerFactory as in case of LocalEntityManagerFactoryBean -->
	<bean id="entityManagerFactoryBean" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
		<property name="dataSource" ref="dataSource" />
		<!-- This makes /META-INF/persistence.xml is no longer necessary -->
		<property name="packagesToScan" value="com.persistence" />
		<!-- JpaVendorAdapter implementation for Hibernate EntityManager. Exposes Hibernate's persistence provider and EntityManager extension interface -->
		<property name="jpaVendorAdapter">
			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter" />
		</property>
		<property name="jpaProperties">
			<props>
				<prop key="hibernate.hbm2ddl.auto">create</prop>
				<prop key="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</prop>
			</props>
		</property>
	</bean>

	<!-- This transaction manager is appropriate for applications that use a single JPA EntityManagerFactory for transactional data access. JTA (usually through
		JtaTransactionManager) is necessary for accessing multiple transactional resources within the same transaction. -->
	<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
		<property name="entityManagerFactory" ref="entityManagerFactoryBean" />
	</bean>

	<!-- Simple implementation of the standard JDBC DataSource interface, configuring the plain old JDBC DriverManager via bean properties -->
	<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver" />
		<property name="url" value="jdbc:mysql://192.168.137.4:3306/test_schema" />
		<property name="username" value="test_user" />
		<property name="password" value="test_user" />
	</bean>
	<tx:annotation-driven />
```
***
#### Entities
##### Person.java
``` java
import javax.persistence.*;

@Entity
@Table(name = "T_PERSON")
public class Person {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "ID_CARD_ID")
	private IdCard idCard;

	@OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Phone> phones = new ArrayList<>();
	/* getters and setters.  */
}
```
##### Phone.java
``` java
import javax.persistence.*;

@Entity
@Table(name = "T_PHONE")
public class Phone {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "NUMBER")
	private String number;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERSON_ID")
	private Person person;

}
```
***
#### DAOs
##### GenericDAO.java
``` java
package com.daos;
public interface GenericDAO<T> {
	public void create(T item) throws Exception;
	public void update(T item) throws Exception;
	public void remove(T item) throws Exception;
}
```
##### PersonDAO.java
``` java
package com.daos;

import java.util.List;
import com.persistence.Person;

public interface PersonDAO extends GenericDAO<Person> {
	public List<Person> getPersonAll();
	public Person getPersonById(Long id);
	public Person getPersonByIdJoin(Long id);
	public List<Person> getPersonByNameEqual(String name);
	public List<Person> getPersonByNameLike(String name);
	public List<Person> getPersonsByPhone(String myPhone);
	public List<Person> getPersonsByPhoneCriteria(String myPhone);
}
```
##### PersonDAOImpl.java
``` java
package com.daos;

import javax.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
		resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}

	public List<Person> getPersonsByPhoneCriteriaAux(String myPhone) {
		List<Person> resultList = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> personRoot = query.from(Person.class);		
		Fetch<Person, Phone> myFetch = personRoot.fetch("phones",  JoinType.LEFT);
		query.where(builder.equal(personRoot.get("firstName"), myPhone));
		//query.where(builder.equal(myFetch.get("firstName"), myPhone));
		resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}
}
```
***
#### Services
##### PersonManager.java
``` java
package com.services;

import java.util.List;
import com.persistence.Person;

public interface PersonManager {
	public void createUser(Person person) throws Exception;
	public void updteUser(Person person) throws Exception;
	public List<Person> queryPersonAll() throws Exception;
	public Person queryPersonById(Long id) throws Exception;
	public Person queryPersonByIdJoin(Long id) throws Exception;
	public Person queryPersonByPhone(String myPhone) throws Exception;
	public Person queryPersonByPhoneCriteria(String myPhone) throws Exception;
	public List<Person> queryPersonByNameEqual(String name) throws Exception;
	public List<Person> queryPersonByNameLike(String name) throws Exception;
}
```
##### PersonManagerImpl.java
``` java
package com.services;

@Service
public class PersonManagerImpl implements PersonManager {

	@Autowired private PersonDAO personDAO;

	@Override
	public void createUser(Person person) throws Exception {
		personDAO.create(person);
	}

	@Override
	public List<Person> queryPersonAll() throws Exception {
		return personDAO.getPersonAll();
	}

	@Override
	public Person queryPersonById(Long id) throws Exception {
		return personDAO.getPersonById(id);
	}

	@Override
	public Person queryPersonByIdJoin(Long id) throws Exception {
		return personDAO.getPersonByIdJoin(id);
	}

	@Override
	public List<Person> queryPersonByNameEqual(String name) throws Exception {
		return personDAO.getPersonByNameEqual(name);
	}

	@Override
	public List<Person> queryPersonByNameLike(String name) throws Exception {
		return personDAO.getPersonByNameLike(name);
	}

	@Override
	public void updteUser(Person person) throws Exception {
		personDAO.update(person);
	}

	@Override
	public Person queryPersonByPhone(String myPhone) throws Exception {
		Person person = null;
		List<Person> persons = personDAO.getPersonsByPhone(myPhone);
		if (persons.size() > 1) {
			throw new Exception("More than one person.");
		}
		if (persons.size()==1) {
			person = persons.get(0);
		}
		return person;
	}

	@Override
	public Person queryPersonByPhoneCriteria(String myPhone) throws Exception {
		Person person = null;
		List<Person> persons = personDAO.getPersonsByPhoneCriteria(myPhone);
		if (persons.size() > 1) {
			throw new Exception("More than one person.");
		}
		if (persons.size()==1) {
			person = persons.get(0);
		}
		return person;
	}
}
```
***
#### Controllers

***
#### Lazy Loading
[https://github.com/FasterXML/jackson-datatype-hibernate](https://github.com/FasterXML/jackson-datatype-hibernate)
* pom.xml
``` xml
<dependency>
	<groupId>com.fasterxml.jackson.datatype</groupId>
	<artifactId>jackson-datatype-hibernate4</artifactId>
	<version>2.5.3</version>
</dependency>
```
* applicationContext.xml
``` xml
<mvc:annotation-driven>
	<mvc:message-converters>
		<!-- Use the HibernateAware mapper instead of the default -->
		<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
			<property name="objectMapper">
				<bean class="com.utilities.HibernateAwareObjectMapper" />
			</property>
		</bean>
	</mvc:message-converters>
</mvc:annotation-driven>
```

* HibernateAwareObjectMapper.java
``` java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public HibernateAwareObjectMapper() {
		registerModule(new Hibernate4Module());
	}
}
```
***
### Adding HSQLDB
Thanks to: [http://devcrumb.com/hibernate/hibernate-jpa-spring-and-hsqldb](http://devcrumb.com/hibernate/hibernate-jpa-spring-and-hsqldb)
* pom.xml
``` xml
<!-- Spring JDBC -->
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-jdbc</artifactId>
	<version>4.1.4.RELEASE</version>
</dependency>

<!-- HyperSQL DB -->
<dependency>
	<groupId>org.hsqldb</groupId>
	<artifactId>hsqldb</artifactId>
	<version>2.3.2</version>
</dependency>		
```
* applicationContext.xml
``` xml
<bean id="entityManagerFactoryBean" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
	<property name="dataSource" ref="dataSource" />
	<property name="packagesToScan" value="com.persistence" />
	<property name="persistenceUnitName" value="jpaData" />
	<property name="jpaVendorAdapter">
		<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter" />
	</property>
	<property name="jpaProperties">
		<props>
			<prop key="hibernate.dialect">org.hibernate.dialect.HSQLDialect</prop>
			<prop key="hibernate.show_sql">true</prop>
			<prop key="hibernate.format_sql">false</prop>
			<prop key="hibernate.hbm2ddl.auto">create</prop>
		</props>
	</property>
</bean>

<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
	<property name="entityManagerFactory" ref="entityManagerFactoryBean" />
</bean>

<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	<property name="driverClassName" value="org.hsqldb.jdbcDriver" />
	<property name="url" value="jdbc:hsqldb:hsql://localhost/" />
	<property name="username" value="sa" />
	<property name="password" value="" />
</bean>
```
* pesistence.xml  
In META-INF folder.  
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="1.0"
    xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd">

    <persistence-unit name="jpaData" />

</persistence>
```

***
### Run HSQLDB
Select Java Application: org.hsqldb.Server

[[images/08_run_hsqldb.jpg]]
***

### Query with JPQL
``` java
import javax.persistence.TypedQuery;

public List<ResourceEntity> getResources(String name, Integer author, String organization) {
	String queryStr = "Select a From ResourceEntity a join fetch a.uploadedUser as u where 1=1";
	if (StringUtils.isNotEmpty(name)) {
		queryStr += " and UPPER(a.name) like :name";
	}
	if (author!=null) {
		queryStr += " and u.id = :id_author";
	}
	if (StringUtils.isNotEmpty(organization)) {
		queryStr += " and UPPER(u.organization) like :organization";
	}
	TypedQuery<ResourceEntity> typedQuery = em.createQuery(queryStr, ResourceEntity.class);
	if (StringUtils.isNotEmpty(name)) {
		typedQuery = typedQuery.setParameter("name",  "%" + name.toUpperCase() + "%");
	}				
	if (author!=null) {
		typedQuery = typedQuery.setParameter("id_author", author);
	}
	if (StringUtils.isNotEmpty(organization)) {
		typedQuery = typedQuery.setParameter("organization", "%" + organization.toUpperCase() + "%");
	}
	return typedQuery.getResultList();
}
```
