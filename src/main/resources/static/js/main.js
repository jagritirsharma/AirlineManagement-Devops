// Hide all content sections initially
document.addEventListener('DOMContentLoaded', () => {
    hideAllSections();
});

function hideAllSections() {
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });
}

function showFlights() {
    hideAllSections();
    const flightsList = document.getElementById('flightsList');
    flightsList.style.display = 'block';
    
    // Fetch flights from the backend
    fetch('/flights')
        .then(response => response.json())
        .then(flights => {
            const table = createFlightsTable(flights);
            flightsList.innerHTML = '<h2>Available Flights</h2>' + table;
        })
        .catch(error => {
            flightsList.innerHTML = '<p>Error loading flights. Please try again later.</p>';
            console.error('Error:', error);
        });
}

function showTickets() {
    hideAllSections();
    const ticketsList = document.getElementById('ticketsList');
    ticketsList.style.display = 'block';
    
    // Fetch tickets from the backend
    fetch('/tickets')
        .then(response => response.json())
        .then(tickets => {
            const table = createTicketsTable(tickets);
            ticketsList.innerHTML = '<h2>Tickets</h2>' + table;
        })
        .catch(error => {
            ticketsList.innerHTML = '<p>Error loading tickets. Please try again later.</p>';
            console.error('Error:', error);
        });
}

function createFlightsTable(flights) {
    if (!flights || flights.length === 0) {
        return '<p>No flights available.</p>';
    }

    let table = `
        <table>
            <thead>
                <tr>
                    <th>Flight Number</th>
                    <th>Airline</th>
                    <th>Source</th>
                    <th>Destination</th>
                    <th>Capacity</th>
                </tr>
            </thead>
            <tbody>
    `;

    flights.forEach(flight => {
        table += `
            <tr>
                <td>${flight.flightNumber}</td>
                <td>${flight.airline}</td>
                <td>${flight.source}</td>
                <td>${flight.destination}</td>
                <td>${flight.capacity}</td>
            </tr>
        `;
    });

    table += '</tbody></table>';
    return table;
}

function createTicketsTable(tickets) {
    if (!tickets || tickets.length === 0) {
        return '<p>No tickets available.</p>';
    }

    let table = `
        <table>
            <thead>
                <tr>
                    <th>Ticket ID</th>
                    <th>Flight Number</th>
                    <th>Passenger Name</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
    `;

    tickets.forEach(ticket => {
        table += `
            <tr>
                <td>${ticket.id}</td>
                <td>${ticket.flightNumber}</td>
                <td>${ticket.passengerName}</td>
                <td>${ticket.status}</td>
            </tr>
        `;
    });

    table += '</tbody></table>';
    return table;
} 