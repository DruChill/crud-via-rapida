document.querySelectorAll('.seat').forEach(seat => {
    seat.addEventListener('click', function() {
        document.querySelectorAll('.seat').forEach(s => s.classList.remove('selected'));
        this.classList.add('selected');
        document.getElementById('seatNumber').value = this.getAttribute('data-seat');
    });
});

document.getElementById('fromLocation').addEventListener('change', updateSeats);
document.getElementById('toLocation').addEventListener('change', updateSeats);
document.getElementById('travelDate').addEventListener('change', updateSeats);
document.querySelectorAll('.time-button').forEach(button => {
    button.addEventListener('click', function() {
        document.querySelectorAll('.time-button').forEach(b => b.classList.remove('selected'));
        this.classList.add('selected');
        document.getElementById('travelTimeInput').value = this.getAttribute('data-time');
        updateSeats();
    });
});

function updateSeats() {
    const fromLocation = document.getElementById('fromLocation').value;
    const toLocation = document.getElementById('toLocation').value;
    const travelDate = document.getElementById('travelDate').value;
    const travelTime = document.getElementById('travelTimeInput').value;

    if (fromLocation && toLocation && travelDate && travelTime) {
        fetch(`/?fromLocation=${fromLocation}&toLocation=${toLocation}&travelDate=${travelDate}&travelTime=${travelTime}`)
            .then(response => response.text())
            .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                const newSeatSelection = doc.getElementById('seatSelection');
                document.getElementById('seatSelection').innerHTML = newSeatSelection.innerHTML;
                document.querySelectorAll('.seat').forEach(seat => {
                    seat.addEventListener('click', function() {
                        document.querySelectorAll('.seat').forEach(s => s.classList.remove('selected'));
                        this.classList.add('selected');
                        document.getElementById('seatNumber').value = this.getAttribute('data-seat');
                    });
                });
            });
    }
}