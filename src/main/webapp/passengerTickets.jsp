<%@ page import="java.sql.*, java.util.*, java.time.LocalDate, Util.DBUtil, model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"PASSENGER".equals(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<html>
<head><title>My Tickets</title></head>
<body>

<h2>Tickets for <%= user.getUsername() %></h2>

<a href="passengerTripSearch.jsp">Back to Trip Search</a> |
<a href="logout">Logout</a>


<br><br>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>Ticket ID</th>
        <th>Trip ID</th>
        <th>Ticket Type</th>
        <th>Purchase Date</th>
        <th>Fare (JD)</th>
        <th>Action</th>
    </tr>
    <%
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM tickets WHERE passenger_username = ? ORDER BY purchase_date DESC"
             )) {

            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();

            boolean hasTickets = false;
            while (rs.next()) {
                hasTickets = true;
    %>
    <tr>
        <td><%= rs.getInt("id") %></td>
        <td><%= rs.getInt("trip_id") %></td>
        <td><%= rs.getString("ticket_type") %></td>
        <td><%= rs.getDate("purchase_date") %></td>
        <td><%= rs.getDouble("fare") %></td>
        <td>
            <form method="post" action="cancelTicket" onsubmit="return confirm('Are you sure you want to cancel this ticket?');">
                <input type="hidden" name="ticketId" value="<%= rs.getInt("id") %>">
                <input type="submit" value="Cancel">
            </form>
        </td>
    </tr>
    <%
        }

        if (!hasTickets) {
    %>
    <tr>
        <td colspan="6">No tickets found.</td>
    </tr>
    <%
        }

    } catch (Exception e) {
    %>
    <tr>
        <td colspan="6">Error loading tickets.</td>
    </tr>
    <%
            e.printStackTrace();
        }
    %>
</table>

</body>
</html>
