<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Hello Spring MVC - JPA</title>
  </head>
  <body>
    <h3>Hello Spring MVC - JPA</h3>
    <hr>    
    <c:forEach items="${persons}"  var="myPerson">
    	${myPerson}<br>
    </c:forEach>
    <hr>
    <c:forEach items="${phones}"  var="myPhone">
    	${myPhone}<br>
    </c:forEach>
    
  </body>
</html>