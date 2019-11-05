/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appunidad1;

import CapaNegocio.clsCliente;
import CapaNegocio.clsCuota;
import CapaNegocio.clsDevolucion;
import CapaNegocio.clsVenta;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Benel Ramirez Sara
 *         Vilchez Villegas Jose
 */
public class JDDevolucionVenta extends javax.swing.JDialog {
    clsCliente objCliente = new clsCliente();
    clsDevolucion objDev = new clsDevolucion();
    int usuario=0;
    int venta=0;
    /**
     * Creates new form JDDevolucionVenta
     */
    public JDDevolucionVenta(java.awt.Frame parent, boolean modal) {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentasCredito = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtNombre = new javax.swing.JTextField();
        txtCod = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        txtMotivo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblMonto = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblCod = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Devolución de Venta");
        setBackground(new java.awt.Color(222, 227, 218));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
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
        jScrollPane3.setViewportView(tblDetalle);

        jLabel1.setText("Ingrese el motivo: ");

        lblMonto.setText("???");

        jButton1.setText("DEVOLVER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Código de Devolución:");

        lblCod.setText("???");

        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIDKeyReleased(evt);
            }
        });

        jLabel3.setText("Código del Cliente:");

        jLabel4.setText("Nombre del Cliente:");

        jLabel5.setText("Ventas del Cliente:");

        jLabel6.setText("Detalle de Venta:");

        jLabel7.setText("Monto a devolver:");

        lblFecha.setText("???");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre)
                            .addComponent(txtCod)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtID)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMonto)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMotivo)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFecha)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCod)
                    .addComponent(jLabel2)
                    .addComponent(lblFecha))
                .addGap(15, 15, 15)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMonto)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblVentasCreditoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentasCreditoMouseClicked
        
        try {
            listarDetalle(Integer.valueOf(String.valueOf(tblVentasCredito.getValueAt(tblVentasCredito.getSelectedRow(), 0))));
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
        llenarDatos(txtID.getText());
    }//GEN-LAST:event_tblClientesMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        listarClientes();
        generarDevolucion();
    }//GEN-LAST:event_formWindowActivated

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            objDev.registrarDevolucionVenta(lblCod.getText(), txtMotivo.getText(), lblMonto.getText(), usuario, tblDetalle, venta);
            JOptionPane.showMessageDialog(this, "Devolución Registrada");
            limpiarControles();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyReleased
        // TODO add your handling code here:
        listarClientes();
    }//GEN-LAST:event_txtIDKeyReleased
    private void listarVentas(){
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
            
            rs = objVenta.listarTodasVentaPorCliente(Integer.valueOf(txtCod.getText()));
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
                JOptionPane.showMessageDialog(rootPane, "No registra ventas");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
        
    }
    
    public void limpiarControles(){
        txtID.setText("");
        txtMotivo.setText("");
        txtNombre.setText("");
        lblMonto.setText("");
        txtCod.setText("");
        int tam = tblVentasCredito.getRowCount()-1;
        for(int i=tam; i>=0; i--){
            ((DefaultTableModel) tblVentasCredito.getModel()).removeRow(i);
        }
        tam = tblDetalle.getRowCount()-1;
        for(int i=tam; i>=0; i--){
            ((DefaultTableModel) tblDetalle.getModel()).removeRow(i);
        }
        generarDevolucion();
    }
    
    public void generarDevolucion(){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        lblFecha.setText(formatoFecha.format(new java.util.Date()));
        try {
            lblCod.setText(String.valueOf(objDev.generarCodigoDev()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
    
    public void llenarDatos(String doc){
        if(doc.equals("null")){
            int rpta = JOptionPane.showConfirmDialog(rootPane, "El usuario no cuenta con el Documento Necesario, ¿Desea Agregarlo?", "SISTEMA", JOptionPane.YES_NO_OPTION);
            if (rpta==0){
                JDMantenimientoCliente objMantCliente = new JDMantenimientoCliente((Frame) SwingUtilities.getWindowAncestor(this), true);
                objMantCliente.setLocationRelativeTo(this);
                objMantCliente.setVisible(true);
            }
        }else{
            txtID.setText(doc);
            try {
                ResultSet rs = objCliente.buscarCliente(doc);
                while(rs.next()){
                    txtCod.setText(rs.getString("codcliente"));
                    
                    txtNombre.setText(rs.getString("nombres"));
                    listarClientes();
                    listarVentas();
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage());
            }
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
    
     public void listarDetalle(Integer numventa){
        ResultSet rs = null;
        try {
            calcularTotal(numventa);
            clsVenta objV = new clsVenta();
            rs = objV.listarDetalleVenta(numventa);
            DefaultTableModel modelo = new DefaultTableModel();
            
            modelo.addColumn("Cod. Producto");
            modelo.addColumn("Nomb. Producto");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Precio venta");
            modelo.addColumn("Descuento");
                        
            while(rs.next()){
                modelo.addRow(new Object[]{rs.getString("codproducto"), 
                    rs.getString("nomproducto"), 
                    rs.getString("cantidad"),
                    rs.getString("precioventa"),
                    rs.getString("descuento")
                });
            }
            tblDetalle.setModel(modelo);
            venta = numventa;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
     
    public void calcularTotal(Integer venta){
        try {
            clsCuota obj = new clsCuota();
            int m = obj.cuotasPagadasPorVenta(venta);
            lblMonto.setText(String.valueOf(m));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }
    
    public void setUser(int user){
        usuario = user;
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
            java.util.logging.Logger.getLogger(JDDevolucionVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDDevolucionVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDDevolucionVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDDevolucionVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDDevolucionVenta dialog = new JDDevolucionVenta(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblVentasCredito;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMotivo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
