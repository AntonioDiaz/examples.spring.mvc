## Spring MVC project with Tiles
Example of the use Apache Tiles on a web application built with Spring MVC.

**Important points**  
* [Project Structure](#project-structure)  
* [applicationContext.xml](#applicationcontextxml)  
* [pom.xml](#pomxml)
* [tiles_definitions.xml](#tiles_definitionsxml)
* [template.jsp](#templatejsp)  

***
##### Project Structure
![](https://antoniodiaz.github.io/images/spring_mvc/05_structure.jpg)

***
##### applicationContext.xml
``` xml
<bean id="tilesViewResolver" class="org.springframework.web.servlet.view.UrlBasedViewResolver">
	<property name="viewClass" value="org.springframework.web.servlet.view.tiles3.TilesView" />
	<property name="order" value="1" />
</bean>
<bean id="tilesConfigurer" class="org.springframework.web.servlet.view.tiles3.TilesConfigurer">
	<property name="definitions">
		<list>
			<value>/WEB-INF/tiles_definitions.xml</value>
		</list>
	</property>
</bean>
```

***
##### pom.xml  
``` xml
<properties>
	<tiles.version>3.0.5</tiles.version>
</properties>
<dependencies>
	<dependency>
		<groupId>org.apache.tiles</groupId>
		<artifactId>tiles-jsp</artifactId>
		<version>${tiles.version}</version>
	</dependency>
	<dependency>
		<groupId>org.apache.tiles</groupId>
		<artifactId>tiles-servlet</artifactId>
		<version>${tiles.version}</version>
	</dependency>
	<dependency>
		<groupId>org.apache.tiles</groupId>
		<artifactId>tiles-template</artifactId>
		<version>${tiles.version}</version>
	</dependency>
	<dependency>
		<groupId>org.apache.tiles</groupId>
		<artifactId>tiles-el</artifactId>
		<version>${tiles.version}</version>
	</dependency>
</dependencies>
```

***
##### tiles_definitions.xml  
``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE tiles-definitions PUBLIC "-//Apache Software Foundation//DTD Tiles Configuration 3.0//EN" "http://tiles.apache.org/dtds/tiles-config_3_0.dtd">
<tiles-definitions>
	<definition name="homeTemplate" template="/WEB-INF/jsp/template.jsp">
		<put-attribute name="title" value="Tiles with Spring MV" />
	</definition>
	<definition name="home" extends="homeTemplate">
		<put-attribute name="header" value="/WEB-INF/jsp/header.jsp" />
		<put-attribute name="body" value="/WEB-INF/jsp/body.jsp" />
	</definition>
</tiles-definitions>
```

***
##### template.jsp  
``` jsp  
<!DOCTYPE html>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title><tiles:insertAttribute name="title" ignore="true" defaultValue="title" /></title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
</body>
</html>
```
