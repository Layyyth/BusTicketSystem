<%@ page import="java.util.*, java.time.LocalDate, model.Trip, repository.TripRepository, model.User, config.FareConfig" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null || !"ADMIN".equals(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
  }

  List<Trip> trips = TripRepository.getAllTrips();
  FareConfig fareSettings = FareConfig.getInstance();
%>

<html>
<head>
  <title>Admin Trip Manager</title>
</head>
<body>

<a href="adminReport.jsp">View Reports</a> |
<a href="logout">Logout</a>

<h2>Welcome, Admin <%= user.getUsername() %>!</h2>

<!-- Fare Config Form -->
<h3>Fare Configuration</h3>
<form method="post" action="fare-config">
  Base Fare (JD): <input type="number" step="0.01" name="baseFare" value="<%= fareSettings.getBaseFare() %>" required><br><br>
  Student Discount (%): <input type="number" step="0.01" name="studentDiscount" value="<%= fareSettings.getStudentDiscount() %>" required><br><br>
  Senior Discount (%): <input type="number" step="0.01" name="seniorDiscount" value="<%= fareSettings.getSeniorDiscount() %>" required><br><br>
  Evening Discount (%): <input type="number" step="0.01" name="eveningDiscount" value="<%= fareSettings.getEveningDiscount() %>" required><br><br>
  <input type="submit" value="Update Fare Settings">
</form>

<hr>

<!-- Add New Trip Form -->
<h3>Add New Trip</h3>
<form method="post" action="adminTrips">
  Origin: <input type="text" name="origin" required><br><br>
  Destination: <input type="text" name="destination" required><br><br>
  Departure Date: <input type="date" name="departureDate" required><br><br>
  Travel Type:
  <select name="travelType">
    <option value="CITY">City</option>
    <option value="INTERCITY">Inter-City</option>
  </select><br><br>
  <input type="submit" value="Add Trip">
</form>

<!-- Trips Table -->
<h3>All Trips</h3>
<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>ID</th>
    <th>Origin</th>
    <th>Destination</th>
    <th>Departure Date</th>
    <th>Travel Type</th>
  </tr>
  <%
    for (Trip trip : trips) {
  %>
  <tr>
    <td><%= trip.getId() %></td>
    <td><%= trip.getOrigin() %></td>
    <td><%= trip.getDestination() %></td>
    <td><%= trip.getDepartureTime() %></td>
    <td><%= trip.getTravelType() %></td>
  </tr>
  <%
    }
  %>
</table>

</body>
</html>
