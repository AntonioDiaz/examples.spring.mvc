<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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