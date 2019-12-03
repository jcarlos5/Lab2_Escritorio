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
import javax.swing.JOptionPane;

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
        objConectar.conectar();
        Connection con = objConectar.getConnection();
        if (fpago.equalsIgnoreCase("null")){//No le veo sentido al if :/
            
            CallableStatement sentencia = con.prepareCall("INSERT INTO cuota VALUES(?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,Integer.parseInt(numVenta));
            sentencia.setInt(2,Integer.parseInt(numCuota));
            sentencia.setString(3, fecha);
            sentencia.setString(4, null);
            sentencia.setBoolean(5, Boolean.parseBoolean(estado));
            sentencia.setFloat(6, Float.parseFloat(montoIngresado));
            sentencia.setFloat(7, Float.parseFloat(vuelto));
            sentencia.setFloat(8, Float.parseFloat(monto));
            sentencia.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
        }else {
            CallableStatement sentencia = con.prepareCall("INSERT INTO cuota VALUES(?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,Integer.parseInt(numVenta));
            sentencia.setInt(2,Integer.parseInt(numCuota));
            sentencia.setString(3, fecha);
            sentencia.setString(4, fpago);
            sentencia.setBoolean(5, Boolean.parseBoolean(estado));
            sentencia.setFloat(6, Float.parseFloat(montoIngresado));
            sentencia.setFloat(7, Float.parseFloat(vuelto));
            sentencia.setFloat(8, Float.parseFloat(monto));
            sentencia.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
        }
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la venta");
        }
    }
    
    //PRIMERA TRANSACCIÓN DEL ITEM A
    public void registrarCuota(String[][] datos, boolean tipo) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            con.setAutoCommit(false);
            sent = con.createStatement();
            strSQL="UPDATE venta SET estadopago = " + tipo + ", tipopago = " + tipo + " WHERE numventa=" + datos[0][0] + ";";
            sent.executeUpdate(strSQL);
            
            for (String[] dato : datos) {
                strSQL="INSERT INTO cuota VALUES (" + dato[0] + ", " + dato[1] + ", '" + dato[2] + "' , " + dato[3] + " , " + dato[4] + ", " + dato[5] + " , " + dato[6] + ","+dato[7]+");";
                sent.executeUpdate(strSQL);
            }
            
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception(e.getMessage());
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
//    public void pagarcuota(int codcuota, int codventa, Float montoIngresado,Float vuelto) throws Exception{
//        strSQL = "UPDATE cuota SET cancelada=TRUE, fechapago=CURRENT_DATE,ingreso="+montoIngresado+",vuelto="+vuelto+" WHERE numcuota="+codcuota+" AND codventa="+codventa+";";
//        try {
//            objConectar.ejecutarBD(strSQL);
//            
//        } catch (Exception e) {
//            throw new Exception("Error al registrar el pago de la Cuota");
//        }
//    }
    
    //SEGUNDA TRANSACCIÓN DEL ITEM A
    public boolean pagarcuota(int codcuota, int codventa, Float montoIngresado,Float vuelto) throws Exception{
        try {
            boolean fin = false;
            objConectar.conectar();
            con = objConectar.getConnection();
            con.setAutoCommit(false);
            sent = con.createStatement();
            
            strSQL = "UPDATE cuota SET cancelada=TRUE, fechapago=CURRENT_DATE,ingreso="+montoIngresado+",vuelto="+vuelto+" WHERE numcuota="+codcuota+" AND codventa="+codventa+";";
            sent.executeUpdate(strSQL);
            
            strSQL = "SELECT count(*) FROM cuota WHERE cancelada = false AND codventa="+codventa+";";
            rs=objConectar.consultarBD(strSQL);
            
            if (rs.next()){
                if(rs.getInt(1)==1){
                    strSQL="UPDATE venta SET estadopago = true WHERE numventa=" + codventa + ";";
                    sent.executeUpdate(strSQL);
                    fin=true;
                }
            }
            
            con.commit();
            return fin;
        } catch (Exception e) {
            con.rollback();
            throw new Exception(e.getMessage());
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
     
     //ventas PAGADAS AL CONTADO
    public float conocerMonto() throws Exception{
        strSQL = "SELECT SUM(c.monto) as MontoTotal FROM venta v INNER JOIN cuota c ON v.numventa = c.codventa WHERE c.fechapago = current_date and v.tipopago = true and c.cancelada = true;";
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
    
    //Monto TOTAL caja: ventas pagadas al CONTADO + cuotas PAGADAS
    public float conocerMontoCaja() throws Exception{
        strSQL = "SELECT SUM(monto) as MontoTotal FROM cuota WHERE fechapago = current_date and cancelada = true; ";
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
        strSQL = "SELECT SUM(c.monto) as MontoTotal FROM venta v INNER JOIN cuota c ON v.numventa = c.codventa WHERE c.fechapago = current_date and v.tipopago = false and c.cancelada = true;";
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
    
    //para saber el monto de solo creditos que hemos dado al dia
    public float ventasCreditos() throws Exception{
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
