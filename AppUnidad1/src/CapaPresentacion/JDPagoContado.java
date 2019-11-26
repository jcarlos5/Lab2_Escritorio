/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaPresentacion;

import CapaNegocio.clsCuota;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/*
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
*/

public class JDPagoContado extends javax.swing.JDialog {
    boolean pagado=false;
    String[][] cuotas;
    clsCuota objcuota = new clsCuota();

    /**
     * Creates new form JDPago2
     */
    public JDPagoContado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiarControles();
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
        txtNumeroVenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPago = new javax.swing.JTextField();
        lblTituloVuelto = new javax.swing.JLabel();
        lblVuelto = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(".:Registrar Pago al Contado:.");

        jPanel1.setBackground(new java.awt.Color(222, 227, 218));

        jLabel1.setText("Venta N°:");

        txtNumeroVenta.setEditable(false);

        jLabel2.setText("Cliente:");

        txtNombre.setEditable(false);

        jLabel3.setText("DNI/RUC:");

        txtDocumento.setEditable(false);

        jLabel4.setText("Monto Total:");

        txtMonto.setEditable(false);

        jLabel5.setText("Pago Efectivo:");

        txtPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPagoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPagoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagoKeyTyped(evt);
            }
        });

        lblTituloVuelto.setText("Vuelto:");

        lblVuelto.setText("???");

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTituloVuelto)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNumeroVenta)
                                .addComponent(txtNombre)
                                .addComponent(txtDocumento)
                                .addComponent(txtMonto)
                                .addComponent(txtPago, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
                            .addComponent(lblVuelto))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNumeroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloVuelto)
                    .addComponent(lblVuelto))
                .addGap(15, 15, 15)
                .addComponent(btnGuardar)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPagoKeyReleased

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        pagado = true;
        dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoKeyPressed
        // TODO add your handling code here:
        try {
            limpiarControles();
            if(txtPago.getText().length()>0 && evt.getKeyCode()==KeyEvent.VK_ENTER){
                float vuelto = Float.parseFloat(txtPago.getText())-Float.parseFloat(txtMonto.getText());
                if(vuelto<0){
                    JOptionPane.showMessageDialog(rootPane, "El monto a pagar es mayor");
                    txtPago.setText("");
                    txtPago.requestFocus();
                    limpiarControles();
                }else{
                    lblTituloVuelto.setVisible(true);
                    lblVuelto.setText(String.valueOf(vuelto));
                    lblVuelto.setVisible(true);
                    btnGuardar.setVisible(true);
                    cuotas = new String[1][8];
                    LocalDate ld = LocalDate.now();
                    Date fe = Date.valueOf(ld);
                    cuotas[0] = new String[]{txtNumeroVenta.getText(), "1", String.valueOf(fe), "'"+String.valueOf(fe)+"'" , "true", txtPago.getText(), lblVuelto.getText(), txtMonto.getText()};
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtPagoKeyPressed

    private void txtPagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoKeyTyped
        // TODO add your handling code here:
        char []p={'1','2','3','4','5','6','7','8','9','0','.'};
        int b=0;
        for(int i=0;i<=10;i++){if (p[i] == evt.getKeyChar()){b=1;}}
        if(b==0){
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtPagoKeyTyped

    private void limpiarControles(){
        lblVuelto.setVisible(false);
        btnGuardar.setVisible(false);
        lblTituloVuelto.setVisible(false);
    }
    
    public void setDatos(String numVenta, String cliente, String doc, String monto){
        txtDocumento.setText(doc);
        txtNombre.setText(cliente);
        txtNumeroVenta.setText(numVenta);
        txtMonto.setText(monto);
    }
    
    public boolean getPago(){
        return pagado;
    }
    
    public String[][] getCuotas(){
        return cuotas;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTituloVuelto;
    private javax.swing.JLabel lblVuelto;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumeroVenta;
    private javax.swing.JTextField txtPago;
    // End of variables declaration//GEN-END:variables
}