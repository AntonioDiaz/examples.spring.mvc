<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Spring MVC: working with forms</title>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<!-- bootstrap-datetimepicker  -->
<script type="text/javascript" src="resources/js/moment.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-datetimepicker.css" />
<style type="text/css">
.error_class {
	color: red;
	font-weight: bold;
}
</style>
<script type="text/javascript">
		$(document).ready(function() { 
			<c:if test='${result=="ok"}'> 
				$('#modalContent').html('Everything works fine.');
				$('#myModal').modal('show');
			</c:if>
		});	
	</script>
</head>
<body>
	<div class="container">
		<h3>Hello Form</h3>
		<br>
		<div style="width: 50%">
			<form:form method="post" action="doCreateEntity" commandName="my_form" cssClass="form-horizontal">
				<div class="row">
					<div class="col-sm-6">
						Name:
						<form:input path="name" cssClass="xlarge form-control" size="30" placeholder="Entity name" />
					</div>
					<div class="col-sm-6">
						<br>
						<form:errors path="name" cssClass="help-inline error_class" />
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-6">
						Surname:
						<form:input path="surname" cssClass="xlarge form-control" size="30" placeholder="Entity surname" />
					</div>
					<div class="col-sm-6">
						<br>
						<form:errors path="surname" cssClass="help-inline error_class" />
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-6">
						Birthdate
						<div class='input-group date' id='datetimepicker1'>
							<!-- <form:input type="text" path="birthdate" cssClass="xlarge form-control" size="30" />-->
							<input type='text' class="form-control" id="birthdate" name="birthdate" />
								<span class="input-group-addon"> 
									<span class="glyphicon glyphicon-calendar"></span>
								</span>
						</div>
					</div>
					<script type="text/javascript">
						$(function () {
							$('#datetimepicker1').datetimepicker({
								defaultDate: "${birthdate_str}"
							});
						});
		        	</script>
					<div class="col-sm-6">
						<br>
						<form:errors path="birthdate" cssClass="help-inline" />
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-2">
						<button type="submit" class="btn btn-primary btn-block">send</button>
					</div>
					<div class="col-sm-10"></div>
				</div>
			</form:form>
		</div>
	</div>
	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Entity Created</h4>
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
</body>
</html>