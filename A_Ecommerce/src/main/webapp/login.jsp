<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register OR Login</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="loginScript.js"></script>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body class="container">
		<div id="login" class="firstpage">
				<h1>Login</h1>
				<b>UserName</b><input type="text" id="name" name="name" required><br><br>
				<b>Password </b><input type="password" id="password" name="password" required><br><br>
				<button onclick="login()">Submit</button>
				<button type="reset">Reset</button>
				<p><a style="cursor:pointer;" onclick="toRegister()">New user click here</a></p>
		</div>
		<div id="register" class="firstpage">
			<h1>Register a new Account</h1>
			<b>UserName</b><input type="text" id="r_name" name="name" required><br><br>
			<b>Email-ID</b><input type="text" id="r_email" name="email" required><br><br>
            <b>Password </b><input type="password" id="r_password" name="password" required><br><br>   
            <button onclick="register()">Submit</button>
         	<br><br>
            <a onclick="toLogin()" style="cursor:pointer;" text-decoration="none">Already have an account</a>
        </div>

</body>


</html>