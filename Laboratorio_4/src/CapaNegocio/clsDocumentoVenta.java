/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaNegocio.*;
import CapaDatos.clsJDBC;
import Reporte.Lista;
import Reporte.Lista;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */
public class clsDocumentoVenta {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con;
    Statement sent;
    
    public ResultSet getDatos(int num) throws Exception{
        try {
            objConectar.conectar();
            Connection con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM fn_generar_boleta(?)");
            sentencia.setInt(1, num);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void crear_Boleta(ResultSet rs) throws JRException, Exception{
        int i=0;
        ResultSet p = null;
        Lista datasource = new Lista();
        String[] data = datasource.agregar(rs);
        
        Map<String, Object> param = new HashMap<>();
        param.put("NUMVENTA",data[0]);
        param.put("NOM_CLIENTE",data[1]);
        param.put("DNI",data[2]);
        param.put("FECHA", data[3]);
        param.put("MONTO", data[4]);

        JasperPrint jasperPrint = JasperFillManager.fillReport("src\\Reporte\\boleta.jasper" , param, datasource);  

        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        String name = "Boleta"+data[0]+".pdf";
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(name));
        exporter.exportReport();

//        if(rs.next()){
//            Map<String, Object> param = new HashMap<>();
//            param.put("NUMVENTA",rs.getString(1));
//            param.put("NOM_CLIENTE",rs.getString(3));
//            param.put("DNI",rs.getString(4));
//            param.put("FECHA", rs.getString(2));
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport("src\\Reporte\\boleta.jasper" , param, datasource);  
//
//            JRExporter exporter = new JRPdfExporter();  
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reporte2PDF.pdf")); 
//            exporter.exportReport();
//        }
    }
}
