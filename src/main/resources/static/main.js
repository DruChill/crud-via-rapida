// Manejar la selección de asientos
document.addEventListener('DOMContentLoaded', function() {
    setupSeatSelection();
});

function setupSeatSelection() {
    document.querySelectorAll('.seat').forEach(seat => {
        if (!seat.classList.contains('purchased')) {
            seat.addEventListener('click', function() {
                document.querySelectorAll('.seat').forEach(s => s.classList.remove('selected'));
                this.classList.add('selected');
                document.getElementById('seatNumber').value = this.getAttribute('data-seat');
                console.log('Asiento seleccionado:', this.getAttribute('data-seat'));
            });
        }
    });
}