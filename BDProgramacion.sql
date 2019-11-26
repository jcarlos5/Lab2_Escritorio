/*
================================================================
--Fecha de Creación	: 02 de Octubre del 2019
--Última moficiación	: 23 de Octubre del 2019
--Autor			: VILCHEZ VILLEGAS, José Carlos
================================================================
*/

-- Database: "BDProgramacion"

-- DROP DATABASE "BDProgramacion";

/*
CREATE DATABASE "BDProgramacion"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Spain.1252'
       LC_CTYPE = 'Spanish_Spain.1252'
       CONNECTION LIMIT = -1;
*/

--CREACIÓN DE LAS TABLAS

CREATE TABLE USUARIO(
codUsuario int not null primary key,
nomUsuario varchar(20) not null,
clave varchar(20) not null,
nombreCompleto varchar(80) not null,
cargo varchar(30) null,
estado boolean not null,
pregunta VARCHAR(50) NULL,
respuesta VARCHAR(50) NULL
);

CREATE TABLE MOVIMIENTO(
numMovimiento serial not null primary key,
codUsuario int not null,
fecha date not null,
hora time not null,
estado Boolean not null
);

CREATE TABLE MARCA(
codMarca int not null primary key,
nomMarca varchar(30) not null,
vigencia BOOLEAN NOT NULL
);

CREATE TABLE CATEGORIA(
codCategoria int not null primary key,
nomCategoria varchar(30) not null,
descripcion varchar(100) null,
vigencia boolean not null
);

CREATE TABLE PRODUCTO(
codProducto int not null primary key,
nomProducto varchar(30) not null,
descripcion varchar(100) not null,
precio decimal (8, 2) not null,
stock int not null,
vigencia boolean not null,
codMarca int not null,
codCategoria int not null
);

CREATE TABLE TIPO_CLIENTE(
codTipo INT NOT NULL PRIMARY KEY,
nombre VARCHAR(30) NOT NULL
);

CREATE TABLE CLIENTE(
codCliente INT NOT NULL PRIMARY KEY,
dni CHAR(8) NULL,
ruc CHAR(11) NULL,
nombres VARCHAR(100) NOT NULL,
telefono VARCHAR(13) NULL,
correo VARCHAR(50) NULL,
direccion VARCHAR(50) NULL,
vigencia BOOLEAN NOT NULL,
codTipo INT NOT NULL
);

CREATE TABLE VENTA(
numVenta INT NOT NULL PRIMARY KEY,
codCliente INT NOT NULL,
fecha DATE NOT NULL,
igv DECIMAL(10, 2) NULL,
subtotal DECIMAL(10, 2) NULL,
total DECIMAL(10, 2) NOT NULL,
tipoComprobante BOOLEAN NOT NULL, --True: Boleta, False: Factura
estadoPago BOOLEAN NOT NULL,
tipoPago BOOLEAN NULL --True: Contado, False: Credito
);

CREATE TABLE DETALLE(
numventa INT NOT NULL,
codproducto INT NOT NULL,
cantidad INT NOT NULL,
precioVenta DECIMAL(8, 2) NOT NULL,
descuento smallint null,
subtotal DECIMAL(10, 2) NOT NULL
);

CREATE TABLE cuota(
codventa INT NOT NULL,
numcuota INT NOT NULL,
fechaprogramada DATE NOT NULL, --En la que se debería pagar
fechapago DATE NULL, --fecha en la que se pagó
cancelada BOOLEAN NOT NULL,
ingreso DECIMAL(10, 2) NULL,
vuelto DECIMAL(10,2) NULL,
monto DECIMAL(10,2) NOT NULL
);

--TABLA DEVOLUCION AGREGADA 23-OCT-2019
CREATE TABLE devolucion(
	numerodev INT NOT NULL PRIMARY KEY,
	fecha DATE NOT NULL,
	motivo VARCHAR(100) NOT NULL,
	montodev DECIMAL(10,2) NOT NULL,
	atendidopor INT NOT NULL
);

--CREACIÓN DE CLAVES FORÁNEAS
ALTER TABLE DEVOLUCION ADD CONSTRAINT FK_USUARIO_DEVOLUCION FOREIGN KEY (atendidopor) REFERENCES USUARIO(CODUSUARIO);
ALTER TABLE PRODUCTO ADD CONSTRAINT FK_MARCA_PRODUCTO FOREIGN KEY (codMarca) REFERENCES MARCA;
ALTER TABLE PRODUCTO ADD CONSTRAINT FK_CATEGORIA_PRODUCTO FOREIGN KEY (codCategoria) REFERENCES CATEGORIA;
ALTER TABLE MOVIMIENTO ADD CONSTRAINT FK_MOV_USU FOREIGN KEY (codUsuario) REFERENCES USUARIO;
ALTER TABLE CLIENTE ADD CONSTRAINT FK_TIPO_CLIENTE_CLIENTE FOREIGN KEY (codTipo) REFERENCES TIPO_CLIENTE;
ALTER TABLE VENTA ADD CONSTRAINT FK_CLIENTE_VENTA FOREIGN KEY (codCliente) REFERENCES CLIENTE;
ALTER TABLE DETALLE ADD CONSTRAINT FK_PRODUCTO_DETALLE FOREIGN KEY (codProducto) REFERENCES PRODUCTO;
ALTER TABLE DETALLE ADD CONSTRAINT FK_VENTA_DETALLE FOREIGN KEY (numVenta) REFERENCES VENTA;
ALTER TABLE DETALLE ADD CONSTRAINT PK_DETALLE PRIMARY KEY (numVenta, codProducto);
ALTER TABLE cuota ADD CONSTRAINT PK_cuota PRIMARY KEY (codventa, numcuota);
ALTER TABLE cuota ADD CONSTRAINT FK_venta_cuota FOREIGN KEY (codventa) REFERENCES venta;

--CREACIÓN DE PROCEDIMIENTOS ALMACENADOS
CREATE OR REPLACE FUNCTION LISTAR_DEUDA(d varchar)RETURNS TABLE(NumVenta int,NumCuota int, Monto decimal(10,2),Fecha date, Estado boolean) AS
$$
DECLARE
BEGIN
	return query
		Select venta.numventa,cuota.numcuota,cuota.monto, cuota.fechaprogramada, cuota.cancelada from (select * from cliente where dni=d or ruc=d) cli
		inner join venta on cli.codcliente=venta.codcliente
		inner join cuota on cuota.codventa=venta.numventa
		where cuota.cancelada=FALSE;
END;
$$language 'plpgsql';

CREATE OR REPLACE FUNCTION DEUDA(d varchar)RETURNS int AS
$$
DECLARE
	c int;
BEGIN
	Select COUNT(*) INTO c FROM (select * from cliente where dni=d or ruc=d) cli
		inner join venta on cli.codcliente=venta.codcliente
		inner join cuota on cuota.codventa=venta.numventa
		where cuota.cancelada=FALSE;

	IF(c>=1)THEN
		RETURN 1;
	else
		RETURN 0;
	END IF;
END;
$$language 'plpgsql';

CREATE OR REPLACE FUNCTION DATOSCLIENTE(d varchar)RETURNS TABLE(numVenta int, nombres varchar) AS
$$
DECLARE
BEGIN
	return query
		Select venta.numventa,cli.nombres from (select * from cliente where dni=d or ruc=d) cli
		inner join venta on cli.codcliente=venta.codcliente
		inner join cuota on cuota.codventa=venta.numventa
		where cuota.cancelada=FALSE;

END;
$$language 'plpgsql';

CREATE OR REPLACE FUNCTION VENTACREDITO(id varchar)RETURNS TABLE (numVenta int, codcliente int, fecha date, igv decimal(10,2), subtotal decimal(10,2), total decimal(10,2), tipoComprobante boolean, estadoPago boolean) AS
$$
DECLARE
	c int;
	i int=0;
	c_venta cursor for Select v.numventa from venta v inner join cliente c on v.codCliente=c.codcliente where c.dni=id or c.ruc=id;
BEGIN
	open c_venta;
	fetch c_venta into c;
	while found loop
		select count(numcuota) into i from cuota cu inner join venta v on cu.codventa=v.numventa where v.numventa=c;
		IF i>1 THEN
			return query select * from venta ve where ve.numventa=c;
		end if;
		fetch c_venta into c;
	end loop;
	close c_venta;
END;
$$ language 'plpgsql';

--INSERCIÓN DE EJEMPLOS

INSERT INTO USUARIO VALUES(1, 'admin', '123456', 'Juan Perez Perez', 'Gerente General', TRUE, 'Ciudad de Nacimiento', 'Lima');
INSERT INTO USUARIO VALUES(2, 'invitado', 'usat2019', 'Maria Mendoza', 'supervisor', FALSE, 'Primer número celular', '979105594');
INSERT INTO USUARIO VALUES(3, 'venta', 'chiclayo', 'Pedro Casas Merino', 'Jefe de ventas', TRUE, 'Nombre de tu mascota', 'Boby');

INSERT INTO MARCA VALUES (1, 'EPSON', TRUE);
INSERT INTO MARCA VALUES (2, 'SAMSUNG', TRUE);
INSERT INTO MARCA VALUES (3, 'LG', TRUE);
INSERT INTO MARCA VALUES (4, 'HUAWEI', TRUE);
INSERT INTO MARCA VALUES (5, 'IBM', FALSE);
INSERT INTO MARCA VALUES (6, 'HP', TRUE);

INSERT INTO CATEGORIA VALUES (1, 'IMPRESORAS', 'Impresoras Multifuncionales', TRUE);
INSERT INTO CATEGORIA VALUES (2, 'CELULARES', 'Smarphones', TRUE);
INSERT INTO CATEGORIA VALUES (3, 'COMPUTO', 'Ordenadores y partes', TRUE);
INSERT INTO CATEGORIA VALUES (4, 'LAPTOPS', 'Gamers y Normal', TRUE);

INSERT INTO PRODUCTO VALUES (1, 'EPSON LX570', 'Color: Negro', 900.00, 50, TRUE, 1, 1);
INSERT INTO PRODUCTO VALUES (2, 'Laptop HP 2459', 'Color: Negro', 5400.00, 10, TRUE, 6, 4);
INSERT INTO PRODUCTO VALUES (3, 'Laptop SM-59711', 'Color: Blanco', 3000.00, 20, TRUE, 2, 4);
INSERT INTO PRODUCTO VALUES (4, 'Monitor LG UHD', 'Color: Negro', 500.00, 60, TRUE, 3, 3);
INSERT INTO PRODUCTO VALUES (5, 'Monitor IBM 2400', 'Color: Gris', 500.00, 75, TRUE, 5, 3);
INSERT INTO PRODUCTO VALUES (6, 'HUAWEI P30', 'Color: Azul, Rosa y Rojo', 3200.00, 70, TRUE, 4, 2);

INSERT INTO TIPO_CLIENTE VALUES(1, 'Persona Natural');
INSERT INTO TIPO_CLIENTE VALUES(2, 'Persona Jurídica');
INSERT INTO TIPO_CLIENTE VALUES(3, 'Persona Natural con RUC');

INSERT INTO CLIENTE VALUES(1, '76594288', null, 'Juan Perez Piña', '953406872', 'Chiclayo', 'jperez@usat.edu.pe', TRUE, 1);
INSERT INTO CLIENTE VALUES(2, '72120524', null, 'Franco Escamilla', '974763843', 'Chiclayo', 'diablo@gmail.com', TRUE, 1);
INSERT INTO CLIENTE VALUES(3, '45331977', '58413649224', 'Pablo Pulido', '961431251', 'Ferreñafe', 'pp@gmail.com', TRUE, 3);
INSERT INTO CLIENTE VALUES(4, '46197244', null, 'Pedro Zelada', '934016823', 'Lambayeque', 'pzelada@hotmail.com', FALSE, 1);
INSERT INTO CLIENTE VALUES(5, null, '75193466519', 'Los Olivares SAC', '953194069', 'Chiclayo', 'olivares.info@gmail.com', TRUE, 2);
INSERT INTO CLIENTE VALUES(6, '71359403', null, 'Julio Jaramillo', '991025349', 'Ferreñafe', 'jjaramillo@gmail.com', TRUE, 1);
INSERT INTO CLIENTE VALUES(7, '79163522', '731526940', 'Gustavo Rios', '920136490', 'Chiclayo', 'riosgustavo@hotmail.com', TRUE, 3);

/* REEMPLAZADO POR UNA TRANSACCIÓN
--TRIGGER PARA ACTUALIZAR VENTA CUANDO SE TERMINEN DE PAGAR LAS CUOTAS
CREATE OR REPLACE FUNCTION actualizarventa() RETURNS TRIGGER AS
$$
DECLARE
	c int;
	d int;
BEGIN
	Select COUNT(*) INTO c FROM cliente
	inner join venta on cliente.codcliente=venta.codcliente
	inner join (SELECT * FROM cuota WHERE cuota.codventa=new.codventa) c on c.codventa=venta.numventa;

	Select COUNT(*) INTO d FROM cliente
	inner join venta on cliente.codcliente=venta.codcliente
	inner join (SELECT * FROM cuota WHERE cuota.codventa=new.codventa) c on c.codventa=venta.numventa
	WHERE c.cancelada=true;
	
	IF(d=c)THEN
		UPDATE venta SET estadopago=true WHERE numventa=new.codventa;
	END IF;
	
	return new;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER TG_ActualizarVentas AFTER UPDATE ON cuota
FOR EACH ROW EXECUTE PROCEDURE actualizarventa();
*/


/* REEMPLAZADO POR UNA TRANSACCIÓN
--TRIGGER PARA REDUCIR EL STOCK
CREATE OR REPLACE FUNCTION actualizarstock() RETURNS TRIGGER AS
$$
DECLARE
BEGIN
	UPDATE public.producto
   	SET stock=stock-new.cantidad
 	WHERE producto.codproducto = new.codproducto;
	
	return new;
END;
$$LANGUAGE 'plpgsql';

CREATE TRIGGER TG_Actualizarstock AFTER INSERT ON detalle
FOR EACH ROW EXECUTE PROCEDURE actualizarstock();
*/


CREATE OR REPLACE FUNCTION fn_cambiarProducto(numven int, prod_old int, prod_new int, cant_new int, desc_new int) RETURNS BOOLEAN AS
$$
DECLARE
	cant_old int;
	prec_new decimal(8,2);
	subt_new decimal(10,2);
	mont_new decimal(10,2);
	subm_new decimal(10,2);
	igv_new decimal(10,2);
BEGIN
	--ELIMINAR EL PRODUCTO NO DESEADO
	SELECT cantidad INTO cant_old FROM detalle WHERE numventa = numven and codproducto = prod_old;
	UPDATE producto SET stock = (stock+cant_old) WHERE codproducto = prod_old;
	DELETE FROM detalle WHERE numventa = numven and codproducto = prod_old;

	--BUSCAR DATOS DEL NUEVO PRODUCTO
	SELECT precio INTO prec_new FROM producto WHERE codproducto = prod_new;
	subt_new = cant_new*(prec_new - ((prec_new*desc_new)/100));
	
	--AGREGAR EL NUEVO PRODUCTO
	INSERT INTO detalle(numventa, codproducto, cantidad, precioventa, descuento, subtotal)
		VALUES (numven, prod_new, cant_new, prec_new, desc_new, subt_new);
	UPDATE producto set stock = stock - cant_new WHERE codproducto = prod_new;

	--CALCULAR NUEVOS DATOS DE LA VENTA
	SELECT sum(subtotal) INTO subm_new FROM detalle WHERE numventa = numven;
	igv_new = subm_new * 0.18;
	mont_new = subm_new + igv_new;

	--ACTUALIZAR LA VENTA
	UPDATE venta SET subtotal = subm_new, igv = igv_new, total = mont_new WHERE numventa = numven;

	return TRUE;

	exception when others then return FALSE
END
$$ language 'plpgsql';


CREATE OR REPLACE FUNCTION fn_estadisticas_venta(OUT numVentas bigint, OUT montoventas decimal(10,2), OUT montominimo decimal(10,2), OUT montomaximo decimal(10,2)) RETURNS SETOF RECORD AS
$$
DECLARE
BEGIN
	RETURN QUERY
	SELECT count(*), sum(total), min(total), max(total) FROM venta;
END
$$ language 'plpgsql';


CREATE OR REPLACE FUNCTION fn_buscar_venta(num int) RETURNS TABLE(numero int, fecha date, cliente varchar(100), total decimal(10,2), producto varchar(100), cantidad int, descuento smallint) AS
$$
DECLARE
BEGIN
	RETURN QUERY
	SELECT v.numventa, v.fecha, c.nombres, d.subtotal, p.nomproducto, d.cantidad, d.descuento FROM detalle d
		INNER JOIN (SELECT * FROM venta WHERE numventa = num) v ON d.numventa = v.numventa
		INNER JOIN producto p ON p.codproducto = d.codproducto
		INNER JOIN cliente c ON c.codcliente = v.codcliente
		ORDER BY 5;
END
$$ language 'plpgsql';


--DROP FUNCTION fn_filtrar_ventas(finicio date, ffinal date)
CREATE OR REPLACE FUNCTION fn_filtrar_ventas(finicio date, ffinal date) RETURNS TABLE(numventa integer, fecha date, cliente character varying,
											total numeric, subtotal numeric, igv numeric, estadopago boolean, 
											tipopago boolean, tipocomprobante boolean) AS
$$
DECLARE
BEGIN
	RETURN query
	SELECT v.numventa, v.fecha, c.nombres, v.total,v.subtotal, v.igv, v.estadopago, v.tipopago, v.tipocomprobante 
	FROM venta v
	INNER JOIN cliente c on c.codcliente = v.codcliente
	WHERE v.fecha>=finicio and v.fecha<=ffinal
	ORDER BY v.fecha;
END
$$
LANGUAGE 'plpgsql';


--DATOS NECESARIOS PARA ELABORAR BOLETA O FACTURA
CREATE OR REPLACE FUNCTION fn_generar_boleta(num int) RETURNS TABLE(numero int, fecha date, nombres varchar(100), dni char(8), ruc char(11), total numeric(10,2),
									subtotal numeric(10,2), igv numeric(10,2), comprobante boolean, cantidad int, producto varchar(30),
									precioventa numeric(10,2), monto numeric(10,2)) AS
$$
DECLARE
BEGIN
	RETURN query
	SELECT v.numventa, v.fecha, c.nombres, c.dni, c.ruc, v.total, v.subtotal, v.igv, v.tipocomprobante, d.cantidad, p.nomproducto, d.precioventa, d.subtotal
	FROM (SELECT * FROM venta b WHERE b.numventa=num) v
	INNER JOIN cliente c on c.codcliente = v.codcliente
	INNER JOIN detalle d on d.numventa = v.numventa
	INNER JOIN producto p on p.codproducto = d.codproducto;
END
$$ language 'plpgsql';