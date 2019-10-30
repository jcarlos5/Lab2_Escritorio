/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */
public class clsMarca {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public Integer generarCodigoMarca() throws Exception{
        strSQL = "SELECT COALESCE(max(codMarca),0)+1 AS codigo FROM marca;" ;
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
    
    public void registrar(Integer cod, String nom, Boolean vig) throws Exception{
        strSQL="INSERT INTO MARCA VALUES(" + cod + ",'" + nom + "'," + vig + ");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la marca");
        }
    }
    
    public ResultSet buscarMarca(Integer cod) throws Exception{
        strSQL = "SELECT * FROM marca WHERE codMarca=" + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
    /*
    public void eliminarMarca(Integer cod) throws Exception {
        strSQL="DELETE FROM marca WHERE codMarca=" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar la marca");
        }
    }
    */
    
    //Eliminar con transacción
    public void eliminarMarca(Integer cod) throws Exception {
        try {
            boolean logica = false;
            strSQL="SELECT * FROM producto WHERE codmarca=" + cod + ";";
            rs = objConectar.consultarBD(strSQL);
            while(rs.next()){
                logica = true;
            }
            if(logica){
                objConectar.conectar();
                objConectar.getConnection().setAutoCommit(false);
                
                Statement sent = objConectar.getConnection().createStatement();
                strSQL="UPDATE producto SET vigencia = false WHERE codMarca=" + cod + ";";
                sent.executeUpdate(strSQL);
                
                sent = objConectar.getConnection().createStatement();
                strSQL="UPDATE marca SET vigencia = false WHERE codMarca=" + cod + ";";
                sent.executeUpdate(strSQL);
                
                objConectar.getConnection().commit();
            }else{
                strSQL="DELETE FROM marca WHERE codMarca=" + cod + ";";
                objConectar.ejecutarBD(strSQL);
            }
        } catch (Exception e) {
            objConectar.getConnection().rollback();
            throw new Exception(e.getMessage());
        }
    }

    public ResultSet listarMarcas() throws Exception{
        strSQL = "SELECT * FROM marca;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
    public void modificarMarca(Integer cod, String nombre, boolean vigencia) throws Exception {
        strSQL="UPDATE marca SET nommarca = '" + nombre + "', vigencia = " + vigencia + " WHERE codmarca =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar la marca");
        }
    }
    
    public void darDeBajaMarca(Integer cod) throws Exception {
        strSQL="UPDATE marca SET vigencia = false WHERE codmarca =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de Baja la marca");
        }
    }
    
    public int getCodigo(String nombre) throws Exception{
        strSQL = "SELECT codmarca FROM marca WHERE nommarca='"+nombre+"';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("codmarca");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar marcas");
        }
        return 0;
    }
    
    public String getNombre(int codigo) throws Exception{
        strSQL = "SELECT nommarca FROM marca WHERE codmarca='"+codigo+"';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getString("nommarca");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar marcas");
        }
        return null;
    }
}
