/*delimiter /
-- procedures de actualizar 
-- actualizar precio de total del producto al cambiar las unidades (detalle)
create procedure ActualizarPrecioTotal(in idProducto varchar(5),in unidades_nuevas int)
begin
	declare precio_unitario float; 
	set precio_unitario= (select precioUnitario from producto where id=idProducto);
	if idProducto <> all(select id from Producto) then
		signal sqlstate '04001' set message_text = 'ID producto no existente';
	elseif unidades_nuevas <0 then
		signal sqlstate '04002' set message_text ='Unidades negativas no validas';
	else
		update detalle set totalProdu = unidades_nuevas *(precio_unitario) where id_producto=idProducto;
	end if;
end /
-- actualizar la calidad de un producto (producto)
create procedure ActualizarCalidad(in idProducto varchar(5),in nueva_calidad varchar(10))
begin
	if idProducto <> all(select id from Producto) then
		signal sqlstate '04001' set message_text = 'ID producto no existente';
	else
		update producto set calidad = nueva_calidad where id=idProducto;
	end if; 
end /
-- actualizar metodo de pago de la factura (Factura)
create procedure ActualizarMetodoDePago(in idFactura int,in metodoPago enum('Efectivo','Transferencia'))
begin
	if metodoPago != 'Efectivo' or metodoPago != 'Transferencia' then 
		signal sqlstate '04100' set message_text ='Metodo de pago invalido';
	elseif idFactura <> all(select id from Factura) then
		signal sqlstate '04200' set message_text='ID de factura inexistente';
	else
		update Factura set metodo_pago=metodoPago where id=idFactura;
	end if;
end /
-- Actualizar la dirección del cliente (cliente)
create procedure ActualizarDireccion(in cedulaCliente varchar(13),in direccionNueva varchar(50))
begin
	if cedulaCliente <> all(select cedula from Cliente)then
		signal sqlstate '04002' set message_text ='Cédula no se encuentra en la base de datos';
	else
		update Cliente set direccion = direccionNueva where cedula =cedulaCliente;
    end if;
end /
-- Actualizar la descripción de la reclamación de un cliente en específico (reclamación)
create procedure ActualizarReclamacion (in cedulaCliente varchar(13),in descripcion_nueva varchar(100))
begin
	if cedulaCliente <> all(select id_cliente from reclamacion)then
		signal sqlstate '04003' set message_text ='Cédula no se encuentra con una tupla para reclamaciones';
	else 
		update Reclamacion set descripcion=descripcion_nueva where id_cliente=cedulaCliente;
   end if;     
end /
-- Actualizar hora de fin de jornada laboral de una secretaria (secretaria)
create procedure ActualizarHoraFin (in idSecretaria char(10),in nueva_hora_fin time)
begin
	if idSecretaria <> all (select id from secretaria) or idSecretaria <> all (select id from empleado) then 
		signal sqlstate '04004' set message_text ='ID de secretaria no encontrado';
	else 
		update empleado set horaFin=nueva_hora_fin where id=idSecretaria;
    end if;
end /
-- Actualizar el sueldo de un empleado en específico (Rol_de_pagos)
create procedure ActualizarSueldo (in idEmpleado char(10),in nuevo_sueldo float) 
begin
	if (idEmpleado <> all(select id from empleado)) then
		signal sqlstate '04005' set message_text = 'Empleado no identificado en la base de datos';
	else 
		update Rol_de_pagos set sueldo=nuevo_sueldo where id_empleado=idEmpleado;
	end if;
end /
-- Actualizar hora de inicio de un operario (operario)
create procedure ActualizarHoraInicio (in idOperario char(10),in nueva_hora_inicio time)
begin
	if idOperario <> all (select id from operario)  or idOperario <> all (select id from empleado) then 
		signal sqlstate '04006' set message_text ='ID de operario no encontrado';
	else 
		update empleado set horaInicio=nueva_hora_inicio where id=idOperario;
    end if;
end /
-- Actualizar la fecha de capacitación de un asistente de operario (asistente_operario)
create procedure ActualizarFechaCapacitacion (in idAsistente char(10), in nueva_fecha date)
begin
	if idAsistente <> all (select id from asistente_operario) or idAsistente <> all (select id from empleado) then
		signal sqlstate '04007' set message_text = 'Asistente no encontrado en la base de datos';
	else
		update empleado set fechaCapacitacion= nueva_fecha where id= idAsistente;
	end if;
end /
-- Actualizar la fecha del registro de una limpieza en específico (registro) 
create procedure ActualizarFechaRegistro(in idLimpieza int,in fechaNueva date)
begin
	if idLmpieza <> all(select id_limpieza from registro) then
		signal sqlstate '04008' set message_text ='Limpieza no encontrada en la base de datos';
    else
		update registro set fecha=fechaNueva where id_limpieza = idLimpieza;
    end if; 
end /
-- actualizar el lugar de la limpieza (Limpieza)
create procedure ActualizarLugar(in idLimpieza int,in lugarNuevo varchar(30))
begin
	if idLmpieza <> all(select id from Limpieza) then
		signal sqlstate '04008' set message_text ='Limpieza no encontrada en la base de datos';
	else
		update Limpieza set lugar =lugarNuevo where id=idLimpieza;
	end if;
end /
-- actualizar los detalles del mantenimiento (mantenimiento)
create procedure ActualizarDetallesMantenimiento (in idMantenimiento int, in detallesNuevos varchar(100))
begin
	if idMantenimiento <> all(select id from mantenimiento) then
		signal sqlstate '04009' set message_text= 'ID de mantenimiento no existente';
	else
		update Mantenimiento set detalles=detallesNuevos where id=idMantenimiento;
	end if;
end /
-- actualizar la marca de una maquinaria (maquinaria)
create procedure ActualizarMarca(in codigoMaquinaria int, in marcaNueva varchar(20))
begin
	if codigoMaquinaria <> all(select codigo from maquinaria) then
		signal sqlstate '04010' set message_text = 'Código de msquinaria no existente';
	else
		update Maquinaria set marca= marcaNueva where codigo =codigoMaquinaria;
	end if;
end /
-- actualizar la fecha de llegada de un lote de madera (lote_madera)
create procedure ActualizarLlegada(in idLote int, in NuevaFechaLlegada date)
begin
	if idLote <> all(select id from lote_madera) then
		signal sqlstate '04011' set message_text = 'ID del lote no encontrado';
	else 
		update lote_madera set fecha_llegada=NuevaFechaLlegada where id=idLote;
	end if;
end / 
-- actualizar el número de teléfono de un proveedor (proveedor)
create procedure ActualizarTelefono(in idProveedor char(10),in telefonoNuevo int)
begin
	if idProveedor <> all(select cedula from proveedor) then
		signal sqlstate '04012' set message_text= 'Proveedor no existente';
	else 
		update proveedor set telefono=telefonoNuevo where cedula=idProveedor;
	end if;
end /
-- actualizar los detalles de una evaluación(evaluacion)
create procedure ActualizarDetallesEvaluacion (in idEvaluacion int, in detallesNuevos varchar(100))
begin
	if idEvaluacion <> all(select id from evaluacion) then
		signal sqlstate '04013' set message_text= 'ID de la evaluación no existe';
	else
		update Evaluacion set detalle=detallesNuevos where id=idEvaluacion;
	end if;
end /
-- actualizar la cantidad de una especificación de un lote en específico
create procedure ActualizarCantidad(in idLote int, in cantidadNueva int)
begin
	if idLote <> all(select id_lote from especificacion) then
		signal sqlstate '04014' set message_text= 'Lote sin especificación';
	else
		update Especificacion set cantidad=cantidadNueva where id_lote=idLote;
	end if;
end /
-- actualizar el precio unitario de una madera (tipo_de_madera)
create procedure ActualizarPrecioUnitario(in idMadera varchar(6), in precioUnitario float)
begin
	if idMadera <> all(select id from tipo_de_madera) then
		signal sqlstate '04015' set message_text= 'ID de madera no existente';
	else
		update Tipo_de_madera set precio_unitario=precioUnitario where id=idMadera;
	end if;
end /
*/
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
create procedure EliminarOperario (in idOperario char(10),in eliminar boolean)
begin if idOperario <> all(select id from Operario) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar = true then
        delete from Operario where id=idOperario;
        delete from empleado where id=idOperario;
		delete from Rol_de_pagos where id_empleado='Esta vacio';
        delete from Mantenimiento where id_operario='Esta vacio';
        commit;
    else
		delete from Operario where id=idOperario;
        delete from empleado where id=idOperario;
        commit;
	end if; 
end /
create trigger trgBorrarOperario before delete on Operario
for each row
begin
	update Rol_de_Pagos set id_empleado='Esta vacio' where id_empleado=old.id;
    update Mantenimiento set id_operario ='Esta vacio' where id_operario=old.id;
end / 
-- eliminar asistente
create procedure EliminarAsistente (in idAsistente char(10),in eliminar boolean)
begin if idAsistente <> all(select id from Asistente_operario) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar=true then
		delete from Asistente_operario where id=idAsistente;
        delete from empleado where id=idAsistente;
		delete from Registro where id_asistente='Esta vacio';
		delete from Rol_de_pagos where id_empleado='Esta vacio';
        commit;
	else
		delete from Asistente_operario where id=idAsistente;
        delete from empleado where id=idAsistente;
        commit;
	end if; 
end /
create trigger trgBorrarAsistente before delete on Asistente_operario
for each row
begin
	update Rol_de_pagos set id_empleado= 'Esta vacio' where ID_empleado=old.id;
    update registro set id_asistente = 'Esta vacio' where id_asistente=old.id;
end / 
-- eliminar secretaria
create procedure EliminarSecretaria (in idSecretaria char(10),in eliminar boolean)
begin if idSecretaria <> all(select id from Secretaria) or idSecretaria<> all(select id from Empleado) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar =true then
		delete from Secretaria where id=idSecretaria;
        delete from empleado where id=idSecretaria;
		delete from Rol_de_pagos where id_empleado='Esta vacio';
		delete from reclamacion where id_secretaria ='Esta vacio';
		delete from factura where id_secretaria='Esta vacio';
		delete from lote_madera where id_secretaria='Esta vacio';
		delete from mantenimiento where id_secretaria='Esta vacio';
		delete from registro where id_secretaria='Esta vacio';
        commit;
	else
		delete from secretaria where id=idSecretaria;
		delete from empleado where id=idSecretaria;
        commit;
	end if; 
end /
create trigger trgBorrarSecretaria before delete on Secretaria
for each row
begin
	update Rol_de_pagos set id_empleado='Esta vacio' where ID_empleado=old.id;
    update reclamacion set id_secretaria = 'Esta vacio' where id_secretaria=old.id;
    update factura set id_secretaria ='Esta vacio' where id_secretaria=old.id;
    update lote_madera set id_secretaria='Esta vacio' where id_secretaria=old.id;
    update mantenimiento set id_secretaria='Esta vacio' where id_secretaria=old.id;
    update registro set id_secretaria='Esta vacio' where id_secretaria=old.id;
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
create procedure EliminarMaquinaria (in idMaquinaria int,in eliminar boolean)
begin if idMaquinaria <> all(select codigo from Maquinaria) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar=true then
		delete from Maquinaria where idMaquinaria=codigo;
		delete from Mantenimiento where codigo_maquinaria is null;
        commit;
	else
        delete from Maquinaria where idMaquinaria=codigo;
        commit;
	end if; 
end /
create trigger trgBorrarMaquinaria before delete on Maquinaria
for each row
begin
	update Mantenimiento set codigo_maquinaria=null where codigo_maquinaria=old.codigo;
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
create procedure EliminarLimpieza (in idLimpieza int,in eliminar boolean)
begin if idLimpieza <> all(select id from Limpieza) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar=true then
		delete from Limpieza where idLimpieza=id;
		delete from Registro where id_limpieza=999999;
        commit;
	else
        delete from Limpieza where idLimpieza=id;
        commit;
	end if; 
end /
create trigger trgBorrarLimpieza before delete on Limpieza
for each row
begin
	update Registro set id_limpieza=999999 where id_limpieza=old.id;
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
create procedure EliminarCliente (in idCliente varchar(13),in eliminar boolean)
begin if idCliente <> all(select cedula from Cliente) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar=true then
		delete from Cliente where idCliente=cedula;
		delete from Reclamacion where id_cliente ='Esta vacio';
        commit;
	else
        delete from Cliente where idCliente=cedula;
        commit;
	end if; 
end /
create trigger trgBorrarCliente before delete on Cliente
for each row
begin
	update factura set id_cliente='0000000000000' where ID_cliente=old.cedula;
    update reclamacion set id_cliente = 'Esta vacio' where id_cliente=old.cedula;
end / 
-- Eliminar factura 
create procedure EliminarFactura (in idFactura int,in eliminar boolean)
begin if idFactura <> all(select id from Factura) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar = true then
        delete from Factura where idFactura=id;
		delete from Detalle where id_factura = 999999;
        commit;
    else
		delete from Factura where id=idFactura;
        commit;
	end if; 
end /
create trigger trgBorrarFactura before delete on Factura
for each row
begin
	update Detalle set id_factura=999999 where id_factura=old.id;
end / 
-- eliminar detalle
create procedure EliminarDetalle (in idFactura int,in idProducto varchar(6))
begin if idFactura <> all(select id_factura from Detalle) or idProducto <> all(select id_producto from Detalle) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	else
        delete from Factura where idFactura=id_factura and idProducto=id_producto;
        commit;
	end if; 
end /
-- eliminar producto
create procedure EliminarProducto (in idProducto varchar(6),in eliminar boolean)
begin if idProducto <> all(select id from producto) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar = true then
        delete from Producto where idProducto=id;
		delete from Detalle where id_Producto = 'Vacio';
        commit;
    else
		delete from Producto where id=idProducto;
        commit;
	end if; 
end /
create trigger trgBorrarProducto before delete on Producto
for each row
begin
	update Detalle set id_producto='Vacio' where id_producto=old.id;
end / 
-- eliminar lote de madera
create procedure Eliminarlote (in idLote int,in eliminar boolean)
begin if idLote <> all(select id from Lote_madera) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar = true then
        delete from Lote_madera where idLote=id;
		delete from Especificacion where id_lote = 999999;
        commit;
    else
		delete from Lote_madera where idLote=id;
        commit;
	end if; 
end /
create trigger trgBorrarLote before delete on Lote_madera
for each row
begin
	update Especificacion set id_lote=999999 where id_lote=old.id;
end / 
-- eliminar proveedor
create procedure EliminarProveedor (in idProveedor char(10),in eliminar boolean)
begin if idProveedor <> all(select cedula from Proveedor) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar = true then
        delete from Proveedor where idProveedor=cedula;
		delete from Lote_madera where id_proveedor = 'Esta vacio';
        delete from Evaluacion where id_proveedor ='Esta vacio';
        commit;
    else
		delete from Proveedor where idProveedor=cedula;
        commit;
	end if; 
end /
create trigger trgBorrarProveedor before delete on Proveedor
for each row
begin
	update Lote_madera set id_proveedor='Esta vacio' where id_proveedor=old.cedula;
    update Evaluacion set id_proveedor='Esta vacio' where id_proveedor=old.cedula;
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
begin if idLote <> all(select id_lote from Especificacion) or idMadera <> all(select id_madera from Especificacion) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	else
        delete from Especificacion where idLote=id_lote and idMadera=id_madera;
        commit;
	end if; 
end /
-- eliminar tipo de madera
create procedure EliminarMadera (in idMadera varchar(6),in eliminar boolean)
begin if idMadera <> all(select id from tipo_de_madera) then
		signal sqlstate '04020' set message_text ='ID inexistente';
        rollback;
	elseif eliminar = true then
        delete from tipo_de_madera where id=idMadera;
		delete from Especificacion where id_madera = 'Vacio';
        commit;
    else
		delete from tipo_de_madera where id=idMadera;
        commit;
	end if; 
end /
create trigger trgBorrarMadera before delete on tipo_de_madera
for each row
begin
	update Especificacion set id_madera='Vacio' where id_madera=old.id;
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
		update detalle set unidades=unidades_nuevas,totalProdu = unidades_nuevas *(precio_unitario),detalle_adic=nuevo_detalle_adic 
        where id_producto=idProducto and idFactura=id_factura;
	end if;
END /
create trigger ActualizarTotal after update on Detalle
for each row
begin
	update factura set subtotal_sin_impuestos= new.totalProdu-old.totalProdu+subtotal_sin_impuestos , ValorTotal=new.totalProdu-old.totalProdu+ValorTotal
    where ID=new.ID_factura;
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
create trigger trgActualizarDetalle after update on Producto
for each row
begin
	update Detalle set totalProdu=unidades*(new.precioUnitario) where id_producto=new.id;
end /
-- actualizar factura
create procedure ActualizarFactura(in idFactura int,in NuevaIdSecretaria char(10),in NuevoIdCliente char(10),in NuevaFecha date,in NuevaHora time,in NuevaDireccion varchar(30),in metodoPago enum('Efectivo','Transferencia'),in NuevoSubtotalSinImpuestos float,in Nuevosubtotal_0PorCiento float)
begin
    declare Valor_total float;
    set Valor_total=NuevoSubtotalSinImpuestos -Nuevosubtotal_0PorCiento;
	if metodoPago != 'Efectivo' or metodoPago != 'Transferencia' then 
		signal sqlstate '04100' set message_text ='Metodo de pago invalido';
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
		update Factura set fecha=NuevaFecha,hora=NuevaHora,direccion=NuevaDireccion,metodo_pago=metodoPago,Subtotal_sin_impuestos=NuevoSubtotalSinImpuestos,subtotal_0Porcent=Nuevosubtotal_0PorCiento,ValorTotal=Valor_total where id=idFactura;
        commit;
	end if;
end /
-- Actualizar cliente 
create procedure ActualizarCliente(in cedulaCliente varchar(13),in NuevoNombre varchar(30),in direccionNueva varchar(50),in NuevoNumContacto int,in NuevoCorreo varchar(20))
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
		update Mantenimiento set id_operario=idOperario,codigo_maquinaria=codMaquinaria,id_secretaria=idSecretaria,detalles=NuevosDetalles,fecha=fechaNueva;
        commit;
	end if;
end /
-- actualizar la información del operario
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
create trigger actualizarOperario
before update on operario
for each row
begin
	UPDATE mantenimiento
	SET ID_operario= new.ID
	WHERE ID_operario= old.ID;
	UPDATE rol_de_pagos
	SET ID_operario= new.ID
	WHERE ID_operario= old.ID;
end/
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
		WHERE ID_asistente=idAsist and id_limpieza=idLimpieza and id_secretaria=idSecretaria;
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
	SET ID_asistente= new.ID
	WHERE ID_asistente= old.ID;
	UPDATE rol_de_pagos
	SET ID_empleado= new.ID
	WHERE ID_empleado= old.ID;
end/
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
        SET ID_secretaria= new.ID
        WHERE ID_secretaria= old.ID;
        UPDATE rol_de_pagos
        SET ID_empleado= new.ID
        WHERE ID_empleado= old.ID;
	end if;
end/
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
	if idLote <> all(select id from lote_madera) then
		signal sqlstate '09301' set message_text ='ID del lote no existente';
        rollback;
	elseif idProveedor <> all(select cedula from proveedor)then
		signal sqlstate '09334' set message_text ='Proveedor no existente';
        rollback;
	elseif idsecret <>all(select id from secretaria) then 
		signal sqlstate '08891' set message_text= 'ID de la secretaria no existe';
        rollback;
	else 
		UPDATE lote_madera
		SET id_proveedor=idProveedor, id_secretaria = idsecret,precio=precioNuevo,fecha_llegada=fechaNueva
		WHERE id=idLote;
        commit;
	end if;
end/
-- actualizar una especificación
create procedure ActualizarEspecificacion(in idLote int,in idMadera varchar(6), in cantidadNueva int)
begin
	DECLARE precUnit float;
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
    SELECT precio_unitario INTO precUnit FROM tipo_de_madera WHERE id=idMadera;
    UPDATE especificacion SET importe= cantidadNueva*precUnit,cantidad=cantidadNueva
    WHERE id_madera = idMadera and idLote=id_lote;
    commit;
    end if;
end/
CREATE TRIGGER ActualizarPrecioLote AFTER UPDATE ON especificacion
FOR EACH ROW
BEGIN
	update Lote_madera set precio= precio+new.importe-old.importe 
    where id_lote=new.id_lote;
END/ 
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
CREATE TRIGGER ActualizarImporteEspecificacion
AFTER UPDATE ON tipo_de_madera
FOR EACH ROW
BEGIN
	update Especificacion set importe=new.precio_unitario*cantidad where id_madera=new.id;
END/
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
    IN p_horaFine TIME,
    IN p_fechaCapacitacion DATE,
    IN p_tipoCapacitacion VARCHAR(20)
)
BEGIN
if p_ID=any(select ID from Secretaria) or p_ID = any(select ID from Empleado) then
	signal sqlstate '07444' set message_text='ID de secretaria ya existente';
    rollback;
else
    INSERT INTO Secretaria (ID)
    VALUES (p_ID);
    INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
    VALUES (p_ID, p_nombre, p_horaInicio, p_horaFin, p_fechaCapacitacion, p_tipoCapacitacion);
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
    IN p_totalProdu INT,
    IN p_detalle_adic VARCHAR(100)
)
BEGIN
if p_ID_factura=(select id_factura from detalle where p_ID_factura=id_factura and p_ID_producto=id_producto) and p_ID_producto=(select id_producto from detalle where p_ID_factura=id_factura and p_ID_producto=id_producto) then
	signal sqlstate '08211' set message_text='Detalle ya existente';
	rollback;
elseif p_ID_factura <>all(select id from factura) or p_ID_producto<>all(select id from producto)then
	signal sqlstate '04020' set message_text ='ID inexistente';
    rollback;
else
    INSERT INTO Detalle (ID_factura, ID_producto, unidades, totalProdu, detalle_adic)
    VALUES (p_ID_factura, p_ID_producto, p_unidades, p_totalProdu, p_detalle_adic);
    commit;
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
if p_id_proveedor <>all(select cedula from proveedor) then
	signal sqlstate '04020' set message_text ='ID inexistente';
    rollback;
else
    INSERT INTO Evaluacion (id_proveedor, calidad, puntualidad, detalle)
    VALUES (p_id_proveedor, p_calidad, p_puntualidad, p_detalle);
    commit;
end if;
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
if p_id_lote=(select id_lote from especificacion where id_lote=p_id_lote and id_madera=p_id_madera) and p_id_madera=(select id_madera from especificacion where id_lote=p_id_lote and id_madera=p_id_madera)then
	signal sqlstate '05402' set message_text= 'Especificacion ya existente';
	rollback;
elseif p_id_lote<>any(select id from lote_madera) or p_id_madera <> any(select id from tipo_de_madera)then
	signal sqlstate '04020' set message_text ='ID inexistente';
	rollback;
else
    INSERT INTO Especificacion (id_lote, id_madera, importe, cantidad)
    VALUES (p_id_lote, p_id_madera, p_importe, p_cantidad);
    commit;
end if;
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
	INSERT INTO Asistente_operario (ID)
    VALUES (p_ID);
    INSERT INTO Empleado (ID, nombre, horaInicio, horaFin, fechaCapacitacion, tipoCapacitacion)
    VALUES (p_ID, p_nombre, p_horaInicio, p_horaFin, p_fechaCapacitacion, p_tipoCapacitacion);
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

DELIMITER ;

-- vistas
-- Vista para ver los asistentes encargados de la limpieza de algun lugar
create view AsistenteEncargadoDeLugar as 
select a.nombre,r.fecha,l.lugar FROM empleado e JOIN
Asistente_operario a ON e.ID=a.ID join Registro r on a.id=r.id_asistente join limpieza l on l.id=r.id_limpieza
order by r.fecha;
select * from AsistenteEncargadoDeLugar;
drop view AsistenteEncargadoDeLugar;
-- vista para ver los productos pertenecientes a cada factura 
create view ProductosPorFactura as 
select p.nombre,d.unidades,f.id as factura_id,f.fecha,f.hora from
Producto p join Detalle d on p.id=d.id_producto join Factura f on f.id=d.id_factura;
select * from ProductosPorFactura;
drop view ProductosPorFactura;
-- vista para identificar los lotes con su fecha y los tipos de madera que contiene cada uno
create view EspecificacionLote as 
select t.nombre,e.cantidad,l.id as lote_id,l.fecha_llegada from
Tipo_de_madera t join especificacion e on e.id_madera=t.id join Lote_madera l on l.id=e.id_lote;
select * from EspecificacionLote;
drop view EspecificacionLote;
-- vista para identificar los sueldos y el liquido a recibir
create view SueldosPorEmpleado as 
select a.nombre as empleados,r.rol,r.sueldo,r.liquido_a_recibir from empleado a join rol_de_pagos r on a.id=r.id_empleado;
select * from SueldosPorEmpleado;
drop view SueldosPorEmpleado;


create view Empleados as 
select a.id,a.nombre as empleados,r.rol,a.horaINicio,a.horafin,a.fechacapacitacion,a.tipocapacitacion from empleado a join rol_de_pagos r on a.id=r.id_empleado;
select * from empleados;
drop view Empleados;
