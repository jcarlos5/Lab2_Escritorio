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

public class clsProducto {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con=null;
    Statement sent;
    
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
        try {         
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("INSERT INTO producto VALUES(?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,cod);
            sentencia.setString(2, nom);
            sentencia.setString(3, descrip);
            sentencia.setDouble(4, precio);
            sentencia.setInt(5, stock);
            sentencia.setBoolean(6, vig);
            sentencia.setInt(7, codMarca);
            sentencia.setInt(8, codCate);
            sentencia.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Registrado Correctamente");            
        } catch (Exception e) {
            throw new Exception("Error al registrar Producto");
        }finally{
            objConectar.desconectar();
        } 
    }

    public Integer obtenerCodigoProducto(String nom) throws Exception{
        strSQL = "SELECT codProducto FROM proveedor WHERE nomproducto='" + nom + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if (rs.next()) return rs.getInt("codProducto");
        } catch (Exception e) {
            throw new Exception("Error al obtener el Codigo del Producto");
        }
        return 0;
    }

    public String obtenerCategoriaProducto(Integer cod) throws Exception{
        strSQL = "SELECT categoria.nomcategoria as cate FROM producto INNER JOIN categoria ON categoria.codcategoria=producto.codcategoria WHERE producto.codproucto="+cod+"" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if (rs.next()) return rs.getString("cate");
        } catch (Exception e) {
            throw new Exception("Error al obtener el Nombre de la Categoria de un Producto");
        }
        return null;
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
        Integer cantidad=0;
        
        
        try {
            strSQL="SELECT COUNT(*) AS cantidad FROM detalle WHERE codproducto=" + cod ;
            objConectar.conectar();
            Connection con =objConectar.getConnection();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            
            while(rs.next()){
                cantidad=rs.getInt("cantidad");
            }
            if(cantidad>0){
                String strSQL1="UPDATE producto set vigencia=false WHERE codproducto=" + cod;
                sent.executeUpdate(strSQL1);
                JOptionPane.showMessageDialog(null, " Producto Eliminado Correctamente");
            }else{
                String strSQL1="DELETE FROM producto WHERE codproducto=" + cod;
                sent.executeUpdate(strSQL1);
                JOptionPane.showMessageDialog(null, " Producto Eliminado Correctamente");
            }    
            con.commit();
            
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar al Producto");
        }finally{
            objConectar.desconectar();
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
    
     public ResultSet listarProductosVig() throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM producto p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria WHERE vigencia=true ORDER BY codproducto;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar productos");
        }
    }
    
    public String ProveedorProducto(Integer codP) throws Exception{
     strSQL = "SELECT proveedor.nombre FROM proveedor INNER JOIN alamacen ON alamcen.codproveedor=proveedor.codproveedor INNER JOIN producto ON producto.codproducto=almacen.codproducto WHERE producto.codProducto="+codP+";" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs.getString("nombre");
        } catch (Exception e) {
            throw new Exception("Error al listar El Proveedor de un Producto");
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
    
    
    public void ActualizarStock(Integer codP, Integer cantidad) throws Exception{
       try {
         //registar proveedor
           objConectar.conectar();
           con = objConectar.getConnection();
           CallableStatement sentencia = con.prepareCall("UPDATE producto set stock=stock+? WHERE codProducto=?)");
           sentencia.setInt(1, codP);
           sentencia.setInt(2, cantidad);

          JOptionPane.showMessageDialog(null, "Actualizado Correctamente"); 
        } catch (Exception e) {
            throw new Exception("Error al Actualizar Stock");
        }finally{
            objConectar.desconectar();
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
