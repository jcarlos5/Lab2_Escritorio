/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacion;

import CapaNegocio.clsCliente;
import CapaNegocio.clsCuota;
import CapaNegocio.clsVenta;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.Date;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */

public class JDCreditosCliente extends javax.swing.JDialog {

    clsCliente objCliente = new clsCliente();
    /**
     * Creates new form JDCreditosCliente
     */
    public JDCreditosCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentasCredito = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".: Buscar por DNI / RUC el crédito del CLIENTE :.");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIDKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDKeyTyped(evt);
            }
        });

        tblVentasCredito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblVentasCredito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentasCreditoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVentasCredito);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                    .addComponent(txtID, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyTyped

    }//GEN-LAST:event_txtIDKeyTyped

    private void tblVentasCreditoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentasCreditoMouseClicked
        Integer id =(int)tblVentasCredito.getValueAt(tblVentasCredito.getSelectedRow(), 0);
        try {
           JDCronograma objC = new JDCronograma((Frame) SwingUtilities.getWindowAncestor(this), true);
           objC.listarCreditos(id);
           objC.setLocationRelativeTo(this);
           objC.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error");
        }
    }//GEN-LAST:event_tblVentasCreditoMouseClicked

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        String documento;
        if(String.valueOf(tblClientes.getValueAt(tblClientes.getSelectedRow(), 0))!="null"){
            documento=tblClientes.getValueAt(tblClientes.getSelectedRow(), 0).toString();
        }else{
            documento=tblClientes.getValueAt(tblClientes.getSelectedRow(), 1).toString();
        }
        txtID.setText(documento);
        listarClientes();
        listarCreditos(txtID.getText());
    }//GEN-LAST:event_tblClientesMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        listarClientes();
    }//GEN-LAST:event_formWindowActivated

    private void txtIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyReleased
        // TODO add your handling code here:
        if ( evt.getKeyChar()==KeyEvent.VK_ENTER ){
            if (txtID.getText().length()==8 || txtID.getText().length()==11 ){
                listarCreditos(txtID.getText());
            }else {
                JOptionPane.showMessageDialog(this, "Por favor ingrese un dni o ruc correcto");
            }
        }
        listarClientes();
    }//GEN-LAST:event_txtIDKeyReleased
    
    private void listarCreditos(String cli){
        ResultSet rs;
        DefaultTableModel model = new DefaultTableModel();
        clsVenta objVenta = new clsVenta();
        try {
            model.addColumn("Codigo");
            model.addColumn("Fecha");
            model.addColumn("Tipo comprobante");
            model.addColumn("Subtotal");
            model.addColumn("Igv");
            model.addColumn("Total");
            model.addColumn("Estado de pago");
        
            rs = objVenta.listarVentaCredito(cli);
            while (rs.next()){
                String tipoc = (rs.getBoolean("tipocomprobante")) ? "Boleta":"Factura" ;
                model.addRow(new Object[]{rs.getInt("numventa"), 
                    rs.getString("fecha"),
                    tipoc, 
                    rs.getFloat("igv"),
                    rs.getFloat("subtotal"),
                    rs.getFloat("total"),
                    rs.getBoolean("estadopago")
                } );
            }
            tblVentasCredito.setModel(model);
            if(tblVentasCredito.getRowCount()==0){
                JOptionPane.showMessageDialog(rootPane, "No registra ventas al crédito");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error", "Error al listar tabla", WIDTH);
        }
        
    }
    
    private void listarClientes(){
        
        ResultSet rsClientes = null;
        try {
            rsClientes = objCliente.filtrarClientes(txtID.getText());
            DefaultTableModel modelo = new DefaultTableModel();
            
            modelo.addColumn("DNI");
            modelo.addColumn("RUC");
            modelo.addColumn("NOMBRE");
            
            while(rsClientes.next()){
                modelo.addRow(new Object[]{rsClientes.getString("dni"), rsClientes.getString("ruc"), rsClientes.getString("nombres")});
            }
            tblClientes.setModel(modelo);
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
            java.util.logging.Logger.getLogger(JDCreditosCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDCreditosCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDCreditosCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDCreditosCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDCreditosCliente dialog = new JDCreditosCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblVentasCredito;
    private javax.swing.JTextField txtID;
    // End of variables declaration//GEN-END:variables
}