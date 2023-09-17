package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlightBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the JSON data from the request
        String jsonData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        // Parse the JSON data into a Map
        Map<String, String> jsonMap = parseJsonData(jsonData);

        // Extract data from the JSON map
        String flightId = jsonMap.get("flightId");
        String passengerName = jsonMap.get("passengerName");
        String passengerAge = jsonMap.get("passengerAge");

        // Perform flight booking and store booking details in the database
        boolean isBookingSuccessful = performFlightBooking(flightId, passengerName, passengerAge);

        // Prepare the response
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Send the response JSON based on the booking status
        if (isBookingSuccessful) {
            out.println("{\"status\": \"success\", \"message\": \"Booking successful.\"}");
        } else {
            out.println("{\"status\": \"error\", \"message\": \"Booking failed.\"}");
        }
    }

    private Map<String, String> parseJsonData(String jsonData) {
        // Implement your JSON parsing logic here
        // For simplicity, let's assume we return a dummy map
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("flightId", "FL123");
        jsonMap.put("passengerName", "John Doe");
        jsonMap.put("passengerAge", "30");
        return jsonMap;
    }

    private boolean performFlightBooking(String flightId, String passengerName, String passengerAge) {
        // Implement your flight booking logic here
        // For simplicity, let's assume the booking is always successful
        return true;
    }
}
