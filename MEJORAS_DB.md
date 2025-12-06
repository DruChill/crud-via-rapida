# Mejoras en la Base de Datos - Sistema Vía Rápida

## 🎯 Cambios Implementados

### 1. Nueva Entidad: **User**
- **Tabla:** `users`
- **Propósito:** Centralizar información de pasajeros y evitar duplicación
- **Campos:**
  - `id` (PK)
  - `dni` (UNIQUE) - Cédula del pasajero
  - `name` - Nombre completo
  - `email` - Correo electrónico
  - `phone` - Teléfono de contacto
  - `tickets` - Relación OneToMany con Ticket

**Ventajas:**
- ✅ No se duplica información del pasajero
- ✅ Un usuario puede tener múltiples tickets
- ✅ Historial completo de viajes por pasajero

---

### 2. Relaciones en **Ticket**
Se reemplazaron los campos String por relaciones reales:

**ANTES:**
```java
private String passengerName;
private String dni;
private String fromLocation;
private String toLocation;
```

**AHORA:**
```java
@ManyToOne
private User user;

@ManyToOne
private Destination fromDestination;

@ManyToOne
private Destination toDestination;
```

**Ventajas:**
- ✅ Integridad referencial garantizada
- ✅ Consultas más eficientes
- ✅ Cascada automática de actualizaciones
- ✅ Previene inconsistencias (ej: nombres de ciudades mal escritos)

---

### 3. Consultas Mejoradas en TicketRepository

Nuevos métodos disponibles:
```java
// Por usuario
findByUser(User user)
findByUserId(Long userId)

// Por destino
findByFromDestination(Destination from)
findByToDestination(Destination to)
findByFromDestinationAndToDestination(from, to)

// Por fecha
findByTravelDate(LocalDate date)
findByTravelDateBetween(start, end)

// Combinadas
findByUserAndTravelDate(user, date)
findByFromDestinationAndToDestinationAndTravelDate(from, to, date)
```

---

### 4. Nuevo DTO: **TicketRequest**
Simplifica la creación de tickets desde el frontend:

```java
{
  "dni": "12345678",
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "phone": "987654321",
  "fromDestinationId": 1,
  "toDestinationId": 2,
  "travelDate": "2025-12-10",
  "travelTime": "08:00",
  "seatNumber": "A1",
  "price": 15.0
}
```

El servicio automáticamente:
1. Busca si el usuario existe por DNI
2. Si no existe, lo crea
3. Asocia los destinos por ID
4. Crea el ticket

---

### 5. Nuevo Controller: **UserController**
Endpoints disponibles:
- `GET /api/users` - Listar todos
- `GET /api/users/{id}` - Por ID
- `GET /api/users/dni/{dni}` - Por DNI
- `POST /api/users` - Crear usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

---

## 📊 Estructura de la Base de Datos

```
┌──────────────┐
│     USER     │
├──────────────┤
│ id (PK)      │
│ dni (UNIQUE) │
│ name         │
│ email        │
│ phone        │
└──────┬───────┘
       │ 1
       │
       │ N
┌──────▼───────┐        N        ┌──────────────┐
│    TICKET    │◄────────────────►│ DESTINATION  │
├──────────────┤                  ├──────────────┤
│ id (PK)      │                  │ id (PK)      │
│ user_id (FK) │                  │ name         │
│ from_dest_id │                  │ description  │
│ to_dest_id   │                  │ price        │
│ travel_date  │                  └──────────────┘
│ travel_time  │
│ seat_number  │
│ price        │
└──────────────┘
```

---

## 🚀 Próximos Pasos (Recomendaciones)

1. **Agregar tabla Route** (Ruta)
   - Para definir rutas predefinidas con precios fijos
   - Ej: Huamanga→Huanta = S/15.00

2. **Agregar tabla Schedule** (Horarios)
   - Horarios disponibles por ruta
   - Capacidad de asientos por horario

3. **Agregar tabla Bus**
   - Información de buses (placa, capacidad, etc.)
   - Asignación de bus a ticket

4. **Implementar enum para SeatStatus**
   - AVAILABLE, RESERVED, OCCUPIED

---

## ⚠️ Notas Importantes

1. **Migración de datos existentes:**
   - Los tickets antiguos no tendrán relación con User
   - Considera crear un script de migración si tienes datos en producción

2. **Validaciones necesarias:**
   - DNI debe tener exactamente 8 dígitos
   - Email debe ser válido
   - No permitir mismo asiento para misma fecha/hora/ruta

3. **Frontend:**
   - Actualizar formularios para usar IDs de destinos
   - Implementar autocompletado de usuario por DNI
   - Mostrar historial de tickets por usuario

---

## 📝 Archivos Creados/Modificados

### Nuevos:
- `model/User.java`
- `repository/UserRepository.java`
- `service/UserService.java`
- `serviceImpl/UserServiceImpl.java`
- `controller/UserController.java`
- `dto/TicketRequest.java`

### Modificados:
- `model/Ticket.java` - Agregadas relaciones
- `repository/TicketRepository.java` - Nuevas consultas
- `serviceImpl/TicketServiceImpl.java` - Lógica actualizada
- `config/DataInitializer.java` - Datos de ejemplo
