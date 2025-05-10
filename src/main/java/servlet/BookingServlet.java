package servlet;

import factory.TicketFactory;
import model.User;
import model.strategy.*;
import model.tickets.Ticket;
import Util.DBUtil;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/bookTicket")
public class BookingServlet extends HttpServlet {

    private static final int MAX_SEATS = 50;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !"PASSENGER".equals(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int tripId = Integer.parseInt(request.getParameter("tripId"));
        String ticketType = request.getParameter("ticketType");
        String userCategory = request.getParameter("userCategory");
        String travelType = request.getParameter("travelType");


        FareStrategy baseStrategy;
        switch (userCategory.toUpperCase()) {
            case "STUDENT":
                baseStrategy = new StudentFareStrategy();
                break;
            case "SENIOR":
                baseStrategy = new SeniorFareStrategy();
                break;
            default:
                baseStrategy = new RegularFareStrategy();
                break;
        }

        // only wrap with EveningFareStrategy if it's ONE_TRIP and time is after 7 PM
        FareStrategy strategy = baseStrategy;
        if ("ONE_TRIP".equalsIgnoreCase(ticketType) && LocalTime.now().isAfter(LocalTime.of(19, 0))) {
            strategy = new EveningFareStrategy(strategy);
        }


        Ticket ticket = TicketFactory.createTicket(
                ticketType,
                tripId,
                user.getUsername(),
                LocalDate.now(),
                strategy,
                travelType
        );

        ticket.calculateFare();


        synchronized (BookingServlet.class) {
            try (Connection conn = DBUtil.getConnection()) {
                // Count how many tickets were already booked for this trip
                PreparedStatement countStmt = conn.prepareStatement("SELECT COUNT(*) FROM tickets WHERE trip_id = ?");
                countStmt.setInt(1, tripId);
                ResultSet rs = countStmt.executeQuery();

                int bookedSeats = 0;
                if (rs.next()) {
                    bookedSeats = rs.getInt(1);
                }

                if (bookedSeats >= MAX_SEATS) {
                    response.sendRedirect("soldOut.jsp");
                    return;
                }


                PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO tickets (trip_id, passenger_username, ticket_type, purchase_date, fare) VALUES (?, ?, ?, ?, ?)"
                );
                insertStmt.setInt(1, ticket.getTripId());
                insertStmt.setString(2, ticket.getPassengerUsername());
                insertStmt.setString(3, ticket.getType());
                insertStmt.setDate(4, java.sql.Date.valueOf(ticket.getPurchaseDate()));
                insertStmt.setDouble(5, ticket.getFare());

                insertStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
                return;
            }
        }

        response.sendRedirect("passengerTickets.jsp");
    }
}
