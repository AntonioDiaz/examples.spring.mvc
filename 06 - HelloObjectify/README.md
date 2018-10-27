## Java web project developed as proof of concept.
* Spring MVC 4.1  
* Objectify 5.0  
* Smart Table --> http://lorenzofox3.github.io/smart-table-website/  
* DAO and Service  

**Important points**  
* [Project Structure](#project-structure)  
* [Front End](#front-end)  
* [Objectify](#objectify)  
* [DAO example](#dao-example)
* [Service example](#service-example)
* [Controller](#controller)
* [Angular smart-table](#angular-smart-table)

***
### Project Structure
![](https://antoniodiaz.github.io/images/spring_mvc/06_structure.jpg)

***
### Front End
![](https://antoniodiaz.github.io/images/spring_mvc/06_front_end.jpg)

***
### Objectify
**pom.xml**
``` xml
<properties>
	<objectify.version>5.0.1</objectify.version>
</properties>
<dependencies>
	<dependency>
		<groupId>com.googlecode.objectify</groupId>
		<artifactId>objectify</artifactId>
		<version>${objectify.version}</version>
	</dependency>
	....
</dependiencies>
```
**Register Entities:** it is necesary to register the entities before you use Objectify.  
**applicatonContext.xml**
``` xml
<bean id="registerEntities" class="com.utils.RegisterEntities" init-method="init" />
```  
**RegisterEntities.java**  
``` java
public class RegisterEntities {
	public void init() {
		ObjectifyService.register(Customer.class);
	}
}
```
**Entity**
``` java
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

	/* getters and setters. */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
```
**Add filter to xml**
``` xml
<filter>
	<filter-name>ObjectifyFilter</filter-name>
	<filter-class>com.googlecode.objectify.ObjectifyFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>ObjectifyFilter</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
```

***
### DAO example
In this example I've used 3 source files: 2 intefaces and a class.  

**GenericDAO.java**
``` java
package com.dao;
public interface GenericDAO<T> {
	public void create(T item) throws Exception;
	public boolean update(T item) throws Exception;
	public boolean remove(T item) throws Exception;
}
```
**CustomerDAO.java**
``` java
package com.dao;

import java.util.List;
import com.domain.Customer;

public interface CustomerDAO extends GenericDAO<Customer>{
	public List<Customer> findAllCustomers();
	public List<Customer> findCustomers(Customer customer);
}
```
**CustomeDAOImpl.java**
``` java
package com.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.domain.Customer;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public void create(Customer customer) throws Exception {
		ofy().save().entity(customer).now();
	}

	@Override
	public boolean update(Customer customer) throws Exception {
		boolean updateResult;
		if (customer == null || customer.getId() == null) {
			updateResult = false;
		} else {
			Customer c = ofy().load().type(Customer.class).id(customer.getId()).now();
			if (c != null) {
				ofy().save().entity(customer).now();
				updateResult = true;
			} else {
				updateResult = false;
			}
		}
		return updateResult;
	}

	@Override
	public boolean remove(Customer customer) throws Exception {
		ofy().delete().entity(customer).now();
		return true;
	}

	@Override
	public List<Customer> findAllCustomers() {
		Query<Customer> query = ofy().load().type(Customer.class);
		return query.order("name").list();		
	}

	@Override
	public List<Customer> findCustomers(Customer customer) {
		Query<Customer> query = ofy().load().type(Customer.class);
		List<Customer> returnList = new ArrayList<Customer>();
		if (customer.getId()!=null) {
			Customer c = ofy().load().key(Key.create(Customer.class, customer.getId())).now();
			if (c!=null) {
				returnList.add(c);
			}
		} else {
			if (StringUtils.isNotBlank(customer.getName())) {
				query = query.filter("name", customer.getName());
			}
			if (StringUtils.isNotBlank(customer.getSurname01())) {
				query = query.filter("surname01", customer.getSurname01());
			}
			if (StringUtils.isNotBlank(customer.getSurname02())) {
				query = query.filter("surname02", customer.getSurname02());
			}
			returnList = query.order("name").list();
		}
		return returnList;
	}
}
```
***
### Service example  
In this example I've used an interface and a class.  

**CustomerManager.java**
``` java
package com.services;
import java.util.List;
import com.domain.Customer;
public interface CustomerManager {
	public List<Customer> customers();
	public List<Customer> customers(Customer customer);
	public void add(Customer customer) throws Exception;
	public boolean remove(Customer customer) throws Exception;
	public boolean update(Customer customer) throws Exception;
}
```
**CustomerManagerImpl.java**
``` java
package com.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.CustomerDAO;
import com.domain.Customer;

@Service ("customerManager")
public class CustomerManagerImpl implements CustomerManager {

	@Autowired CustomerDAO dao;

	@Override
	public List<Customer> customers() {		
		return dao.findAllCustomers();
	}

	@Override
	public List<Customer> customers(Customer customer) {
		return dao.findCustomers(customer);
	}
	@Override
	public void add(Customer customer) throws Exception{
		dao.create(customer);		
	}
	...
}
```
***
##### Controller
Two methods (doSearch and viewOrders) in this controller return a json as response.   
To do this use the annotation @ResponseBody.
Also it is necesary to add some dependences in the pom file.
``` xml
<!-- Jackson JSON Mapper -->
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-core</artifactId>
	<version>2.4.1</version>
</dependency>
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
	<version>2.4.1.1</version>
</dependency>
```

``` java
package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.domain.Customer;
import com.domain.Order;
import com.googlecode.objectify.Ref;
import com.services.CustomerManager;

@Controller
public class MyController {

	private static final Logger logger = Logger.getLogger(MyController.class);

	@Autowired
	CustomerManager customerManager;

	@RequestMapping(value = {"/customersList","/"}, method = RequestMethod.GET)
	public String customerList(ModelMap modelMap) {
		modelMap.addAttribute("customersList", customerManager.customers());
		return "customers";
	}

	@RequestMapping(value = "/doSearch")
	public @ResponseBody List<Map<String, Object>> doSearch(
			@RequestParam(value = "id", required = false, defaultValue = "") Long id,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "surname01", required = false, defaultValue = "") String surname01,
			@RequestParam(value = "surname02", required = false, defaultValue = "") String surname02) {
		logger.debug("searching customers");
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);
		customer.setSurname01(surname01);
		customer.setSurname02(surname02);
		List<Customer> customers = customerManager.customers(customer);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Customer c : customers) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", c.getId());
			data.put("name", c.getName());
			data.put("surname01", c.getSurname01());			
			data.put("surname02", c.getSurname02());
			list.add(data);
		}		
		return list;
	}

	@RequestMapping(value="/viewOrders")
	public @ResponseBody List<Order> viewOrders(@RequestParam(value="idCustomer", required=true) Long idCustomer){
		logger.debug("idCustomer " + idCustomer);
		List<Order> orders = new ArrayList<Order>();
		Customer customer = new Customer();
		customer.setId(idCustomer);
		customer = customerManager.customers(customer).get(0);
		for (Ref<Order> myRef : customer.getOrders()) {
			orders.add(myRef.get());
		}
		return orders;
	}
}
```

***
### Angular Smart-table
``` jsp
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-smart-table/2.1.6/smart-table.js"></script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<meta charset="utf-8">
<title>Customers - List</title>
<script type="text/javascript">
	$(document).ready(function() { });
	var app = angular.module('myApp', [ 'smart-table' ]);
	app.controller('paginationCtrl', [ '$scope', '$http', '$window',
			function($scope, $http, $window) {
				$scope.itemsByPage = 10;
				$scope.rowCollection = [];
				$scope.displayedCollection = [].concat($scope.rowCollection);
				$scope.search = function doSearch() {
					var params = {
						params : {
							"id" : $scope.filterId,
							"name" : $scope.filterName,
							"surname01" : $scope.filterSurname01,
							"surname02" : $scope.filterSurname02
						}
					};
					$http.get("doSearch", params).success(function(response) {
						console.log("response ->" + response);
						console.log("response ->" + response.length);
						$scope.rowCollection = response;
					})
				}
				$scope.queryOrders = function doSearch(myCustomer) {
					console.log(myCustomer);
					console.log(myCustomer.id);
					var params = {
						params : {
							"idCustomer" : myCustomer.id,
						}
					};
					$http.get("viewOrders", params).success(function(response) {
						console.log("response ->" + response);
						console.log("response ->" + response.length);
						var newHtml = "";
						for (i = 0; i < response.length; i++) {
							newHtml += response[i].name + "<br>";
						}
						$('#modalContent').html(newHtml);
						$('#myModal').modal('show')
					})
				}
			} ]);
</script>
</head>
<body ng-app="myApp">
	<div class="container">
		<h3>Customers</h3>
		<form ng-submit="search()" ng-controller="paginationCtrl" class="form-inline">
			<div class="form-group">
				<label for="name">Id</label> <input type="number" class="form-control" id="id" ng-model="filterId">
			</div>
			<div class="form-group">
				<label for="name">Name</label> <input type="text" class="form-control" id="name" ng-model="filterName">
			</div>
			<div class="form-group">
				<label for="name">Surname_01</label> <input type="text" class="form-control" id="surname01" ng-model="filterSurname01">
			</div>
			<div class="form-group">
				<label for="name">Surname_02</label> <input type="text" class="form-control" id="surname02" ng-model="filterSurname02">
			</div>
			<div class="form-group">
				<button type="button" class="btn btn-default" ng-click="search()">Search</button>
			</div>
			<hr>
			<table st-table="displayedCollection" st-safe-src="rowCollection" class="table table-striped">
				<thead>
					<tr>
						<th st-sort="id">Id</th>
						<th st-sort="name">Name</th>
						<th st-sort="surname01">Surname 01</th>
						<th st-sort="surname02">Surname 02</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="row in displayedCollection">
						<td>{{row.id}}</td>
						<td>{{row.name}}</td>
						<td>{{row.surname01}}</td>
						<td>{{row.surname02}}</td>
						<td align="center">
							<button type="button" ng-click="queryOrders(row)" class="btn btn-md btn-warning">
								<i class="glyphicon glyphicon-eye-open"> </i>&nbsp;View Orders
							</button>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5" class="text-center">
							<div st-pagination="" st-items-by-page="itemsByPage" st-displayed-pages="7"></div>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
		<br>
		<hr>
		<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Modal Header</h4>
					</div>
					<div class="modal-body">
						<div id="modalContent"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
```
