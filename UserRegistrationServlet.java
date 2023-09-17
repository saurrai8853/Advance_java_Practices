package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRegistrationServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fly_away";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // Other user registration data
        
        // Validate user data here (e.g., check for empty fields, strong password, etc.)

        // Insert new user data into the database
        boolean isRegistrationSuccessful = registerNewUser(username, password);
        
        // Prepare the response
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": " + isRegistrationSuccessful + "}");
    }

    private boolean registerNewUser(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "INSERT INTO users (username, password) VALUES (?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
