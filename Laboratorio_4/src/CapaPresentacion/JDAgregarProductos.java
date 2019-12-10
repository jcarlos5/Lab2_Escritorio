/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacion;

import CapaNegocio.clsProducto;
import java.awt.Frame;
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

public class JDAgregarProductos extends javax.swing.JDialog {

    clsProducto objProducto = new clsProducto();
    
    private Integer prod = 0;
    private int cant = 0;
    private float prec = 0;
    /**
     * Creates new form JDAñadirProductos
     */
    public JDAgregarProductos(java.awt.Frame parent, boolean modal) {
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

        txtNombreProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnBusqueda = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".:Añadir Producto:.");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyTyped(evt);
            }
        });

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        btnBusqueda.setText("BÚSQUEDA AVANZADA");
        btnBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
            .addComponent(txtNombreProducto)
            .addComponent(btnBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnBusqueda))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyTyped
        //TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoKeyTyped

    private void txtNombreProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyReleased
        // TODO add your handling code here:
        listarProductos();
    }//GEN-LAST:event_txtNombreProductoKeyReleased

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        listarProductos();
    }//GEN-LAST:event_formWindowActivated

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        // TODO add your handling code here:
        int cod = Integer.parseInt(String.valueOf(tblProductos.getValueAt(tblProductos.getSelectedRow(), 0)));
        String ctd = String.valueOf(JOptionPane.showInputDialog(rootPane, "Ingrese la Cantidad:"));
                
        if (ctd!="null"){
            try {
                pasarDatos(cod, Integer.parseInt(ctd));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Cantidad no válida");
            }
        }
    }//GEN-LAST:event_tblProductosMouseClicked

    private void pasarDatos(int cod, int ctd){
        try {
            if (ctd<0){
                JOptionPane.showMessageDialog(rootPane, "No se acepta una cantidad negativa");
            }else {
                prod = cod;
                cant = ctd;
                String precio = String.valueOf(JOptionPane.showInputDialog(rootPane, "Ingrese el precio del producto:"));
                prec = Float.parseFloat(precio);
                dispose();
            }            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
    
    private void btnBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaActionPerformed
        // TODO add your handling code here:
        JDConsultas objConsulta = new JDConsultas((Frame) SwingUtilities.getWindowAncestor(this), true);
        objConsulta.setBuscando(true);
        objConsulta.setLocationRelativeTo(this);
        objConsulta.setVisible(true);
        int cod = objConsulta.getCod();
        if(cod >0){
            String ctd = String.valueOf(JOptionPane.showInputDialog(rootPane, "Ingrese la Cantidad:"));
        
            if (ctd!="null"){
                try {
                    pasarDatos(cod, Integer.parseInt(ctd));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Cantidad no válida");
                }
            }
        }
    }//GEN-LAST:event_btnBusquedaActionPerformed

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
            java.util.logging.Logger.getLogger(JDAgregarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDAgregarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDAgregarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDAgregarProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDAgregarProductos dialog = new JDAgregarProductos(new javax.swing.JFrame(), true);
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

    private void listarProductos(){
        ResultSet rsProductos = null;
        DefaultTableModel modelo = new DefaultTableModel(){
                                            @Override
                                            public boolean isCellEditable(int row, int col)
                                            {
                                                return false;
                                            }
                                            };
        modelo.addColumn("CÓDIGO");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("DESCRIPCIÓN");
        modelo.addColumn("PRECIO");
        modelo.addColumn("STOCK");
        modelo.addColumn("MARCA");
        modelo.addColumn("CATEGORÍA");
        try {
            rsProductos=objProducto.filtrar(txtNombreProducto.getText());
            while(rsProductos.next()){
                modelo.addRow(new Object[]{rsProductos.getInt("codProducto"),rsProductos.getString("nomProducto"), rsProductos.getString("descripcion"), rsProductos.getString("precioventa"), rsProductos.getString("stock"), rsProductos.getString("nommarca"), rsProductos.getString("nomcategoria")});
           }
        } catch (Exception e) {
           JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
        tblProductos.setModel(modelo);
    }
    
    public int getCod(){
        return prod;
    }
    
    public int getCant(){
        return cant;
    }
    
    public float getPrec(){
        return prec;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBusqueda;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtNombreProducto;
    // End of variables declaration//GEN-END:variables
}
