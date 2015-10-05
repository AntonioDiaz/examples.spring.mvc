<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Hello Controller</title>
    <script type="text/javascript">
    	function fTicketLoaded(myIFrame) {
    		try {
    			myIFrame.contentWindow.print();
			} catch(e){
				console.log(e);
            }
		}    
    </script>
  </head>
  <body>
    <h3>Hello Controller</h3>
    <br>
    ${message}
    <br>
<div style="width: 100%; height: 500px; background: aqua; visibility: visible;">    
<iframe id="visualizador-pdf" name="visualizador-pdf" src="getPdf" height="400" width="521" onload="fTicketLoaded(this)"></iframe>
 <a class="pdf-print" id="wp-submit"></a>
 </div>
     
    
  </body>
</html> 