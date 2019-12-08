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
import javax.swing.JTable;

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
    
    public Integer generarCodigoVenta() throws Exception{
        strSQL = "SELECT COALESCE(max(codInven),0)+1 AS codigo FROM almacen;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código del Nuevo Inventario");
        }
        return 0;
    }
    
    public void registrar(int numIven, String bolCom, int prov, float total, JTable tblProductos) throws Exception{
        try {
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            CallableStatement sentencia = con.prepareCall("INSERT INTO almacen VALUES (?, ?, current_date, ?, ?, ?, ?, ?);;");
            int ctd = tblProductos.getRowCount();
            for (int i=0; i<ctd; i++){
                sentencia.setInt(1, numIven);
                sentencia.setString(2, bolCom);
                sentencia.setInt(3, prov);
                sentencia.setInt(4, Integer.parseInt(tblProductos.getValueAt(i, 0).toString()));
                sentencia.setInt(5, Integer.parseInt(tblProductos.getValueAt(i, 3).toString()));
                sentencia.setFloat(6, Float.parseFloat(tblProductos.getValueAt(i, 2).toString()));
                sentencia.setFloat(7,total);
                sentencia.executeUpdate();
            }            
            con.commit();           
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al guardar Inventario");
        }finally{
            objConectar.desconectar();
        }
    }

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
