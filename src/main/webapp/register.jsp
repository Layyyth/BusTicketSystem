<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Register</title></head>
<body>

<h2>Passenger Registration</h2>

<% if ("1".equals(request.getParameter("error"))) { %>
<p style="color:red;">Username already exists. Try a different one.</p>
<% } %>

<form method="post" action="register">
  Username: <input type="text" name="username" required><br><br>
  Password: <input type="password" name="password" required><br><br>
  <input type="submit" value="Register">
</form>

<p>Already have an account? <a href="login.jsp">Login here</a></p>

</body>
</html>
