<#-- @ftlvariable name="logs" type="String" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>LoggerDude</title>
    <link rel="stylesheet" href="static/console.css" type="text/css">
</head>
<body>
<h1>Console</h1>
<p>Logs are overwritten each time a user dumps from the app. Make sure to copy and save them.</p>
<button onclick="copyElementText('logs')">Copy to clipboard</button>
<br>
<div id="log-view">
    <p id="logs">${logs}</p>
</div>
<br>
<form action="clear">
    <input type="submit" value="Clear">
</form>

<script>
    function copyElementText(id) {
        let text = document.getElementById(id).innerText;
        let elem = document.createElement("textarea");
        document.body.appendChild(elem);
        elem.value = text;
        elem.select();
        document.execCommand("copy");
        document.body.removeChild(elem);
    }
</script>

</body>
</html>