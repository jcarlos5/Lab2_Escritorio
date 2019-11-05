/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporte;

import java.awt.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author JCarlos
 */
public class Lista implements JRDataSource{
    
    private ArrayList<String[]> datos = new ArrayList<>();
    
    private int indiceParticipanteActual = -1;

    public Object getFieldValue(JRField jrf) throws JRException{
        Object valor = null;

        if ("CANTIDAD".equalsIgnoreCase(jrf.getName())){
            valor = datos.get(indiceParticipanteActual)[0];
        }else if ("DESCRIPCION".equalsIgnoreCase(jrf.getName())){
            valor = datos.get(indiceParticipanteActual)[1];
        }else if("PRECIO".equalsIgnoreCase(jrf.getName())){
            valor = datos.get(indiceParticipanteActual)[2];
        }else if("MONTO".equalsIgnoreCase(jrf.getName())){
            valor = datos.get(indiceParticipanteActual)[3];
        }

        return valor;
    }

    public boolean next() throws JRException{
        return ++indiceParticipanteActual < datos.size();
    }
    
    public String[] agregar(ResultSet listado) throws Exception{
        try {
            String data[] = new String[5];
            int i=0;
            while (listado.next()){
                datos.add(new String[]{listado.getString(10), listado.getString(11), listado.getString(12), listado.getString(13)});
                
                data[0] = listado.getString(1);
                data[1] = listado.getString(3);
                data[2] = listado.getString(4);
                data[3] = listado.getString(2);
                data[4] = listado.getString(6);
            }
            return data;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
