package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.TripRepository;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/adminTrips")
public class TripManagerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        String departureStr = request.getParameter("departureDate"); // must match input field name
        String travelType = request.getParameter("travelType");

        if (departureStr == null || departureStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing departure date");
            return;
        }

        try {
            LocalDate departureDate = LocalDate.parse(departureStr);
            TripRepository.addTrip(origin, destination, departureDate, travelType);
            response.sendRedirect("adminTripManager.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to parse date or save trip");
        }
    }
}
