<%@ page import="java.sql.*, Util.DBUtil, model.User" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null || !"ADMIN".equals(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
  }
%>

<html>
<head><title>Admin Reports</title></head>
<body>

<h2>Ticket Sales Report</h2>
<a href="adminTripManager.jsp">Back to Admin Dashboard</a>
<br><br>

<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>Ticket Type</th>
    <th>Tickets Sold</th>
    <th>Total Revenue (JD)</th>
  </tr>
  <%
    try (Connection conn = DBUtil.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(
                 "SELECT ticket_type, COUNT(*) AS total_sold, SUM(fare) AS revenue " +
                         "FROM tickets GROUP BY ticket_type ORDER BY ticket_type"
         )) {

      while (rs.next()) {
  %>
  <tr>
    <td><%= rs.getString("ticket_type") %></td>
    <td><%= rs.getInt("total_sold") %></td>
    <td><%= rs.getDouble("revenue") %></td>
  </tr>
  <%
    }

  } catch (Exception e) {
  %>
  <tr>
    <td colspan="3">Error loading report.</td>
  </tr>
  <%
      e.printStackTrace();
    }
  %>
</table>

</body>
</html>
