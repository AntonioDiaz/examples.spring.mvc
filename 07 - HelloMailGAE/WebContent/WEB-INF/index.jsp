<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Send mail from GAE</title>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() { 
			<c:if test='${result=="ok"}'> 
				$('#modalContent').html('Email sent successufully');
				$('#myModal').modal('show');
			</c:if>
			<c:if test='${result=="fail"}'> 
				$('#modalContent').html('Email could not be sent');
				$('#myModal').modal('show');
			</c:if>
		});	
	</script>	
</head>
<script>
</script>
<body style="100%">
	<div class="container">
		<div class="row">
			<div class="col-sm-4">
				<h3>Send mail from GAE</h3>
				<hr>
				<form action="sendMailAPI">
					<div class="row">
						<div class="col-sm-3">From </div> 
						<div class="col-sm-9"><input type="text" id="from" name="from" value="" size="50" class="form-control"></div>
					</div>
					<div class="row">
						<div class="col-sm-3">To</div>
						<div class="col-sm-9"><input type="text" id="to" name="to" value="" size="50" class="form-control"></div>
					</div>
					<div class="row">
						<div class="col-sm-3">&nbsp;</div>
						<div class="col-sm-9"><button type="submit" class="btn btn-primary btn-block">Send now!</button></div>
					</div>
				</form>
			</div>
			<div class="col-sm-2">&nbsp;</div>
			<div class="col-sm-4">
				<h3>Send mail from GAE: SendGrid </h3>
				<hr>
				<form action="sendMailGrid" method="post">
					<div class="row">
						<div class="col-sm-3">From</div>
						<div class="col-sm-9"><input type="text" class="form-control" id="from" name="from" value=""></div>
					</div>
					<div class="row">
					  	<div class="col-sm-3">To</div>
					  	<div class="col-sm-9"><input type="text" class="form-control" id="to" name="to" value=""></div>
					</div>
					<div class="row">
					  	<div class="col-sm-3">Text</div>
					  	<div class="col-sm-9"><textarea class="form-control" rows="5" cols="24" id="text" name="text"></textarea></div>
					</div>
					<div class="row">
					  	<div class="col-sm-3">&nbsp;</div>
					  	<div class="col-sm-9"><button type="submit" class="btn btn-primary btn-block">Send now!</button></div>
					</div>
				 </form>				
			</div>
			<div class="col-sm-2">&nbsp;</div>
		</div>
	</div>
	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
	    <div class="modal-dialog modal-sm">
	        <!-- Modal content-->
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal">&times;</button>
	                <h4 class="modal-title">Modal Header</h4>
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
