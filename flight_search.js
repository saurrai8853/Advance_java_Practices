// flight_search.js
document.getElementById("flightSearchForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const date = document.getElementById("date").value;
    const source = document.getElementById("source").value;
    const destination = document.getElementById("destination").value;
    const passengers = document.getElementById("passengers").value;

    // Send the user's input to the backend using AJAX
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `FlightSearchServlet?date=${date}&source=${source}&destination=${destination}&passengers=${passengers}`);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const flightResults = JSON.parse(xhr.responseText);
                displayFlightResults(flightResults);
            } else {
                console.error("Failed to fetch flight results.");
            }
        }
    };
    xhr.send();
});

function displayFlightResults(flightResults) {
    const flightResultsDiv = document.getElementById("flightResults");
    flightResultsDiv.innerHTML = "";

    if (flightResults.length === 0) {
        flightResultsDiv.textContent = "No flights found for the given criteria.";
        return;
    }

    const table = document.createElement("table");
    table.innerHTML = `
        <tr>
            <th>Flight ID</th>
            <th>Airline</th>
            <th>Source</th>
            <th>Destination</th>
            <th>Ticket Price</th>
        </tr>
    `;

    flightResults.forEach(flight => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${flight.id}</td>
            <td>${flight.airline}</td>
            <td>${flight.source}</td>
            <td>${flight.destination}</td>
            <td>${flight.ticketPrice}</td>
        `;
        table.appendChild(row);
    });

    flightResultsDiv.appendChild(table);
}
// flight_search.js

// Add an event listener to handle flight selection
document.addEventListener("DOMContentLoaded", function () {
  const flightResults = document.getElementById("flight-results");
  flightResults.addEventListener("click", handleFlightSelection);
});

// Function to handle flight selection
function handleFlightSelection(event) {
  const selectedFlight = event.target.closest(".flight");
  if (selectedFlight) {
    const flightId = selectedFlight.dataset.flightId;
    const airline = selectedFlight.dataset.airline;
    const source = selectedFlight.dataset.source;
    const destination = selectedFlight.dataset.destination;
    const ticketPrice = selectedFlight.dataset.ticketPrice;

    // Save the selected flight details in a variable or display them in the booking form
    // For example:
    document.getElementById("selected-flight-details").innerText = `
      Flight ID: ${flightId}
      Airline: ${airline}
      Source: ${source}
      Destination: ${destination}
      Ticket Price: $${ticketPrice}
    `;

    // Show the booking form
    document.getElementById("booking-form").style.display = "block";
  }
}
// flight_search.js

document.addEventListener("DOMContentLoaded", function () {
  // ...

  // Add an event listener to handle form submission
  const bookingForm = document.getElementById("booking-form");
  bookingForm.addEventListener("submit", handleFormSubmission);
});

// Function to handle form submission
function handleFormSubmission(event) {
  event.preventDefault();

  // Gather user input from the form
  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;
  const passengers = parseInt(document.getElementById("passengers").value);

  // Gather selected flight details (you can get this information from the variables set during flight selection)
  const selectedFlightId = 123; // Replace with the actual selected flight ID
  const selectedAirline = "Sample Airline"; // Replace with the actual selected airline
  const selectedSource = "Sample Source"; // Replace with the actual selected source
  const selectedDestination = "Sample Destination"; // Replace with the actual selected destination
  const selectedTicketPrice = 200.0; // Replace with the actual selected ticket price

  // Create an object to store booking details
  const bookingDetails = {
    name,
    email,
    passengers,
    flightId: selectedFlightId,
    airline: selectedAirline,
    source: selectedSource,
    destination: selectedDestination,
    ticketPrice: selectedTicketPrice,
  };

  // Send an AJAX request to the server to process the booking
  // For simplicity, I'll use the fetch API, but you can use other libraries like Axios
  fetch("/flight-booking", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(bookingDetails),
  })
    .then((response) => response.json())
    .then((data) => {
      // Handle the server response (e.g., show confirmation message)
      console.log(data); // Assuming the server responds with JSON data containing booking details
      // Display confirmation message
      alert("Booking successful! Your booking details: " + JSON.stringify(data));
    })
    .catch((error) => {
      // Handle error
      console.error("Error during booking:", error);
    });
}
// ... (existing code)

// Function to handle flight booking
function bookFlight() {
    var selectedFlightId = getSelectedFlightId();

    // Check if a flight is selected
    if (selectedFlightId === null) {
        alert("Please select a flight to book.");
        return;
    }

    // Make an AJAX request to book the flight
    var xhr = new XMLHttpRequest();
    var url = "/bookFlight?id=" + selectedFlightId;

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Flight booking successful
                alert("Flight booked successfully!");
                // Optionally, redirect to a booking confirmation page
                // window.location.href = "booking_confirmation.html";
            } else {
                // Flight booking failed
                alert("Flight booking failed. Please try again.");
            }
        }
    };

    xhr.open("POST", url, true);
    xhr.send();
}

// ... (existing code)

