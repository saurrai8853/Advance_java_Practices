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

public class FlightSearchServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fly_away";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Flight> flights = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM flights";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int flightId = resultSet.getInt("id");
                String airline = resultSet.getString("airline");
                String flightSource = resultSet.getString("source");
                String flightDestination = resultSet.getString("destination");
                double ticketPrice = resultSet.getDouble("ticket_price");

                Flight flight = new Flight(flightId, airline, flightSource, flightDestination, ticketPrice);
                flights.add(flight);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("ClassNotFoundException: Unable to load database driver.");
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("SQLException: Unable to connect to the database or execute the query.");
            return;
        }

        // Serialize the list of flights to JSON format
        String jsonFlightList = convertFlightsToJson(flights);

        // Set the response content type to JSON
        response.setContentType("application/json");
        response.getWriter().write(jsonFlightList);
    }

    private String convertFlightsToJson(List<Flight> flights) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            jsonBuilder.append("{\"id\":").append(flight.getId()).append(",");
            jsonBuilder.append("\"airline\":\"").append(flight.getAirline()).append("\",");
            jsonBuilder.append("\"source\":\"").append(flight.getSource()).append("\",");
            jsonBuilder.append("\"destination\":\"").append(flight.getDestination()).append("\",");
            jsonBuilder.append("\"ticketPrice\":").append(flight.getTicketPrice()).append("}");
            if (i < flights.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
	 
