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
    
    public Integer generarCodigoLote() throws Exception{
        strSQL = "SELECT COALESCE(max(lote),0)+1 AS codigo FROM comprobante;" ;
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
    
    public Integer conocerLote() throws Exception{
        strSQL = "SELECT COALESCE(max(numero),0) AS codigo FROM comprobante;" ;
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
    
    public Integer generarCodigoNumero() throws Exception{
        strSQL = "SELECT COALESCE(max(numero),0)+1 AS codigo FROM comprobante;" ;
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
    
//    public String generarNumeroComprobante() throws Exception{
//        int numero = generarCodigoNumero();
//        int lote = conocerLote();
//        if (lote == 0 ){
//            lote = generarCodigoLote();
//        } else if (numero == 10000){
//            lote = generarCodigoLote();
//            numero = 1;
//        } 
//    }
}
