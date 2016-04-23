<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Hello Controller</title>
</head>
<body>
	<h1>Error Page</h1>
	<p>Application has encountered an error. Please contact support on ...</p>
	<hr>
	Failed URL: ${url}
	<br>
	Exception:  ${exception}
	<br>
	<c:forEach items="${exception.stackTrace}" var="ste">    
		<pre>        ${ste}<br></pre> 
	</c:forEach>
</body>
</html>