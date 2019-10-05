/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author Sara
 */
public class clsPago {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public Integer generarCodigoPago() throws Exception{
        strSQL = "SELECT COALESCE(max(numventa),0)+1 AS codigo FROM pago;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de venta");
        }
        return 0;
    }
    //para registrar pagos
    public void registrarPago(Integer codPago,Integer codTipoPago,float montoIngresado, float vuelto, Integer codVenta, Boolean estadoPago) throws Exception{
        strSQL = "INSERT INTO pago VALUES ("+codPago+", "+montoIngresado+" , "+vuelto+" , "+codTipoPago+ " , "+codVenta +" , true );";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la venta");
        }
    }
    //para generar pagos (cuotas) si se elige la opcion de pago al CREDITO 
    /*numCuotas = numero de cuotas
    tipoCuota= mensual o semanal
    fecha_inicio = fecha en que comienza a pagar
    TODAS LAS VARIABLES ESCIFICADAS EN EL PDF
    */
    public void generarPago(Integer codVenta, Integer numCuotas, Integer tipoCuota, Date fecha_inicio) throws Exception{
        strSQL = "";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar el pago de la venta");
        }
    }
    //para saber cuanto dinero ingreso en pagos por dia SIN contar credito
    public float conocerMontoCaja() throws Exception{
        float monto=0;
        strSQL = "SELECT * FROM pago WHERE fecha=CURRENT_DATE and estadoPago=true;";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                monto+=rs.getFloat("monto");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
        return monto;
    }
    //para saber cuanto dinero ingreso en pagos por dia contando los creditos
    public float conocerMonto() throws Exception{
        float monto=0;
        strSQL = "SELECT * FROM pago WHERE fecha=CURRENT_DATE;";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                monto+=rs.getFloat("monto");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
        return monto;
    }
    //para saber el monto de solo creditos que hemos dado al dia
    public float conocerMontoCreditos() throws Exception{
        float monto=0;
        strSQL = "SELECT * FROM pago WHERE fecha=CURRENT_DATE and estadoPago=false;";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                monto+=rs.getFloat("monto");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
        return monto;
    }
    //para conocer el numero de deudas que tiene un cliente 
    public int conocerDeuda(int codcliente) throws Exception{
        int deuda=0;
        strSQL = "SELECT count(p.codpago) FROM pago p inner join venta v on p.numventa=v.numventa inner join cliente c on v.codcliente=c.codcliente group by p.codpago "
                + "WHERE p.estadopago=false and c.codcliente="+codcliente+";";
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                deuda+=1;
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
        return deuda;
    }
}
