## Example of user authentication and authorization in Spring MVC 3.2.

**Important points**
* [Project Structure](#project-structure)  
* [pom.xml](#pomxml)
* [web.xml](#webxml)
* [applicationContext-security.xml](#applicationcontext-securityxml)
* [User Authentication](#user-authentication)
* [User Authorization](#user-authorization)

***
##### Project Structure
![](https://antoniodiaz.github.io/images/spring_mvc/03_structure.jpg)

***
##### pom.xml
``` xml
<properties>
	<spring-security.version>3.2.7.RELEASE</spring-security.version>
	<aspectj.version>1.6.12</aspectj.version>
</properties>
<dependencies>
	...
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-config</artifactId>
		<version>${spring-security.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-web</artifactId>
		<version>${spring-security.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-taglibs</artifactId>
		<version>${spring-security.version}</version>
	</dependency>
	<dependency>
		<groupId>org.aspectj</groupId>
		<artifactId>aspectjrt</artifactId>
		<version>${aspectj.version}</version>
	</dependency>
	<dependency>
		<groupId>org.aspectj</groupId>
		<artifactId>aspectjweaver</artifactId>
		<version>${aspectj.version}</version>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-aspects</artifactId>
		<version>${spring.version}</version>
	</dependency>
</dependencies>
```
***
##### web.xml
``` xml
...
<context-param>
	<param-name>contextConfigLocation</param-name>
	<param-value>/WEB-INF/applicationContext-security.xml</param-value>
</context-param>
...
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
<filter>
	<filter-name>springSecurityFilterChain</filter-name>
	<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>
<filter-mapping>
	<filter-name>springSecurityFilterChain</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
```
***
##### applicationContext-security.xml
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/security" xmlns:beans="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.2.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
        http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-3.2.xsd">
	<context:property-placeholder location="classpath*:META-INF/spring/*.properties" />
	<context:spring-configured />
	<context:component-scan base-package="com.controllers" />
	<context:component-scan base-package="com.service" />
	<http auto-config="true" use-expressions="true">
		<intercept-url pattern="/admin/*" access="hasRole('ROLE_ADMIN')" />		
		<intercept-url pattern="/login" access="permitAll" />
		<intercept-url pattern="/logout" access="permitAll" />
		<intercept-url pattern="/loginfailed" access="permitAll" />
		<intercept-url pattern="/**" access="isAuthenticated()" />
		<remember-me />
		<form-login login-page="/login" default-target-url="/"
                    authentication-success-handler-ref="myAuthenticationSuccessHandler"
                    authentication-failure-handler-ref="myAuthenticationFailureHandler" />
		<logout logout-success-url="/logout" />
	</http>
	<beans:bean id="myAuthenticationSuccessHandler" class="com.service.MyAuthenticationSuccessHandler">
		<!-- <property name="defaultFailureUrl" value="/index.jsp" /> -->
	</beans:bean>
	<beans:bean id="myAuthenticationFailureHandler" class="com.service.MyAuthenticationFailureHandler">
		<beans:property name="defaultFailureUrl" value="/loginfailed"></beans:property>
	</beans:bean>
	<!-- Configure Authentication mechanism -->
	<authentication-manager alias="authenticationManager">
		<!-- SHA-256 values can be produced using 'echo -n your_desired_password | sha256sum' (using normal *nix environments) -->
		<authentication-provider user-service-ref="customUserDetailsService">
			<password-encoder hash="sha-256" />
		</authentication-provider>
	</authentication-manager>
	<global-method-security secured-annotations="enabled" />
</beans:beans>
````
***
##### User Authentication
* **login.jsp**  
``` html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Hello Security Spring MVC</title>
</head>
<body>
	<h3>User Sign in</h3>
	<form method='POST' class="form-signin" action="<c:url value='j_spring_security_check' />">
		usuario : <input type="text" class="form-control" name='j_username'><br>
		password : <input type="password" class="form-control" name='j_password'><br>
		<button type="submit">	Entrar	</button>
	</form>
	<br>
	<c:if test="${not empty error}">
		<div style="color: red;">
			<p>Error de usuario o contraseña</p>
			Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
</body>
</html>
```
* **User.java**: implements interface **org.springframework.security.core.userdetails.UserDetails**  

``` java
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private boolean admin;
	private boolean enabled;
	private boolean bannedUser;
	private boolean accountNonExpired;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (admin) {
			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authorityList;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !bannedUser;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	...
}
```  
* **AbstractCurrentUserManager.java**

``` java
import org.springframework.security.core.context.SecurityContextHolder;
import com.domain.User;

public abstract class AbstractCurrentUserManager implements CurrentUserManager {
	@Override
	public User getEnabledUser() {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal;
	}
}
```  
* **CurrentUserManager.java**

``` java
import com.domain.User;

public interface CurrentUserManager {
	public User getEnabledUser();
}
```  
* **CustomUserDetailsService.java**

``` java
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.domain.User;

@Service ("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	Logger logger = Logger.getLogger(CustomUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("username: " + username);
		/** should find user by "username". */
		User user = new User();
		user.setUsername("adiaz");
		user.setEnabled(true);
		user.setBannedUser(false);
		user.setAccountNonExpired(true);
		/** password: admin*/
		user.setPassword("bb37067afeb4ee16d668eef073ca6eea4f3b4a1fc6c68e3c0b1fd01a5fb7f5ad");
		user.setAdmin(true);
		return user;
	}
}
```    
* **MyAuthenticationSuccessHandler.java**

``` java
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.domain.User;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = Logger.getLogger(MyAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
			throws IOException, ServletException {
		logger.debug("onAuthenticationSuccess");
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.debug("user name -->" + user.getUsername());
		logger.debug("user authorities -->" + user.getAuthorities());
		httpServletResponse.sendRedirect("");
	}
}
```
* **MyAuthenticationFailureHandler.java**

``` java
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger logger = Logger.getLogger(MyAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException) throws IOException, ServletException {
		logger.debug("onAuthenticationFailure");
		super.onAuthenticationFailure(httpServletRequest, httpServletResponse, authenticationException);		
	}
}
```
* **SecurityController.java**

``` java
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {

	private static final Logger logger = Logger.getLogger(SecurityController.class);

	@RequestMapping (value="/", method=RequestMethod.GET)
	public String goIndex(ModelMap modelMap){
		return "index";		
	}

	@RequestMapping (value="/login", method=RequestMethod.GET)
	public String goLogin(){
		logger.debug("goLoggin");
		return "login";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout() {
		return "login";
	}

	@RequestMapping (value="/loginfailed", method=RequestMethod.GET)
	public String goLoginFailed(ModelMap modelMap){
		modelMap.addAttribute("error", "true");
		return "login";
	}

	@RequestMapping (value="/test", method=RequestMethod.GET)
	public String goTest(ModelMap modelMap){
		return "test";
	}
}
```
* **logout**
``` jsp
	<a href="<c:url value="j_spring_security_logout" />"> LogOut </a>
```  
* **username**
``` jsp
	<c:out value="${pageContext['request'].userPrincipal.name }"></c:out>
```  
***  
##### User Authorization
See **[applicationContext-security.xml](#applicationcontext-securityxml)**
* **jsp authorization**

``` html
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Hello Security</title>
</head>
<body>
	<h2>Spring MVC - Security Example</h2>
	wellcome:
	<c:out value="${pageContext['request'].userPrincipal.name }"></c:out>
	<br>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<a href="admin/userslist">admin section</a>
		<br>
	</sec:authorize>
	<a href="test">all user section</a>
	<br>
	<a href="<c:url value="j_spring_security_logout" />"> LogOut </a>
</body>
</html>
```  
