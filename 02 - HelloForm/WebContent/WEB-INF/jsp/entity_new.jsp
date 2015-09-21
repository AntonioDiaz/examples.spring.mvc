<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Hello Controller - New Entity</title>
  </head>
  <body>
    <h3>Hello Controller - New Entity</h3>
    <br>
    <form:form method="post" action="doCreateEntity" commandName="my_form" cssClass="form-horizontal">
    	Nombre: <form:input path="name" cssClass="xlarge form-control" size="30" placeholder="Entity name" />
    	<br>
    	Surname: <form:input path="surname" cssClass="xlarge form-control" size="30" placeholder="Entity surname" />
    	<br>
    	Birthdate: <form:input path="birthdate" cssClass="xlarge form-control" size="30" placeholder="Entity birthdate"  type="date" />
    	<br>
    	<button type="button" onclick="document.getElementById('my_form').submit();">enviar</button>
    	<br>
    	<form:errors path="name" cssClass="help-inline" />
    </form:form>
    
    
  </body>
</html>