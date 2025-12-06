# API Endpoints - Via Rápida

## Base URL
```
http://localhost:8080/api
```

## Destinations

### GET /api/destinations
Obtener todos los destinos
```json
Response: [
  {
    "id": 1,
    "name": "Lima",
    "description": "Capital del Perú",
    "price": 20.0
  }
]
```

### GET /api/destinations/{id}
Obtener destino por ID

### GET /api/destinations/available-seats
Obtener asientos disponibles
```
Params:
- fromLocation: string
- toLocation: string
- travelDate: YYYY-MM-DD
- travelTime: HH:mm
```

### GET /api/destinations/purchased-seats
Obtener asientos comprados (mismos parámetros que available-seats)

### POST /api/destinations
Crear nuevo destino
```json
{
  "name": "Arequipa",
  "description": "Ciudad blanca",
  "price": 50.0
}
```

### PUT /api/destinations/{id}
Actualizar destino

### DELETE /api/destinations/{id}
Eliminar destino

---

## Tickets

### GET /api/tickets
Obtener todos los tickets

### GET /api/tickets/{id}
Obtener ticket por ID

### GET /api/tickets/search?dni={dni}
Buscar tickets por DNI

### POST /api/tickets
Crear nuevo ticket
```json
{
  "dni": "12345678",
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "987654321",
  "fromDestinationId": 1,
  "toDestinationId": 2,
  "travelDate": "2025-12-15",
  "travelTime": "08:00",
  "seatNumber": "1-A"
}
```

### PUT /api/tickets/{id}
Actualizar ticket (mismo formato que POST)

### DELETE /api/tickets/{id}
Eliminar ticket

---

## Users

### GET /api/users
Obtener todos los usuarios

### GET /api/users/{id}
Obtener usuario por ID

### GET /api/users/dni/{dni}
Obtener usuario por DNI

### POST /api/users
Crear nuevo usuario
```json
{
  "dni": "12345678",
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "987654321"
}
```

### PUT /api/users/{id}
Actualizar usuario

### DELETE /api/users/{id}
Eliminar usuario

---

## Admin

### POST /api/admin/login
Login de administrador
```json
Request:
{
  "username": "admin",
  "password": "password"
}

Response:
{
  "success": true,
  "message": "Login successful",
  "token": "admin-token-xxxxx"
}
```

### GET /api/admin/tickets
Obtener todos los tickets (requiere autenticación)

### GET /api/admin/destinations
Obtener todos los destinos (requiere autenticación)

### GET /api/admin/users
Obtener todos los usuarios (requiere autenticación)

---

## Ejemplo de uso con React

```javascript
// Obtener destinos
const getDestinations = async () => {
  const response = await fetch('http://localhost:8080/api/destinations');
  const data = await response.json();
  return data;
};

// Crear ticket
const createTicket = async (ticketData) => {
  const response = await fetch('http://localhost:8080/api/tickets', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(ticketData),
  });
  const data = await response.json();
  return data;
};

// Login admin
const loginAdmin = async (username, password) => {
  const response = await fetch('http://localhost:8080/api/admin/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ username, password }),
  });
  const data = await response.json();
  return data;
};
```
