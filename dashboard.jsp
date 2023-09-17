<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
</head>
<body>
    <h1>Welcome to Your Dashboard</h1>

    <h2>Your Booked Flights:</h2>
    <table>
        <thead>
            <tr>
                <th>Flight ID</th>
                <th>Airline</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Ticket Price</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${bookedFlights}" var="flight">
                <tr>
                    <td>${flight.id}</td>
                    <td>${flight.airline}</td>
                    <td>${flight.source}</td>
                    <td>${flight.destination}</td>
                    <td>${flight.ticketPrice}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Add other dashboard functionalities and profile management here -->

</body>
</html>
