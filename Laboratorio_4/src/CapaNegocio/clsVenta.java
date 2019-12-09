/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;

/*
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
*/

public class clsVenta {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con;
    Statement sent;
    
    public Integer generarCodigoVenta() throws Exception{
        strSQL = "SELECT COALESCE(max(numventa),0)+1 AS codigo FROM venta;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de venta");
        }
        return 0;
    }
    
    //registramos la venta, el detalle de la venta y actualizamos el Stock de los productos
    public void registrar(int cod, float total, float subtotal, float igv, boolean tipo, int cliente, JTable tblProductos) throws Exception{
        try {
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            CallableStatement sentencia = con.prepareCall("INSERT INTO venta VALUES (?,?, CURRENT_DATE,?, ?, ?, ?, false, null ,'001');");
            sentencia.setInt(1, cod);
            sentencia.setInt(2, cliente);
            sentencia.setFloat(3, igv);
            sentencia.setFloat(4, subtotal);
            sentencia.setFloat(5, total);
            sentencia.setBoolean(6,tipo);
            
            sentencia.executeUpdate();
            
            int ctd = tblProductos.getRowCount();
            for (int i=0; i<ctd; i++){
                String descuento = tblProductos.getValueAt(i, 3).toString();
                //strSQL = "INSERT INTO detalle VALUES (" + cod + ", " + tblProductos.getValueAt(i, 0).toString() + ", " + tblProductos.getValueAt(i, 5).toString() + ", " + tblProductos.getValueAt(i, 2).toString() + ", " + descuento.substring(0, descuento.length()-1) +", " + tblProductos.getValueAt(i, 6).toString() +");";
                sentencia = con.prepareCall("INSERT INTO detalle VALUES ( ? , ? , ? , ? , ? , ? );");
                sentencia.setInt(1, cod);
                sentencia.setInt(2, Integer.parseInt(tblProductos.getValueAt(i, 0).toString()));
                sentencia.setInt(3, Integer.parseInt(tblProductos.getValueAt(i, 5).toString()));
                sentencia.setFloat(4, Float.parseFloat(tblProductos.getValueAt(i, 2).toString()));
                sentencia.setInt(5, Integer.parseInt(descuento.substring(0, descuento.length()-1)));
                sentencia.setFloat(6, Float.parseFloat(tblProductos.getValueAt(i, 6).toString()));
                sentencia.executeUpdate();
                
                //strSQL = "UPDATE producto SET stock = stock-"+ Integer.parseInt(tblProductos.getValueAt(i, 5).toString())+" WHERE codproducto=" + Integer.parseInt(tblProductos.getValueAt(i, 0).toString()) + ";";
                sentencia = con.prepareCall("UPDATE producto SET stock = stock-? WHERE codproducto=?");
                sentencia.setInt(1, Integer.parseInt(tblProductos.getValueAt(i, 5).toString()));
                sentencia.setInt(2, Integer.parseInt(tblProductos.getValueAt(i, 0).toString()));
                sentencia.executeUpdate();
            }
            con.commit();           
        } catch (Exception e) {
            con.rollback();
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    //ya no es necesario
    /*
    public void registrarDetalle(String venta, String prod, String cant, String preVen, String desc, String sub) throws Exception{
        strSQL = "INSERT INTO detalle VALUES (" + venta + ", " + prod + ", " + cant + ", " + preVen + ", " + desc +", " + sub +");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al guardar el detalle de venta");
        }
    }
    */
    
    
    //para listar todas las ventas pendientes de pago - JDPAGO
    public ResultSet listarVentaPagoPendiente() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM venta WHERE estadopago=false;");
            rs=sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar venta");
        }
    }
    //para listar las ventas pendientes de pago por cliente - JDPAGO
    public ResultSet listarVentaPagoPendiente(String codcliente) throws Exception{
        //strSQL = "SELECT * FROM venta WHERE estadopago=false and tipopago is null and codcliente="+codcliente+";";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM venta WHERE estadopago=false and tipopago is null and codcliente= ?;");
            sentencia.setInt(1, Integer.parseInt(codcliente));
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar ventas");
        }
    }
    
    
    //para listar las ventas por fecha
    //el tipo de dato Date es de la libreria de sql 
    //para el jd ventas diarias 
    public ResultSet listarVenta(Date fecha) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM venta WHERE fecha= ?");
            sentencia.setDate(1, fecha);
            rs=sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }

    public ResultSet listarVenta(int numVenta) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT v.*, c.dni, c.ruc,c.nombres, cu.vuelto, cu.ingreso, cu.cancelada FROM venta v INNER JOIN cliente c on v.codcliente=c.codcliente INNER JOIN cuota cu on v.numventa=cu.codventa WHERE numventa = ?");
            sentencia.setInt(1, numVenta);
            rs=sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar ventas por numero de venta");
        }
    }
    //para listar las ventas no pagadas del cliente
    public ResultSet listarVentaPorCliente(int codcliente) throws Exception{
        //strSQL = "SELECT * FROM venta v inner join cliente c on v.codcliente=c.codcliente WHERE codcliente = " + codcliente + " and estadopago = false;";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM venta v inner join cliente c on v.codcliente=c.codcliente WHERE codcliente = ? and estadopago = false;");
            sentencia.setInt(1, codcliente);
            rs=sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    //para listar los detalles por venta
    public ResultSet listarDetalleVenta(int venta) throws Exception{
        //strSQL = "";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT d.*, p.nomproducto FROM detalle d inner join producto p on d.codproducto=p.codproducto WHERE d.numventa = ? ;");
            sentencia.setInt(1, venta);
            rs=sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    public ResultSet listarVentaCredito(String dniruc) throws Exception{
        //strSQL = "SELECT * FROM (SELECT * FROM venta WHERE tipopago=false) v INNER JOIN cliente c on v.codcliente=c.codcliente WHERE c.dni ='" + dniruc+"' or c.ruc='"+dniruc+"';";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM (SELECT * FROM venta WHERE tipopago=false) v INNER JOIN cliente c on v.codcliente=c.codcliente WHERE c.dni =? or c.ruc=?;");
            sentencia.setString(1, dniruc);
            sentencia.setString(2, dniruc);
            rs=sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    public ResultSet listarTodasVentaPorCliente(int codcliente) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM venta v inner join cliente c on v.codcliente=c.codcliente WHERE c.codcliente = ? and estadopago is not null;");
            sentencia.setInt(1, codcliente);
            rs=sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    public Boolean getComprobante(int numVenta) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT tipoComprobante FROM venta WHERE numventa = ?");
            sentencia.setInt(1, numVenta);
            rs=sentencia.executeQuery();
            if(rs.next()){
                return rs.getBoolean(1);
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    public boolean cambiarProducto(int numven, int prod_old, int prod_new, int cant_new, int desc_new) throws Exception{
        try {
            boolean rpta = false;
            objConectar.conectar();
            con = objConectar.getConnection();
            try {
                con.setAutoCommit(false);
                CallableStatement sentencia = con.prepareCall("SELECT fn_cambiarProducto(?, ?, ?, ?, ?)");
                sentencia.setInt(1, numven);
                sentencia.setInt(2, prod_old);
                sentencia.setInt(3, prod_new);
                sentencia.setInt(4, cant_new);
                sentencia.setInt(5, desc_new);
                ResultSet resultado = sentencia.executeQuery();
                if (resultado.next()) 
                {
                    rpta = resultado.getBoolean("fn_cambiarProducto");
                }
                con.commit();
                return rpta;
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Error ");
            }finally{
                objConectar.desconectar();
            }
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    public ResultSet estadisticas() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("select * from fn_estadisticas_venta()");
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception("Error ");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public ResultSet ver_venta(int numventa) throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM fn_buscar_venta(?)");
            sentencia.setInt(1, numventa);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception("Error ");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public ResultSet getVentasFechas(Date f1, Date f2) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM fn_filtrar_ventas(?,?)");
            sentencia.setDate(1, f1);
            sentencia.setDate(2, f2);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
}
