/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author JCarlos
 */
public class clsUsuario {
    //Crear instancia de la clase clsJDBC
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs;
    
    public String login(String user, String pass) throws Exception{
        strSQL = "SELECT nombreCompleto FROM USUARIO WHERE nomUsuario='" + user + "' and clave='" + pass + "' and estado = true;";
        try {
            rs = objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getString("nombreCompleto");
            }
        } catch (Exception e) {
            throw new Exception("Error al iniciar Sesión");
        }
        return "";
    }
    
    public boolean validarExistencia(String user) throws Exception{
        strSQL = "SELECT * FROM USUARIO WHERE nomUsuario = '" + user + "';";
        try {
            rs = objConectar.consultarBD(strSQL);
            while (rs.next()){
                return true;
            }
        } catch (Exception e) {
            throw new Exception("Error al iniciar Sesión");
        }
        return false;
    }
    
    public boolean validarVigencia(String user) throws Exception{
        strSQL = "SELECT estado FROM USUARIO WHERE nomUsuario = '" + user + "';";
        try {
            rs = objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getBoolean("estado");
            }
        } catch (Exception e) {
            throw new Exception("Error al iniciar Sesión");
        }
        return false;
    }
    
    public String obtenerPreguntaSecreta(String usu) throws Exception{
        strSQL = "select pregunta from usuario where nomusuario='" + usu + "'";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getString("pregunta");
            }
        } catch (Exception e) {
            throw new Exception("Error al validar Usuario");
        }
        return "";
    }
    
    public String validarPreguntaSecreta(String usu, String rpta) throws Exception{
        strSQL = "select nombrecompleto from usuario where nomusuario='" + usu + "' and UPPER(respuesta)=UPPER('" + rpta + "') and estado=true";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getString("nombrecompleto");
            }
        } catch (Exception e) {
            throw new Exception("Error al validar usuario");
        }
        return "";
    }
    
    public void cambiarContraseña(String nuevaCon, String nombre) throws Exception{
        strSQL="UPDATE usuario SET clave='" + nuevaCon + "' where nomusuario='" + nombre + "'";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al cambiar la contraseña..");
        }
    }
    
    public void registrarInicio(String user) throws Exception{
        strSQL="INSERT INTO movimiento(codusuario, fecha, hora, estado) VALUES ((SELECT codusuario FROM usuario WHERE nomusuario = '" + user + "'), CURRENT_DATE, CURRENT_TIME, TRUE);";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error guardar Inicio de Sesión");
        }
    }
    
    public void registrarCierre(String user) throws Exception{
        strSQL="UPDATE movimiento SET estado = false where codusuario = (SELECT codusuario FROM usuario WHERE nomusuario = '" + user + "')";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error guardar Cierre de Sesión");
        }
    }
    
    public void cerrarSesiones() throws Exception{
        strSQL="UPDATE movimiento SET estado = false;";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error cerrar Sesiones");
        }
    }
    
    public String[] ultimaSesion(String user) throws Exception{
        String sesion[] = new String[2];
        strSQL= "select * from movimiento " +
                "where codusuario = (SELECT codusuario FROM usuario WHERE nomusuario = '" + user + "') and estado = false " +
                "order by fecha desc, hora desc limit 1;";
        try {
            rs=objConectar.consultarBD(strSQL);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss a");
            while(rs.next()){
                sesion[0] = formatoFecha.format(rs.getDate("fecha"));
                sesion[1] = formatoHora.format(rs.getTime("hora"));
                return sesion;
            }
        } catch (Exception e) {
            throw new Exception("Error al consultar la BD");
        }
        return null;
    }
    
    public String sesionActual(String user) throws Exception{
        String sesion = "";
        strSQL= "select * from movimiento " +
                "where codusuario = (SELECT codusuario FROM usuario WHERE nomusuario = '" + user + "') and estado = true " +
                "order by fecha desc, hora desc limit 1;";
        try {
            rs=objConectar.consultarBD(strSQL);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss a");
            while(rs.next()){
                sesion = formatoFecha.format(rs.getDate("fecha")) + " a las " + formatoHora.format(rs.getTime("hora"));
                return sesion;
            }
        } catch (Exception e) {
            throw new Exception("Error al consultar la BD");
        }
        return null;
    }
    
    public String contarSesiones(String user) throws Exception{
        strSQL= "select count(*) from movimiento " +
                "where codusuario = (SELECT codusuario FROM usuario WHERE nomusuario = '" + user + "') group by codusuario;";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return String.valueOf(rs.getInt(1));
            }
        } catch (Exception e) {
            throw new Exception("Error al consultar la BD");
        }
        return "";
    }
    
    public Integer generarCodigoUsuario() throws Exception{
        strSQL = "SELECT COALESCE(max(codusuario),0)+1 AS codigo FROM usuario;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de usuario");
        }
        return 0;
    }
    
    public String generarClave(){
        return String.valueOf((int)(Math.random() * 100000) + 11111);
    }
    
    public void registrar(Integer cod, String user, String clave, String nom, String cargo, Boolean vig) throws Exception{
        strSQL="INSERT INTO usuario (codusuario, nomusuario, clave, nombrecompleto, cargo, estado) VALUES(" + cod + ",'" + user + "','" + clave + "','" + nom + "', '" + cargo + "', " + vig + ");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar usuario");
        }
    }
    
    //CON PREGUNTA
    public void registrar(Integer cod, String user, String clave, String nom, String cargo, Boolean vig, String preg, String rpta) throws Exception{
        strSQL="INSERT INTO usuario (codusuario, nomusuario, clave, nombrecompleto, cargo, estado, pregunta, respuesta) VALUES(" + cod + ",'" + user + "','" + clave + "','" + nom + "', '" + cargo + "', " + vig + ", '" + preg + "', '" + rpta + "');";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar usuario");
        }
    }
    
    //CON PREGUNTA Y CONTRASEÑA
    public void registrarPe(Integer cod, String user, String clave, String nom, String cargo, Boolean vig, String preg, String rpta) throws Exception{
        strSQL="INSERT INTO usuario (codusuario, nomusuario, clave, nombrecompleto, cargo, estado, pregunta, respuesta) VALUES(" + cod + ",'" + user + "','" + clave + "','" + nom + "', '" + cargo + "', " + vig + ", '" + preg + "', '" + rpta + "');";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar usuario");
        }
    }
    
    public ResultSet buscarUsuario(Integer cod) throws Exception{
        strSQL = "SELECT * FROM usuario WHERE codusuario=" + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar usuario");
        }
    }
    
    public void eliminarUsuario(int cod) throws Exception {
        strSQL="DELETE FROM usuario WHERE codusuario=" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar usuario");
        }
    }

    public ResultSet listarUsuarios() throws Exception{
        strSQL = "SELECT * FROM usuario ORDER BY codusuario;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar usuarios");
        }
    }
    
    public void modificarUsuario(Integer cod, String user, String nom, String cargo, Boolean vig) throws Exception {
        strSQL="UPDATE usuario SET nomusuario = '" + user + "', nombrecompleto = '" + nom + "', cargo = '" + cargo + "', estado = " + vig + " WHERE codusuario =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar usuario");
        }
    }
    
    //CON PREGUNTA
    public void modificarUsuario(Integer cod, String user, String nom, String cargo, Boolean vig, String preg, String rpta) throws Exception {
        strSQL="UPDATE usuario SET nomusuario = '" + user + "', nombrecompleto = '" + nom + "', cargo = '" + cargo + "', estado = " + vig + ", pregunta = '" + preg + "', respuesta = '" + rpta + "' WHERE codusuario =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar usuario");
        }
    }
    
    //CON PREGUNTA Y CONTRASEÑA
    public void modificarUsuario(Integer cod, String user, String nom, String clave, String cargo, Boolean vig, String preg, String rpta) throws Exception {
        strSQL="UPDATE usuario SET nomusuario = '" + user + "', clave = '" + clave + "', nombrecompleto = '" + nom + "', cargo = '" + cargo + "', estado = " + vig + ", pregunta = '" + preg + "', respuesta = '" + rpta + "' WHERE codusuario =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar usuario");
        }
    }
    
    public void cambiarPreguntaSecreta(String user, String pregunta, String rpta) throws Exception{
        strSQL="UPDATE usuario SET pregunta = '" + pregunta + "', respuesta = '" + rpta + "' WHERE nomusuario ='" + user + "';";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar usuario");
        }
    }
    
    public void darDeBajaUsuario(Integer cod) throws Exception {
        strSQL="UPDATE usuario SET estado = false WHERE codusuario =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de Baja al usuario");
        }
    }
    
    public ResultSet getDatos(String user) throws Exception{
        strSQL = "SELECT * FROM usuario WHERE nomusuario = '" + user + "';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar datos de usuario");
        }
    }
}
