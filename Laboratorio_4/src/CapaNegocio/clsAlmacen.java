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
            if(rs.next()){
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
            CallableStatement sentencia;
            sentencia= con.prepareCall("INSERT INTO almacen VALUES (?, ?, current_date, ?, ?, ?, ?, ?);;");
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
                
                sentencia = con.prepareCall("UPDATE producto SET stock = stock+? WHERE codproducto=?");
                sentencia.setInt(1, Integer.parseInt(tblProductos.getValueAt(i,3 ).toString()));
                sentencia.setInt(2, Integer.parseInt(tblProductos.getValueAt(i, 0).toString()));
                sentencia.executeUpdate();
                
                float precioCom = Float.parseFloat(tblProductos.getValueAt(i, 2).toString());
                float ganancia = (precioCom * 20)/100 ;
                float nuevoPrecio = precioCom + ganancia;
                sentencia = con.prepareCall("UPDATE producto SET precio = ? WHERE codproducto=?");
                sentencia.setFloat(1, nuevoPrecio);
                sentencia.setInt(2, Integer.parseInt(tblProductos.getValueAt(i, 0).toString()));
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
            strSQL = "SELECT valor FROM parametro WHERE nombre='Ganacia';";
            rs = objConectar.consultarBD(strSQL);
            while(rs.next()){
                this.porcentaje_ganacia = rs.getFloat("valor");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
       
    
}
