/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appunidad1;

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
 *
 * @author Sara
 */
public class JDCreditosCliente extends javax.swing.JDialog {

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addComponent(txtID))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyTyped
        if ( evt.getKeyChar()==KeyEvent.VK_ENTER ){
            if (txtID.getText().length()==8 || txtID.getText().length()==11 ){
                listarCreditos(txtID.getText());
            }else {
                JOptionPane.showMessageDialog(this, "Por favor ingrese un dni o ruc correcto");
            }
        }
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error", "Error al listar tabla", WIDTH);
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
    private javax.swing.JTable tblVentasCredito;
    private javax.swing.JTextField txtID;
    // End of variables declaration//GEN-END:variables
}
