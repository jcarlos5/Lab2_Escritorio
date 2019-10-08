/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author JCarlos
 */
public class clsCuota {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
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
    
    //listar las cuotas pendientes de pago de un cliente
    public ResultSet listarcuotasporpagar(String documento) throws Exception{
         strSQL = "SELECT * FROM LISTAR_DEUDA('"+documento+"');";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs;
            }
        } catch (Exception e) {
            throw new Exception("Error al listar las cuotas pendientes de pago del cliente");
        }
        return null;
    }
    
    //pagar una cuota 
    public void pagarcuota(int codcuota, int codventa) throws Exception{
         strSQL = "UPDATE cuota SET cancelada=TRUE, fechapago=CURRENT_DATE WHERE numcuota="+codcuota+" AND codventa="+codventa+";";
        try {
            objConectar.ejecutarBD(strSQL);
            
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la Cuota");
        }
    }
    
    //Saber si hay deudas
    public int saberdeuda(String documento) throws Exception{
         strSQL = "SELECT DEUDA('"+documento+"') as resultado;";
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
         strSQL = "SELECT DATOS_CLIENTE("+documento+");";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs;
            }
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
        return null;
    }
     
     //para saber cuanto dinero ingreso en pagos por dia SIN contar credito
    public float conocerMontoCaja() throws Exception{
        float monto=0;
        strSQL = "SELECT * FROM cuota WHERE fechapago=CURRENT_DATE and estadoPago=true;";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                monto+=rs.getFloat("monto");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
        return monto;
    }
}
