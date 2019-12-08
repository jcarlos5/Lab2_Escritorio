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
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */
public class clsDevolucion {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs;
    Connection con;
    Statement sent;
    
    public Integer generarCodigoDev() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT COALESCE(max(numerodev),0)+1 AS codigo FROM devolucion;");
            rs = sentencia.executeQuery();
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
        return 0;
    }
    
    public void registrarDevolucionVenta(String cod, String motivo, String montodev, Integer us, JTable tbl, int numventa ) throws SQLException, Exception{
        try {    
            Integer dias=0;
            strSQL="SELECT CURRENT_DATE-fecha as dias FROM venta WHERE numventa="+ numventa+" estadopago=true" ;
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            while(rs.next()){
                dias=rs.getInt("dias");
            }
            
            if(dias>=0 && dias<=7){
                strSQL = "INSERT INTO devolucion values ("+cod+" , CURRENT_DATE , '"+motivo+"' , "+montodev+" , "+us +");";
                sent.executeUpdate(strSQL);
                for (int i = 0; i < tbl.getRowCount(); i++) {
                    strSQL = "UPDATE producto SET stock=stock + " + tbl.getValueAt(i, 2) + " WHERE codproducto = "+ tbl.getValueAt(i, 0);
                    sent.executeUpdate(strSQL);
                }
                strSQL = "DELETE FROM detalle where numventa = " + numventa;
                sent.executeUpdate(strSQL);
                strSQL = "DELETE FROM cuota where codventa = " + numventa;
                sent.executeUpdate(strSQL);
                strSQL = "DELETE FROM venta where numventa = " + numventa;
                sent.executeUpdate(strSQL);
                 JOptionPane.showMessageDialog(null,"Devolucion realizada correctamente");
            }else{
                JOptionPane.showMessageDialog(null,"Se han excedido la cantidad de Dias permitidos");
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al ejecutar la transacción");
        }finally{
            objConectar.desconectar();
        }
        
    }
}
