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
    totalProdu float NOT NULL,
    detalle_adic varchar(100),
    PRIMARY KEY(ID_factura,ID_producto),
    FOREIGN KEY (ID_factura) REFERENCES Factura(ID),
    FOREIGN KEY (ID_producto) REFERENCES Producto(ID),
    CONSTRAINT unidadesConst CHECK (unidades>0),
    CONSTRAINT totalProduConst CHECK (totalProdu>=0)
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
INSERT INTO Producto (id, nombre, precioUnitario, calidad, condic_ambiental, tiempo_almacenamiento, dimension, descripcion)
VALUES ('Vacio', 'Producto Vacio', 0, 'De Segunda', 'N/A', 0, 'N/A', 'Producto Placeholder');
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

-- CRUD sección ventas
-- INSERT
insert into Producto
values('LT01', 'Listón de Teca', 22.00, 'De segunda', 'Seco', 15, '3x3x10', 'Listón de teca para construcción');
insert into Cliente (cedula, nombre, direccion, num_contacto, correo_contacto) VALUES
('0777798989', 'Saúl Villegas', 'Avenida Principal 144, Machala, El Oro', 0987655320, 'saul_ville@gmail.com');
insert into Secretaria (ID, nombre, horaInicio, horaFine, fechaCapacitacion, tipoCapacitacion) values
('0701238888', 'Andrea Guzmán', '07:30:00', '16:00:00', '2022-09-25', 'Secretaria');
insert into Factura (ID_secretaria, ID_cliente, fecha, hora, direccion_local, metodo_pago, subtotal_sin_impuestos, subtotal_0Porcent, ValorTotal) values
('0703456789', '0701234333', '2023-02-02', '12:30:00', 'Calle Bolívar 123, Machala, El Oro', 'Efectivo', 35.00, 0.00, 35.00);
insert into Detalle (ID_factura, ID_producto, unidades, totalProdu, detalle_adic)
values(3, 'VR01', 3, 32,NULL);
insert into reclamacion (ID_secretaria, ID_cliente, descripcion) values
('0704567890', '0709012345001', 'Falta de limpieza en el local');
-- UPDATE 
update Detalle
set ID_producto='TR01'
where ID_factura=1 and ID_producto= 'CC01';
update Producto
set precioUnitario=17.00
where id='CN01';
update Factura
set hora = '11:45:00'
where id=9;
update reclamacion
set ID_cliente = '0705678900'
where id =8;
update Cliente
set correo_contacto=  'maria_lopez55@hotmail.com'
where cedula='0702345678';
update Secretaria
set horaInicio=  '06:00:00'
where ID='0705678901';
-- SELECT
-- Buscar los detalles que sí tengan detalles adicionales
select *
from Detalle
where detalle_adic is not null;
-- Buscar los productos que tengan un precio unitario mayor a 10.00
select id,nombre,precioUnitario
from Producto
where precioUnitario>10.00;
-- Buscar las facturas realizadas en la tarde
select *
from Factura
where hora > '12:00:00';
-- Buscar la descripción de las reclamaciones que ha hecho el cliente con ID = 0709012345001
select descripcion
from reclamacion
where id_cliente='0709012345001'; 
-- Buscar los clientes que ocupen un correo de contacto hotmail
select cedula,nombre 
from Cliente
where correo_contacto like '%@hotmail%';
-- Buscar las secretarias que tienen de hora de inicio antes de las 9 AM
select ID,nombre,horaInicio
from Secretaria
where horaInicio < '09:00:00';
-- DELETE
delete from Detalle
where ID_factura=8 and ID_producto='CC01';
delete from Producto
where id ='CC01';
delete from Factura
where id_secretaria='0708901234';
delete from reclamacion
where id_secretaria='0708901234' and id_cliente='0705678900';
delete from Cliente
where cedula= '0705678900';
delete from Secretaria
where ID= '0708901234';


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
create table especificacion(id_lote int,
	id_madera varchar(6),
    importe int not null,
    cantidad int not null,
    primary key(id_lote,id_madera),
    foreign key(id_lote) references lote_madera(id),
    foreign key (id_madera) references tipo_de_madera(id));
insert into proveedor
values ('0901234567','Paul Herrera',0991234567),
('0902345678','Antonio Banderas',0982345678),
('0903456789','Andrea Viteri',0973456789),
('0904567890','Saúl Bonilla',0964567890),
('0905678901','Carlos Morales',0955678901),
('0906789012','Fabricio Morales',0946789012),
('0907890123','Angelica Solano',0937890123),
('0908901234','Juan Muñoz',0928901234),
('0909012345','Mario Castañeda',0919012345),
('0900123456','Sofia Murillo',0909876543);
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
values('0901234567', '0701234567', 1500.50, '2024-01-15'),
('0902345678', '0702345678', 2300.75, '2024-02-20'),
('0903456789', '0703456789', 1750.00, '2024-03-05'),
('0904567890', '0704567890', 1980.25, '2024-04-18'),
('0905678901', '0705678901', 2100.80, '2024-05-22'),
('0906789012', '0706789012', 2500.00, '2024-06-15'),
('0901234567', '0707890123', 1500.50, '2024-07-10'),
('0907890123', '0708901234', 2600.60, '2024-08-01'),
('0908901234', '0709012345', 1900.70, '2024-09-12'),
('0909012345', '0710123456', 2200.90, '2024-10-30');
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

-- CRUD Sección compras
-- INSERT
insert into proveedor
values ('0922443789','Patricio Macias',0934567890);
insert into tipo_de_madera
values('TSO11', 'Zebrano', 12.50, 'Tropical');
insert into lote_madera (id_proveedor,id_secretaria,precio,fecha_llegada)
values('0903456789', '0707890123', 2000.50, '2024-11-03');
insert into especificacion
values(11, 'TSO11', 400, 30),
(10, 'TSO07', 100, 5);
insert into evaluacion (id_proveedor,calidad,puntualidad,detalle)
values ('0909012345','Media','Puntual',null);
-- UPDATE 
update especificacion
set cantidad = 20,importe = 1000
where id_lote=1;
update tipo_de_madera
set precio_unitario=15.00
where id='TSO03';
update evaluacion
set detalle = 'Su calidad fue mejor de lo esperado'
where id=10;
update lote_madera
set precio = 2200.50
where id =2;
update proveedor
set telefono=0987236541
where cedula='0905678901';
-- SELECT 
-- Buscar las especificaciones donde se haya comprado más de 15 cantidades
select id_lote,id_madera,importe
from especificacion
where cantidad >15;
-- Buscar la madera que tiene como condición ambiental un clima tropical
select id,nombre
from tipo_de_madera
where condic_ambiental='Tropical';
-- Buscar las evaluaciones del proveedor con la cedula sea 0901234567
select id,calidad,puntualidad,detalle
from evaluacion
where id_proveedor='0901234567';
-- Buscar el lote de madera más caro y cuando llegó
select id,precio,fecha_llegada
from lote_madera
order by precio desc
limit 1;
-- Buscar los proveedores que tengan un apellido que comience con M
select nombre 
from proveedor
where nombre like '% M%'; 
-- DELETE
delete from especificacion
where id_lote=8;
delete from tipo_de_madera
where id ='TSO08';
delete from evaluacion
where id=7;
delete from lote_madera
where id= 8;
delete from proveedor
where cedula= '0907890123';

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
    nombre VARCHAR(50) NOT NULL,
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
        REFERENCES Maquinaria (codigo)
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
CREATE TABLE Empleado (
    ID CHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    horaInicio TIME NOT NULL,
    horaFin TIME NOT NULL,
    fechaCapacitacion DATE,
    tipoCapacitacion VARCHAR(50)
);
CREATE TABLE Rol_de_pagos (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID_empleado CHAR(10) NOT NULL,
    rol ENUM('A', 'S', 'O') NOT NULL,
    FOREIGN KEY (ID_empleado)
        REFERENCES Empleado(ID),
    dias_laborados INT NOT NULL,
    sueldo FLOAT NOT NULL,
    horas_extras FLOAT,
    total_ingresos FLOAT NOT NULL,
    egreso_IESS FLOAT NOT NULL,
    anticipos FLOAT,
    total_egresos FLOAT NOT NULL,
    liquido_a_recibir FLOAT NOT NULL
);
DROP TABLE Rol_de_pagos;
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
INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion) VALUES
-- Secretarias
('0701234567', 'Ana Pérez', '08:00:00', '17:00:00', '2023-01-15', 'Secretaria'),
('0702345678', 'María López', '08:30:00', '17:30:00', NULL, NULL),
('0703456789', 'Luisa Gómez', '09:00:00', '18:00:00', '2023-03-25', 'Secretaria'),
('0704567890', 'Carmen Rodríguez', '08:15:00', '17:15:00', NULL, NULL),
('0705678901', 'Sofía Torres', '08:45:00', '17:45:00', '2023-05-10', 'Secretaria'),
('0706789012', 'Laura Fernández', '09:15:00', '18:15:00', NULL, NULL),
('0707890123', 'Clara García', '08:30:00', '17:30:00', '2023-07-15', 'Secretaria'),
('0708901234', 'Elena Martínez', '08:00:00', '17:00:00', NULL, NULL),
('0709012345', 'Isabel Ramírez', '09:00:00', '18:00:00', '2023-09-05', 'Secretaria'),
('0710123456', 'Marta Jiménez', '08:15:00', '17:15:00', NULL, NULL),

-- Operarios
('0943671209', 'Juan Pérez', '08:00:00', '17:00:00', '2024-03-15', 'Seguridad'),
('0923456789', 'Ana Gómez', '09:00:00', '18:00:00', '2024-04-10', 'Mantenimiento'),
('0934567890', 'Luis Fernández', '07:30:00', '16:30:00', '2024-02-20', 'Seguridad'),
('0912345678', 'Laura Martínez', '10:00:00', '19:00:00', '2024-05-05', 'Higiene'),
('0945678901', 'Carlos Rodríguez', '08:30:00', '17:30:00', '2024-06-12', 'Mantenimiento'),
('0956789012', 'María López', '07:45:00', '16:45:00', '2024-07-22', 'Seguridad'),
('0967890123', 'José García', '09:15:00', '18:15:00', '2024-08-30', 'Higiene'),
('0978901234', 'Patricia Romero', '08:00:00', '17:00:00', '2024-09-14', 'Mantenimiento'),
('0989012345', 'Alejandro Díaz', '09:30:00', '18:30:00', '2024-10-10', 'Seguridad'),
('0990123456', 'Claudia Jiménez', '07:00:00', '16:00:00', '2024-11-01', 'Higiene'),

-- Asistentes de operarios
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

INSERT INTO Rol_de_pagos (ID_empleado, rol, dias_laborados, sueldo, horas_extras, total_ingresos, egreso_IESS, anticipos, total_egresos, liquido_a_recibir) VALUES
('0702345678', 'S', 22, 1500.00, 50.00, 1550.00, 100.00, 0.00, 100.00, 1450.00),
('0703456789', 'S', 23, 1550.00, 60.00, 1610.00, 105.00, 10.00, 115.00, 1495.00),
('0912345678', 'O', 20, 1800.00, 75.00, 1875.00, 120.00, 50.00, 170.00, 1705.00),
('0923456789', 'O', 21, 1850.00, 80.00, 1930.00, 125.00, 55.00, 180.00, 1750.00),
('0934567890', 'O', 22, 1900.00, 85.00, 1985.00, 130.00, 60.00, 190.00, 1795.00),
('0912345689', 'A', 30, 2300.00, 125.00, 2425.00, 170.00, 100.00, 270.00, 2155.00),
('0923456790', 'A', 31, 2350.00, 130.00, 2480.00, 175.00, 105.00, 280.00, 2200.00),
('0704567890', 'S', 20, 1500.00, 55.00, 1555.00, 100.00, 30.00, 130.00, 1425.00),
('0943671209', 'O', 23, 1950.00, 90.00, 2040.00, 135.00, 65.00, 200.00, 1840.00),
('0934567801', 'A', 32, 2400.00, 135.00, 2535.00, 180.00, 110.00, 290.00, 2245.00);



-- procedures de actualizar 
-- actualizar precio de total del producto al cambiar las unidades (detalle)

delimiter /
-- procedure de eliminación 
-- eliminar rol de pago
create procedure EliminarRolDePago (in id_Rol_de_pago int)
begin if id_Rol_de_pago <> all(select id from Rol_de_pagos) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	else 
		delete from Rol_de_pagos where id=id_Rol_de_pago;
        commit;
	end if;
end /
-- eliminar un operario
use aserriodatapro;
delimiter /
CREATE PROCEDURE EliminarOperario (IN idOperario CHAR(10))
BEGIN
    DECLARE v_count INT;
    DECLARE v_exists_in_operario BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_rol_de_pagos BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_mantenimiento BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_empleado BOOLEAN DEFAULT FALSE;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en Operario
    SELECT COUNT(*) INTO v_count FROM Operario WHERE id = idOperario;
    IF v_count > 0 THEN
        SET v_exists_in_operario = TRUE;
    END IF;

    -- Verificar existencia en Rol_de_pagos
    SELECT COUNT(*) INTO v_count FROM Rol_de_pagos WHERE id_empleado = idOperario;
    IF v_count > 0 THEN
        SET v_exists_in_rol_de_pagos = TRUE;
    END IF;

    -- Verificar existencia en Mantenimiento
    SELECT COUNT(*) INTO v_count FROM Mantenimiento WHERE id_operario = idOperario;
    IF v_count > 0 THEN
        SET v_exists_in_mantenimiento = TRUE;
    END IF;

    -- Verificar existencia en empleado
    SELECT COUNT(*) INTO v_count FROM empleado WHERE id = idOperario;
    IF v_count > 0 THEN
        SET v_exists_in_empleado = TRUE;
    END IF;

    -- Verificar si el ID existe en al menos una tabla
    IF NOT (v_exists_in_operario OR v_exists_in_rol_de_pagos OR v_exists_in_mantenimiento OR v_exists_in_empleado) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en todas las tablas';
    ELSE
        -- Eliminar registros de las tablas existentes
        IF v_exists_in_rol_de_pagos THEN
            DELETE FROM Rol_de_pagos WHERE id_empleado = idOperario;
        END IF;

        IF v_exists_in_mantenimiento THEN
            DELETE FROM Mantenimiento WHERE id_operario = idOperario;
        END IF;

        IF v_exists_in_operario THEN
            DELETE FROM Operario WHERE id = idOperario;
        END IF;

        IF v_exists_in_empleado THEN
            DELETE FROM empleado WHERE id = idOperario;
        END IF;

        -- Confirmar la transacción
        COMMIT;
    END IF;
END /
drop procedure EliminarAsistente;
-- eliminar asistente
delimiter /

create procedure EliminarAsistente (in idAsistente char(10))
begin 
	DECLARE v_count INT;
    DECLARE v_exists_in_registro BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_rol_de_pagos BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_asistente_operario BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_empleado BOOLEAN DEFAULT FALSE;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en Registro
    SELECT COUNT(*) INTO v_count FROM Registro WHERE id_asistente = idAsistente;
    IF v_count > 0 THEN
        SET v_exists_in_registro = TRUE;
    END IF;

    -- Verificar existencia en Rol_de_pagos
    SELECT COUNT(*) INTO v_count FROM Rol_de_pagos WHERE id_empleado = idAsistente;
    IF v_count > 0 THEN
        SET v_exists_in_rol_de_pagos = TRUE;
    END IF;

    -- Verificar existencia en Asistente_operario
    SELECT COUNT(*) INTO v_count FROM Asistente_operario WHERE id = idAsistente;
    IF v_count > 0 THEN
        SET v_exists_in_asistente_operario = TRUE;
    END IF;

    -- Verificar existencia en empleado
    SELECT COUNT(*) INTO v_count FROM empleado WHERE id = idAsistente;
    IF v_count > 0 THEN
        SET v_exists_in_empleado = TRUE;
    END IF;

    -- Verificar si el ID existe en al menos una tabla
    IF NOT (v_exists_in_registro OR v_exists_in_rol_de_pagos OR v_exists_in_asistente_operario OR v_exists_in_empleado) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en todas las tablas';
    ELSE
        -- Eliminar registros de las tablas existentes
        IF v_exists_in_registro THEN
            DELETE FROM Registro WHERE id_asistente = idAsistente;
        END IF;

        IF v_exists_in_rol_de_pagos THEN
            DELETE FROM Rol_de_pagos WHERE id_empleado = idAsistente;
        END IF;

        IF v_exists_in_asistente_operario THEN
            DELETE FROM Asistente_operario WHERE id = idAsistente;
        END IF;

        IF v_exists_in_empleado THEN
            DELETE FROM empleado WHERE id = idAsistente;
        END IF;

        -- Confirmar la transacción
        COMMIT;
    END IF;

end /
drop procedure EliminarSecretaria;
-- eliminar secretaria
delimiter /
create procedure EliminarSecretaria (in idSecretaria char(10))
BEGIN 
    DECLARE v_count INT;
    DECLARE v_exists_in_rol_de_pagos BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_reclamacion BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_factura BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_lote_madera BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_mantenimiento BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_registro BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_secretaria BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_empleado BOOLEAN DEFAULT FALSE;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en cada tabla
    SELECT COUNT(*) INTO v_count FROM Rol_de_pagos WHERE id_empleado = idSecretaria;
    IF v_count > 0 THEN
        SET v_exists_in_rol_de_pagos = TRUE;
    END IF;

    SELECT COUNT(*) INTO v_count FROM reclamacion WHERE id_secretaria = idSecretaria;
    IF v_count > 0 THEN
        SET v_exists_in_reclamacion = TRUE;
    END IF;

    SELECT COUNT(*) INTO v_count FROM factura WHERE id_secretaria = idSecretaria;
    IF v_count > 0 THEN
        SET v_exists_in_factura = TRUE;
    END IF;

    SELECT COUNT(*) INTO v_count FROM lote_madera WHERE id_secretaria = idSecretaria;
    IF v_count > 0 THEN
        SET v_exists_in_lote_madera = TRUE;
    END IF;

    SELECT COUNT(*) INTO v_count FROM mantenimiento WHERE id_secretaria = idSecretaria;
    IF v_count > 0 THEN
        SET v_exists_in_mantenimiento = TRUE;
    END IF;

    SELECT COUNT(*) INTO v_count FROM registro WHERE id_secretaria = idSecretaria;
    IF v_count > 0 THEN
        SET v_exists_in_registro = TRUE;
    END IF;

    SELECT COUNT(*) INTO v_count FROM Secretaria WHERE id = idSecretaria;
    IF v_count > 0 THEN
        SET v_exists_in_secretaria = TRUE;
    END IF;

    SELECT COUNT(*) INTO v_count FROM Empleado WHERE id = idSecretaria;
    IF v_count > 0 THEN
        SET v_exists_in_empleado = TRUE;
    END IF;

    -- Verificar si el ID existe en al menos una tabla
    IF NOT (v_exists_in_rol_de_pagos OR v_exists_in_reclamacion OR v_exists_in_factura OR v_exists_in_lote_madera OR v_exists_in_mantenimiento OR v_exists_in_registro OR v_exists_in_secretaria OR v_exists_in_empleado) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en todas las tablas';
    ELSE
        -- Eliminar registros de las tablas existentes
        IF v_exists_in_rol_de_pagos THEN
            DELETE FROM Rol_de_pagos WHERE id_empleado = idSecretaria;
        END IF;

        IF v_exists_in_reclamacion THEN
            DELETE FROM reclamacion WHERE id_secretaria = idSecretaria;
        END IF;

        IF v_exists_in_factura THEN
            -- Primero eliminar los detalles asociados a las facturas
            DELETE FROM detalle WHERE ID_factura IN (SELECT ID FROM factura WHERE id_secretaria = idSecretaria);
            DELETE FROM factura WHERE id_secretaria = idSecretaria;
        END IF;
		
        IF v_exists_in_lote_madera THEN
			DELETE FROM especificacion WHERE id_lote = (SELECT id FROM lote_madera WHERE  id_secretaria = idSecretaria);
            DELETE FROM lote_madera WHERE id_secretaria = idSecretaria;
            
        END IF;

        IF v_exists_in_mantenimiento THEN
            DELETE FROM mantenimiento WHERE id_secretaria = idSecretaria;
        END IF;

        IF v_exists_in_registro THEN
            DELETE FROM registro WHERE id_secretaria = idSecretaria;
        END IF;

        IF v_exists_in_secretaria THEN
            DELETE FROM Secretaria WHERE id = idSecretaria;
        END IF;

        IF v_exists_in_empleado THEN
            DELETE FROM Empleado WHERE id = idSecretaria;
        END IF;

        -- Confirmar la transacción
        COMMIT;
    END IF;
end /
-- eliminar mantenimiento
create procedure EliminarMantenimiento (in idMantenimiento int)
begin if idMantenimiento <> all(select id from Mantenimiento) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	else
		delete from Mantenimiento where id=idMantenimiento;
        commit;
	end if; 
end /
-- eliminar maquinaria

create procedure EliminarMaquinaria (in idMaquinaria int)
begin 
	DECLARE v_count INT;
    DECLARE v_exists_in_maquinaria BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_mantenimiento BOOLEAN DEFAULT FALSE;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en Maquinaria
    SELECT COUNT(*) INTO v_count FROM Maquinaria WHERE codigo = idMaquinaria;
    IF v_count > 0 THEN
        SET v_exists_in_maquinaria = TRUE;
    END IF;

    -- Verificar existencia en Mantenimiento
    SELECT COUNT(*) INTO v_count FROM Mantenimiento WHERE codigo_maquinaria = idMaquinaria;
    IF v_count > 0 THEN
        SET v_exists_in_mantenimiento = TRUE;
    END IF;

    -- Verificar si el ID existe en al menos una tabla
    IF NOT (v_exists_in_maquinaria) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en la tabla Maquinaria';
    ELSE
        -- Eliminar registros de las tablas existentes
        IF v_exists_in_mantenimiento THEN
            DELETE FROM Mantenimiento WHERE codigo_maquinaria = idMaquinaria;
        END IF;

        IF v_exists_in_maquinaria THEN
            DELETE FROM Maquinaria WHERE codigo = idMaquinaria;
        END IF;

        -- Confirmar la transacción
        COMMIT;
    END IF;
end /
-- eliminar registro
create procedure EliminarRegistro (in idAsistente char(10),in idSecretaria char(10),in idLimpieza int)
begin if idAsistente <> all(select id from Asistente_operario) or idSecretaria <> all(select id from secretaria) or idLimpieza <> all(select id from limpieza) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	else
        delete from Registro where idAsistente=id_asistente and idSecretaria=id_secretaria and id_limpieza=idLimpieza;
        commit;
	end if; 
end /
-- eliminar limpieza

create procedure EliminarLimpieza (in idLimpieza int)
begin
	DECLARE v_count INT;
    DECLARE v_exists_in_limpieza BOOLEAN DEFAULT FALSE;
    DECLARE v_exists_in_registro BOOLEAN DEFAULT FALSE;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en Limpieza
    SELECT COUNT(*) INTO v_count FROM Limpieza WHERE id = idLimpieza;
    IF v_count > 0 THEN
        SET v_exists_in_limpieza = TRUE;
    END IF;

    -- Verificar existencia en Registro
    SELECT COUNT(*) INTO v_count FROM Registro WHERE id_limpieza = idLimpieza;
    IF v_count > 0 THEN
        SET v_exists_in_registro = TRUE;
    END IF;

    -- Verificar si el ID existe en al menos una tabla
    IF NOT (v_exists_in_limpieza) THEN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en la tabla Limpieza';
    ELSE
        -- Eliminar registros de las tablas existentes
        IF v_exists_in_registro THEN
            DELETE FROM Registro WHERE id_limpieza = idLimpieza;
        END IF;

        IF v_exists_in_limpieza THEN
            DELETE FROM Limpieza WHERE id = idLimpieza;
        END IF;

        -- Confirmar la transacción
        COMMIT;
    END IF;
end /
-- eliminar reclamación
create procedure EliminarReclamacion (in idReclamacion int)
begin if idReclamacion <> all(select id from Reclamacion) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	else
        delete from Reclamacion where idReclamacion=id;
        commit;
	end if; 
end /
-- Eliminar cliente

CREATE PROCEDURE eliminarCliente (IN idCliente VARCHAR(13))
BEGIN
    DECLARE v_count INT;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en Cliente
    SELECT COUNT(*) INTO v_count FROM Cliente WHERE cedula = idCliente;

    IF v_count = 0 THEN
        -- El cliente no existe, emite un mensaje de error
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en la tabla Cliente';
        ROLLBACK;
    ELSE
        -- Actualizar las tablas relacionadas
        UPDATE Factura 
        SET ID_cliente = '0000000000000' 
        WHERE ID_cliente = idCliente;
        
        UPDATE Reclamacion 
        SET ID_cliente = '0000000000000' 
        WHERE ID_cliente = idCliente;
        
        -- Eliminar el cliente de la tabla Cliente
        DELETE FROM Cliente 
        WHERE cedula = idCliente;

        -- Confirmar la transacción
        COMMIT;
    END IF;
END/
drop procedure eliminarCliente/
-- Eliminar factura 
create procedure EliminarFactura (in idFactura int)
begin
	DECLARE v_count INT;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en Factura
    SELECT COUNT(*) INTO v_count FROM Factura WHERE id = idFactura;

    IF v_count = 0 THEN
        -- El ID de factura no existe, emite un mensaje de error
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en la tabla Factura';
        ROLLBACK;
    ELSE
        -- Eliminar registros relacionados en Detalle
        DELETE FROM Detalle WHERE id_factura = idFactura;
        
        -- Eliminar el registro de la tabla Factura
        DELETE FROM Factura WHERE id = idFactura;

        -- Confirmar la transacción
        COMMIT;
    END IF;
end /
-- eliminar detalle


create procedure EliminarDetalle (in idFactura int,in idProducto varchar(6))
begin 
	DECLARE total FLOAT;
    DECLARE nuevoValorTotal FLOAT;

    -- Verifica si el ID de factura o el ID de producto existen en la tabla Detalle
    IF NOT EXISTS (SELECT 1 FROM Detalle WHERE id_factura = idFactura AND id_producto = idProducto) THEN
        SIGNAL SQLSTATE '04020' SET MESSAGE_TEXT = 'ID inexistente';
        ROLLBACK;
    ELSE
        -- Obtén el valor total del detalle
        SELECT totalProdu INTO total
        FROM Detalle
        WHERE ID_factura = idFactura AND ID_producto = idProducto;

        -- Calcula el nuevo ValorTotal después de la eliminación
        SELECT ValorTotal - total INTO nuevoValorTotal
        FROM Factura
        WHERE ID = idFactura;

        -- Verifica si el nuevo ValorTotal es 0
        IF nuevoValorTotal = 0 THEN
            -- Si el ValorTotal es 0, no elimina el detalle, solo actualiza el ValorTotal
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede eliminar el detalle ya que el ValorTotal sería 0';
            ROLLBACK;
        ELSE
            -- Si el ValorTotal no es 0, elimina el detalle y actualiza el ValorTotal
            DELETE FROM Detalle
            WHERE ID_factura = idFactura AND ID_producto = idProducto;

            UPDATE Factura
            SET ValorTotal = nuevoValorTotal, subtotal_sin_impuestos= nuevoValorTotal
            WHERE ID = idFactura;

            COMMIT;
        END IF;
    END IF;
end /
call InsertDetalle(12,'CN01',2,'Hola')/
call EliminarDetalle(12,'CN01') /
-- eliminar producto
create procedure EliminarProducto (in idProducto varchar(6))
begin    
	DECLARE v_count INT;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en Producto
    SELECT COUNT(*) INTO v_count FROM Producto WHERE id = idProducto;

    IF v_count = 0 THEN
        -- El ID del producto no existe, emite un mensaje de error
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en la tabla Producto';
        ROLLBACK;
    ELSE
        -- Eliminar registros relacionados en Detalle
        DELETE FROM Detalle WHERE ID_producto = idProducto;
        
        -- Eliminar el registro en la tabla Producto
        DELETE FROM Producto WHERE id = idProducto;

        -- Confirmar la transacción
        COMMIT;
    END IF;
end /
-- eliminar lote de madera
create procedure Eliminarlote (in idLote int,in eliminar boolean)
begin if idLote <> all(select id from Lote_madera) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar = true then
		delete from Especificacion where id_lote = idLote;
        delete from Lote_madera where idLote=id;
        commit;
	end if; 
end /
-- eliminar proveedor
create procedure EliminarProveedor (in idProveedor char(10),in eliminar boolean)
begin if idProveedor <> all(select cedula from Proveedor) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar = true then
        delete from Proveedor where idProveedor=cedula;
        delete from especificacion where id_lote = (select id from lote_madera where id_proveedor = idProveedor);
		delete from Lote_madera where id_proveedor = idProveedor;
        delete from Evaluacion where id_proveedor =idProveedor;
        commit;
	end if; 
end /
-- eliminar evaluacion
create procedure EliminarEvaluacion (in idEvaluacion int)
begin if idEvaluacion <> all(select id from Evaluacion) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	else
        delete from Evaluacion where idEvaluacion=id;
        commit;
	end if; 
end /
-- eliminar especificacion 
create procedure EliminarEspecificacion (in idLote int,in idMadera varchar(6))
begin 
DECLARE importeOld float;
DECLARE precioNew float;

SELECT importe into importeOld 
FROM especificacion
WHERE id_lote = idLote and id_madera = idMadera;

SELECT precio - importeOld into precioNew 
FROM lote_madera 
WHERE id = idLote;

if idLote <> all(select id_lote from Especificacion) or idMadera <> all(select id_madera from Especificacion) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
elseif precioNew <= 0 then 
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede eliminar la especifacacion ya que el precio del lote asociado sería 0';
            ROLLBACK;
	else
		UPDATE lote_madera
        SET precio = precioNew
        WHERE id = idLote;
        delete from Especificacion where id_lote = idLote and id_madera = idMadera;
        commit;
	end if; 
end /
-- eliminar totalProdutipo de madera

create procedure EliminarMadera (in idMadera varchar(6))
begin 
	DECLARE v_count INT;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Verificar existencia en tipo_de_madera
    SELECT COUNT(*) INTO v_count FROM tipo_de_madera WHERE id = idMadera;

    IF v_count = 0 THEN
        -- El ID de madera no existe, emite un mensaje de error
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente en la tabla tipo_de_madera';
        ROLLBACK;
    ELSE
        -- Eliminar registros relacionados en Especificacion
        DELETE FROM Especificacion WHERE id_madera = idMadera;

        -- Eliminar el registro en la tabla tipo_de_madera
        DELETE FROM tipo_de_madera WHERE id = idMadera;

        -- Confirmar la transacción
        COMMIT;
    END IF;
end /
delimiter ;
-- procedures de actualizar 
-- actualizar un detalle
delimiter /

create procedure ActualizarDetalle(in idFactura int,in idProducto varchar(6),in unidades_nuevas int,in nuevo_detalle_adic varchar(100))
BEGIN
	declare precio_unitario float;
	set precio_unitario= (select precioUnitario from producto where id=idProducto);
	if idProducto <> all(select id_producto from Detalle) then
		signal sqlstate '04001' set message_text = 'ID producto no existente';
        rollback;
	elseif unidades_nuevas <0 then
		signal sqlstate '04002' set message_text ='Unidades negativas no validas';
        rollback;
	elseif idFactura <> all(select id_factura from Detalle) then
		signal sqlstate '04987' set message_text= 'ID factura no existente';
        rollback;
	else
		update detalle 
        set unidades=unidades_nuevas,totalProdu = unidades_nuevas *(precio_unitario),detalle_adic=nuevo_detalle_adic 
        where ID_factura=id_factura AND ID_producto=idProducto ;
        commit;
	end if;
END /
drop procedure ActualizarDetalle/
create trigger ActualizarTotal after update on Detalle
for each row
begin
	UPDATE factura 
    SET subtotal_sin_impuestos = subtotal_sin_impuestos - old.totalProdu + new.totalProdu,

        ValorTotal = ValorTotal - old.totalProdu + new.totalProdu
    WHERE ID = old.ID_factura;
end /
-- actualizar producto 
create procedure ActualizarProducto(in idProducto varchar(6),in nuevoNombre varchar(30),in nuevoPrecioUnitario float,in nueva_calidad varchar(10),in nueva_cond_amb varchar(20), in nuevo_tiempo_almac int,in nueva_dimension varchar(20),in nueva_desc varchar(100))
begin
	if idProducto <> all(select id from Producto) then
		signal sqlstate '04001' set message_text = 'ID producto no existente';
        rollback;
	else
		update producto set nombre=nuevoNombre,precioUnitario=nuevoPrecioUnitario,calidad = nueva_calidad,condic_ambiental=nueva_cond_amb,tiempo_almacenamiento=nuevo_tiempo_almac,dimension=nueva_dimension,descripcion=nueva_desc where id=idProducto;
        commit;
	end if; 
end /
create trigger trgActualizarProducto before update on Producto
for each row begin 
	update Detalle set id_madera=new.ID where id_madera=old.ID;
end/ 
-- actualizar factura
create procedure ActualizarFactura(in idFactura int,in NuevaIdSecretaria char(10),in NuevoIdCliente varchar(13),in NuevaFecha date,in NuevaHora time,in NuevaDireccion varchar(100),in metodoPago varchar(20),in NuevoSubtotalSinImpuestos float,in Nuevosubtotal_0PorCiento float)
begin
    declare total float;
    SELECT ValorTotal
    INTO total
    FROM factura
    WHERE ID=idFactura;
    SET total= total+NuevoSubtotalSinImpuestos; 
	IF metodoPago NOT IN ('Efectivo', 'Transferencia') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Método de pago inválido';
		rollback;
	elseif idFactura <> all(select id from Factura) then
		signal sqlstate '04200' set message_text='ID de factura inexistente';
        rollback;
	elseif NuevaIdSecretaria <>all(select id_secretaria from factura) then
		signal sqlstate '05666' set message_text='ID de secretaria inexistente';
        rollback;
	elseif NuevoIdCliente <>all(select id_cliente from factura) then
		signal sqlstate '05667' set message_text='ID del cliente inexistente';
        rollback;
	else
		update Factura 
        set fecha=NuevaFecha,hora=NuevaHora,direccion_local=NuevaDireccion,metodo_pago=metodoPago,Subtotal_sin_impuestos=NuevoSubtotalSinImpuestos,subtotal_0Porcent=Nuevosubtotal_0PorCiento,ValorTotal=total 
		where ID=idFactura;
        commit;
	end if;
end /
call ActualizarFactura(10,'0710123456','0000000000000','2023-07-10','19:30','Calle Huaquillas, El Oro','Transferencia',60,0.0)/
drop procedure ActualizarFactura/
-- Actualizar cliente 
create procedure ActualizarCliente(in cedulaCliente varchar(13),in NuevoNombre varchar(30),in direccionNueva varchar(100),in NuevoNumContacto int,in NuevoCorreo varchar(100))
begin
	if cedulaCliente <> all(select cedula from Cliente)then
		signal sqlstate '04002' set message_text ='Cédula no se encuentra en la base de datos';
        rollback;
	else
		update Cliente set nombre=NuevoNombre, direccion = direccionNueva,num_contacto=NuevoNumContacto,correo_contacto=NuevoCorreo where cedula =cedulaCliente;
        commit;
    end if;
end /

-- Actualizar reclamación 
create procedure ActualizarReclamacion (in idEspecifico int,in idSecretaria char(10),in cedulaCliente varchar(13),in descripcion_nueva varchar(100))
begin
	if cedulaCliente <> all(select cedula from cliente)then
		signal sqlstate '04003' set message_text ='Cédula no se encuentra con una tupla para reclamaciones';
        rollback;
	elseif idEspecifico <> all(select id from reclamacion)then
		signal sqlstate '05889' set message_text ='ID no existente';
        rollback;
	elseif idSecretaria <> all(select id from secretaria) then
		signal sqlstate '05555' set message_text='ID secretaria no existente';
        rollback;
	else
		update Reclamacion set id_secretaria=idSecretaria,id_cliente=cedulaCliente,descripcion=descripcion_nueva where id=idEspecifico;
   end if;     
end /
-- actualizar la información del mantenimiento
create procedure actualizarMantenimiento(in idNuevo int, in idOperario char(10),in codMaquinaria int, in idSecretaria char(10),in NuevosDetalles varchar(100),fechaNueva date)
begin
	if idNuevo <>all(select id from mantenimiento)then
		signal sqlstate '07777' set message_text='ID del mantenimiento no existe';
        rollback;
	elseif idOperario <>all(select id from operario)then
		signal sqlstate '09222' set message_text='ID de operario no existente';
        rollback;
	elseif codMaquinaria <>all(select codigo from maquinaria)then
		signal sqlstate'07779' set message_text='Codigo de maquinaria no existe';
        rollback;
	elseif idSecretaria <>all(select id from secretaria)then
		signal sqlstate '07780' set message_text='ID de la secretaria no existe';
        rollback;
	else
		update Mantenimiento 
        set ID_operario=idOperario,codigo_maquinaria=codMaquinaria,ID_secretaria=idSecretaria,detalles=NuevosDetalles,fecha=fechaNueva
        where ID=idNuevo;
        commit;
	end if;
end /
-- actualizar la información del operario
delimiter /
create procedure actualizarOperario(in idOperario char(10), in nomb varchar(40),in horaI time,in horaF time,in fechaCapa date,in tipoCapa varchar(20))
begin
	if idOperario<> all(select id from Operario) then
		signal sqlstate '09222' set message_text='ID de operario no existente';
        rollback;
	else
		UPDATE empleado
		SET nombre=nomb,horaInicio=horaI,horaFin=horaF,fechaCapacitacion=fechaCapa,tipoCapacitacion=tipoCapa,ID=idOperario
		WHERE ID=idOperario;
        UPDATE operario
        SET ID=idOperario
        WHERE ID=idOperario;
	end if;
end/
delimiter /
create trigger actualizarOperario
before update on operario
for each row
begin
	UPDATE mantenimiento
	SET ID= new.ID
	WHERE ID= old.ID;
	UPDATE rol_de_pagos
	SET ID_empleado= new.ID
	WHERE ID_empleado= old.ID;
end/
USE aserriodatapro/
drop trigger actualizarOperario/
-- actualizar la información de la maquinaria
create procedure actualizarMaquinaria(in codig int,in nomb varchar(20),in mar varchar(20),in fechaAdqui date)
begin
	if codig<>all (select codigo from maquinaria)then
		signal sqlstate '08888' set message_text='Codigo inexistente';
        rollback;
	else
	UPDATE maquinaria
    SET codigo=codig, nombre=nomb, marca=mar, fecha_adqui=fechaAdqui 
    WHERE codigo=codi;
    commit;
    end if;
end/
create trigger actualizarMaquinaria
before update on maquinaria
for each row
begin
	UPDATE matenimiento
	SET codigo_maquinaria= new.codigo
	WHERE codigo_maquinaria= old.codigo;
end/
-- actualizar la información del registro
create procedure actualizarRegistro(in idAsist char(10),idLimpieza int,idSecretaria char(10),in fech date)
begin
	if idAsist <> all(select id_asistente from Registro) then
		signal sqlstate '08889' set message_text ='ID de asistente no existe';
        rollback;
	elseif idLimpieza <>all(select id_limpieza from Registro) then
		signal sqlstate '08890' set message_text='ID de la limpieza inexistente';
        rollback;
	elseif idSecretaria <>all(select id_secretaria from Registro) then
		signal sqlstate '08891' set message_text= 'ID de la secretaria no existe';
        rollback;
	else
		UPDATE registro
		SET fecha=fech
		WHERE ID_asistente=idAsist and ID_limpieza=idLimpieza and ID_secretaria=idSecretaria;
        commit;
	end if;
end/
-- actualizar la información del asistente de operario
create procedure actualizarAsistente(in idAsistente char(10), in nomb varchar(40),in horaI time,in horaF time,in fechaCapa date,in tipoCapa varchar(20))
begin
	if idAsistente<>all(select ID from Asistente_operario) then
		signal sqlstate '08889' set message_text ='ID de asistente no existe';
        rollback;
	else 
		UPDATE empleado
		SET nombre=nomb,horaInicio=horaI,horaFin=horaF,fechaCapacitacion=fechaCapa,tipoCapacitacion=tipoCapa,ID=idAsistente
		WHERE ID=idAsistente;
        UPDATE asistente_operario
        SET ID=idAsistente
        WHERE ID=idAsistente;
        commit;
	end if;
end/
create trigger actualizarAsistente
before update on asistente_operario
for each row
begin
	UPDATE registro
	SET ID= new.ID
	WHERE ID= old.ID;
	UPDATE rol_de_pagos
	SET ID_empleado= new.ID
	WHERE ID_empleado= old.ID;
end/
drop trigger actualizarAsistente/
-- actualizar la información de la limpieza
create procedure actualizarLimpieza(in idLimpieza int, in place varchar(30))
begin
if idLimpieza<>all(select id from limpieza) then
		signal sqlstate '08890' set message_text='ID de la limpieza inexistente';
        rollback;
	else
		UPDATE limpieza SET lugar=place WHERE ID=idLimpieza;
        commit;
	end if;
end/ 
-- actualizar la información de la secretaria
create procedure actualizarSecretaria(in idSecretaria char(10), in nomb varchar(40),in horaI time,in horaF time,in fechaCapa date,in tipoCapa varchar(20))
begin
	if idSecretaria<> all(select id from Secretaria) or idSecretaria <> all(select id from Empleado) then
		signal sqlstate '08891' set message_text= 'ID de la secretaria no existe';
        rollback;
	else
		UPDATE empleado
		SET nombre=nomb,horaInicio=horaI,horaFin=horaF,fechaCapacitacion=fechaCapa,tipoCapacitacion=tipoCapa,ID=idSecretaria
		WHERE ID=idSecretaria;
        UPDATE secretaria
        SET ID = idSecretaria
        WHERE ID = idSecretaria;
        commit;
	end if;
end/
create trigger actualizarSecretaria
before update on secretaria
for each row
begin
	if old.ID <> new.ID then
		update factura set id_secretaria=new.id where id_secretaria=old.id;
		UPDATE lote_madera
        SET id_secretaria= new.id
        WHERE id_secretaria= old.id;
        UPDATE mantenimiento
        SET ID_secretaria= new.ID
        WHERE ID_secretaria= old.ID;
        UPDATE reclamacion
        SET ID_secretaria= new.ID
        WHERE ID_secretaria= old.ID;
        UPDATE registro
        SET ID= new.ID
        WHERE ID= old.ID;
        UPDATE rol_de_pagos
        SET ID_empleado= new.ID
        WHERE ID_empleado= old.ID;
	end if;
end/
drop trigger actualizarSecretaria/
-- actualizar la información de la evaluación
create procedure actualizarEvaluacion(in evalu int,in proveedor char(10), in p_calidad varchar(10), in p_puntualidad varchar(15), in p_detalle varchar(100))
begin
	if evalu <> all(select id from evaluacion)	then
		signal sqlstate '09254' set message_text='ID de la evaluación inexistente';
        rollback;
	elseif proveedor <>all(select cedula from proveedor) then
		signal sqlstate '09334' set message_text ='Proveedor no existente';
        rollback;
	else
		UPDATE evaluacion
		SET id_proveedor=proveedor, calidad=p_calidad, puntualidad= p_puntualidad, detalle=p_detalle
		WHERE id=evalu;
        commit;
	end if;
end/
-- actualizar la información del proveedor
create procedure actualizarProveedor(in p_cedu char(10), in p_nomb varchar(30), in p_telef int)
begin
	if p_cedu <>all(select cedula from proveedor) then
		signal sqlstate '09334' set message_text ='Proveedor no existente';
        rollback;
	else
		UPDATE proveedor
		SET nombre=p_nomb, telefono=p_telef, cedula=p_cedu
		WHERE cedula=p_cedu;
        commit;
	end if;
end/
create trigger actualizarProveedor
before update on proveedor
for each row
begin
	if old.cedula <> new.cedula then
        UPDATE lote_madera
        SET id_proveedor = new.cedula
        WHERE id_proveedor = old.cedula;
        UPDATE evaluacion
        SET id_proveedor= new.cedula
        WHERE id_proveedor = old.cedula;
        end if;
end/
-- actualizar la informacion de un lote de madera
create procedure ActualizarLoteMadera(in idLote int,in idProveedor char(10),in idsecret char(10),in precioNuevo float,in fechaNueva date)

begin

	 DECLARE precioAnterior FLOAT;

    -- Validar la existencia del lote
    IF NOT EXISTS (SELECT 1 FROM lote_madera WHERE id = idLote) THEN
        SIGNAL SQLSTATE '09301' SET MESSAGE_TEXT = 'ID del lote no existente';
        rollback;
    END IF;

    -- Validar la existencia del proveedor
    IF NOT EXISTS (SELECT 1 FROM proveedor WHERE cedula = idProveedor) THEN
        SIGNAL SQLSTATE '09334' SET MESSAGE_TEXT = 'Proveedor no existente';
        rollback;
    END IF;

    -- Validar la existencia de la secretaria
    IF NOT EXISTS (SELECT 1 FROM secretaria WHERE id = idsecret) THEN
        SIGNAL SQLSTATE '08891' SET MESSAGE_TEXT = 'ID de la secretaria no existe';
        rollback;
    END IF;

    -- Obtener el precio actual del lote
    SELECT precio INTO precioAnterior
    FROM lote_madera
    WHERE id = idLote;

    -- Actualizar el registro en lote_madera
    UPDATE lote_madera
    SET id_proveedor = idProveedor,
        id_secretaria = idsecret,
        precio = precioAnterior,
        fecha_llegada = fechaNueva
    WHERE id = idLote;

end/
-- actualizar una especificación
create procedure ActualizarEspecificacion(in idLote int,in idMadera varchar(6), in cantidadNueva int)
begin
	DECLARE precUnit float;
    DECLARE precLote float;
    DECLARE importeNuevo float;
    DECLARE importeAnterior float;
	if idLote <> all(select id_lote from especificacion) then
		signal sqlstate '05030' set message_text = 'ID del lote no existente';
        rollback;
	elseif idMadera <> all(select id_madera from especificacion) then
		signal sqlstate '05031' set message_text ='ID de la madera no existente';
        rollback;
	elseif cantidadNueva <0 then
		signal sqlstate '04002' set message_text ='Unidades negativas no validas';
        rollback;
	else
    -- valores anteriores
     -- Iniciar la transacción

        -- Obtener el precio unitario de la madera
        SELECT precio_unitario INTO precUnit FROM tipo_de_madera WHERE id = idMadera;

        -- Obtener el importe anterior del lote
        SELECT importe INTO importeAnterior FROM especificacion WHERE id_lote = idLote AND id_madera = idMadera;

        -- Calcular el nuevo importe
        SET importeNuevo = cantidadNueva * precUnit;

        -- Actualizar la especificación con el nuevo importe y cantidad
        UPDATE especificacion
        SET importe = importeNuevo, cantidad = cantidadNueva
        WHERE id_madera = idMadera AND id_lote = idLote;

        -- Obtener el precio actual del lote
        SELECT precio INTO precLote FROM lote_madera WHERE id = idLote;

        -- Actualizar el precio del lote
        UPDATE lote_madera
        SET precio = precLote - importeAnterior + importeNuevo
        WHERE id = idLote;

        -- Confirmar la transacción
        COMMIT;
    end if;
end/
-- actualizar tipo de madera 
CREATE PROCEDURE ActualizarTipoMadera(IN idMadera varchar(6),in NuevoNombre VARCHAR(30), IN precUnit FLOAT, IN condicAmb VARCHAR(20))
BEGIN
	if idMadera<> all(select id from tipo_de_madera) then
		signal sqlstate '05031' set message_text ='ID de la madera no existente';
        rollback;
	else
		UPDATE tipo_de_madera
		SET nombre = name, precio_unitario = precUnit, condic_ambiental = condicAmb
		WHERE id = idMadera;
		commit;
end if;
END /

create trigger trgActualizarTipoMadera before update on tipo_de_madera
for each row begin
	update Especificacion set ID_madera=new.ID where ID_madera=old.ID;
end/
-- actualizar rol de pago
create procedure ActualizarRolDePago(in idNuevo int,in idEmpleado char(10),in laborados int,in sueldoNuevo float,in horasExtras float,in totalIngresos float,in EgresosIEES float,NuevosAnticipos float,totalEgresos float)
begin
	if idNuevo <>all(select id from rol_de_pago)then
		signal sqlstate '07221' set message_text='ID del rol de pago no existente';
        rollback;
	elseif idEmpleado <>all(select id from empleado) then
		signal sqlstate '08111' set message_text= 'ID del empleado no existente';
        rollback;
	else
		update rol_de_pagos set id_empleado=idEmpleado,dias_laborados=laborados,sueldo=sueldoNuevo,horas_extras=horasExtras,total_ingresos=totalIngresos,egreso_IEES=EgresosIEES,anticipos=NuevosAnticipos,total_egresos=totalEgresos,liquido_a_recibir=totalIngresos-totalEgresos
        where id=idNuevo;
        commit;
    end if;
end/	
-- procedure de agregacion
-- Procedimientos almacenados para la tabla Cliente
DELIMITER //

CREATE PROCEDURE InsertCliente(
    IN p_cedula VARCHAR(13),
    IN p_nombre VARCHAR(30),
    IN p_direccion VARCHAR(100),
    IN p_num_contacto INT,
    IN p_correo_contacto VARCHAR(100)
)
BEGIN
if p_cedula =any(select cedula from Cliente) then
	signal sqlstate '09375' set message_text ='Cédula ya existente';
    rollback;
else
    INSERT INTO Cliente (cedula, nombre, direccion, num_contacto, correo_contacto)
    VALUES (p_cedula, p_nombre, p_direccion, p_num_contacto, p_correo_contacto);
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Producto
CREATE PROCEDURE InsertProducto(
    IN p_ID VARCHAR(6),
    IN p_nombre VARCHAR(30),
    IN p_precioUnitario FLOAT,
    IN p_calidad ENUM('De primera', 'De segunda'),
    IN p_condic_ambiental VARCHAR(20),
    IN p_tiempo_almacenamiento INT,
    IN p_dimension VARCHAR(20),
    IN p_descripcion VARCHAR(100)
)
BEGIN
if p_ID =any(select ID from producto) then
	signal sqlstate '08332' set message_text ='ID del producto ya existente';
    rollback;
elseif p_calidad<>all(select distinct calidad from Producto) then
	signal sqlstate '07755' set message_text='Calidad insertada de forma incorrecta';
    rollback;
else
    INSERT INTO Producto (ID, nombre, precioUnitario, calidad, condic_ambiental, tiempo_almacenamiento, dimension, descripcion)
    VALUES (p_ID, p_nombre, p_precioUnitario, p_calidad, p_condic_ambiental, p_tiempo_almacenamiento, p_dimension, p_descripcion);
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Secretaria
CREATE PROCEDURE InsertSecretaria(
    IN p_ID CHAR(10),
    IN p_nombre VARCHAR(40),
    IN p_horaInicio TIME,
    IN p_horaFin TIME,
    IN p_fechaCapacitacion DATE,
    IN p_tipoCapacitacion VARCHAR(20)
)
BEGIN
if p_ID=any(select ID from Secretaria) or p_ID = any(select ID from Empleado) then
	signal sqlstate '07444' set message_text='ID de secretaria ya existente';
    rollback;
else
	INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
    VALUES (p_ID, p_nombre, p_horaInicio, p_horaFin, p_fechaCapacitacion, p_tipoCapacitacion);
    INSERT INTO Secretaria (ID)
    VALUES (p_ID);
    
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Factura
CREATE PROCEDURE InsertFactura(
    IN p_ID_secretaria CHAR(10),
    IN p_ID_cliente VARCHAR(13),
    IN p_fecha DATE,
    IN p_hora TIME,
    IN p_direccion_local VARCHAR(100),
    IN p_metodo_pago ENUM('Efectivo', 'Transferencia'),
    IN p_subtotal_sin_impuestos FLOAT,
    IN p_subtotal_0Porcent FLOAT,
    IN p_ValorTotal FLOAT
)
BEGIN
if p_ID_secretaria <>all(select id from secretaria) or p_ID_cliente<>all(select cedula from cliente)then
	signal sqlstate '04020' set message_text ='ID inexistente';
    rollback;
elseif p_metodo_pago <>all(select distinct metodo_pago from factura) then
	signal sqlstate '08711' set message_text='Metodo de pago incorrecto';
    rollback;
else
    INSERT INTO Factura (ID_secretaria, ID_cliente, fecha, hora, direccion_local, metodo_pago, subtotal_sin_impuestos, subtotal_0Porcent, ValorTotal)
    VALUES (p_ID_secretaria, p_ID_cliente, p_fecha, p_hora, p_direccion_local, p_metodo_pago, p_subtotal_sin_impuestos, p_subtotal_0Porcent, p_ValorTotal);
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Detalle

CREATE PROCEDURE InsertDetalle(
    IN p_ID_factura INT,
    IN p_ID_producto VARCHAR(6),
    IN p_unidades INT,

    IN p_detalle_adic VARCHAR(100)
)
BEGIN
DECLARE precio_unitario FLOAT;
DECLARE total_produ FLOAT;
if p_ID_factura=(select id_factura from detalle where p_ID_factura=id_factura and p_ID_producto=id_producto) and p_ID_producto=(select id_producto from detalle where p_ID_factura=id_factura and p_ID_producto=id_producto) then
	signal sqlstate '08211' set message_text='Detalle ya existente';
	rollback;
elseif p_ID_factura <>all(select id from factura) or p_ID_producto<>all(select id from producto)then
	signal sqlstate '04020' set message_text ='ID inexistente';
    rollback;
else

    -- Obtiene el precio unitario del producto
        SELECT precioUnitario 
        INTO precio_unitario 
        FROM Producto 
        WHERE id = p_ID_producto;

        -- Calcula el totalProdu
        SET total_produ = p_unidades * precio_unitario;

        -- Inserta el nuevo detalle
        INSERT INTO Detalle (ID_factura, ID_producto, unidades, totalProdu, detalle_adic)
        VALUES (p_ID_factura, p_ID_producto, p_unidades, total_produ, p_detalle_adic);

        COMMIT;
end if;
END //
-- Procedimientos almacenados para la tabla Reclamacion
CREATE PROCEDURE InsertReclamacion(
    IN p_ID_secretaria CHAR(10),
    IN p_ID_cliente VARCHAR(13),
    IN p_descripcion VARCHAR(100)
)
BEGIN
if p_ID_secretaria <> all(select id from secretaria) or p_ID_cliente <>all(select cedula from cliente) then
	signal sqlstate '04020' set message_text ='ID inexistente';
	rollback;
else
    INSERT INTO Reclamacion (ID_secretaria, ID_cliente, descripcion)
    VALUES (p_ID_secretaria, p_ID_cliente, p_descripcion);
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Proveedor
CREATE PROCEDURE InsertProveedor(
    IN p_cedula CHAR(10),
    IN p_nombre VARCHAR(30),
    IN p_telefono INT
)
BEGIN
if p_cedula =any(select cedula from proveedor)then
	signal sqlstate '09220' set message_text='Cedula de proveedor ya existente';
    rollback;
else
    INSERT INTO Proveedor (cedula, nombre, telefono)
    VALUES (p_cedula, p_nombre, p_telefono);
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Lote_madera
CREATE PROCEDURE InsertLoteMadera(
    IN p_id_proveedor CHAR(10),
    IN p_id_secretaria CHAR(10),
    IN p_precio FLOAT,
    IN p_fecha_llegada DATE
)
BEGIN
if p_id_proveedor <> all(select cedula from proveedor) or p_id_secretaria<>all(select id from secretaria)then
	signal sqlstate '04020' set message_text ='ID inexistente';
    rollback;
else
    INSERT INTO Lote_madera (id_proveedor, id_secretaria, precio, fecha_llegada)
    VALUES (p_id_proveedor, p_id_secretaria, p_precio, p_fecha_llegada);
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Evaluacion
CREATE PROCEDURE InsertEvaluacion(
    IN p_id_proveedor CHAR(10),
    IN p_calidad VARCHAR(10),
    IN p_puntualidad VARCHAR(15),
    IN p_detalle VARCHAR(100)
)
BEGIN
    -- Verificar si el ID del proveedor existe en la tabla proveedor
    IF NOT EXISTS (SELECT 1 FROM proveedor WHERE cedula = p_id_proveedor) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'ID inexistente';
        -- No es necesario un ROLLBACK aquí ya que el INSERT no ha ocurrido aún.
    ELSE
        INSERT INTO Evaluacion (id_proveedor, calidad, puntualidad, detalle)
        VALUES (p_id_proveedor, p_calidad, p_puntualidad, p_detalle);
        COMMIT;
    END IF;
END //

-- Procedimientos almacenados para la tabla Tipo_de_madera
CREATE PROCEDURE InsertTipoDeMadera(
    IN p_id VARCHAR(6),
    IN p_nombre VARCHAR(30),
    IN p_precio_unitario FLOAT,
    IN p_condic_ambiental VARCHAR(20)
)
BEGIN
if p_id =any(select id from tipo_de_madera) then
	signal sqlstate '06229' set message_text='ID de la madera ya existente';
    rollback;
else
    INSERT INTO Tipo_de_madera (id, nombre, precio_unitario, condic_ambiental)
    VALUES (p_id, p_nombre, p_precio_unitario, p_condic_ambiental);
end if;
END //

-- Procedimientos almacenados para la tabla Especificacion
CREATE PROCEDURE InsertEspecificacion(
    IN p_id_lote INT,
    IN p_id_madera VARCHAR(6),
    IN p_importe INT,
    IN p_cantidad INT
)
BEGIN
 DECLARE importeNew FLOAT;

    -- Verifica si la especificación ya existe
    IF EXISTS (
        SELECT 1 
        FROM especificacion 
        WHERE id_lote = p_id_lote 
          AND id_madera = p_id_madera
    ) THEN
        SIGNAL SQLSTATE '05402' 
            SET MESSAGE_TEXT = 'Especificacion ya existente';

    -- Verifica si el lote y tipo de madera existen
    ELSEIF NOT EXISTS (
        SELECT 1 
        FROM lote_madera 
        WHERE id = p_id_lote
    ) OR NOT EXISTS (
        SELECT 1 
        FROM tipo_de_madera 
        WHERE id = p_id_madera
    ) THEN
        SIGNAL SQLSTATE '04020' 
            SET MESSAGE_TEXT = 'ID inexistente';

    ELSE
        -- Calcula el importe
        SELECT precio_unitario * p_cantidad 
        INTO importeNew
        FROM tipo_de_madera
        WHERE id = p_id_madera;

        -- Inserta la nueva especificación
        INSERT INTO especificacion (id_lote, id_madera, importe, cantidad)
        VALUES (p_id_lote, p_id_madera, importeNew, p_cantidad);
    END IF;
END //

-- Procedimientos almacenados para la tabla Operario
CREATE PROCEDURE InsertOperario(
    IN p_ID CHAR(10),
    IN p_nombre VARCHAR(40),
    IN p_horaInicio TIME,
    IN p_horaFin TIME,
    IN p_fechaCapacitacion DATE,
    IN p_tipoCapacitacion VARCHAR(20)
)
BEGIN
if p_ID =any(select id from operario) or p_ID = any(select id from empleado) then
	signal sqlstate '07301' set message_text='Operario ya existente';
    rollback;
else
    INSERT INTO Operario (ID)
    VALUES (p_ID);
    INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
    VALUES (p_ID, p_nombre, p_horaInicio, p_horaFin, p_fechaCapacitacion, p_tipoCapacitacion);
end if;
END //

-- Procedimientos almacenados para la tabla Asistente_operario
CREATE PROCEDURE InsertAsistenteOperario(
    IN p_ID CHAR(10),
    IN p_nombre VARCHAR(40),
    IN p_horaInicio TIME,
    IN p_horaFin TIME,
    IN p_fechaCapacitacion DATE,
    IN p_tipoCapacitacion VARCHAR(20)
)
BEGIN
if p_ID =any(select id from Asistente_operario) or p_ID=any(select id from Empleado)then
	signal sqlstate '07302' set message_text='Asistente ya existente';
    rollback;
else
	INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
    VALUES (p_ID, p_nombre, p_horaInicio, p_horaFin, p_fechaCapacitacion, p_tipoCapacitacion);
	INSERT INTO Asistente_operario (ID)
    VALUES (p_ID);
    
    commit;
end if;
END //
-- Procedimientos almacenados para la tabla Maquinaria
CREATE PROCEDURE InsertMaquinaria(
    IN p_codigo INT,
    IN p_nombre VARCHAR(50),
    IN p_marca VARCHAR(20),
    IN p_fecha_adqui DATE
)
BEGIN
if p_codigo =any(select codigo from Maquinaria) then
	signal sqlstate '07303' set message_text='Maquinaria ya existente';
    rollback;
else
    INSERT INTO Maquinaria (codigo, nombre, marca, fecha_adqui)
    VALUES (p_codigo, p_nombre, p_marca, p_fecha_adqui);
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Mantenimiento
CREATE PROCEDURE InsertMantenimiento(
    IN p_ID_operario CHAR(10),
    IN p_codigo_maquinaria INT,
    IN p_ID_secretaria CHAR(10),
    IN p_detalles VARCHAR(100),
    IN p_fecha DATE
)
BEGIN
if p_ID_operario <>all(select id from operario) or p_codigo_maquinaria<>all(select codigo from Maquinaria) or p_ID_secretaria <>all(select id from secretaria)then
	signal sqlstate '04020' set message_text ='ID inexistente';
	rollback;
else
    INSERT INTO Mantenimiento (ID_operario, codigo_maquinaria, ID_secretaria, detalles, fecha)
    VALUES (p_ID_operario, p_codigo_maquinaria, p_ID_secretaria, p_detalles, p_fecha);
end if;
END //

-- Procedimientos almacenados para la tabla Limpieza
CREATE PROCEDURE InsertLimpieza(
    IN p_lugar VARCHAR(30)
)
BEGIN
    INSERT INTO Limpieza (lugar)
    VALUES (p_lugar);
    commit;
END //

-- Procedimientos almacenados para la tabla Registro
CREATE PROCEDURE InsertRegistro(
    IN p_ID_asistente CHAR(10),
    IN p_ID_limpieza INT,
    IN p_ID_secretaria CHAR(10),
    IN p_fecha DATE
)
BEGIN
if p_ID_asistente=(select id_asistente from registro where id_asistente=p_ID_asistente and id_limpieza=p_ID_limpieza and id_secretaria=p_ID_secretaria)
and p_ID_limpieza=(select id_limpieza from registro where id_asistente=p_ID_asistente and id_limpieza=p_ID_limpieza and id_secretaria=p_ID_secretaria)
and p_ID_secretaria=(select id_secretaria from registro where id_asistente=p_ID_asistente and id_limpieza=p_ID_limpieza and id_secretaria=p_ID_secretaria)then
	signal sqlstate '07110' set message_text='Registro ya existente';
    rollback;
elseif p_ID_asistente<>all(select id from asistente_operario) or p_ID_limpieza<>all(select id from limpieza) or p_ID_secretaria <> all(select id from secretaria)then
	signal sqlstate '04020' set message_text ='ID inexistente';
	rollback;
else
    INSERT INTO Registro (ID_asistente, ID_limpieza, ID_secretaria, fecha)
    VALUES (p_ID_asistente, p_ID_limpieza, p_ID_secretaria, p_fecha);
    commit;
end if;
END //

-- Procedimientos almacenados para la tabla Empleado
CREATE PROCEDURE InsertEmpleado(
    IN p_ID CHAR(10),
    IN p_nombre VARCHAR(100),
    IN p_horaInicio TIME,
    IN p_horaFin TIME,
    IN p_fechaCapacitacion DATE,
    IN p_tipoCapacitacion VARCHAR(50)
)
BEGIN
    INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
    VALUES (p_ID, p_nombre, p_horaInicio, p_horaFin, p_fechaCapacitacion, p_tipoCapacitacion);
END //

-- Procedimientos almacenados para la tabla Rol_de_pagos
CREATE PROCEDURE InsertRolDePagos(
    IN p_ID_empleado CHAR(10),
    IN p_rol ENUM('A', 'S', 'O'),
    IN p_dias_laborados INT,
    IN p_sueldo FLOAT,
    IN p_horas_extras FLOAT,
    IN p_total_ingresos FLOAT,
    IN p_egreso_IESS FLOAT,
    IN p_anticipos FLOAT,
    IN p_total_egresos FLOAT,
    IN p_liquido_a_recibir FLOAT
)
BEGIN
if p_ID_empleado <>all(select id from empleado) then
	signal sqlstate '04020' set message_text ='ID inexistente';
	rollback;
elseif p_rol <>all(select distinct rol from rol_de_pagos) then
	signal sqlstate '07396' set message_text='Rol no válido';
    rollback;
else
    INSERT INTO Rol_de_pagos (ID_empleado, rol, dias_laborados, sueldo, horas_extras, total_ingresos, egreso_IESS, anticipos, total_egresos, liquido_a_recibir)
    VALUES (p_ID_empleado, p_rol, p_dias_laborados, p_sueldo, p_horas_extras, p_total_ingresos, p_egreso_IESS, p_anticipos, p_total_egresos, p_liquido_a_recibir);
    commit;
end if;
END //

-- triggers de insercion
delimiter /
create trigger trgNuevoTotal after insert on Detalle
for each row begin
	update Factura set ValorTotal= ValorTotal+new.totalProdu,subtotal_sin_impuestos=subtotal_sin_impuestos+new.totalProdu
    where ID=new.id_factura;
end /
create trigger trgNuevoPrecio after insert on Especificacion
for each row begin
	update Lote_madera set precio=precio+new.importe
    where id=new.id_lote;
end /
DELIMITER ;
-- reportes
-- Reporte mensual de compras
create view ReporteCompras as 
select p.nombre,l.fecha_llegada,t.nombre as name,e.importe,e.cantidad FROM Proveedor p JOIN
Lote_madera l ON p.cedula=l.ID_proveedor join Especificacion e on e.id_lote=l.id join Tipo_de_madera t on t.id=e.ID_madera
WHERE l.fecha_llegada BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') AND LAST_DAY(NOW())
order by l.fecha_llegada;
select * from ReporteCompras;
-- Reporte de ventas por dia
create view ReporteVentasDia as 
select p.nombre,d.unidades,sum(d.totalProdu) from
Producto p join Detalle d on p.id=d.id_producto join Factura f on f.id=d.id_factura
where f.fecha=now()
group by p.nombre,d.unidades;
select * from ReporteVentasDia;
-- Reporte mensual de mantenimiento
create view ReporteMensualDeMantenimiento as 
select e.nombre,m.detalles,m.fecha,ma.nombre as maquinaria from empleado e join operario o using(id)
join mantenimiento m on m.id_operario=o.id join maquinaria ma on ma.codigo=m.codigo_maquinaria
WHERE m.fecha BETWEEN DATE_FORMAT(NOW(), '%Y-%m-01') AND LAST_DAY(NOW())
;
select * from ReporteMensualDeMantenimiento;
-- Reporte de limpieza diaria del area de corte
create view AreaDeCorteLimpiezaDiaria as 
select e.nombre
FROM empleado e JOIN asistente_operario a using(id) JOIN registro r ON a.id = r.id_asistente 
JOIN limpieza l ON r.id_limpieza = l.id
WHERE l.lugar = 'Área de Corte' AND r.fecha = now();

-- Script de creación y permisos de usuario
-- usuario 1, dueño del negocio
CREATE USER 'dueño'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON *. * TO 'dueño'@'project-sbd.mysql.database.azure.com' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.*  TO 'proyectoAserrio'@'127.0.0.1:3306' WITH GRANT OPTION;

-- usuario 2, secretaria 1 del negocio
CREATE USER 'secret1'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '1111';
GRANT ALL PRIVILEGES ON aserriodatapro.* TO 'secret1'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarroldepago FROM 'secret1'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret1'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.InsertRolDePagos FROM 'secret1'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret1'@'project-sbd.mysql.database.azure.com';

-- usuario 3, secretaria 2 del negocio
CREATE USER 'secret2'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarroldepago FROM 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.InsertRolDePagos FROM 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT ON aserriodatapro.Reportecompras FROM 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT ON aserriodatapro.Reporteventasdia FROM 'secret2'@'project-sbd.mysql.database.azure.com';
-- permiso a vistas
GRANT SELECT ON  aserriodatapro.Reportecompras to 'secret2'@'project-sbd.mysql.database.azure.com';
GRANT SELECT ON aserriodatapro.Reporteventasdia to 'secret2'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret2'@'project-sbd.mysql.database.azure.com';

-- usuario 4, secretaria 3 del negocio
CREATE USER 'secret3'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarroldepago FROM 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarMaquinaria FROM 'secret3'@'project-sbd.mysql.database.azure.com';
-- permiso a 1 sp

GRANT EXECUTE ON PROCEDURE aserriodatapro.eliminarMaquinaria to 'secret3'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret3'@'project-sbd.mysql.database.azure.com';

-- usuario 5, secretaria 4 del negocio
CREATE USER 'secret4'@'project-sbd.mysql.database.azure.com' IDENTIFIED BY '2222';
GRANT INSERT, UPDATE ON aserriodatapro.* TO 'secret4'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.eliminarroldepago FROM 'secret4'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.actualizarRolDePago FROM 'secret4'@'project-sbd.mysql.database.azure.com';
REVOKE EXECUTE ON PROCEDURE aserriodatapro.InsertRolDePagos FROM 'secret4'@'project-sbd.mysql.database.azure.com';
REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret4'@'project-sbd.mysql.database.azure.com';

REVOKE SELECT, INSERT, UPDATE, DELETE ON aserriodatapro.rol_de_pagos FROM 'secret4'@'project-sbd.mysql.database.azure.com';
-- indices
-- Índice para la tabla Factura en el campo fecha
CREATE INDEX idx_factura_fecha ON Factura (fecha);

-- Índice para la tabla Lote_madera en el campo fecha_llegada
CREATE INDEX idx_lote_madera_fecha_llegada ON Lote_madera (fecha_llegada);

-- Índice para la tabla Mantenimiento en el campo fecha
CREATE INDEX idx_mantenimiento_fecha ON Mantenimiento (fecha);

-- Índice para la tabla Registro en el campo fecha
CREATE INDEX idx_registro_fecha ON Registro (fecha);

-- Índice para la tabla Limpieza en el campo lugar
CREATE INDEX idx_limpieza_lugar ON Limpieza (lugar);
