/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacion;

import CapaNegocio.clsCliente;
import CapaNegocio.clsCuota;
import CapaNegocio.clsProducto;
import CapaNegocio.clsVenta;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JCarlos
 */
public class JDCambiarProducto extends javax.swing.JDialog {

    clsCliente objCliente = new clsCliente();
    clsVenta objVenta = new clsVenta();
    clsProducto objProducto = new clsProducto();
    String codUser=null;
    int prod_old=0;
    int prod_new = 0;
    int venta=0;
    int cant_new = 0;
    int desc_new = 0;
    boolean cambiando = false;
    /**
     * Creates new form JDPagarVenta
     */
    public JDCambiarProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        listarClientes();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtCodCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblMonto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".:CAMBIAR PRODUCTO:.");

        jLabel1.setText("Cliente:");

        jLabel2.setText("Ventas por pagar:");

        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });
        txtDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDocumentoKeyReleased(evt);
            }
        });

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
        jScrollPane1.setViewportView(tblClientes);

        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblVentas);

        jLabel3.setText("Código del Cliente:");

        txtCodCliente.setEditable(false);

        jLabel4.setText("Nombre Cliente:");

        txtNombre.setEditable(false);

        jLabel5.setText("Producto a cambiar:");

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetalleMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblDetalle);

        jButton1.setText("GUARDAR CAMBIO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Monto:");

        lblMonto.setText("???");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMonto))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5)
                        .addComponent(jScrollPane2)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                                .addComponent(txtDocumento)
                                .addComponent(txtCodCliente)
                                .addComponent(txtNombre)))
                        .addComponent(jScrollPane3)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4))
                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblMonto))
                .addGap(20, 20, 20)
                .addComponent(jButton1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDocumentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocumentoKeyReleased
        // TODO add your handling code here:
        if(txtDocumento.getText().length()>0 && evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(tblClientes.getModel().getRowCount()==1 && (txtDocumento.getText().length()==8 || txtDocumento.getText().length()==11)){
                llenarDatos(txtDocumento.getText());
            }else{
                JOptionPane.showMessageDialog(rootPane, "El documento ingresado no existe");
                limpiarControles();
            }
        }
        listarClientes();
    }//GEN-LAST:event_txtDocumentoKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        String doc = "null";
        if(tblClientes.getValueAt(tblClientes.getSelectedRow(), 0)!=null){
            doc = String.valueOf(tblClientes.getValueAt(tblClientes.getSelectedRow(), 0));
        }else{
            doc = String.valueOf(tblClientes.getValueAt(tblClientes.getSelectedRow(), 1));
        }
        llenarDatos(doc);
    }//GEN-LAST:event_tblClientesMouseClicked

    private void tblVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentasMouseClicked
        // TODO add your handling code here:
        try {
            listarDetalle(Integer.parseInt(tblVentas.getValueAt(tblVentas.getSelectedRow(), 0).toString()));
            tblDetalle.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_tblVentasMouseClicked

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDocumentoActionPerformed

    private void tblDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetalleMouseClicked
        // TODO add your handling code here:
        if(tblDetalle.isEnabled()){
            prod_old = Integer.parseInt(String.valueOf(tblDetalle.getValueAt(tblDetalle.getSelectedRow(), 0)));
            JDAñadirProductos objAnaProd = new JDAñadirProductos((Frame) SwingUtilities.getWindowAncestor(this), true);
            objAnaProd.setLocationRelativeTo(this);
            objAnaProd.setVisible(true);
            int producto = objAnaProd.getCod();
            prod_new = producto;
            int cantidad = objAnaProd.getCant();
            cant_new = cantidad;
            int descuento = objAnaProd.getDesc();
            desc_new = descuento;
            agregarProducto(producto, cantidad, descuento);
        }
    }//GEN-LAST:event_tblDetalleMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            boolean rpta = objVenta.cambiarProducto(venta, prod_old, prod_new, cant_new, desc_new);
            if(rpta){
                JOptionPane.showMessageDialog(rootPane, "Cambio Realizado");
                limpiarControles();
            }else{
                JOptionPane.showMessageDialog(rootPane, "Error al realizar el cambio de producto");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void agregarProducto(int producto, int cantidad, int descuento){
        if(producto!=0 && cantidad!=0){
            ResultSet rs = null;
            try {
                DefaultTableModel modelo = (DefaultTableModel) tblDetalle.getModel();
                
                boolean repetido = false;
                int fila = -1;
                for (int i=0; i<tblDetalle.getRowCount(); i++){
                    if(Integer.parseInt(String.valueOf(tblDetalle.getValueAt(i, 0))) == producto){
                        repetido = true;
                        fila = i;
                    }
                }
                
                int stock = objProducto.getStock(producto);
                
                if (repetido){
                    int aux = Integer.parseInt(String.valueOf(tblDetalle.getValueAt(fila, 2)));
                    cantidad += aux;
                    stock += Integer.parseInt(String.valueOf(tblDetalle.getValueAt(tblDetalle.getSelectedRow(), 2)));
                    modelo.removeRow(fila);
                }
                
                
                if(cantidad > stock){
                    cantidad = stock;
                    JOptionPane.showMessageDialog(rootPane, "Stock Insuficiente");
                }
                
                rs = objProducto.buscarProducto(producto);
                
                while(rs.next()){
                    modelo.addRow(new Object[]{rs.getString("codproducto"), rs.getString("nomproducto"), cantidad, rs.getFloat("precio"), descuento, (cantidad * (rs.getFloat("precio")-descuento*rs.getFloat("precio")/100))});
                }
                
                try{
                    modelo.removeRow(tblDetalle.getSelectedRow());
                }catch(Exception e){}
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
        }
        calcularTotal();
        tblDetalle.setEnabled(false);
    }
    
    private void listarDetalle(int numventa){
        ResultSet rs = null;
        try {
            clsVenta objV = new clsVenta();
            rs = objV.listarDetalleVenta(numventa);
            DefaultTableModel modelo = new DefaultTableModel();
            
            modelo.addColumn("Cod. Producto");
            modelo.addColumn("Nomb. Producto");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Precio venta");
            modelo.addColumn("Descuento");
            modelo.addColumn("Subtotal");
                        
            while(rs.next()){
                modelo.addRow(new Object[]{rs.getString("codproducto"), 
                    rs.getString("nomproducto"), 
                    rs.getString("cantidad"),
                    rs.getString("precioventa"),
                    rs.getString("descuento"),
                    rs.getString("subtotal")
                });
            }
            tblDetalle.setModel(modelo);
            venta = numventa;
            calcularTotal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
    
    private void limpiarControles(){
        txtDocumento.setText("");
        txtNombre.setText("");
        txtCodCliente.setText("");
        while(tblVentas.getRowCount()>0){
            ((DefaultTableModel)tblVentas.getModel()).removeRow(0);
        }
        while(tblDetalle.getRowCount()>0){
            ((DefaultTableModel)tblDetalle.getModel()).removeRow(0);
        }
    }
    
    public void calcularTotal(){
        try {
            float monto = 0;
            for(int i=0; i<tblDetalle.getRowCount(); i++){
                monto += Float.parseFloat(tblDetalle.getValueAt(i, 5).toString());
            }
            lblMonto.setText(String.valueOf(monto));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
    
    private void llenarDatos(String doc){
        txtDocumento.setText(doc);
        try {
            ResultSet rs = objCliente.buscarCliente(doc);
            while(rs.next()){
                codUser = rs.getString("codcliente");
                txtCodCliente.setText(codUser);
                txtNombre.setText(rs.getString("nombres"));
                listarClientes();
                cambiando = true;
            }
            listarVentas(codUser);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
    
    private void listarVentas(String cod){
        ResultSet rs = null;
        try {
            rs = objVenta.listarVentaPagoPendiente(cod);
            DefaultTableModel modelo = new DefaultTableModel();
            
            modelo.addColumn("N°");
            modelo.addColumn("FECHA");
            modelo.addColumn("MONTO");
            modelo.addColumn("TIPO COMPROBANTE");
            
            while(rs.next()){
                modelo.addRow(new Object[]{rs.getString("numventa"), rs.getString("fecha"), rs.getString("total"), rs.getString("tipocomprobante")});
            }
            tblVentas.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
    
    private void listarClientes(){
        ResultSet rsClientes = null;
        try {
            rsClientes = objCliente.filtrarClientes(txtDocumento.getText());
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
            java.util.logging.Logger.getLogger(JDCambiarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDCambiarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDCambiarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDCambiarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDCambiarProducto dialog = new JDCambiarProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblVentas;
    private javax.swing.JTextField txtCodCliente;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
