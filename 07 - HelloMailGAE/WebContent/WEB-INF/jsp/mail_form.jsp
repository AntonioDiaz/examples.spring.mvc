<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Send mail from GAE</title>
</head>
<script>
</script>
<body>
	<c:if test="${result=='ok'}">
		<script type="text/javascript">
			alert ("Enviado");
		</script>
	</c:if>
	<h3>Send mail from GAE</h3>
	<hr>
	<form action="sendMail">
		From: <input type="text" id="from" name="from" value="sender@gmail.com" size="50">
		<br>
		To: <input type="text" id="to" name="to" value="receiver@gmail.com" size="50">
		<br>
		<br>
		<input type="submit" value="submit">
	</form>
</body>
</html>
