/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
public class clsCuota {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con;
    Statement sent;
    
    //para registrar pagos
    public void registrarCuota(String numVenta, String numCuota, String fecha, String fpago, String estado, String montoIngresado, String vuelto,String monto) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            if (fpago.equalsIgnoreCase("null")){//No le veo sentido al if :/
                CallableStatement sentencia = con.prepareCall("INSERT INTO cuota VALUES(?,?,?,null,?,?,?,?)");
                sentencia.setInt(1,Integer.parseInt(numVenta));
                sentencia.setInt(2,Integer.parseInt(numCuota));
                sentencia.setDate(3, Date.valueOf(fecha));
                sentencia.setBoolean(4, Boolean.parseBoolean(estado));
                sentencia.setFloat(5, Float.parseFloat(montoIngresado));
                sentencia.setFloat(6, Float.parseFloat(vuelto));
                sentencia.setFloat(7, Float.parseFloat(monto));
                sentencia.executeUpdate(); 
                JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            }else {
                CallableStatement sentencia = con.prepareCall("INSERT INTO cuota VALUES(?,?,?,?,?,?,?,?)");
                sentencia.setInt(1,Integer.parseInt(numVenta));
                sentencia.setInt(2,Integer.parseInt(numCuota));
                sentencia.setDate(3, Date.valueOf(fecha));
                sentencia.setDate(4, Date.valueOf(fpago));
                sentencia.setBoolean(5, Boolean.parseBoolean(estado));
                sentencia.setFloat(6, Float.parseFloat(montoIngresado));
                sentencia.setFloat(7, Float.parseFloat(vuelto));
                sentencia.setFloat(8, Float.parseFloat(monto));
                sentencia.executeUpdate(); 
                JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            }
            //objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la venta");
        }
    }
    
    //PRIMERA TRANSACCIÓN DEL ITEM A
    public void registrarCuota(String[][] datos, boolean tipo) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            con.setAutoCommit(false);
            CallableStatement sentencia = con.prepareCall("UPDATE venta SET estadopago = ?  , tipopago = ? WHERE numventa= ?;");
            sentencia.setBoolean(1,tipo);
            sentencia.setBoolean(2,tipo);
            sentencia.setInt(3, Integer.parseInt(datos[0][0]));
            sentencia.executeUpdate();
            //sent = con.createStatement();
            //strSQL="UPDATE venta SET estadopago = " + tipo + ", tipopago = " + tipo + " WHERE numventa=" + datos[0][0] + ";";
            //sent.executeUpdate(strSQL);
            
            if (tipo){
                for (String[] dato : datos) {
                    sentencia = con.prepareCall("INSERT INTO cuota VALUES (?, ?, ?, current_date, ?, ?, ?, ?);");

                    sentencia.setInt(1, Integer.parseInt(dato[0]));
                    sentencia.setInt(2, Integer.parseInt(dato[1]));
                    sentencia.setDate(3, Date.valueOf(dato[2]));
                    sentencia.setBoolean(4, Boolean.parseBoolean(dato[4]));
                    sentencia.setFloat(5, Float.parseFloat(dato[5]));
                    sentencia.setFloat(6, Float.parseFloat(dato[6]));
                    sentencia.setFloat(7, Float.parseFloat(dato[7]));
                    sentencia.executeUpdate();
                    //strSQL="INSERT INTO cuota VALUES (" + dato[0] + ", " + dato[1] + ", '" + dato[2] + "' , " + dato[3] + " , " + dato[4] + ", " + dato[5] + " , " + dato[6] + ","+dato[7]+");";
                    //sent.executeUpdate(strSQL);
                }
            }else{
                for (String[] dato : datos) {
                    sentencia = con.prepareCall("INSERT INTO cuota VALUES (?, ?, ?, null, ?, null, null, ?);");

                    sentencia.setInt(1, Integer.parseInt(dato[0]));
                    sentencia.setInt(2, Integer.parseInt(dato[1]));
                    sentencia.setDate(3, Date.valueOf(dato[2]));
                    sentencia.setBoolean(4, Boolean.parseBoolean(dato[4]));
                    sentencia.setFloat(5, Float.parseFloat(dato[7]));
                    sentencia.executeUpdate();
                    //strSQL="INSERT INTO cuota VALUES (" + dato[0] + ", " + dato[1] + ", '" + dato[2] + "' , " + dato[3] + " , " + dato[4] + ", " + dato[5] + " , " + dato[6] + ","+dato[7]+");";
                    //sent.executeUpdate(strSQL);
                }
            }
            
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception(e.getMessage());
        }
    }
    
    //listar las cuotas pendientes de pago de un cliente
    public ResultSet listarcuotasporpagar(String documento) throws Exception{
     try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM LISTAR_DEUDA(?);");
            sentencia.setString(1,documento);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar las cuotas pendientes de pago del cliente");
        }
    }
    
    //pagar una cuota 
//    public void pagarcuota(int codcuota, int codventa, Float montoIngresado,Float vuelto) throws Exception{
//        strSQL = "UPDATE cuota SET cancelada=TRUE, fechapago=CURRENT_DATE,ingreso="+montoIngresado+",vuelto="+vuelto+" WHERE numcuota="+codcuota+" AND codventa="+codventa+";";
//        try {
//            objConectar.ejecutarBD(strSQL);
//            
//        } catch (Exception e) {
//            throw new Exception("Error al registrar el pago de la Cuota");
//        }
//    }
    
    //SEGUNDA TRANSACCIÓN DEL ITEM A
    public boolean pagarcuota(int codcuota, int codventa, Float montoIngresado,Float vuelto) throws Exception{
        try {
            boolean fin = false;
            objConectar.conectar();
            con = objConectar.getConnection();
            con.setAutoCommit(false);
            //sent = con.createStatement();
            
            CallableStatement sentencia = con.prepareCall("UPDATE cuota SET cancelada=TRUE, fechapago=CURRENT_DATE,ingreso=?,vuelto=? WHERE numcuota=? AND codventa=?;");
            sentencia.setFloat(1,montoIngresado);
            sentencia.setFloat(2,vuelto);
            sentencia.setInt(3,codcuota);
            sentencia.setInt(4,codventa);
            sentencia.executeUpdate();
            //strSQL = "UPDATE cuota SET cancelada=TRUE, fechapago=CURRENT_DATE,ingreso="+montoIngresado+",vuelto="+vuelto+" WHERE numcuota="+codcuota+" AND codventa="+codventa+";";
            //sent.executeUpdate(strSQL);
            
            sentencia = con.prepareCall("SELECT count(*) FROM cuota WHERE cancelada = false AND codventa= ?;");
            sentencia.setInt(1,codventa);
            rs = sentencia.executeQuery();
            //strSQL = "SELECT count(*) FROM cuota WHERE cancelada = false AND codventa="+codventa+";";
            //rs=objConectar.consultarBD(strSQL);
            
            if (rs.next()){
                if(rs.getInt(1)==0){
                    sentencia = con.prepareCall("UPDATE venta SET estadopago = true WHERE numventa= ?;");
                    sentencia.setInt(1,codventa);
                    sentencia.executeUpdate();
                    //strSQL="UPDATE venta SET estadopago = true WHERE numventa=" + codventa + ";";
                    //sent.executeUpdate(strSQL);
                    fin=true;
                }
            }
            
            con.commit();
            return fin;
        } catch (Exception e) {
            con.rollback();
            throw new Exception(e.getMessage());
        }
    }
    
    //Saber si hay deudas
    public int saberdeuda(String documento) throws Exception{
        //strSQL = "SELECT * FROM DEUDA('"+documento+"') as resultado;";
        try{
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM DEUDA(?) as resultado;");
            sentencia.setString(1,documento);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("resultado");
            }
        } catch (Exception e) {
            throw new Exception("Error al saber las deudas del cliente");
        }
        return 0;
    }
    
    public ResultSet datoscliente(String documento) throws Exception{
        //strSQL = "SELECT * FROM DATOSCLIENTE('"+documento+"');";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM DATOSCLIENTE(?);");
            sentencia.setString(1,documento);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
    }
     
     //ventas PAGADAS AL CONTADO
    public float conocerMonto() throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT SUM(c.monto) as MontoTotal FROM venta v INNER JOIN cuota c ON v.numventa = c.codventa WHERE c.fechapago = current_date and v.tipopago = true and c.cancelada = true");
            ResultSet resultado=sentencia.executeQuery();
            while( resultado.next()){
              return resultado.getFloat("MontoTotal");                
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
        return 0;
    }
    
    //Monto TOTAL caja: ventas pagadas al CONTADO + cuotas PAGADAS
    public float conocerMontoCaja() throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT SUM(monto) as MontoTotal FROM cuota WHERE fechapago = current_date and cancelada = true");
            ResultSet resultado=sentencia.executeQuery();
            while (resultado.next()){
                return resultado.getFloat("MontoTotal");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
        return 0;        
    }
    
     //para saber cuanto dinero ingreso en pagos por cuotas de ventas PASADAS
    public float creditosPagados() throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT sum(monto) as monto FROM cuota where fechapago = current_date");
            ResultSet resultado=sentencia.executeQuery();
            while(resultado.next()){
                return resultado.getFloat("monto");                
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
        return 0;
    }
    
    //para saber el monto de solo creditos que hemos dado al dia
    public float conocerMontoCreditos() throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT SUM(c.monto) as MontoTotal FROM venta v INNER JOIN cuota c ON v.numventa = c.codventa WHERE c.fechapago = current_date and v.tipopago = false and c.cancelada = true");
            ResultSet resultado=sentencia.executeQuery();
            while (resultado.next()){
                return resultado.getFloat("MontoTotal");                
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
        return 0;
    }
    
    //para saber el monto de solo creditos que hemos dado al dia
    public float ventasCreditos() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT SUM(total) as MontoTotal FROM venta WHERE fecha= current_date and tipopago = false");
            rs =sentencia.executeQuery();
            while (rs.next()){
               return rs.getFloat("MontoTotal"); 
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
        return 0;
    }            

    public ResultSet cuotasPorVenta(int venta) throws Exception{
        //strSQL = "SELECT * FROM cuota where codventa="+ venta+" ;";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM cuota where codventa= ?;");
            sentencia.setInt(1,venta);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
    }
    
    public Integer cuotasPagadasPorVenta(int venta) throws Exception{
        //strSQL = "SELECT sum(monto) as monto FROM cuota where codventa="+ venta+" and cancelada=true;";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT sum(monto) as monto FROM cuota where codventa= ? and cancelada=true;");
            sentencia.setInt(1,venta);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("monto");
            }
            
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
        return null;
    }
}
