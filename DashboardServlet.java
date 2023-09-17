package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DashboardServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fly_away";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        // Get user's booked flights
        List<Flight> bookedFlights = getUserBookedFlights(userId);

        // Pass the booked flights to the JSP page
        request.setAttribute("bookedFlights", bookedFlights);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    private List<Flight> getUserBookedFlights(int userId) {
        List<Flight> bookedFlights = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT f.* FROM flights f "
                         + "INNER JOIN bookings b ON f.id = b.flight_id "
                         + "WHERE b.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int flightId = resultSet.getInt("id");
                String airline = resultSet.getString("airline");
                String source = resultSet.getString("source");
                String destination = resultSet.getString("destination");
                double ticketPrice = resultSet.getDouble("ticket_price");

                Flight flight = new Flight(flightId, airline, source, destination, ticketPrice);
                bookedFlights.add(flight);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return bookedFlights;
    }
}
