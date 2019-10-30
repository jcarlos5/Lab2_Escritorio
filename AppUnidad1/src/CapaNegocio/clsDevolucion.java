/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;

/**
 *
 * @author Sara
 */
public class clsDevolucion {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs;
    Connection con;
    Statement sent;
    
    public Integer generarCodigoDev() throws Exception{
        strSQL = "SELECT COALESCE(max(numerodev),0)+1 AS codigo FROM devolucion;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de marca");
        }
        return 0;
    }
    
    public void registrarDevolucionVenta(String cod, String motivo, String montodev, Integer us, JTable tbl, int numventa ) throws SQLException, Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            con.setAutoCommit(false);
            sent = con.createStatement();
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
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al ejecutar la transacción");
        }finally{
            objConectar.desconectar();
        }
        
    }
}
