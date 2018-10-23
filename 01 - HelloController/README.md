## Example of a controller using Spring MVC
* [Project structure](#project-structure)
* [Front End](#front-end)
* [pom.xml](#pomxml)
* [web.xml](#webxml)
* [applicationContext.xml](#applicationcontextxml)
* [MyController.java](#mycontrollerjava)
* [hello.jsp](#hellojsp)  
* [Commons Apache toString](#commons-apache-tostring)
* [Log4j](#log4j)  
* [Initialize app](#initialize-app)  
* [Using properties method 1](#using-properties-method-1)  
* [Using properties method 2](#using-properties-method-2)  
* [Exception handling](#exception-handling)  

***
### Project structure  
[[https://antoniodiaz.github.io/images/spring_mvc/01_structure.jpg]]

***
### Front End
[[https://antoniodiaz.github.io/images/spring_mvc/01_front_end.jpg]]

***
### pom.xml
```xml
...
<dependencies>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-webmvc</artifactId>
		<version>3.2.13.RELEASE</version>
	</dependency>
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>jstl</artifactId>
		<version>1.2</version>
	</dependency>  
</dependencies>
```
***

### web.xml  
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

### applicationContext.xml  
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

### MyController.java
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

### hello.jsp
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
### Commons Apache toString
* pom.xml
``` xml
...
<dependency>
	<groupId>org.apache.commons</groupId>
	<artifactId>commons-lang3</artifactId>
	<version>3.4</version>
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
***

### log4j
Add log4j dependence to pom.xml:
``` xml
...
<dependency>
	<groupId>log4j</groupId>
	<artifactId>log4j</artifactId>
	<version>1.2.17</version>
</dependency>
```
Use log4j in java:
``` java
	import org.apache.log4j.Logger;
	// declare logger
	private static final Logger logger = Logger.getLogger(HelloFormsController.class);
	//
	logger.debug("newentity");
```
Config log4j.properties:
``` properties
# Root logger option
log4j.rootLogger=DEBUG, stdout, file

# Redirect log messages to console
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Redirect log messages to a log file
log4j.appender.file=org.apache.log4j.RollingFileAppender
#outputs to Tomcat home
log4j.appender.file.File=${catalina.home}/logs/helloForms.log
log4j.appender.file.MaxFileSize=5MB
log4j.appender.file.MaxBackupIndex=10
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
```
***

### Initialize app  
* applicationContext.xml
``` xml
	<bean id="initDataBase" class="com.InitDataBase" init-method="init"/>
```

* InitDataBase.java
``` java
package com;

import org.apache.log4j.Logger;

public class InitDataBase {

	private static final Logger logger = Logger.getLogger(InitDataBase.class);

	public void init() {
		logger.debug("init DATABASE");
	}
}
```
***

### Using properties method 1
* config.properties
``` java
mail_user=BONITO
mail_password=123456
```
* applicationContext.xml
``` xml
<context:property-placeholder location="classpath:config.properties" />
```
* HelloController.java
``` java
import org.springframework.beans.factory.annotation.Value;
...
@Value("${mail_user}")
protected String username;
```
***

### Using properties method 2  
* config.properties
``` java
mail_address=simeone@cholo.com
```
* HelloController.java
``` java
import java.util.ResourceBundle;
...
public static ResourceBundle settings = ResourceBundle.getBundle("config");
...
String emailAddress = settings.getString("mail_address");
```
***

### Exception Handling  
This example shows how to handler an exception in a controller and create a view to show the info in more or less an elegant way.  
Thanks to <a href="https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc" target="_blank">spring.io/blog/2013/11/01/exception-handling-in-spring-mvc</a>  
* **HelloController.java**
``` java
import org.springframework.web.bind.annotation.ExceptionHandler;

@ExceptionHandler(Exception.class)
public ModelAndView handleAllException(HttpServletRequest req,Exception exception) {
    logger.error("Request: " + req.getRequestURL() + " raised " + exception);
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", exception);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName("error");
    return mav;
}
```
* **error.jsp**  
``` html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Hello Controller</title>
</head>
<body>
	<h1>Error Page</h1>
	<p>Application has encountered an error. Please contact support on ...</p>
	<hr>
	Failed URL: ${url}
	<br>
	Exception:  ${exception}
	<br>
	<c:forEach items="${exception.stackTrace}" var="ste">    
		<pre>        ${ste}<br></pre>
	</c:forEach>
</body>
</html>
```
