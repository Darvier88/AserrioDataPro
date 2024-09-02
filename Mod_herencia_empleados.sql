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
SELECT s.ID, s.nombre, s.horaInicio, s.horaFine, s.fechaCapacitacion, s.tipoCapacitacion
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
    nombre = o.nombre,
    horaInicio = o.horaInicio,
    horaFin = o.horaFin,
    fechaCapacitacion = o.fechaCapacitacion,
    tipoCapacitacion = o.tipoCapacitacion;

-- Migrar registros desde Asistente_operario a Empleado, incluso si no existen en Empleado
INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
SELECT ao.ID, ao.nombre, ao.horaInicio, ao.horaFin, ao.fechaCapacitacion, ao.tipoCapacitacion
FROM Asistente_operario ao
ON DUPLICATE KEY UPDATE
    nombre = ao.nombre,
    horaInicio = ao.horaInicio,
    horaFin = ao.horaFin,
    fechaCapacitacion = ao.fechaCapacitacion,
    tipoCapacitacion = ao.tipoCapacitacion;



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
DROP COLUMN horaFine,
DROP COLUMN fechaCapacitacion,
DROP COLUMN tipoCapacitacion;

-- 2. Agregar clave foránea para referenciar a la tabla Empleado

-- Tabla Operario
ALTER TABLE Operario
ADD CONSTRAINT ID_empleado_operario
FOREIGN KEY (ID) REFERENCES Empleado(ID);

-- Tabla Asistente_operario
ALTER TABLE Asistente_operario
ADD CONSTRAINT ID_empleado_asistente
FOREIGN KEY (ID) REFERENCES Empleado(ID);

-- Tabla Secretaria
ALTER TABLE Secretaria
ADD CONSTRAINT ID_empleado_secretaria
FOREIGN KEY (ID) REFERENCES Empleado(ID);

-- Subqueries para obtener las tablas completas
-- secretaria
CREATE VIEW TablaSecretaria as
SELECT * 
FROM empleado JOIN secretaria using (ID);

-- secretaria
CREATE VIEW TablaOperario as
SELECT * 
FROM empleado JOIN operario using (ID);

-- secretaria
CREATE VIEW TablaAsistente as
SELECT * 
FROM empleado JOIN asistente_operario using (ID);
DROP TABLE asistente_operario_backup;
DROP TABLE operario_backup;
DROP TABLE empleado_backup;
DROP TABLE secretaria_backup;
