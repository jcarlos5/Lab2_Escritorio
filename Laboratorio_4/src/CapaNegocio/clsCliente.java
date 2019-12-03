/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/*
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
*/

public class clsCliente {
    //Crear instancia de la clase clsJDBC
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs;
    Connection con;
    Statement sent;
    
    public ResultSet listarClientes() throws Exception{
        strSQL = "SELECT c.*, t.nombre FROM CLIENTE c INNER JOIN TIPO_CLIENTE t ON c.codtipo = t.codtipo ORDER BY codcliente;";
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Clientes");
        }
    }
    
    public ResultSet filtrarClientes(String cadena) throws Exception{
        strSQL = "SELECT * FROM CLIENTE WHERE dni LIKE '" + cadena + "%' OR ruc LIKE '" + cadena + "%';";
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Clientes");
        }
    }
    
    public ResultSet listarTipoClientes() throws Exception{
        strSQL = "SELECT * FROM TIPO_CLIENTE;";
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Tipos de Cliente");
        }
    }
    
    public Integer generarCodigoCliente() throws Exception{
        strSQL = "SELECT COALESCE(max(codcliente),0)+1 AS codigo FROM cliente;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de cliente");
        }
        return 0;
    }
    
    public void registrar(String cod, String dni, String ruc, String nom, String tel, String correo, String direccion, Boolean vig, int codtipo) throws Exception{
       try {
            if (codtipo==1){
            
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("INSERT INTO clientes VALUES(?,?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,Integer.parseInt(cod));
            sentencia.setInt(2,codtipo);
            sentencia.setString(3, dni);
            sentencia.setString(4, null); //"'null'"
            sentencia.setString(5, nom);
            sentencia.setString(6, tel);
            sentencia.setString(7, correo);
            sentencia.setString(8,direccion);
            sentencia.setBoolean(9, vig);
            sentencia.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
            }
            
            if (codtipo==2){
                
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("INSERT INTO clientes VALUES(?,?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,Integer.parseInt(cod));
            sentencia.setInt(2,codtipo);
            sentencia.setString(3, dni);
            sentencia.setString(4, null); //"'null'"
            sentencia.setString(5, nom);
            sentencia.setString(6, tel);
            sentencia.setString(7, correo);
            sentencia.setString(8,direccion);
            sentencia.setBoolean(9, vig);
            sentencia.executeUpdate();  
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
            }
            
            if (codtipo==3){
                
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("INSERT INTO clientes VALUES(?,?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,Integer.parseInt(cod));
            sentencia.setInt(2,codtipo);
            sentencia.setString(3, dni);
            sentencia.setString(4, null); //"'null'"
            sentencia.setString(5, nom);
            sentencia.setString(6, tel);
            sentencia.setString(7, correo);
            sentencia.setString(8,direccion);
            sentencia.setBoolean(9, vig);
            sentencia.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
            }
            
        } catch (Exception e) {
            throw new Exception("Error al registrar Cliente");
        }finally{
            objConectar.desconectar();
        } 
        
    }
    
    
    public ResultSet buscarCliente(Integer cod) throws Exception{
        strSQL = "SELECT * FROM cliente WHERE codcliente=" + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
    
    //OCTAVA TRANSACCIÓN DLE ÍTEM A
    public Boolean eliminarCliente(Integer cod) throws Exception {
//        strSQL="DELETE FROM cliente WHERE codcliente=" + cod + ";";
//        try {
//            objConectar.ejecutarBD(strSQL);
//        } catch (Exception e) {
//            throw new Exception("Error al eliminar el cliente");
//        }
        try {
            Boolean elim=null;
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            sent=con.createStatement();
            strSQL="SELECT COUNT(*) FROM venta WHERE codcliente=" + cod + ";";
            rs=sent.executeQuery(strSQL);
            
            if(rs.next()){
                if(rs.getInt(1)>0){
                    ResultSet rs1;
                    strSQL="SELECT COUNT(*) FROM venta WHERE estadopago=false and codcliente=" + cod +";";
                    rs1=sent.executeQuery(strSQL);
                    if(rs1.next()){
                        if(rs1.getInt(1)>0){
                            elim = null;
                        }else{
                            strSQL="UPDATE cliente set vigencia=false WHERE codcliente=" + cod + ";";
                            sent.executeUpdate(strSQL);
                            elim = false;
                        }
                    }
                }else{
                    strSQL="DELETE FROM cliente WHERE codcliente=" + cod + ";";
                    sent.executeUpdate(strSQL);
                    elim = true;
                }
            }
            con.commit();
            return elim;
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar al cliente");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void modificarCliente(String cod, String dni, String ruc, String nom, String tel, String correo, String direccion, Boolean vig, int codtipo) throws Exception {
        
        switch (codtipo) {
            case 1:
                strSQL="UPDATE cliente SET dni='" + dni + "', ruc = null, nombres='" + nom + "', telefono='" + tel + "', correo='" + correo + "', direccion='" + direccion + "', vigencia= " + vig + ", codtipo=" + codtipo + " WHERE codcliente = " + cod + ";";
                break;
            case 2:
                strSQL="UPDATE cliente SET dni=null,  ruc='" + ruc + "', nombres='" + nom + "', telefono='" + tel + "', correo='" + correo + "', direccion='" + direccion + "', vigencia= " + vig + ", codtipo=" + codtipo + " WHERE codcliente = " + cod + ";";
                break;
            default:
                strSQL="UPDATE cliente SET dni='" + dni + "', ruc='" + ruc + "', nombres='" + nom + "', telefono='" + tel + "', correo='" + correo + "', direccion='" + direccion + "', vigencia= " + vig + ", codtipo=" + codtipo + " WHERE codcliente = " + cod + ";";
                break;
        }
        
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar cliente");
        }
    }
    
    public void darDeBajaCliente(Integer cod) throws Exception {
        strSQL="UPDATE cliente SET vigencia = false WHERE codcliente =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de Baja al cliente");
        }
    }
    
    public ResultSet buscarCliente(String doc) throws Exception{
        strSQL = "SELECT c.*, t.nombre FROM (SELECT * FROM cliente WHERE dni = '" + doc + "' OR ruc = '" + doc + "') c INNER JOIN TIPO_CLIENTE t ON c.codtipo = t.codtipo;";
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Clientes");
        }
    }
    
    public ResultSet verificarDocumento(int cod) throws Exception{
        strSQL = "SELECT dni, ruc FROM cliente WHERE codcliente = "+ cod +";";
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al verificar Cliente");
        }
    }
    
    public boolean isAcreditable(String cod) throws Exception{
        strSQL = "SELECT * FROM venta WHERE codcliente = " + cod + " and estadopago = false and tipopago = false;";
        try {
            rs = objConectar.consultarBD(strSQL);
            if(rs.next()){
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            throw new Exception("Error al verificar Cliente");
        }
    }
}
