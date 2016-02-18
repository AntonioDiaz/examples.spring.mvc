## Example of a controller using Spring MVC 
* [pom.xml](#pomxml) 
* [web.xml](#webxml) 
* [applicationContext.xml](#applicationcontextxml) 
* [MyController.java](#mycontrollerjava) 
* [hello.jsp](#hellojsp)  
* [Commons Apache toString](#commons-apache-tostring) 


***
##### pom.xml
```xml
...
<properties>
	<spring.version>3.2.13.RELEASE</spring.version>
	<jstl.version>1.2</jstl.version>
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
</dependencies>
```
***
 
##### web.xml  
```xml
...
<servlet>
	<servlet-name>spring-web</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/applicationContext.xml</param-value>
	</init-param>		
	<load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
	<servlet-name>spring-web</servlet-name>
	<url-pattern>/</url-pattern>
</servlet-mapping>
```
***

##### applicationContext.xml  
```xml
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:context="http://www.springframework.org/schema/context" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:mvc="http://www.springframework.org/schema/mvc"
		xsi:schemaLocation="
	        http://www.springframework.org/schema/beans     
        	http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
	        http://www.springframework.org/schema/mvc 
        	http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd
	        http://www.springframework.org/schema/context 
        	http://www.springframework.org/schema/context/spring-context-3.2.xsd">
	<context:component-scan base-package="com" />
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        	<property name="prefix">
        		<value>/WEB-INF/jsp/</value>
        	</property>
        	<property name="suffix">
	        	<value>.jsp</value>
	        </property>
	</bean>
</beans>
```
***

##### MyController.java 
```java
package com;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {
	
	@RequestMapping(value="/helloWeb", method=RequestMethod.GET)
	public String wellcomePage(ModelMap modelMap) {
		modelMap.addAttribute("message", "Spring 3 MVC Hello World");
		return "hello";
	}	
}
```
***

##### hello.jsp
```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Hello Controller</title>
  </head>
  <body>
    <h3>Hello Controller</h3>
    <br>
    ${message}
  </body>
</html>  
```

***
##### Commons Apache toString
* pom.xml
``` xml
...
<properties>
	<apache.commons>3.4</apache.commons>
</properties>
<dependency>
	<groupId>org.apache.commons</groupId>
	<artifactId>commons-lang3</artifactId>
	<version>${apache.commons}</version>
</dependency>	
```
* java use:
``` java
import org.apache.commons.lang3.builder.ToStringBuilder;

@Override
public String toString() {
	return ToStringBuilder.reflectionToString(this);
}
```
