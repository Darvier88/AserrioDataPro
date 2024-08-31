use aserriodatapro;
-- Herencia segun Table per Subclasses
-- Migracion de data

-- Respalda todos los datos de Empleado en una tabla temporal
CREATE TABLE Empleado_Backup AS
SELECT * FROM Empleado;

-- Respalda los datos de Operario
CREATE TABLE Operario_Backup AS
SELECT * FROM Operario;

-- Respalda los datos de Asistente_operario
CREATE TABLE Asistente_operario_Backup AS
SELECT * FROM Asistente_operario;

-- Respalda los datos de Secretaria
CREATE TABLE Secretaria_Backup AS
SELECT * FROM Secretaria;

-- Migrar registros desde Secretaria a Empleado, incluso si no existen en Empleado
INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
SELECT s.ID, s.nombre, s.horaInicio, s.horaFin, s.fechaCapacitacion, s.tipoCapacitacion
FROM Secretaria s
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    horaInicio = VALUES(horaInicio),
    horaFin = VALUES(horaFin),
    fechaCapacitacion = VALUES(fechaCapacitacion),
    tipoCapacitacion = VALUES(tipoCapacitacion);

-- Migrar registros desde Operario a Empleado, incluso si no existen en Empleado
INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
SELECT o.ID, o.nombre, o.horaInicio, o.horaFin, o.fechaCapacitacion, o.tipoCapacitacion
FROM Operario o
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    horaInicio = VALUES(horaInicio),
    horaFin = VALUES(horaFin),
    fechaCapacitacion = VALUES(fechaCapacitacion),
    tipoCapacitacion = VALUES(tipoCapacitacion);

-- Migrar registros desde Asistente_operario a Empleado, incluso si no existen en Empleado
INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
SELECT ao.ID, ao.nombre, ao.horaInicio, ao.horaFin, ao.fechaCapacitacion, ao.tipoCapacitacion
FROM Asistente_operario ao
ON DUPLICATE KEY UPDATE
    nombre = VALUES(nombre),
    horaInicio = VALUES(horaInicio),
    horaFin = VALUES(horaFin),
    fechaCapacitacion = VALUES(fechaCapacitacion),
    tipoCapacitacion = VALUES(tipoCapacitacion);


-- 1. Eliminar las columnas comunes en las tablas hijas
-- Tabla Operario
ALTER TABLE Operario
DROP COLUMN nombre,
DROP COLUMN horaInicio,
DROP COLUMN horaFin,
DROP COLUMN fechaCapacitacion,
DROP COLUMN tipoCapacitacion;

-- Tabla Asistente_operario
ALTER TABLE Asistente_operario
DROP COLUMN nombre,
DROP COLUMN horaInicio,
DROP COLUMN horaFin,
DROP COLUMN fechaCapacitacion,
DROP COLUMN tipoCapacitacion;

-- Tabla Secretaria
ALTER TABLE Secretaria
DROP COLUMN nombre,
DROP COLUMN horaInicio,
DROP COLUMN horaFin,
DROP COLUMN fechaCapacitacion,
DROP COLUMN tipoCapacitacion;

-- 2. Agregar clave foránea para referenciar a la tabla Empleado

-- Tabla Operario
ALTER TABLE Operario
ADD CONSTRAINT fk_operario_empleado
FOREIGN KEY (ID) REFERENCES Empleado(ID);

-- Tabla Asistente_operario
ALTER TABLE Asistente_operario
ADD CONSTRAINT fk_asistente_operario_empleado
FOREIGN KEY (ID) REFERENCES Empleado(ID);

-- Tabla Secretaria
ALTER TABLE Secretaria
ADD CONSTRAINT fk_secretaria_empleado
FOREIGN KEY (ID) REFERENCES Empleado(ID);

