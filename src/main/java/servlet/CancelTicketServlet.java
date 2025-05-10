package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import Util.DBUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/cancelTicket")
public class CancelTicketServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int ticketId = Integer.parseInt(request.getParameter("ticketId"));

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM tickets WHERE id = ?")) {

            stmt.setInt(1, ticketId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("passengerTickets.jsp");
    }
}
