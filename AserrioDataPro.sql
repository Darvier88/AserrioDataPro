CREATE database AserrioDataPro;
USE AserrioDataPro;

/*Tablas de la seccion de ventas*/
CREATE TABLE Cliente(
	cedula varchar(13) PRIMARY KEY NOT NUlL ,
    nombre varchar(30) NOT NULL,
    direccion varchar(100) NOT NULL,
    num_contacto int ,
    correo_contacto varchar(100) NOT NULL,
    CONSTRAINT chk_cedula_length CHECK (LENGTH(cedula) = 10 OR LENGTH(cedula)=13)
);
CREATE TABLE Producto(
	ID varchar(6) PRIMARY KEY NOT NULL,
    nombre varchar(30) NOT NULL,
    precioUnitario float NOT NULL,
    calidad ENUM('De primera','De segunda'),
    condic_ambiental varchar(20) NOT NULL,
    tiempo_almacenamiento int NOT NULL,
    dimension varchar(20) NOT NULL,
    descripcion varchar(100)
);
CREATE TABLE Secretaria(
	ID char(10) PRIMARY KEY,
    nombre varchar(40) NOT NULL,
    horaInicio Time NOT NULL,
    horaFine Time NOT NULL,
    fechaCapacitacion DATE,
    tipoCapacitacion varchar(20)
);
CREATE TABLE Factura(
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ID_secretaria char(10) NOT NULL,
    ID_cliente varchar(13) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    direccion_local varchar(100) DEFAULT 'Sixto Duran Balle y Manuela Cañizares,Santa Rosa,El Oro' NOT NULL,
    metodo_pago ENUM('Efectivo','Transferencia') NOT NULL,
    subtotal_sin_impuestos float NOT NULL,
    subtotal_0Porcent float DEFAULT 0 NOT NULL,
    ValorTotal float NOT NULL,
    FOREIGN KEY (ID_cliente) REFERENCES Cliente(cedula),
    FOREIGN KEY (ID_secretaria) REFERENCES Secretaria(ID)
);
CREATE TABLE Detalle(
	ID_factura int NOT NULL,
    ID_producto varchar(6) NOT NULL,
    unidades int NOT NULL,
    totalProdu int NOT NULL,
    detalle_adic varchar(100),
    PRIMARY KEY(ID_factura,ID_producto),
    FOREIGN KEY (ID_factura) REFERENCES Factura(ID),
    FOREIGN KEY (ID_producto) REFERENCES Producto(ID),
    CONSTRAINT unidadesConst CHECK (unidades>0),
    CONSTRAINT totalProduConst CHECK (totalProdu>0)
);
CREATE TABLE Reclamacion(
	ID int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ID_secretaria char(10) NOT NULL,
    ID_cliente varchar(13) NOT NULL,
    descripcion varchar(100) NOT NULL,
    FOREIGN KEY (ID_secretaria) REFERENCES Secretaria(ID),
    FOREIGN KEY (ID_cliente) REFERENCES Cliente(cedula)
);
/*Inserciones*/
/*Consumidor final*/
INSERT INTO Cliente (cedula, nombre, direccion,correo_contacto) 
VALUES ('0000000000000', 'Consumidor Final', 'Sin direccion', 'sincorreo@ejemplo.com');
/*9 clientes no consumidor final*/
INSERT INTO Cliente (cedula, nombre, direccion, num_contacto, correo_contacto) VALUES
('0701234567', 'Juan Pérez', 'Avenida Principal 123, Machala, El Oro', 0987654321, 'juan.perez@gmail.com'),
('0702345678', 'María López', 'Calle Secundaria 456, Pasaje, El Oro', NULL, 'maria.lopez@hotmail.com'),
('0703456789', 'Carlos Sánchez', 'Barrio Central 789, Santa Rosa, El Oro', 0987654323, 'carlos.sanchez@outlook.com'),
('0704567890', 'Ana González', 'Sector Norte 321, Huaquillas, El Oro', NULL, 'ana.gonzalez@gmail.com'),
('0705678900', 'Luis Martínez', 'Zona Sur 654, Arenillas, El Oro', 0987654325, 'luis.martinez@hotmail.com'),
('0706789012', 'Carmen Rodríguez', 'Machala, El Oro', NULL, 'carmen.rodriguez@outlook.com'),
('0707890123', 'Jorge Gómez', 'Pasaje, El Oro', 0987654327, 'jorge.gomez@gmail.com'),
('0708901234', 'Lucía Fernández', 'Santa Rosa, El Oro', NULL, 'lucia.fernandez@hotmail.com'),
('0709012345001', 'Pedro Ramírez', 'Huaquillas, El Oro', 0987654329, 'pedro.ramirez@outlook.com');
/*Insercion de 10 productos distintos*/
INSERT INTO Producto (ID, nombre, precioUnitario, calidad, condic_ambiental, tiempo_almacenamiento, dimension, descripcion) VALUES
('TP01', 'Tabla de Pino', 12.50, 'De primera', 'Seco', 12, '2x4x8', 'Tabla de pino de alta calidad'),
('CC01', 'Cuartón de Cedro', 25.00, 'De segunda', 'Húmedo', 8, '4x4x10', 'Cuartón de cedro para construcción'),
('TR01', 'Tablón de Roble', 30.00, 'De primera', 'Seco', 15, '6x6x12', 'Tablón robusto de roble'),
('TN01', 'Tabloncillo de Nogal', 18.75, 'De primera', 'Seco', 10, '1x4x6', 'Tabloncillo fino de nogal'),
('TP02', 'Tablilla de Pino', 5.50, 'De segunda', 'Húmedo', 6, '1x2x4', 'Tablilla de pino para decoraciones'),
('CR01', 'Caña Redonda', 7.00, 'De segunda', 'Seco', 20, '2x8', 'Caña redonda para estructuras ligeras'),
('CP01', 'Caña Picada', 6.00, 'De primera', 'Húmedo', 15, '1.5x7', 'Caña picada para uso en construcción'),
('VR01', 'Viga de Roble', 50.00, NULL, 'Seco', 24, '8x8x20', 'Viga de roble de alta resistencia'),
('TP03', 'Tablón de Pino', 28.00, 'De segunda', 'Seco', 12, '6x6x8', 'Tablón de pino para vigas'),
('CN01', 'Cuartón de Nogal', 22.50, 'De primera', 'Húmedo', 10, '4x4x8', 'Cuartón de nogal para estructuras');
/*Insercion de 10 secretarias distintas*/
INSERT INTO Secretaria (ID, nombre, horaInicio, horaFine, fechaCapacitacion, tipoCapacitacion) VALUES
('0701234567', 'Ana Pérez', '08:00:00', '17:00:00', '2023-01-15', 'Secretaria'),
('0702345678', 'María López', '08:30:00', '17:30:00', NULL, NULL),
('0703456789', 'Luisa Gómez', '09:00:00', '18:00:00', '2023-03-25', 'Secretaria'),
('0704567890', 'Carmen Rodríguez', '08:15:00', '17:15:00', NULL, NULL),
('0705678901', 'Sofía Torres', '08:45:00', '17:45:00', '2023-05-10', 'Secretaria'),
('0706789012', 'Laura Fernández', '09:15:00', '18:15:00', NULL, NULL),
('0707890123', 'Clara García', '08:30:00', '17:30:00', '2023-07-15', 'Secretaria'),
('0708901234', 'Elena Martínez', '08:00:00', '17:00:00', NULL, NULL),
('0709012345', 'Isabel Ramírez', '09:00:00', '18:00:00', '2023-09-05', 'Secretaria'),
('0710123456', 'Marta Jiménez', '08:15:00', '17:15:00', NULL, NULL);
INSERT INTO Factura (ID_secretaria, ID_cliente, fecha, hora, direccion_local, metodo_pago, subtotal_sin_impuestos, subtotal_0Porcent, ValorTotal) VALUES
('0701234567', '0701234567', '2023-07-01', '10:00:00', 'Calle Bolívar 123, Machala, El Oro', 'Efectivo', 50.00, 0.00, 50.00),
('0702345678', '0702345678', '2023-07-02', '11:00:00', 'Avenida 9 de Octubre 456, Pasaje, El Oro', 'Transferencia', 39.75, 0.00, 39.75),
('0703456789', '0703456789', '2023-07-03', '12:00:00', 'Calle 10 de Agosto 789, Santa Rosa, El Oro', 'Efectivo', 42.00, 0.00, 42.00),
('0704567890', '0704567890', '2023-07-04', '13:00:00', 'Avenida 25 de Junio 321, Huaquillas, El Oro', 'Transferencia', 61.00, 0.00, 61.00),
('0705678901', '0705678900', '2023-07-05', '14:00:00', 'Calle Rocafuerte 654, Arenillas, El Oro', 'Efectivo', 65.50, 0.00, 65.50),
('0706789012', '0706789012', '2023-07-06', '15:00:00', 'Calle 17 de Septiembre 852, Zaruma, El Oro', 'Transferencia', 60.00, 0.00, 60.00),
('0707890123', '0707890123', '2023-07-07', '16:00:00', 'Calle Pichincha 963, Machala, El Oro', 'Efectivo', 19.00, 0.00, 19.00),
('0708901234', '0708901234', '2023-07-08', '17:00:00', 'Calle Bolívar 741, Santa Rosa, El Oro', 'Transferencia', 68.75, 0.00, 68.75),
('0709012345', '0709012345001', '2023-07-09', '18:00:00', 'Calle Sucre 951, Pasaje, El Oro', 'Efectivo', 75.00, 0.00, 75.00),
('0710123456', '0708901234', '2023-07-10', '19:00:00', 'Calle Quito 852, Huaquillas, El Oro', 'Transferencia', 50.50, 0.00, 50.50);
INSERT INTO Detalle (ID_factura, ID_producto, unidades, totalProdu, detalle_adic) VALUES
(1, 'TP01', 2, 25, 'Dos Tablas de Pino'),
(1, 'CC01', 1, 25, NULL),
(2, 'CR01', 3, 21, 'Tres Cañas Redondas'),
(2, 'TN01', 1, 18.75, NULL),
(3, 'TR01', 1, 30, 'Un Tablón de Roble'),
(3, 'CP01', 2, 12, NULL),
(4, 'VR01', 1, 50, 'Una Viga de Roble'),
(4, 'TP02', 2, 11, NULL),
(5, 'TP01', 3, 37.5, 'Tres Tablas de Pino'),
(5, 'TP03', 1, 28, NULL),
(6, 'TN01', 2, 37.5, 'Dos Tabloncillos de Nogal'),
(6, 'CN01', 1, 22.5, NULL),
(7, 'CR01', 1, 7, 'Una Caña Redonda'),
(7, 'CP01', 2, 12, NULL),
(8, 'CC01', 2, 50, 'Dos Cuartones de Cedro'),
(8, 'TN01', 1, 18.75, NULL),
(9, 'VR01', 1, 50, 'Una Viga de Roble'),
(9, 'TP01', 2, 25, NULL),
(10, 'CN01', 1, 22.5, 'Un Cuartón de Nogal'),
(10, 'TP03', 1, 28, NULL);
INSERT INTO Reclamacion (ID_secretaria, ID_cliente, descripcion) VALUES
('0701234567', '0701234567', 'Producto entregado con retraso'),
('0702345678', '0702345678', 'Producto recibido en mal estado'),
('0703456789', '0703456789', 'Error en la facturación'),
('0704567890', '0704567890', 'Producto diferente al solicitado'),
('0705678901', '0705678900', 'Falta de piezas en el pedido'),
('0706789012', '0706789012', 'Atención al cliente deficiente'),
('0707890123', '0707890123', 'Demora en la respuesta del soporte'),
('0708901234', '0708901234', 'Problemas con el pago'),
('0709012345', '0709012345001', 'Producto dañado durante el envío'),
('0710123456', '0708901234', 'Calidad del producto no es la esperada');

create table proveedor(cedula char(10) primary key,
	nombre varchar(30) not null,
    telefono int);
create table lote_madera(id int primary key auto_increment,
	id_proveedor char(10) not null,
    id_secretaria char(10) not null,
    precio float not null,
    fecha_llegada date not null,
    foreign key (id_proveedor) references proveedor(cedula),
    foreign key (id_secretaria) references secretaria(id));
create table evaluacion(id int primary key auto_increment,
	id_proveedor char(10) not null,
	calidad varchar(10) not null,
    puntualidad varchar(15) not null,
    detalle varchar(100),
    foreign key (id_proveedor) references proveedor(cedula));
create table tipo_de_madera (id varchar(6) primary key,
	nombre varchar(30) not null,
    precio_unitario float not null,
    condic_ambiental varchar(20) not null);
create table especificacion(id_lote int primary key,
	id_madera varchar(6) primary key,
    importe int not null,
    cantidad int not null,
    foreign key(id_lote) references lote_madera(id),
    foreign key (id_madera) references tipo_de_madera(id));

insert into proveedor
values ('0901234567','Paul Herrera','0991234567'),
('0902345678','Antonio Banderas','0982345678'),
('0903456789','Andrea Viteri','0973456789'),
('0904567890','Saúl Bonilla','0964567890'),
('0905678901','Carlos Morales','0955678901'),
('0906789012','Fabricio Morales','0946789012'),
('0907890123','Angelica Solano','0937890123'),
('0908901234','Juan Muñoz','0928901234'),
('0909012345','Mario Castañeda','0919012345'),
('0900123456','Sofia Murillo', '0909876543');
insert into evaluacion (id_proveedor,calidad,puntualidad,detalle)
values ('0901234567','Baja','Puntual','Se mantuvo en comunicación con el negocio'),
('0902345678','Alta','Puntual',null),
('0901234567','Alta','Tarde','Tomo rutas equivocadas para dejar el cargamento'),
('0902345678','Media','Puntual','Viajó con cuidado desde temprano para cuidar el cargamento'),
('0905678901','Baja','Tarde','Confundió la fecha de entrega'),
('0906789012','Media','Puntual',null),
('0901234567','Media','Puntual',null),
('0902345678','Alta','Tarde','Se confundió de local aunque es la mejor calidad que hemos recibido'),
('0909012345','Baja','Puntual','Llegó incluso temprano a entregar el lote'),
('0900123456','Media','Tarde', null);
insert into tipo_de_madera
values('TSO01', 'Cedro', 15.75, 'Húmedo'),
('TSO02', 'Caoba', 20.50, 'Seco'),
('TSO03', 'Roble', 18.00, 'Moderado'),
('TSO04', 'Pino', 10.25, 'Frío'),
('TSO05', 'Nogal', 25.30, 'Caluroso'),
('TSO06', 'Abeto', 12.45, 'Tropical'),
('TSO07', 'Cerezo', 22.10, 'Templado'),
('TSO08', 'Fresno', 19.55, 'Subtropical'),
('TSO09', 'Teca', 30.00, 'Desértico'),
('TSO10', 'Haya', 17.60, 'Montañoso');
insert into lote_madera (id_proveedor,id_secretaria,precio,fecha_llegada)
values('0901234567', '0912345678', 1500.50, '2024-01-15'),
('0902345678', '0912345678', 2300.75, '2024-02-20'),
('0903456789', '0913456789', 1750.00, '2024-03-05'),
('0904567890', '0914567890', 1980.25, '2024-04-18'),
('0905678901', '0915678901', 2100.80, '2024-05-22'),
('0906789012', '0916789012', 2500.00, '2024-06-15'),
('0901234567', '0912345678', 1500.50, '2024-07-10'),
('0907890123', '0917890123', 2600.60, '2024-08-01'),
('0908901234', '0918901234', 1900.70, '2024-09-12'),
('0909012345', '0919012345', 2200.90, '2024-10-30');
insert into especificacion
values(1, 'TSO01', 500, 10),
(2, 'TSO02', 750, 15),
(3, 'TSO03', 600, 12),
(4, 'TSO04', 800, 16),
(5, 'TSO05', 700, 14),
(6, 'TSO06', 550, 11),
(7, 'TSO07', 650, 13),
(8, 'TSO08', 850, 17),
(9, 'TSO09', 750, 15),
(10, 'TSO10', 900, 18);

-- Creacion de tablas, sección empleados

CREATE TABLE Operario (
    ID CHAR(10) PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL,
    horaInicio TIME NOT NULL,
    horaFin TIME NOT NULL,
    fechaCapacitacion DATE,
    tipoCapacitacion VARCHAR(20)
);

CREATE TABLE Asistente_operario (
    ID CHAR(10) PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL,
    horaInicio TIME NOT NULL,
    horaFin TIME NOT NULL,
    fechaCapacitacion DATE,
    tipoCapacitacion VARCHAR(20)
);

CREATE TABLE Maquinaria (
    codigo INT PRIMARY KEY NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    marca VARCHAR(20) NOT NULL,
    fecha_adqui DATE NOT NULL
);

CREATE TABLE Mantenimiento (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    ID_operario CHAR(10) NOT NULL,
    codigo_maquinaria INT,
    ID_secretaria CHAR(10) NOT NULL,
    detalles VARCHAR(100),
    fecha DATE NOT NULL,
    FOREIGN KEY (ID_operario)
        REFERENCES Operario (ID),
    FOREIGN KEY (ID_secretaria)
        REFERENCES Secretaria (ID),
    FOREIGN KEY (codigo_maquinaria)
        REFERENCES Maquinaria (codigo_maquinaria)
);

CREATE TABLE Limpieza (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    lugar VARCHAR(30) NOT NULL
);
	
CREATE TABLE Registro (
    ID_asistente CHAR(10) ,
    ID_limpieza INT,
    ID_secretaria CHAR(10) NOT NULL,
    fecha DATE NOT NULL,
	PRIMARY KEY (ID_asistente, ID_limpieza),
    FOREIGN KEY (ID_asistente)
        REFERENCES Asistente_operario (ID),
    FOREIGN KEY (ID_limpieza)
        REFERENCES Limpieza (ID),
    FOREIGN KEY (ID_secretaria)
        REFERENCES Secretaria (ID)
);

CREATE TABLE Rol_de_pagos (
    ID INT PRIMARY KEY,
    ID_empleado CHAR(10) NOT NULL,
    rol ENUM('A', 'S', 'O') NOT NULL,
    FOREIGN KEY (ID_empleado)
        REFERENCES Secretaria (ID),
    FOREIGN KEY (ID_empleado)
        REFERENCES Operario (ID),
    FOREIGN KEY (ID_empleado)
        REFERENCES Asistente_operario (ID),
    dias_laborados INT NOT NULL,
    sueldo FLOAT NOT NULL,
    horas_extras FLOAT,
    total_ingresos FLOAT NOT NULL,
    egreso_IESS FLOAT NOT NULL,
    anticipos FLOAT,
    total_egresos FLOAT NOT NULL,
    liquido_a_recibir FLOAT NOT NULL
);

INSERT INTO Operario (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion) VALUES
('0943671209', 'Juan Pérez', '08:00:00', '17:00:00', '2024-03-15', 'Seguridad'),
('0923456789', 'Ana Gómez', '09:00:00', '18:00:00', '2024-04-10', 'Mantenimiento'),
('0934567890', 'Luis Fernández', '07:30:00', '16:30:00', '2024-02-20', 'Seguridad'),
('0912345678', 'Laura Martínez', '10:00:00', '19:00:00', '2024-05-05', 'Higiene'),
('0945678901', 'Carlos Rodríguez', '08:30:00', '17:30:00', '2024-06-12', 'Mantenimiento'),
('0956789012', 'María López', '07:45:00', '16:45:00', '2024-07-22', 'Seguridad'),
('0967890123', 'José García', '09:15:00', '18:15:00', '2024-08-30', 'Higiene'),
('0978901234', 'Patricia Romero', '08:00:00', '17:00:00', '2024-09-14', 'Mantenimiento'),
('0989012345', 'Alejandro Díaz', '09:30:00', '18:30:00', '2024-10-10', 'Seguridad'),
('0990123456', 'Claudia Jiménez', '07:00:00', '16:00:00', '2024-11-01', 'Higiene');

INSERT INTO Asistente_operario (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion) VALUES
('0943671210', 'Pedro Alvarez', '08:00:00', '17:00:00', '2024-03-10', 'Operativo'),
('0923456790', 'Sofia Rivas', '09:00:00', '18:00:00', '2024-04-05', 'Administrativo'),
('0934567801', 'Daniela Soto', '07:30:00', '16:30:00', '2024-02-25', 'Operativo'),
('0912345689', 'Gabriel Salgado', '10:00:00', '19:00:00', '2024-05-12', 'Técnico'),
('0945678912', 'Julia Romero', '08:30:00', '17:30:00', '2024-06-15', 'Administrativo'),
('0956789023', 'Raúl Mendoza', '07:45:00', '16:45:00', '2024-07-20', 'Operativo'),
('0967890134', 'Valeria Cruz', '09:15:00', '18:15:00', '2024-08-25', 'Técnico'),
('0978901245', 'Andrés López', '08:00:00', '17:00:00', '2024-09-05', 'Administrativo'),
('0989012356', 'Mónica Guzmán', '09:30:00', '18:30:00', '2024-10-12', 'Operativo'),
('0990123467', 'Fernando Cordero', '07:00:00', '16:00:00', '2024-11-10', 'Técnico');

INSERT INTO Maquinaria (codigo, nombre, marca, fecha_adqui) VALUES
(1001, 'Sierra Circular', 'Bosch', '2022-01-15'),
(1002, 'Sierra de Banda', 'Makita', '2021-03-22'),
(1003, 'Cepillo', 'DeWalt', '2020-07-10'),
(1004, 'Lijadora de Banda', 'Festool', '2023-02-18'),
(1005, 'Cortadora de Madera', 'Metabo', '2021-09-05'),
(1006, 'Trituradora de Residuos', 'Hitachi', '2022-11-30'),
(1007, 'Escuadradora', 'Husqvarna', '2023-06-25'),
(1008, 'Fresadora', 'Delta', '2021-04-14'),
(1009, 'Compresora de Aire', 'Ingersoll Rand', '2022-08-19'),
(1010, 'Sierra de Mesa', 'Black & Decker', '2023-01-12');

INSERT INTO Mantenimiento (ID, ID_operario, codigo_maquinaria, ID_secretaria, detalles, fecha) VALUES
(0, '0943671209', 1001, '0701234567', 'Cambio de hoja de sierra', '2024-01-15'),
(0, '0923456789', 1002, '0702345678', 'Ajuste de tensión de banda', '2024-02-20'),
(0, '0934567890', 1003, '0703456789', 'Mantenimiento preventivo', '2024-03-10'),
(0, '0912345678', 1004, '0704567890', 'Reemplazo de lijas', '2024-04-22'),
(0, '0945678901', 1005, '0705678901', 'Revisión de corte', '2024-05-18'),
(0, '0956789012', 1006, '0706789012', 'Desatasco de trituradora', '2024-06-30'),
(0, '0967890123', 1007, '0707890123', 'Calibración de escuadradora', '2024-07-15'),
(0, '0978901234', 1008, '0708901234', 'Reemplazo de broca', '2024-08-20'),
(0, '0989012345', 1009, '0709012345', 'Cambio de filtro de aire', '2024-09-10'),
(0, '0990123456', 1010, '0710123456', 'Ajuste de mesa de sierra', '2024-10-05');

INSERT INTO Limpieza (lugar) VALUES
('Área de Corte'),
('Zona de Despacho'),
('Almacén de Madera'),
('Oficina Administrativa'),
('Sala de Máquinas'),
('Pasillos de Almacenamiento'),
('Área de Mantenimiento'),
('Zona de Reciclaje'),
('Área de Recepción'),
('Baños');

INSERT INTO Registro (ID_asistente, ID_limpieza, ID_secretaria, fecha) VALUES
('0943671210', 1, '0701234567', '2024-01-15'),
('0923456790', 2, '0702345678', '2024-02-20'),
('0934567801', 3, '0703456789', '2024-03-10'),
('0912345689', 4, '0704567890', '2024-04-22'),
('0945678912', 5, '0705678901', '2024-05-18'),
('0956789023', 6, '0706789012', '2024-06-30'),
('0967890134', 7, '0707890123', '2024-07-15'),
('0978901245', 8, '0708901234', '2024-08-20'),
('0989012356', 9, '0709012345', '2024-09-10'),
('0990123467', 10, '0710123456', '2024-10-05');

INSERT INTO Rol_de_pagos (ID, ID_empleado, rol, dias_laborados, sueldo, horas_extras, total_ingresos, egreso_IESS, anticipos, total_egresos, liquido_a_recibir) VALUES
(1, '0701234567', 'S', 22, 1500.00, 50.00, 1550.00, 100.00, 0.00, 100.00, 1450.00),
(2, '0943671209', 'O', 20, 1800.00, 75.00, 1875.00, 120.00, 50.00, 170.00, 1705.00),
(3, '0934567890', 'A', 18, 1600.00, 40.00, 1640.00, 110.00, 20.00, 130.00, 1510.00),
(4, '0702345678', 'S', 23, 1550.00, 60.00, 1610.00, 105.00, 10.00, 115.00, 1495.00),
(5, '0945789012', 'O', 21, 1750.00, 80.00, 1830.00, 115.00, 60.00, 175.00, 1655.00),
(6, '0935678901', 'A', 19, 1650.00, 45.00, 1695.00, 115.00, 25.00, 140.00, 1555.00),
(7, '0703456789', 'S', 20, 1500.00, 55.00, 1555.00, 100.00, 30.00, 130.00, 1425.00),
(8, '0946789012', 'O', 22, 1850.00, 90.00, 1940.00, 125.00, 40.00, 185.00, 1755.00),
(9, '0936789012', 'A', 17, 1580.00, 35.00, 1615.00, 105.00, 15.00, 120.00, 1495.00),
(10, '0704567890', 'S', 21, 1520.00, 60.00, 1580.00, 110.00, 20.00, 130.00, 1450.00);

