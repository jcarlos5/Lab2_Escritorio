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
    public void registrarCuota(String numVenta, String numCuota, String fecha, String estado, String montoIngresado, String vuelto) throws Exception{
        strSQL = "INSERT INTO cuota VALUES (" + numVenta + ", " + numCuota + ", '" + fecha + "', " + estado + ", " + montoIngresado + " , " + vuelto + ");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la venta");
        }
    }
}
