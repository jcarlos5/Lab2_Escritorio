/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.ResultSet;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */

public class clsProducto {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public Integer generarCodigoProducto() throws Exception{
        strSQL = "SELECT COALESCE(max(codproducto),0)+1 AS codigo FROM producto;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de producto");
        }
        return 0;
    }
    
    public void registrar(Integer cod, String nom, String descrip, double precio, int stock, Boolean vig, int codMarca, int codCate) throws Exception{
        strSQL="INSERT INTO producto VALUES(" + cod + ",'" + nom + "','" + descrip + "'," + precio + ", " + stock + ", " + vig + ", " + codMarca + ", " + codCate +");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el producto");
        }
    }
    
    public ResultSet buscarProducto(Integer cod) throws Exception{
        strSQL = "SELECT * FROM producto WHERE codproducto=" + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar producto");
        }
    }
    
    public void eliminarProducto(Integer cod) throws Exception {
        strSQL="DELETE FROM producto WHERE codproducto=" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar el producto");
        }
    }

    public ResultSet listarProductos() throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM producto p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria ORDER BY codproducto;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar productos");
        }
    }
    
    public void modificarProducto(Integer cod, String nombre, String descrip, double precio, int stock, boolean vigencia, int codMarca, int codCategoria) throws Exception {
        strSQL="UPDATE producto SET nomproducto = '" + nombre + "', descripcion = '" + descrip + "', precio='" + precio + "', stock='" + stock + "', vigencia = " + vigencia + ", codmarca = '" + codMarca + "', codcategoria = '" + codCategoria + "' WHERE codProducto ='" + cod + "';";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar el producto");
        }
    }
    
    public void darDeBajaProducto(Integer cod) throws Exception {
        strSQL="UPDATE producto SET vigencia = false WHERE codproducto =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de Baja el producto");
        }
    }
    
    public ResultSet filtrarMarca(int marca, String nom, int min, int max) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codmarca = " + marca + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public ResultSet filtrarCategoria(int categoria, String nom, int min, int max) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codcategoria = " + categoria + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public ResultSet filtrar(int marca, int categoria, String nom, int min, int max) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codmarca = " + marca + " AND codcategoria = " + categoria + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public ResultSet filtrar(String nom, int min, int max) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public ResultSet filtrar(String nom) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND vigencia=true) p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public int getPrecioMax() throws Exception{
        strSQL = "SELECT MAX(precio) FROM producto;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener precios");
        }
        return 0;
    }
    
    public int getStock(int cod) throws Exception{
        strSQL = "SELECT stock FROM producto WHERE codproducto = " + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener stock");
        }
        return 0;
    }
}
