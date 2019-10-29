/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.*;

/**
 *
 * @author JCarlos
 */
public class clsTransaccion {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public void ejecutarSentencias() throws Exception{
        try {
            strSQL = "INSERT INTO marca VALUES (7, 'HP', 'hola');";
            objConectar.ejecutarBD(strSQL);
            strSQL = "INSERT INTO categoria VALUES (5, 'PARLANTE', 'dispositivo de audio', true);";
            objConectar.ejecutarBD(strSQL);
            strSQL = "INSERT INTO producto VALUES (7, 'Parlantes wi', 'parlantes inalámbricos', 250, 20, true, 7, 5);";
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar sentencias");
        }
    }
    
    public void ejecutarTransaccion() throws Exception{
        try {
            
        } catch (Exception e) {
            
            throw new Exception("Error al registrar transacción");
        }
    }
}
