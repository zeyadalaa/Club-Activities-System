<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/loginPage.css">
</head>
<body>
    <div class="container">
        <h1>Admin Login</h1>
        <form method="post" action="${pageContext.request.contextPath}/myAdmin">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br><br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br><br>
            <input type="submit" value="Login">
        </form>
    </div>
    
	<% if (request.getAttribute("errorMessage") != null) { %>
       <script>
       	alert("<%= request.getAttribute("errorMessage") %>")
       </script>
   	<% } %>
</body>
</html>