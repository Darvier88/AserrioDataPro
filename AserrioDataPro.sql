CREATE database AserrioDataPro;
USE AserrioDataPro;
/*Tablas de la seccion de ventas*/
CREATE TABLE Cliente(
	cedula varchar(13) PRIMARY KEY NOT NULL ,
    nombre varchar(30) NOT NULL,
    direccion varchar(50) NOT NULL,
    num_contacto int ,
    correo_contacto varchar(20) NOT NULL
);
CREATE TABLE Producto(
	ID varchar(6) PRIMARY KEY NOT NULL,
    nombre varchar(30) NOT NULL,
    precioUnitario float NOT NULL,
    calidad varchar(10) NOT NULL,
    condic_ambiental varchar(20) NOT NULL,
    tiempo_almacenamiento int NOT NULL,
    dimension varchar(20) NOT NULL,
    descripcion varchar(100)
);
CREATE TABLE Factura(
	ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ID_secretaria char(10) NOT NULL,
    ID_cliente varchar(13) NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    direccion_local varchar(30) NOT NULL,
    metodo_pago ENUM('Efectivo','Transferencia') NOT NULL,
    subtotal_sin_impuestos float NOT NULL,
    subtotal_0Porcent float NOT NULL,
    ValorTotal float NOT NULL,
    FOREIGN KEY (ID_cliente) REFERENCES Cliente(cedula)
    /*FOREIGN KEY (ID_secretaria) REFERENCES Secretaria(ID)*/
);
DROP TABLE Factura;
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
	ID int PRIMARY KEY NOT NULL,
    ID_secretaria char(10) NOT NULL,
    ID_cliente char(10) NOT NULL,
    descripcion varchar(100) NOT NULL,
    /*FOREIGN KEY (ID_secretaria) REFERENCES Secretaria(ID),*/
    FOREIGN KEY (ID_cliente) REFERENCES Cliente(cedula)
);
FOREIGN KEY (ID_cliente) REFERENCES Cliente(cedula)
DROP TABLE Reclamacion;
/*Inserciones*/
/*Consumidor final*/
INSERT INTO Cliente (cedula, nombre, direccion,correo_contacto) 
VALUES ('0000000000', 'Consumidor Final', 'Sin direccion', 'sincorreo@ejemplo.com');
/*9 clientes no consumidor final*/