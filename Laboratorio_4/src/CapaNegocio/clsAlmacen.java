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
    float porcentaje_ganacia;

    public clsAlmacen() {
        try {
            objConectar.conectar();
            strSQL = "SELECT valor FROM parametro WHERE nombre = Ganancia;";
            rs = objConectar.consultarBD(strSQL);
            while(rs.next()){
                this.porcentaje_ganacia = rs.getFloat("valor");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
       
    public void ActualizarStock(Integer codP, Integer codprov,Integer cantidad ,Float precio) throws Exception{
       try {
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            //sent = con.createStatement();
            //strSQL = "INSERT INTO almacen VALUES(SELECT COALESCE(max(codalmacen),0)+1 FROM codalmacen,"+codprov+","+codP+",CURRENT_DATE,"+precio+");";
            //sent.executeUpdate(strSQL);
            CallableStatement sentencia = con.prepareCall("INSERT INTO almacen VALUES(SELECT COALESCE(max(codalmacen),0)+1 FROM codalmacen, ?, ?,CURRENT_DATE, ?);");
            sentencia.setInt(1, codprov);
            sentencia.setInt(2, codP);
            sentencia.setFloat(3, precio);
            sentencia.executeUpdate();

            //strSQL = "UPDATE producto set precio="+precio+"+"+precio+"*"+porcentaje_ganacia+" ,stock=stock+"+cantidad+" WHERE codProducto="+codP+";";
            //sent.executeUpdate(strSQL);
            sentencia = con.prepareCall("UPDATE producto set precio= ?,stock=stock+? WHERE codProducto= ?;");
            sentencia.setFloat(1, (precio+(precio*porcentaje_ganacia)));
            sentencia.setInt(2, cantidad);
            sentencia.setInt(3, codP);
            sentencia.executeUpdate();
            
            con.commit();
            
            JOptionPane.showMessageDialog(null, "Actualizado Correctamente"); 
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al Actualizar Stock");
        }finally{
            objConectar.desconectar();
        } 
        
    }
}
