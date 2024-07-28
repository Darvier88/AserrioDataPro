CREATE database AserrioDataPro;
USE AserrioDataPro;
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