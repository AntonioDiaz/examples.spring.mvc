<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Hello Print MVC</title>
	<script type="text/javascript">
		function fGeneratePdf() {
			document.location.href = "getPdf";
		}
		
		function fPrintReceipt() {
			document.location.href = "helloWeb";
		}
	</script>
</head>
<body style="margin-left: 60px;">
	<h3>Hello PRINTER :)</h3>
	<br>
	<button type="button" onclick="fGeneratePdf();" style="width: 100px">generate PDF</button>
	<br><br>
	<button type="button" onclick="fPrintReceipt();" style="width: 100px">print receipt!</button>
</body>
</html>
