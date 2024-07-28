CREATE database AserrioDataPro;
USE AserrioDataPro;

/*Tablas de la seccion de ventas*/
CREATE TABLE Cliente(
	cedula varchar(13) PRIMARY KEY NOT NULL ,
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
	ID char(10) PRIMARY KEY NOT NULL,
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
DROP TABLE Reclamacion;
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

