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
            //strSQL="SELECT CURRENT_DATE-fecha as dias FROM venta WHERE numventa="+ numventa+" estadopago=true" ;
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            //sent=con.createStatement();
            //rs=sent.executeQuery(strSQL);
            CallableStatement sentencia = con.prepareCall("SELECT CURRENT_DATE-fecha as dias FROM venta WHERE numventa= ? estadopago=true");
            sentencia.setInt(1, numventa);
            rs = sentencia.executeQuery();
            while(rs.next()){
                dias=rs.getInt("dias");
            }
            
            if(dias>=0 && dias<=7){
                //strSQL = "INSERT INTO devolucion values ("+cod+" , CURRENT_DATE , '"+motivo+"' , "+montodev+" , "+us +");";
                //sent.executeUpdate(strSQL);
                sentencia = con.prepareCall("INSERT INTO devolucion values (? , CURRENT_DATE , ? , ?, ?);");
                sentencia.setInt(1, Integer.parseInt(cod));
                sentencia.setString(2, motivo);
                sentencia.setFloat(3, Float.parseFloat(montodev));
                sentencia.setInt(4, us);
                sentencia.executeUpdate();
                for (int i = 0; i < tbl.getRowCount(); i++) {
                    //strSQL = "UPDATE producto SET stock=stock + " + tbl.getValueAt(i, 2) + " WHERE codproducto = "+ tbl.getValueAt(i, 0);
                    //sent.executeUpdate(strSQL);
                    sentencia = con.prepareCall("UPDATE producto SET stock=stock + ? WHERE codproducto = ? ;");
                    sentencia.setInt(1, Integer.parseInt(tbl.getValueAt(i, 2).toString()));
                    sentencia.setInt(2, Integer.parseInt(tbl.getValueAt(i, 0).toString()));
                    sentencia.executeUpdate();
                }
                //strSQL = "DELETE FROM detalle where numventa = " + numventa;
                //sent.executeUpdate(strSQL);
                sentencia = con.prepareCall("DELETE FROM detalle where numventa = ?;");
                sentencia.setInt(1, numventa);
                sentencia.executeUpdate();
                //strSQL = "DELETE FROM cuota where codventa = " + numventa;
                //sent.executeUpdate(strSQL);
                sentencia = con.prepareCall("DELETE FROM cuota where codventa = ?;");
                sentencia.setInt(1, numventa);
                sentencia.executeUpdate();
                //strSQL = "DELETE FROM venta where numventa = " + numventa;
                //sent.executeUpdate(strSQL);
                sentencia = con.prepareCall("DELETE FROM venta where numventa = ?;");
                sentencia.setInt(1, numventa);
                sentencia.executeUpdate();
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
