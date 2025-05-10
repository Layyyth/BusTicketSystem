package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import model.User;
import config.FareConfig;

import java.io.IOException;

@WebServlet("/fare-config")
public class FareConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FareConfig config = FareConfig.getInstance();
        request.setAttribute("fareConfig", config);
        request.getRequestDispatcher("adminTripManager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double baseFare = Double.parseDouble(request.getParameter("baseFare"));
        double studentDiscount = Double.parseDouble(request.getParameter("studentDiscount"));
        double seniorDiscount = Double.parseDouble(request.getParameter("seniorDiscount"));
        double eveningDiscount = Double.parseDouble(request.getParameter("eveningDiscount"));

        FareConfig config = FareConfig.getInstance();
        config.setBaseFare(baseFare);
        config.setStudentDiscount(studentDiscount);
        config.setSeniorDiscount(seniorDiscount);
        config.setEveningDiscount(eveningDiscount);

        response.sendRedirect("fare-config");
    }
}
