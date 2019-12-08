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
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT c.*, t.nombre FROM CLIENTE c INNER JOIN TIPO_CLIENTE t ON c.codtipo = t.codtipo ORDER BY codcliente");
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    public ResultSet filtrarClientes(String cadena) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM CLIENTE WHERE dni LIKE ? OR ruc LIKE ?");
            sentencia.setString(1, cadena);
            sentencia.setString(2, cadena);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    public ResultSet listarTipoClientes() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM TIPO_CLIENTE");
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    public Integer generarCodigoCliente() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT COALESCE(max(codcliente),0)+1 AS codigo FROM cliente");
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()){
               return resultado.getInt("codigo"); 
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
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
        //strSQL = "SELECT * FROM cliente WHERE codcliente=" + cod + ";";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM cliente WHERE codcliente= ?;");
            sentencia.setInt(1,cod);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
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
            CallableStatement sentencia = con.prepareCall("SELECT COUNT(*) FROM venta WHERE codcliente= ?;");
            sentencia.setInt(1,cod);
            rs = sentencia.executeQuery();
            //sent=con.createStatement();
            //strSQL="SELECT COUNT(*) FROM venta WHERE codcliente=" + cod + ";";
            //rs=sent.executeQuery(strSQL);
            
            if(rs.next()){
                if(rs.getInt(1)>0){
                    ResultSet rs1;
                    sentencia = con.prepareCall("SELECT COUNT(*) FROM venta WHERE estadopago=false and codcliente= ?;");
                    sentencia.setInt(1,cod);
                    rs1 = sentencia.executeQuery();
                    //strSQL="SELECT COUNT(*) FROM venta WHERE estadopago=false and codcliente=" + cod +";";
                    //rs1=sent.executeQuery(strSQL);
                    if(rs1.next()){
                        if(rs1.getInt(1)>0){
                            elim = null;
                        }else{
                            sentencia = con.prepareCall("UPDATE cliente set vigencia=false WHERE codcliente= ?;");
                            sentencia.setInt(1,cod);
                            sentencia.executeUpdate();
                            //strSQL="UPDATE cliente set vigencia=false WHERE codcliente=" + cod + ";";
                            //sent.executeUpdate(strSQL);
                            elim = false;
                        }
                    }
                }else{
                    sentencia = con.prepareCall("DELETE FROM cliente WHERE codcliente= ?;");
                    sentencia.setInt(1,cod);
                    sentencia.executeUpdate();
                    //strSQL="DELETE FROM cliente WHERE codcliente=" + cod + ";";
                    //sent.executeUpdate(strSQL);
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
        try {
            if (codtipo==1){
                objConectar.conectar();
                Connection con = objConectar.getConnection();
                CallableStatement sentencia = con.prepareCall("UPDATE cliente SET dni=?, ruc =?, nombres=?, telefono=?, correo=?, direccion=?, vigencia= ?, codtipo=? WHERE codcliente =?");
                sentencia.setString(1, dni);
                sentencia.setString(2, null); //"'null'"
                sentencia.setString(3, nom);
                sentencia.setString(4, tel);
                sentencia.setString(5, correo);
                sentencia.setString(6,direccion);
                sentencia.setBoolean(7, vig);
                sentencia.setInt(8, codtipo);
                sentencia.setInt(9,Integer.parseInt(cod));
                sentencia.executeUpdate(); 
                JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
            }
            
            if (codtipo==2){
                objConectar.conectar();
                Connection con = objConectar.getConnection();
                CallableStatement sentencia = con.prepareCall("UPDATE cliente SET dni=?, ruc =?, nombres=?, telefono=?, correo=?, direccion=?, vigencia= ?, codtipo=? WHERE codcliente =?");
                sentencia.setString(1, null);//"'null'"
                sentencia.setString(2, ruc); 
                sentencia.setString(3, nom);
                sentencia.setString(4, tel);
                sentencia.setString(5, correo);
                sentencia.setString(6,direccion);
                sentencia.setBoolean(7, vig);
                sentencia.setInt(8, codtipo);
                sentencia.setInt(9,Integer.parseInt(cod));
                sentencia.executeUpdate(); 
                JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            }
            
            if (codtipo==3){
                objConectar.conectar();
                Connection con = objConectar.getConnection();
                CallableStatement sentencia = con.prepareCall("UPDATE cliente SET dni=?, ruc =?, nombres=?, telefono=?, correo=?, direccion=?, vigencia= ?, codtipo=? WHERE codcliente =?");
                sentencia.setString(1, dni);
                sentencia.setString(2, ruc); 
                sentencia.setString(3, nom);
                sentencia.setString(4, tel);
                sentencia.setString(5, correo);
                sentencia.setString(6,direccion);
                sentencia.setBoolean(7, vig);
                sentencia.setInt(8, codtipo);
                sentencia.setInt(9,Integer.parseInt(cod));
                sentencia.executeUpdate(); 
                JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            }
            
        } catch (Exception e) {
            throw new Exception("Error al registrar Cliente");
        }finally{
            objConectar.desconectar();
        } 
    }
    
    public void darDeBajaCliente(Integer cod) throws Exception {
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("UPDATE cliente SET vigencia = false WHERE codcliente=?");
            sentencia.setInt(1, cod);
            sentencia.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    public ResultSet buscarCliente(String doc) throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT c.*, t.nombre FROM (SELECT * FROM cliente WHERE dni = ? OR ruc = ?) c INNER JOIN TIPO_CLIENTE t ON c.codtipo = t.codtipo");
            sentencia.setString(1, doc);
            sentencia.setString(2, doc);
            ResultSet resultado=sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    public ResultSet verificarDocumento(int cod) throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT dni, ruc FROM cliente WHERE codcliente = ?");
            sentencia.setInt(1, cod);
            ResultSet resultado=sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }        
    }

    
///No recuerso si iva así esta función :/    
    public boolean isAcreditable(String cod) throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT estadopago FROM venta WHERE codcliente =? and estadopago = false and tipopago = false");
            sentencia.setInt(1, Integer.parseInt(cod));
            ResultSet resultado=sentencia.executeQuery();
            while (resultado.next()){
               return resultado.getBoolean("estadopago");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
        return false;
    }
    
}
