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
    public void registrarCuota(String numVenta, String numCuota, String fecha, String estado, String montoIngresado, String vuelto,String monto) throws Exception{
        strSQL = "INSERT INTO cuota VALUES (" + numVenta + ", " + numCuota + ", '" + fecha + "', " + estado + ", " + montoIngresado + " , " + vuelto + ","+monto+");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la venta");
        }
    }
    
    //listar las cuotas pendientes de pago de un cliente
    public void listarcuotasporpagar(String codcliente) throws Exception{
         strSQL = "Select cuota.monto, cuota.fecha, cuota.cancelada from cliente inner join venta on cliente.codcliente=venta.codcliente inner join cuota on cuota.codventa=venta.numventa where cliente.codcliente="+codcliente+" and cuota.cancelada=FALSE;";
        try {
            objConectar.consultarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al listar las cuotas pendientes de pago del cliente");
        }
    }
    
    //pagar una cuota 
    public void pagarcuota(String codcuota) throws Exception{
         strSQL = "UPDATE cuota SET estado=TRUE WHERE numCuota="+codcuota+";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la Cuota");
        }
    }
    
    
}
