package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import Util.DBUtil;
import model.User;

import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBUtil.getConnection()) {

            if (conn == null) {
                response.sendRedirect("login.jsp?error=db");
                return;
            }

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    HttpSession session = request.getSession();
                    session.setAttribute("user", new User(username, password, role));

                    if ("ADMIN".equals(role)) {
                        response.sendRedirect("adminTripManager.jsp");
                    } else {
                        response.sendRedirect("passengerTripSearch.jsp");
                    }

                } else {
                    response.sendRedirect("login.jsp?error=1"); // invalid credentials
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=2"); // SQL or connection error
        }
    }
}

