<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Login</title></head>
<body>

<h2>Login</h2>

<% if (request.getParameter("error") != null) { %>
<p style="color:red;">Invalid username or password.</p>
<% } %>

<form method="post" action="login">
    Username: <input type="text" name="username" required><br><br>
    Password: <input type="password" name="password" required><br><br>
    <input type="submit" value="Login">
</form>

</body>
</html>
<p>Don't have an account? <a href="register.jsp">Register here</a></p>
