/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.CallableStatement;
import java.sql.Connection;
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
public class clsAlmacen {
 
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con=null;
    Statement sent;
    float porcentaje_ganacia=(float) 0.30;
       
    public void ActualizarStock(Integer codP, Integer codprov,Integer cantidad ,Float precio) throws Exception{
       try {
           objConectar.conectar();
           con=objConectar.getConnection();
           con.setAutoCommit(false);
           sent = con.createStatement();
           strSQL = "INSERT INTO almacen VALUES(SELECT COALESCE(max(codalmacen),0)+1 FROM codalmacen,"+codprov+","+codP+",CURRENT_DATE,"+precio+");";
           sent.executeUpdate(strSQL);
           strSQL = "UPDATE producto set precio="+precio+"*"+porcentaje_ganacia+" ,stock=stock+"+cantidad+" WHERE codProducto="+codP+";";
           sent.executeUpdate(strSQL);
          JOptionPane.showMessageDialog(null, "Actualizado Correctamente"); 
          con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al Actualizar Stock");
        }finally{
            objConectar.desconectar();
        } 
        
    }
    
    
    
}
