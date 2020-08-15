<#-- @ftlvariable name="error" type="String" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LoggerDude</title>
    <link href="static/main.css" type="text/css" rel="stylesheet">
</head>
<body>
<h1>LoggerDude</h1>
<p>Welcome to the web console</p>
<form method="post">
    <input name="username" type="text" placeholder="username"><br>
    <input name="password" type="password" placeholder="password"><br>
    <input id="login-button" type="submit" value="Login">
    <label for="login-button">(no pun intended)</label>
</form>
<br>
<p style="color:red">${error}</p>

</body>
</html>