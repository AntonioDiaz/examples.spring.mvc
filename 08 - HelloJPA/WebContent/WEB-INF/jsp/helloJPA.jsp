<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<meta charset="utf-8">
<title>Hello Spring MVC - JPA</title>
</head>
<body>
	<div class="container">
		<div class="page-header">
			<h3>Hello Spring MVC - JPA</h3>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<table class="table">
					<thead>
						<tr>
							<th>Id</th>
							<th>First Name</th>
							<th>Last Name</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${people_list}" var="person">
							<tr>
								<td>${person.id}</td>
								<td>${person.firstName}</td>
								<td>${person.lastName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col-sm-6">
				<table class="table">
					<thead>
						<tr>
							<th>Id</th>
							<th>Number</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${phones}" var="myPhone">
							<tr>
						    	<td>${myPhone.id}</td>
						    	<td>${myPhone.number}</td>
					    	<tr>
						</c:forEach>
					</tbody>
				</table>
			
			</div>
		</div>
	</div>
</body>
</html>