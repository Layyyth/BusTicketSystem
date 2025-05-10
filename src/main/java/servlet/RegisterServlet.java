package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import Util.DBUtil;
import model.User;

import java.io.IOException;
import java.sql.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBUtil.getConnection()) {

            if (conn == null) {
                response.sendRedirect("register.jsp?error=db");
                return;
            }


            try (PreparedStatement check = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
                check.setString(1, username);
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    response.sendRedirect("register.jsp?error=1"); // username taken
                    return;
                }
            }


            try (PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
                insert.setString(1, username);
                insert.setString(2, password);
                insert.setString(3, "PASSENGER");
                insert.executeUpdate();
            }


            HttpSession session = request.getSession();
            session.setAttribute("user", new User(username, password, "PASSENGER"));
            response.sendRedirect("passengerTripSearch.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=2");
        }
    }
}

