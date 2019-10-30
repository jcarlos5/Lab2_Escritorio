/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
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
    
    public void registrar(int cod, float total, float subtotal, float igv, boolean tipo, int cliente, JTable tblProductos) throws Exception{
        try {
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            sent = con.createStatement();
            strSQL = "INSERT INTO venta VALUES (" + cod + ", " + cliente + ", CURRENT_DATE, " + igv + ", " + subtotal + ", " + total + ", " + tipo + ", false, null );";
            sent.executeUpdate(strSQL);
            
            int ctd = tblProductos.getRowCount();
            for (int i=0; i<ctd; i++){
                String descuento = tblProductos.getValueAt(i, 3).toString();
                
                strSQL = "INSERT INTO detalle VALUES (" + cod + ", " + tblProductos.getValueAt(i, 0).toString() + ", " + tblProductos.getValueAt(i, 5).toString() + ", " + tblProductos.getValueAt(i, 2).toString() + ", " + descuento.substring(0, descuento.length()-1) +", " + tblProductos.getValueAt(i, 6).toString() +");";
                sent.executeUpdate(strSQL);
                
                strSQL = "UPDATE producto SET stock = stock-"+ Integer.parseInt(tblProductos.getValueAt(i, 5).toString())+" WHERE codproducto=" + Integer.parseInt(tblProductos.getValueAt(i, 0).toString()) + ";";
                sent.executeUpdate(strSQL);
            }
            con.commit();           
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al guardar venta");
        }finally{
            objConectar.desconectar();
        }
//        try {
//            objConectar.ejecutarBD(strSQL);
//        } catch (Exception e) {
//            throw new Exception("Error al guardar Venta");
//        }
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
}
