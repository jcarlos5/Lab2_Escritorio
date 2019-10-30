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
    
   /* public void registrar(int cod, float total, float subtotal, float igv, boolean tipo, int cliente, boolean pago, boolean contado) throws Exception{
        strSQL = "INSERT INTO venta VALUES (" + cod + ", " + cliente + ", CURRENT_DATE, " + igv + ", " + subtotal + ", " + total + ", " + tipo + ", " + pago + ", "+contado +" );";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al guardar Venta");
        }
    }
    */
    public void registrar(int cod, float total, float subtotal, float igv, boolean tipo, int cliente, boolean pago, boolean contado, JTable detalle) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();
            strSQL = "INSERT INTO venta VALUES (" + cod + ", " + cliente + ", CURRENT_DATE, " + igv + ", " + subtotal + ", " + total + ", " + tipo + ", " + pago + ", "+ contado+" );";
            sent.executeUpdate(strSQL);
            int ctd = detalle.getRowCount();
            for (int i=0; i<ctd; i++){
                String descuento = detalle.getValueAt(i, 3).toString();
                strSQL = "INSERT INTO detalle VALUES (" + cod + ", " + detalle.getValueAt(i, 0).toString() + ", " + detalle.getValueAt(i, 5).toString() + ", " + detalle.getValueAt(i, 2).toString() + ", " + descuento.substring(0, descuento.length()-1) +", " + detalle.getValueAt(i, 6).toString() +");";
                sent.executeUpdate(strSQL);
                strSQL = "update producto set stock=stock - " + Integer.valueOf( detalle.getValueAt(i, 5).toString()) + "where codproducto="+detalle.getValueAt(i, 0).toString() ;
                sent.executeUpdate(strSQL);
                con.commit();
            }
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al guardar Venta");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void registrarSinPago(int cod, float total, float subtotal, float igv, boolean tipo, int cliente, boolean pago, JTable detalle) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();
            strSQL = "INSERT INTO venta VALUES (" + cod + ", " + cliente + ", CURRENT_DATE, " + igv + ", " + subtotal + ", " + total + ", " + tipo + ", " + pago + ", null );";
            sent.executeUpdate(strSQL);
            int ctd = detalle.getRowCount();
            for (int i=0; i<ctd; i++){
                String descuento = detalle.getValueAt(i, 3).toString();
                strSQL = "INSERT INTO detalle VALUES (" + cod + ", " + detalle.getValueAt(i, 0).toString() + ", " + detalle.getValueAt(i, 5).toString() + ", " + detalle.getValueAt(i, 2).toString() + ", " + descuento.substring(0, descuento.length()-1) +", " + detalle.getValueAt(i, 6).toString() +");";
                sent.executeUpdate(strSQL);
                strSQL = "update producto set stock=stock - " + Integer.valueOf( detalle.getValueAt(i, 5).toString()) + "where codproducto="+detalle.getValueAt(i, 0).toString() ;
                sent.executeUpdate(strSQL);
                con.commit();
            }
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al guardar Venta");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void registrarDetalle(String venta, String prod, String cant, String preVen, String desc, String sub) throws Exception{
        strSQL = "INSERT INTO detalle VALUES (" + venta + ", " + prod + ", " + cant + ", " + preVen + ", " + desc +", " + sub +");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al guardar el detalle de venta");
        }
    }
    //para listar todas las ventas pendientes de pago - JDPAGO
    public ResultSet listarVentaPagoPendiente() throws Exception{
        strSQL = "SELECT * FROM venta WHERE estadopago=false;";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar venta");
        }
    }
    //para listar las ventas pendientes de pago por cliente - JDPAGO
    public ResultSet listarVentaPagoPendiente(String codcliente) throws Exception{
        strSQL = "SELECT * FROM venta WHERE estadopago=false and codcliente="+codcliente+";";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar ventas");
        }
    }
    //para listar las ventas por fecha
    //el tipo de dato Date es de la libreria de sql 
    //para el jd ventas diarias 
    public ResultSet listarVenta(Date fecha) throws Exception{
        strSQL = "SELECT * FROM venta WHERE fecha='"+fecha+"';";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }

    public ResultSet listarVenta(int numVenta) throws Exception{
        strSQL = "SELECT v.*, c.dni, c.ruc,c.nombres, cu.vuelto, cu.ingreso, cu.cancelada FROM venta v inner join cliente c on v.codcliente=c.codcliente inner join cuota cu on v.numventa=cu.codventa"
                + " WHERE numventa = " + numVenta;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar ventas por numero de venta");
        }
    }
    //para listar las ventas no pagadas del cliente
    public ResultSet listarVentaPorCliente(int codcliente) throws Exception{
        strSQL = "SELECT * FROM venta v inner join cliente c on v.codcliente=c.codcliente WHERE codcliente = " + codcliente + " and estadopago = false;";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    //para listar los detalles por venta
    public ResultSet listarDetalleVenta(int venta) throws Exception{
        strSQL = "SELECT d.*, p.nomproducto FROM detalle d inner join producto p on d.codproducto=p.codproducto WHERE d.numventa = " + venta+ " ;";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    public ResultSet listarVentaCredito(String dniruc) throws Exception{
        strSQL = "SELECT * FROM (SELECT * FROM venta WHERE tipopago=false) v INNER JOIN cliente c on v.codcliente=c.codcliente WHERE c.dni ='" + dniruc+"' or c.ruc='"+dniruc+"';";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    public ResultSet listarTodasVentaPorCliente(int codcliente) throws Exception{
        strSQL = "SELECT * FROM venta v inner join cliente c on v.codcliente=c.codcliente WHERE c.codcliente = " + codcliente + " and estadopago = true or tipopago=false;";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error ");
        }
    }
    
    public int numeroCuotasNoPagadas(int codventa) throws Exception{
        strSQL = "SELECT count(*) as cuenta FROM venta v inner join cuota cu on v.numventa=cu.codventa " +
                    "WHERE v.numventa="+codventa +" and cu.cancelada=false";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("cuenta");
            }
        } catch (Exception e) {
            throw new Exception("Error ");
        }
        return -1;
    }
    
    public int numeroCuotas(int codventa) throws Exception{
        strSQL = "SELECT count(*) as cuenta FROM venta v inner join cuota cu on v.numventa=cu.codventa " +
                    "WHERE v.numventa="+codventa ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("cuenta");
            }
        } catch (Exception e) {
            throw new Exception("Error ");
        }
        return -1;
    }
    
    public boolean validarCambioProducto(int codventa) throws Exception{
        boolean permitir  = false;;
        try {
            int t = numeroCuotas(codventa);
            int falta = numeroCuotasNoPagadas(codventa);
            if (t==falta) {
                permitir = true;
            }else {
                permitir = false;
            }
        } catch (Exception e) {
            throw new Exception("Error ");
        }
        return permitir;
    }
    
    public boolean editarDetalle(int oldp, int newp,int cant ,int vent) throws Exception 
    {   boolean rpta = false;
        
            
            objConectar.conectar();
            Connection con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("select cambiarProducto(?,?,?,?)");
            sentencia.setInt(1, oldp);
            sentencia.setInt(2, newp);
            sentencia.setInt(3, cant);
            sentencia.setInt(4, vent);
            System.out.println(sentencia);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) 
            {
                rpta = resultado.getBoolean("cambiarProducto");
                System.out.println(rpta);
            }                
            
        
         return rpta;      
    }
    
    public ResultSet ventasTotales() throws Exception 
    {
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("select * from estadisticas_venta()");
        ResultSet resultado = sentencia.executeQuery();
        if (resultado.next()) {
            return resultado;
        }
        return null;
    }
    
    public ResultSet informacionVenta(int cod) throws Exception 
    {
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("select * from info_venta(?)");
        sentencia.setInt(1,cod);
        ResultSet resultado = sentencia.executeQuery();
        
            return resultado;
       
    }
}
