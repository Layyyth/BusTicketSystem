<%@ page import="java.util.*, java.time.LocalDate, model.Trip, repository.TripRepository" %>
<%
  model.User user = (model.User) session.getAttribute("user");
  if (user == null || !"PASSENGER".equals(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
  }

  List<Trip> trips = TripRepository.getAllTrips();
%>

<html>
<head><title>Search Trips</title></head>
<body>
<h2>Welcome, <%= user.getUsername() %>!</h2>
<a href="passengerTickets.jsp">My Tickets</a> |
<a href="logout">Logout</a>


<h3>Search Trips</h3>
<form method="get" action="passengerTripSearch.jsp">
  Origin: <input type="text" name="origin">
  Destination: <input type="text" name="destination">
  Travel Type:
  <select name="travelType">
    <option value="">Any</option>
    <option value="CITY">City</option>
    <option value="INTERCITY">Inter-City</option>
  </select>
  <input type="submit" value="Search">
</form>

<h3>Available Trips</h3>
<table border="1" cellpadding="5" cellspacing="0">
  <tr>
    <th>ID</th><th>Origin</th><th>Destination</th><th>Date</th><th>Type</th>
  </tr>

  <%
    String originFilter = request.getParameter("origin");
    String destFilter = request.getParameter("destination");
    String typeFilter = request.getParameter("travelType");

    for (Trip trip : trips) {
      boolean matches =
              (originFilter == null || originFilter.isEmpty() || trip.getOrigin().equalsIgnoreCase(originFilter)) &&
                      (destFilter == null || destFilter.isEmpty() || trip.getDestination().equalsIgnoreCase(destFilter)) &&
                      (typeFilter == null || typeFilter.isEmpty() || trip.getTravelType().equalsIgnoreCase(typeFilter));

      if (matches) {
  %>
  <tr>
    <td><%= trip.getId() %></td>
    <td><%= trip.getOrigin() %></td>
    <td><%= trip.getDestination() %></td>
    <td><%= trip.getDepartureTime() %></td>
    <td><%= trip.getTravelType() %></td>
  </tr>
  <tr>
    <td colspan="5">
      <form method="post" action="bookTicket">
        <input type="hidden" name="tripId" value="<%= trip.getId() %>">
        <input type="hidden" name="travelType" value="<%= trip.getTravelType() %>">

        Ticket Type:
        <select name="ticketType">
          <option value="ONE_TRIP">One Trip</option>
          <option value="DAILY_PASS">Daily Pass</option>
          <option value="WEEKLY_PASS">Weekly Pass</option>
          <option value="MONTHLY_PASS">Monthly Pass</option>
        </select>

        User Category:
        <select name="userCategory">
          <option value="REGULAR">Regular</option>
          <option value="STUDENT">Student</option>
          <option value="SENIOR">Senior</option>
        </select>

        <input type="submit" value="Book Ticket">
      </form>
    </td>
  </tr>
  <%
      }
    }
  %>
</table>

</body>
</html>
