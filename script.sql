--Fecha de creación	: 27 de Agosto del 2019
--Última Modificación	: 06 de Octubre del 2019
--Autor			: JCarlos

-- Database: "BDProgramacion"

-- DROP DATABASE "BDProgramacion";

CREATE DATABASE "BDProgramacion"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Spain.1252'
       LC_CTYPE = 'Spanish_Spain.1252'
       CONNECTION LIMIT = -1;

--TABLA PARA INICIO DE SESIÓN

CREATE TABLE USUARIO(
codUsuario int not null primary key,
nomUsuario varchar(20) not null,
clave varchar(20) not null,
nombreCompleto varchar(80) not null,
cargo varchar(30) null,
estado boolean not null
);

ALTER TABLE USUARIO ADD pregunta varchar(50);
ALTER TABLE USUARIO ADD respuesta varchar(50);


INSERT INTO USUARIO VALUES(1, 'admin', '123456', 'Juan Perez Perez', 'Gerente General', TRUE);
INSERT INTO USUARIO VALUES(2, 'invitado', 'usat2019', 'Maria Mendoza', 'supervisor', FALSE);
INSERT INTO USUARIO VALUES(3, 'venta', 'chiclayo', 'Pedro Casas Merino', 'Jefe de ventas', TRUE);

UPDATE USUARIO SET pregunta = 'Ciudad de Nacimiento', respuesta = 'Lima' WHERE codUsuario = 1;
UPDATE USUARIO SET pregunta = 'Primer número celular', respuesta = '979105594' WHERE codUsuario = 2;
UPDATE USUARIO SET pregunta = 'Nombre de tu mascota', respuesta = 'Boby' WHERE codUsuario = 3;

SELECT * FROM usuario;

--TABLAS PARA MANTENIMIENTO

CREATE TABLE MARCA(
codMarca int not null primary key,
nomMarca varchar(30) not null
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

--Restricciones / claves foráneas
ALTER TABLE PRODUCTO ADD CONSTRAINT fk_marca_producto FOREIGN KEY (codMarca) REFERENCES MARCA;
ALTER TABLE PRODUCTO ADD CONSTRAINT fk_categoria_producto FOREIGN KEY (codCategoria) REFERENCES CATEGORIA;

SELECT * FROM MARCA;
SELECT * FROM CATEGORIA;
SELECT * FROM PRODUCTO;

--Insertar datos a las tablas de mantenimiento
INSERT INTO MARCA VALUES (1, 'EPSON');
INSERT INTO CATEGORIA VALUES (1, 'IMPRESORAS', 'Impresoras Multifuncionales', TRUE);
INSERT INTO PRODUCTO VALUES (1, 'EPSON LX570', 'Color: Negro', 900.00, 50, TRUE, 1, 1);

--UPDATE PRODUCTO SET precio = 950 WHERE codProducto = 1;
--DELETE FROM PRODUCTO WHERE codProducto = 1;



--03/09/2019, 04/09/2019
--INICIO DE SESIÓN
--validar la vigencia del usuario
SELECT estado FROM USUARIO WHERE nomUsuario = 'admin';
--validar usuario/contraseña
SELECT * FROM USUARIO WHERE nomUsuario='admin' and clave='123456' and estado = true;
SELECT nombreCompleto FROM USUARIO WHERE nomUsuario='admin' and clave='123456' and estado = true;
--cambiar contraseña
UPDATE USUARIO SET clave = 'nuevaclave' where nombreCompleto = 'nombre';
--mostrar pregunta secreta
SELECT pregunta FROM USUARIO WHERE nomUsuario = 'admin';
--validar pregunta secreta
SELECT * FROM USUARIO WHERE nomUsuario = 'admin' and respuesta = 'respuesta' and estado = true;




--08/09/2019
--LABORATORIO 01
CREATE TABLE MOVIMIENTO(
numMovimiento serial not null primary key,
codUsuario int not null, 
fecha date not null,
hora time not null,
estado Boolean not null
);

ALTER TABLE MOVIMIENTO ADD CONSTRAINT FK_MOV_USU FOREIGN KEY (codUsuario) REFERENCES USUARIO;

--CONSULTAS PARA MANTENIMIENTO DE USUARIO
--Generar un nuevo movimiento
INSERT INTO movimiento(codusuario, fecha, hora, estado) VALUES ((SELECT codusuario FROM usuario WHERE nomusuario = 'admin'), CURRENT_DATE, CURRENT_TIME, TRUE);
--Cerrar la Sesion de Movimiento
UPDATE movimiento SET estado = false WHERE codusuario = (SELECT codusuario FROM usuario WHERE nomusuario = 'admin')
UPDATE movimiento SET estado = false;
--Última Sesión
SELECT * FROM movimiento 
WHERE codusuario = (SELECT codusuario FROM usuario WHERE nomusuario = 'admin') and estado = false 
ORDER BY fecha DESC, hora DESC LIMIT 1;
--Sesión Actual
SELECT * FROM movimiento 
WHERE codusuario = (SELECT codusuario FROM usuario WHERE nomusuario = 'admin') and estado = true 
ORDER BY fecha, hora DESC LIMIT 1;
--contar Sesiones
SELECT count(*) FROM movimiento WHERE codusuario = (SELECT codusuario FROM usuario WHERE nomusuario = 'admin') group by codusuario;
--generar código de usuario
SELECT COALESCE(max(codusuario),0)+1 AS codigo FROM usuario;
--registrar solo datos básicos
INSERT INTO usuario (codusuario, nomusuario, clave, nombrecompleto, cargo, estado) VALUES(" + cod + ",'" + user + "','" + clave + "','" + nom + "', '" + cargo + "', " + vig + ");
--registrar con pregunta secreta
INSERT INTO usuario (codusuario, nomusuario, clave, nombrecompleto, cargo, estado, pregunta, respuesta) VALUES(" + cod + ",'" + user + "','" + clave + "','" + nom + "', '" + cargo + "', " + vig + ", '" + preg + "', '" + rpta + "');
--buscar usuario
SELECT * FROM usuario WHERE codusuario=" + cod + ";
--eliminar usuario
DELETE FROM usuario WHERE codusuario=" + cod + ";
--listar usuarios
SELECT * FROM usuario ORDER BY codusuario;
--modificar datos básicos de usuario
UPDATE usuario SET nomusuario = '" + user + "', nombrecompleto = '" + nom + "', cargo = '" + cargo + "', estado = " + vig + " WHERE codusuario =" + cod + ";
--modificar usuario con pregunta y respuesta secreta
UPDATE usuario SET nomusuario = '" + user + "', nombrecompleto = '" + nom + "', cargo = '" + cargo + "', estado = " + vig + ", pregunta = '" + preg + "', respuesta = '" + rpta + "' WHERE codusuario =" + cod + ";
--cambiar pregunta secreta
UPDATE usuario SET pregunta = '" + pregunta + "', respuesta = '" + rpta + "' WHERE nomusuario ='" + user + "';
--dar de baja usuario
UPDATE usuario SET estado = false WHERE codusuario =" + cod + ";
--buscar por nombre de usuario
SELECT * FROM usuario WHERE nomusuario = '" + user + "';




--10-09/2019 Mantenimientos de Base de Datos
--Agregar campo a la tabla MARCA
ALTER TABLE MARCA ADD COLUMN vigencia BOOLEAN NULL;
UPDATE marca SET vigencia=TRUE;

SELECT max(codMarca)+1 FROM marca;
SELECT count(*) FROM marca;

SELECT * FROM marca;
INSERT INTO MARCA VALUES(1,'EPSON', TRUE);

--SELECT ISNULL(max(codMarca),0)+1 FROM marca;
SELECT CASE WHEN (SELECT max(codMarca) FROM marca) 
IS NULL then 1 
else ((select max(codMarca) from marca))+1
end as codigo;

SELECT * FROM marca WHERE codMarca=4;

SELECT COALESCE(max(codMarca),0)+1 AS codigo FROM marca;

DELETE FROM marca WHERE codMarca=4;
SELECT * FROM marca;

--CONSULTAS PARA MANTENIMIENTO DE MARCA
--Generar codigo
SELECT COALESCE(max(codMarca),0)+1 AS codigo FROM marca;
--registrar Marca
INSERT INTO MARCA VALUES(" + cod + ",'" + nom + "'," + vig + ");
--buscar marca
SELECT * FROM marca WHERE codMarca=" + cod + ";
--eliminar marca
DELETE FROM marca WHERE codMarca=" + cod + ";
--listar marcas
SELECT * FROM marca;
--actualizar marca
UPDATE marca SET nommarca = '" + nombre + "', vigencia = " + vigencia + " WHERE codmarca =" + cod + ";
-- dar de baja marca
UPDATE marca SET vigencia = false WHERE codmarca =" + cod + ";
--obtener codigo dado el nombre
SELECT codmarca FROM marca WHERE nommarca='"+nombre+"';
--obtener el nombre dado el código
SELECT nommarca FROM marca WHERE codmarca='"+codigo+"';

--CONSULTAS PARA MANTENIMIENTO CATEGORÍA
--generar código
SELECT COALESCE(max(codcategoria),0)+1 AS codigo FROM categoria;
--registrar categoría
INSERT INTO categoria VALUES(" + cod + ",'" + nom + "','" + descrip + "'," + vig + ");
--buscar categoría
SELECT * FROM categoria WHERE codcategoria=" + cod + ";
--eliminar categoría
DELETE FROM categoria WHERE codcategoria=" + cod + ";
--listar categorías
SELECT * FROM categoria;
--modificar categoría
UPDATE categoria SET nomcategoria = '" + nombre + "', vigencia = " + vigencia + ", descripcion = '" + descrip + "' WHERE codcategoria =" + cod + ";
--dar de baja categoría
UPDATE categoria SET vigencia = false WHERE codcategoria =" + cod + ";
--obtener el código dado el nombre
SELECT codcategoria FROM categoria WHERE nomcategoria='"+nombre+"';
--obtener el nombre dado el código
SELECT nomcategoria FROM categoria WHERE codcategoria='"+codigo+"';

--CONSULTAS PARA EL MANTENIMIENTO PRODUCTO
--generar código producto
SELECT COALESCE(max(codproducto),0)+1 AS codigo FROM producto;
--registrar producto
INSERT INTO producto VALUES(" + cod + ",'" + nom + "','" + descrip + "'," + precio + ", " + stock + ", " + vig + ", " + codMarca + ", " + codCate +");
--buscar producto
SELECT * FROM producto WHERE codproducto=" + cod + ";
--eliminar producto
DELETE FROM producto WHERE codproducto=" + cod + ";
--listar productos con nombre de marca y categoria
SELECT p.*, m.nommarca, c.nomcategoria FROM producto p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria ORDER BY codproducto;
--actualizar producto
UPDATE producto SET nomproducto = '" + nombre + "', descripcion = '" + descrip + "', precio='" + precio + "', stock='" + stock + "', vigencia = " + vigencia + ", codmarca = '" + codMarca + "', codcategoria = '" + codCategoria + "' WHERE codProducto ='" + cod + "';
--dar de baja producto
UPDATE producto SET vigencia = false WHERE codproducto =" + cod + ";
--filtrar productos por marca, nombre y precio
SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codmarca = " + marca + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;
--filtrar productos por categoria, nombre y precio
SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codcategoria = " + categoria + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;
--filtrar productos por marca, categoria, nombre y precio
SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codmarca = " + marca + " AND codcategoria = " + categoria + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;
--filtrar por nombre y precio
SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;
--obtener el precio más alto
SELECT MAX(precio) FROM producto;



--25/09/2019
--Ampliar BDProgramación: incorporar tabla venta
CREATE TABLE TIPO_CLIENTE(
codTipo INT NOT NULL PRIMARY KEY,
nombre VARCHAR(30) NOT NULL
);

INSERT INTO TIPO_CLIENTE VALUES(1, 'Persona Natural');
INSERT INTO TIPO_CLIENTE VALUES(2, 'Persona Jurídica');
INSERT INTO TIPO_CLIENTE VALUES(3, 'Persona Natural con RUC');

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

ALTER TABLE CLIENTE ADD CONSTRAINT FK_TIPO_CLIENTE_CLIENTE FOREIGN KEY (codTipo) REFERENCES TIPO_CLIENTE;

CREATE TABLE VENTA(
numVenta INT NOT NULL PRIMARY KEY,
fecha DATE NOT NULL,
total DECIMAL(10, 2) NOT NULL,
subtotal DECIMAL(10, 2) NULL,
igv DECIMAL(10, 2) NULL,
tipoComprobante BOOLEAN NOT NULL, --True: Boleta, False: Factura
estadoPago BOOLEAN NOT NULL,
codCliente INT NOT NULL
);

ALTER TABLE VENTA ADD CONSTRAINT FK_CLIENTE_VENTA FOREIGN KEY (codCliente) REFERENCES CLIENTE;

CREATE TABLE DETALLE(
numVenta INT NOT NULL,
codProducto INT NOT NULL,
cantidad INT NOT NULL,
precioVenta DECIMAL(8, 2) NOT NULL,
descuento smallint null,
subtotal DECIMAL(10, 2) NOT NULL
);

ALTER TABLE DETALLE ADD CONSTRAINT FK_PRODUCTO_DETALLE FOREIGN KEY (codProducto) REFERENCES PRODUCTO;
ALTER TABLE DETALLE ADD CONSTRAINT FK_VENTA_DETALLE FOREIGN KEY (numVenta) REFERENCES VENTA;
ALTER TABLE DETALLE ADD CONSTRAINT PK_DETALLE PRIMARY KEY (numVenta, codProducto);



--01/10/2019
--CONSULTAS PARA MANTENIMIENTO DE CLIENTE
--listar clientes con nombre de tipo_cliente
SELECT c.*, t.nombre FROM CLIENTE c INNER JOIN TIPO_CLIENTE t ON c.codtipo = t.codtipo ORDER BY codcliente;
--listar tipos de cliente
SELECT * FROM TIPO_CLIENTE;
--generar código de cliente
SELECT COALESCE(max(codcliente),0)+1 AS codigo FROM cliente;
--registrar persona natural
INSERT INTO cliente VALUES ("+cod+", '"+ dni+"', null, '"+nom+"', '"+tel+"', '"+correo+"', '"+direccion+"', "+vig+", "+codtipo+");
--registrar persosna jurídica
INSERT INTO cliente VALUES ("+cod+", null, '"+ ruc+"', '"+nom+"', '"+tel+"', '"+correo+"', '"+direccion+"', "+vig+", "+codtipo+");
--registrar persona con ruc
INSERT INTO cliente VALUES ("+cod+", '"+dni+"', '"+ruc+"', '"+nom+"', '"+tel+"', '"+correo+"', '"+direccion+"', "+vig+", "+codtipo+");
--buscar cliente
SELECT * FROM cliente WHERE codcliente=" + cod + ";
--eliminar cliente
DELETE FROM cliente WHERE codcliente=" + cod + ";
--modificar persona natural
UPDATEcliente SET dni='" + dni + "', nombres='" + nom + "', telefono='" + tel + "', correo='" + correo + "', direccion='" + direccion + "', vigencia= " + vig + ", codtipo='" + codtipo + "' WHERE codcliente = " + cod + ";
--modificar persona jurídica
UPDATE cliente SET ruc='" + ruc + "', nombres='" + nom + "', telefono='" + tel + "', correo='" + correo + "', direccion='" + direccion + "', vigencia= " + vig + ", codtipo='" + codtipo + "' WHERE codcliente = " + cod + ";
--modificar persona con ruc
UPDATE cliente SET dni='" + dni + "', ruc='" + ruc + "', nombres='" + nom + "', telefono='" + tel + "', correo='" + correo + "', direccion='" + direccion + "', vigencia= " + vig + ", codtipo='" + codtipo + "' WHERE codcliente = " + cod + ";
--dar de baja cliente
UPDATE cliente SET vigencia = false WHERE codcliente =" + cod + ";

--02/10/2019
--CONSULTAS PARA VENTA
--filtrar clientes por dni o ruc
SELECT * FROM CLIENTE WHERE dni LIKE '" + cadena + "%' OR ruc LIKE '" + cadena + "%';
--obtener datos del cliente
SELECT c.*, t.nombre FROM (SELECT * FROM cliente WHERE dni = '" + doc + "' OR ruc = '" + doc + "') c INNER JOIN TIPO_CLIENTE t ON c.codtipo = t.codtipo;
--generar código
SELECT COALESCE(max(numventa),0)+1 AS codigo FROM venta;

--06-10-2019
--AMPLIAR BD PARA INCLUIR PAGOS
CREATE TABLE cuota(
codventa INT NOT NULL,
numcuota INT NOT NULL,
fecha DATE NOT NULL,
cancelada BOOLEAN NOT NULL,
ingreso DECIMAL(10, 2),
vuelto DECIMAL(10,2)
);

ALTER TABLE cuota ADD CONSTRAINT PK_cuota PRIMARY KEY (codventa, numcuota);
ALTER TABLE cuota ADD CONSTRAINT FK_venta_cuota FOREIGN KEY (codventa) REFERENCES venta;
ALTER TABLE cuota ADD COLUMN monto DECIMAL(10,2) not null;

--registrar pago(cuota)
INSERT INTO cuota VALUES (" + numVenta + ", " + numCuota + ", '" + fecha + "', " + estado + ", " + montoIngresado + " , " + vuelto + ","+monto+");

-- listar las cuotas pendientes de pago de un cliente
Select cuota.monto, cuota.fecha, cuota.cancelada from cliente
inner join venta on cliente.codcliente=venta.codcliente
inner join cuota on cuota.codventa=venta.numventa
where cliente.codcliente=1 and cuota.cancelada=FALSE;