# Migración a SQL Server y Nuevo Modelo de Datos

## Resumen de Cambios

Se ha migrado la aplicación de MySQL a SQL Server de Microsoft y se han actualizado los modelos de datos para implementar un sistema de reservas más robusto y bien estructurado.

## Cambios en la Base de Datos

### 1. Migración de MySQL a SQL Server

**Archivo modificado**: `src/main/resources/application.properties`
```properties
# Antes (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/web_via
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Después (SQL Server)
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=web_via;encrypt=false;trustServerCertificate=true
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
```

**Archivo modificado**: `pom.xml`
```xml
<!-- Reemplazado MySQL driver -->
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <scope>runtime</scope>
</dependency>
```

## Nuevos Modelos de Datos

### 2. Entidades Relacionales

Se han creado nuevas entidades que siguen el diagrama de base de datos relacionales:

#### 2.1 Cliente
```java
@Entity
@Table(name = "clientes")
public class Cliente {
    private Long id;
    private String nombre;
    private String apellido;
    private String email; // Único
    private String telefono;
    private List<Reserva> reservas; // Relación uno a muchos
}
```

#### 2.2 Vehiculo
```java
@Entity
@Table(name = "vehiculos")
public class Vehiculo {
    private Long id;
    private String tipo;
    private String marca;
    private String modelo;
    private Integer capacidad;
    private List<Pasaje> pasajes; // Relación uno a muchos
}
```

#### 2.3 Ruta
```java
@Entity
@Table(name = "rutas")
public class Ruta {
    private Long id;
    private String origen;
    private String destino;
    private Integer distanciaKm;
    private List<Pasaje> pasajes; // Relación uno a muchos
}
```

#### 2.4 Pasaje
```java
@Entity
@Table(name = "pasajes")
public class Pasaje {
    private Long id;
    private Cliente cliente; // FK
    private Vehiculo vehiculo; // FK
    private Ruta ruta; // FK
    private LocalDateTime fechaReserva;
    private BigDecimal precio;
    private List<Reserva> reservas; // Relación uno a muchos
}
```

#### 2.5 Reserva
```java
@Entity
@Table(name = "reservas")
public class Reserva {
    private Long id;
    private Cliente cliente; // FK
    private Vehiculo vehiculo; // FK
    private Ruta ruta; // FK
    private Pasaje pasaje; // FK
    private LocalDateTime fechaReserva;
    private EstadoReserva estado; // ENUM: PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA
}
```

### 3. Modelos Actualizados

#### 3.1 Destino (anteriormente Destination)
- Renombrado para seguir convención en español
- Migrado a usar `BigDecimal` para precios
- Agregadas anotaciones de tabla y columna

#### 3.2 Ticket
- Migrado a usar `BigDecimal` para precios
- Agregadas anotaciones de tabla y columna
- Agregada relación opcional con `Reserva`
- Mantenida compatibilidad con métodos `double`

## Servicios y Repositorios

### 4. Nuevos Repositorios
- `ClienteRepository`: CRUD completo con búsquedas por email y término
- `VehiculoRepository`: Búsquedas por tipo, marca, capacidad
- `RutaRepository`: Búsquedas por origen, destino, distancia
- `PasajeRepository`: Consultas complejas con joins
- `ReservaRepository`: Gestión de estados y fechas
- `DestinoRepository`: Búsquedas mejoradas

### 5. Nuevos Servicios
- `ClienteService` / `ClienteServiceImpl`
- `ReservaService` / `ReservaServiceImpl`
- `DestinoService` / `DestinoServiceImpl`

### 6. Servicios Actualizados
- `TicketServiceImpl`: Cálculo inteligente de precios basado en rutas
- `DestinationService`: Adaptado para usar `Destino`

## Controladores REST

### 7. Nuevos Controladores
- `ClienteController`: API REST completa para gestión de clientes
- `ReservaController`: API REST para gestión de reservas con cambios de estado

### 8. Controladores Actualizados
- `DestinationController`: Adaptado para usar `Destino`

## Mejoras en la Lógica de Negocio

### 9. Cálculo Dinámico de Precios
El sistema ahora calcula precios automáticamente basándose en:
1. Rutas registradas en la base de datos (distancia × tarifa por km)
2. Fallback a precios fijos originales si no encuentra la ruta

### 10. Estados de Reserva
Sistema de estados para reservas:
- **PENDIENTE**: Reserva creada, esperando confirmación
- **CONFIRMADA**: Reserva confirmada por el cliente
- **CANCELADA**: Reserva cancelada
- **COMPLETADA**: Viaje realizado

### 11. Validaciones
- Email único para clientes
- Validación de existencia de entidades antes de operaciones
- Manejo de errores mejorado

## Inicialización de Datos

### 12. DataInitializer Mejorado
Carga datos de prueba para todas las entidades:
- Clientes con información completa
- Vehículos de diferentes tipos y capacidades
- Rutas con distancias reales
- Destinos con precios actualizados

## Configuración SQL Server

### 13. Configuración Requerida
Para conectar a SQL Server:

1. **Instalar SQL Server** (Express/Developer/Standard)
2. **Configurar instancia** en puerto 1433
3. **Crear base de datos**: `web_via`
4. **Configurar variables de entorno**:
   ```bash
   SPRING_DATASOURCE_USERNAME=tu_usuario
   SPRING_DATASOURCE_PASSWORD=tu_password
   ```

### 14. Configuración de Conexión
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=web_via;encrypt=false;trustServerCertificate=true
```

## API Endpoints

### 15. Nuevos Endpoints

#### Clientes
- `GET /api/clientes` - Listar todos los clientes
- `POST /api/clientes` - Crear cliente
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente
- `GET /api/clientes/buscar?termino=` - Buscar clientes

#### Reservas
- `GET /api/reservas` - Listar todas las reservas
- `POST /api/reservas` - Crear reserva
- `GET /api/reservas/{id}` - Obtener reserva por ID
- `PUT /api/reservas/{id}/confirmar` - Confirmar reserva
- `PUT /api/reservas/{id}/cancelar` - Cancelar reserva
- `PUT /api/reservas/{id}/completar` - Completar reserva
- `GET /api/reservas/cliente/{clienteId}` - Reservas por cliente
- `GET /api/reservas/estado/{estado}` - Reservas por estado

## Compatibilidad

### 16. Mantiene Compatibilidad
- Las funcionalidades existentes siguen funcionando
- Los modelos `Ticket` y `Destination` (ahora `Destino`) mantienen sus métodos originales
- Los controladores web existentes funcionan sin cambios
- Se agregaron métodos de compatibilidad para conversión `double` ↔ `BigDecimal`

## Beneficios del Nuevo Sistema

### 17. Ventajas
1. **Mejor normalización**: Datos sin redundancia
2. **Integridad referencial**: Foreign keys garantizan consistencia
3. **Escalabilidad**: Estructura preparada para crecimiento
4. **Trazabilidad**: Historial completo de reservas y estados
5. **Flexibilidad**: Fácil agregar nuevas funcionalidades
6. **Performance**: SQL Server optimizado para consultas complejas
7. **Tipado fuerte**: `BigDecimal` para precisión monetaria

## Próximos Pasos

### 18. Recomendaciones
1. Migrar datos existentes de MySQL a SQL Server
2. Actualizar el frontend para usar las nuevas APIs
3. Implementar autenticación y autorización
4. Agregar validaciones de negocio adicionales
5. Implementar reporting y analytics
6. Configurar backup automático en SQL Server