package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthoServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fly_away";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Validate user credentials
            User user = validateUser(username, password);

            if (user != null) {
                // User login successful, you can set session attributes here
                response.setContentType("text/plain");
                PrintWriter out = response.getWriter();
                out.write("success");
            } else {
                // Invalid credentials
                response.setContentType("text/plain");
                PrintWriter out = response.getWriter();
                out.write("error");
            }
        } else if (action.equals("register")) {
            // Get user registration data
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // Add more registration data as needed

            // Register user
            boolean isRegistered = registerUser(username, password);

            if (isRegistered) {
                response.setContentType("text/plain");
                PrintWriter out = response.getWriter();
                out.write("success");
            } else {
                response.setContentType("text/plain");
                PrintWriter out = response.getWriter();
                out.write("error");
            }
        }
    }

    private User validateUser(String username, String password) {
        // Validate user credentials against the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String dbUsername = resultSet.getString("username");
                // Add more user properties if needed

                User user = new User(userId, dbUsername, password);
                return user;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean registerUser(String username, String password) {
        // Register user in the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
