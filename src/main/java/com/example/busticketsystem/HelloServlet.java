package com.example.busticketsystem;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().write("<h1>Hello from Servlet!</h1>");
    }
}