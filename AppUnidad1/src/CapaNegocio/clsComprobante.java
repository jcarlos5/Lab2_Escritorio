/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Sara
 */
public class clsComprobante {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con;
    Statement sent;
    
    public Integer generarCodigoDev() throws Exception{
        strSQL = "SELECT COALESCE(max(idcomprobante),0)+1 AS codigo FROM comprobante;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de comprobante");
        }
        return 0;
    }
    
    public void registrarComprobante(Integer id, int venta, String serie, String numero, Boolean tipo) throws Exception{
        strSQL="INSERT INTO COMPROBANTE VALUES(" + id + "," + venta +",'"+serie+ "' ,'"+numero+"', " + tipo+");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el comprobante");
        }
    }
}
