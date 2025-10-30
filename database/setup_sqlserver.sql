-- Script para configurar SQL Server para la aplicación Via Rápida
-- Ejecutar este script en SQL Server Management Studio o Azure Data Studio

-- 1. Crear la base de datos (si no existe)
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'web_via')
BEGIN
    CREATE DATABASE web_via;
END
GO

USE web_via;
GO

-- 2. Crear usuario para la aplicación (opcional - para producción)
-- IF NOT EXISTS (SELECT name FROM sys.sql_logins WHERE name = 'viarapida_user')
-- BEGIN
--     CREATE LOGIN viarapida_user WITH PASSWORD = 'Password123!';
-- END
-- GO

-- IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'viarapida_user')
-- BEGIN
--     CREATE USER viarapida_user FOR LOGIN viarapida_user;
--     ALTER ROLE db_datareader ADD MEMBER viarapida_user;
--     ALTER ROLE db_datawriter ADD MEMBER viarapida_user;
--     ALTER ROLE db_ddladmin ADD MEMBER viarapida_user;
-- END
-- GO

-- 3. Configuraciones recomendadas para la base de datos
ALTER DATABASE web_via SET RECOVERY SIMPLE;
ALTER DATABASE web_via SET AUTO_CREATE_STATISTICS ON;
ALTER DATABASE web_via SET AUTO_UPDATE_STATISTICS ON;
GO

-- 4. Verificar que las tablas se crearán automáticamente
-- Las tablas se crearán automáticamente por Hibernate con spring.jpa.hibernate.ddl-auto=update

-- 5. Datos de ejemplo adicionales (se ejecutarán después de que Hibernate cree las tablas)
-- Estos datos complementan los del DataInitializer de Spring Boot

PRINT 'Base de datos web_via configurada correctamente para SQL Server';
PRINT 'Asegúrate de configurar las variables de entorno:';
PRINT 'SPRING_DATASOURCE_USERNAME=sa (o tu usuario de SQL Server)';
PRINT 'SPRING_DATASOURCE_PASSWORD=tu_password_seguro';
PRINT '';
PRINT 'Para desarrollo local, puedes usar el usuario "sa" con la contraseña que configuraste al instalar SQL Server.';
PRINT 'Para producción, se recomienda crear un usuario específico con permisos limitados.';

-- 6. Índices recomendados (se crearán después de que la aplicación genere las tablas)
/*
-- Ejecutar estos comandos después de iniciar la aplicación por primera vez:

USE web_via;
GO

-- Índices para mejorar performance
CREATE INDEX IX_clientes_email ON clientes(email);
CREATE INDEX IX_reservas_cliente_id ON reservas(cliente_id);
CREATE INDEX IX_reservas_estado ON reservas(estado);
CREATE INDEX IX_reservas_fecha ON reservas(fecha_reserva);
CREATE INDEX IX_pasajes_cliente_id ON pasajes(cliente_id);
CREATE INDEX IX_pasajes_ruta_id ON pasajes(ruta_id);
CREATE INDEX IX_rutas_origen_destino ON rutas(origen, destino);
CREATE INDEX IX_tickets_dni ON tickets(dni);
CREATE INDEX IX_tickets_travel_date ON tickets(travel_date);
*/