/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appunidad1;

import CapaNegocio.clsVenta;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.toedter.calendar.JDateChooser;
import java.awt.Frame;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/*
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
*/

public class JDVentasPorFechas extends javax.swing.JDialog {
    clsVenta objVenta = new clsVenta();
     
        
    /**
     * Creates new form JDVentasDiarias
     */
    public JDVentasPorFechas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        /*DatePickerSettings set = new DatePickerSettings();
        set.setFormatForDatesCommonEra("yyyy-MM-dd");
        set.setFormatForDatesBeforeCommonEra("uuuu/MM/dd");        
        dpFecha.setSettings(set);*/
        addListener(finicial);
        addListener(ffinal);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentasDiarias = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblCantVentas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        finicial = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        ffinal = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".:Listar Ventas Por Rango de Fechas:.");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(222, 227, 218));

        tblVentasDiarias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblVentasDiarias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentasDiariasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVentasDiarias);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/salir.png"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Total de ventas:");

        lblCantVentas.setText("jLabel5");
        lblCantVentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Desde:");

        jLabel3.setText("Hasta:");

        jLabel1.setText("LISTAR VENTAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCantVentas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(finicial, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ffinal, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel1))
                    .addComponent(finicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ffinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblCantVentas)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
    }//GEN-LAST:event_formWindowActivated

    private void tblVentasDiariasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentasDiariasMouseClicked
        
            String id =String.valueOf(tblVentasDiarias.getValueAt(tblVentasDiarias.getSelectedRow(), 0));
            clsVenta objV = new clsVenta();
            try {
                ResultSet rs = objV.listarVenta(Integer.valueOf(id));
                if(rs.next()){
                    String doc = (rs.getBoolean("tipocomprobante"))?rs.getString("dni"):rs.getString("ruc");
                    JDVentaDatos objVentaDatos = new JDVentaDatos((Frame) SwingUtilities.getWindowAncestor(this), true);
                    objVentaDatos.setDatos(rs.getInt("numventa"),rs.getString("nombres"), doc,rs.getFloat("subtotal"), rs.getFloat("igv"), rs.getFloat("total"),rs.getFloat("vuelto"), rs.getBoolean("cancelada"),rs.getBoolean("tipocomprobante") );
                    objVentaDatos.listarDetalle(rs.getInt("numventa"));
                    objVentaDatos.setLocationRelativeTo(this);
                    objVentaDatos.setVisible(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Error");
            }
    }//GEN-LAST:event_tblVentasDiariasMouseClicked
    
    private void listarVentas(Date fech){
        ResultSet rsMarcas = null;
        DefaultTableModel model = new DefaultTableModel();
        try {
            model.addColumn("Codigo");
            model.addColumn("Fecha");
            model.addColumn("Tipo comprobante");
            model.addColumn("Subtotal");
            model.addColumn("Igv");
            model.addColumn("Total");
            model.addColumn("Estado de pago");
        
            rsMarcas = objVenta.listarVenta(fech);
            while (rsMarcas.next()){
                String tipoc = (rsMarcas.getBoolean("tipocomprobante")) ? "Boleta":"Factura" ;
                model.addRow(new Object[]{rsMarcas.getInt("numventa"), 
                    rsMarcas.getString("fecha"),
                    tipoc, 
                    rsMarcas.getFloat("subtotal"),
                    rsMarcas.getFloat("igv"),
                    rsMarcas.getFloat("total"),
                    rsMarcas.getBoolean("estadopago")
                } );
            }
            tblVentasDiarias.setModel(model);
            lblCantVentas.setText(String.valueOf(tblVentasDiarias.getRowCount()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error", "Error al listar tabla", WIDTH);
        }
        
    }
    
    private void addListener(JDateChooser fecha) {
        fecha.getDateEditor().addPropertyChangeListener(
                new java.beans.PropertyChangeListener() {
 
                    @Override
                    public void propertyChange(java.beans.PropertyChangeEvent evt) {
                        try {
                            if ("date".equals(evt.getPropertyName())) {
                                listarVentasFechas();
                            }
                        } catch (Exception e) {
                        }
                    }
                });
    }
    
    private void listarVentasFechas(){
        try {
            if (finicial.getDate() != null && ffinal.getDate() != null ) {
                Date f1, f2;
                if (finicial.getDate().compareTo(ffinal.getDate())>0){
                    f1 = new Date(ffinal.getDate().getTime());
                    f2 = new Date(finicial.getDate().getTime());
                    finicial.setDate(f2);
                    ffinal.setDate(f1);
                }else{
                    f1 = new Date(finicial.getDate().getTime());
                    f2 = new Date(ffinal.getDate().getTime());
                }
                DefaultTableModel modelo = new DefaultTableModel();
                modelo.addColumn("N°");
                modelo.addColumn("Núm. Venta");
                modelo.addColumn("Fecha");
                modelo.addColumn("Cliente");
                modelo.addColumn("Monto Total");
                modelo.addColumn("Subtotal");
                modelo.addColumn("IGV");
                modelo.addColumn("Estado Pago");
                modelo.addColumn("Tipo Pago");
                modelo.addColumn("Tipo Comprobante");

                tblVentasDiarias.setModel(modelo);

                ResultSet rs = objVenta.getVentasFechas(f1, f2);
                int i=0;
                while (rs.next()) {
                    i++;
                    modelo.addRow(new Object[]{i, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)?"Pagado":"No Pagado", rs.getBoolean(8)?"Contado":"Crédito", rs.getBoolean(9)?"Boleta":"Factura"});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDVentasPorFechas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDVentasPorFechas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDVentasPorFechas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDVentasPorFechas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDVentasPorFechas dialog = new JDVentasPorFechas(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser ffinal;
    private com.toedter.calendar.JDateChooser finicial;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantVentas;
    private javax.swing.JTable tblVentasDiarias;
    // End of variables declaration//GEN-END:variables
}
