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
