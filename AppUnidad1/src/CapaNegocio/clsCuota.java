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

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */

public class clsCuota {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con;
    Statement sent;
    
    //para registrar pagos
    public void registrarCuota(String numVenta, String numCuota, String fecha, String fpago, String estado, String montoIngresado, String vuelto,String monto) throws Exception{
        if (fpago.equalsIgnoreCase("null")){
            strSQL = "INSERT INTO cuota VALUES (" + numVenta + ", " + numCuota + ", '" + fecha + "' , " + fpago + " , " + estado + ", " + montoIngresado + " , " + vuelto + ","+monto+");";
        }else {
            strSQL = "INSERT INTO cuota VALUES (" + numVenta + ", " + numCuota + ", '" + fecha + "' , '" + fpago + "' , " + estado + ", " + montoIngresado + " , " + vuelto + ","+monto+");";
        }
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la venta");
        }
    }
    //registrar pagos con transaccion metodo simple solo contado por el momento 
    public void registrarCuotaTransaccion(String numVenta, String numCuota, String fecha, String fpago, String estado, String montoIngresado, String vuelto,String monto) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();
            strSQL = "INSERT INTO cuota VALUES (" + numVenta + ", " + numCuota + ", CURRENT_DATE , CURRENT_DATE , " + estado + ", " + montoIngresado + " , " + vuelto + ","+monto+");";
            sent.executeUpdate(strSQL);
            strSQL = "update venta set estadopago=true, tipopago=true where numventa="+numVenta;
            sent.executeUpdate(strSQL);
            con.commit();
        } catch (Exception e) {
            con.rollback();
        }finally{
            objConectar.desconectar();
        }       
       
    }
    
    
    //listar las cuotas pendientes de pago de un cliente
    public ResultSet listarcuotasporpagar(String documento) throws Exception{
         strSQL = "SELECT * FROM LISTAR_DEUDA('"+documento+"');";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar las cuotas pendientes de pago del cliente");
        }
    }
    
    //pagar una cuota 
    public void pagarcuota(int codcuota, int codventa, Float montoIngresado,Float vuelto) throws Exception{
         strSQL = "UPDATE cuota SET cancelada=TRUE, fechapago=CURRENT_DATE,ingreso="+montoIngresado+",vuelto="+vuelto+" WHERE numcuota="+codcuota+" AND codventa="+codventa+";";
        try {
            objConectar.ejecutarBD(strSQL);
            
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la Cuota");
        }
    }
    
    //Saber si hay deudas
    public int saberdeuda(String documento) throws Exception{
         strSQL = "SELECT * FROM DEUDA('"+documento+"') as resultado;";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("resultado");
            }
        } catch (Exception e) {
            throw new Exception("Error al saber las deudas del cliente");
        }
        return 0;
    }
    
    public ResultSet datoscliente(String documento) throws Exception{
        strSQL = "SELECT * FROM DATOSCLIENTE('"+documento+"');";

        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
    }
     
     //para saber cuanto dinero ingreso en pagos por dia contando los creditos
    public float conocerMonto() throws Exception{
        strSQL = "SELECT SUM(total) as MontoTotal FROM venta WHERE fecha = current_date ";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
              return rs.getFloat("MontoTotal");
            }
        } catch (Exception e) {
            throw new Exception("Error al conocer el monto TOTAL");
        }
    return 0;
    }
    
    //para saber cuanto dinero ingreso en pagos por dia CONTADO
    public float conocerMontoCaja() throws Exception{
        strSQL = "SELECT SUM(total) as MontoTotal FROM venta WHERE fecha= current_date and tipopago = true; ";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getFloat("MontoTotal");
            }
        } catch (Exception e) {
            throw new Exception("Error al conocer el monto de CAJA");
        }
        return 0;
    }
    
     //para saber cuanto dinero ingreso en pagos por cuotas de ventas PASADAS
    public float creditosPagados() throws Exception{
        strSQL = "SELECT sum(monto) as monto FROM cuota where fechapago = current_date;";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getFloat("monto");
            }
        } catch (Exception e) {
            throw new Exception("Error al conocer el monto de CAJA");
        }
        return 0;
    }
    
    //para saber el monto de solo creditos que hemos dado al dia
    public float conocerMontoCreditos() throws Exception{
        strSQL = "SELECT SUM(total) as MontoTotal FROM venta WHERE fecha= current_date and tipopago = false;";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
              return rs.getFloat("MontoTotal");
            }
        } catch (Exception e) {
            throw new Exception("Error al conocer el monto pasado en CUOTAS");
        }
        return 0;
    }            

    public ResultSet cuotasPorVenta(int venta) throws Exception{
        strSQL = "SELECT * FROM cuota where codventa="+ venta+" ;";

        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
    }
    
    public Integer cuotasPagadasPorVenta(int venta) throws Exception{
        strSQL = "SELECT sum(monto) as monto FROM cuota where codventa="+ venta+" and cancelada=true;";

        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("monto");
            }
            
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
        return null;
    }
}
