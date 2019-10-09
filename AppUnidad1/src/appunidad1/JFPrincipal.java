/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appunidad1;

import CapaNegocio.clsUsuario;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */

public class JFPrincipal extends javax.swing.JFrame implements Runnable{
    
    Thread hilo;
    String user;
    String nomUsuario;
    clsUsuario objUsuario = new clsUsuario();
    boolean sesion = false;
    /**
     * Creates new form JPrincipal
     */
    public JFPrincipal() {
        initComponents();
        lblUsuario.setText("");
        lblDatosSesion.setText("");
        this.setExtendedState(MAXIMIZED_BOTH);
        hilo = new Thread(this);
        hilo.start();
        iniciarSesion();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    //Cambiart el icono de un formulario
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
        getImage(ClassLoader.getSystemResource("src/icon1.png"));
        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem12 = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblDatosSesion = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        mnuPagoContado = new javax.swing.JMenuItem();
        mnuPagoCredito = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        mnuVentasDiarias = new javax.swing.JMenuItem();
        mnuCreditosClientes = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        jMenuItem12.setText("jMenuItem12");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(":. SISTEMA DE COMERCIALIZACIÓN .:");
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/user.png"))); // NOI18N
        jButton1.setToolTipText("Iniciar Sesión");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setPreferredSize(new java.awt.Dimension(50, 39));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/changeuser.png"))); // NOI18N
        jButton2.setToolTipText("Cambiar Contraseña");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setPreferredSize(new java.awt.Dimension(50, 39));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/logout.png"))); // NOI18N
        jButton3.setToolTipText("Cerrar Sesión");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setPreferredSize(new java.awt.Dimension(50, 39));
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator1);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/consultar.png"))); // NOI18N
        jButton4.setToolTipText("Consultar Productos");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setPreferredSize(new java.awt.Dimension(50, 39));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/vender.png"))); // NOI18N
        jButton5.setToolTipText("Registrar Venta");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setPreferredSize(new java.awt.Dimension(50, 39));
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cliente.png"))); // NOI18N
        jButton6.setToolTipText("Registrar Cliente");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setPreferredSize(new java.awt.Dimension(50, 39));
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton6);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/pago.png"))); // NOI18N
        jButton7.setToolTipText("Registrar Pago");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setPreferredSize(new java.awt.Dimension(50, 39));
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton7);
        jToolBar1.add(jSeparator2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel7.setText("   ");
        jPanel3.add(jLabel7);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/miniuser.png"))); // NOI18N
        jLabel1.setText("Usuario:  ");
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 25));
        jPanel3.add(jLabel1);

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblUsuario.setText("User");
        lblUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(lblUsuario);

        jLabel2.setText("   ");
        jPanel3.add(jLabel2);

        lblDatosSesion.setText("--");
        jPanel3.add(lblDatosSesion);

        jPanel1.add(jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/calendario.png"))); // NOI18N
        jLabel5.setText("Fecha:  ");
        jPanel4.add(jLabel5);

        lblFecha.setText("NULL");
        jPanel4.add(lblFecha);

        jLabel10.setText("   ");
        jPanel4.add(jLabel10);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/clock.png"))); // NOI18N
        jLabel6.setText("Hora:  ");
        jPanel4.add(jLabel6);

        lblHora.setText("NULL");
        jPanel4.add(lblHora);

        jLabel11.setText("   ");
        jPanel4.add(jLabel11);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 189, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);

        jPanel5.setBackground(new java.awt.Color(222, 227, 218));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/android2.png"))); // NOI18N
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Login");

        jMenuItem1.setText("Cambiar Usuario");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setText("Cambiar Contraseña");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem2.setText("Cerrar Sesión");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Mantenimiento");

        jMenuItem4.setText("Usuarios");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Marca");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("Categoría");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem7.setText("Producto");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ventas");

        jMenuItem8.setText("Consultar Producto");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem9.setText("Gestionar Cliente");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setText("Registrar Venta");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenu5.setText("Registrar Pago");

        mnuPagoContado.setText("Pago al Contado");
        mnuPagoContado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPagoContadoActionPerformed(evt);
            }
        });
        jMenu5.add(mnuPagoContado);

        mnuPagoCredito.setText("Pago al Crédito");
        mnuPagoCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPagoCreditoActionPerformed(evt);
            }
        });
        jMenu5.add(mnuPagoCredito);

        jMenu3.add(jMenu5);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reportes");

        jMenu6.setText("Consultas");

        mnuVentasDiarias.setText("Ventas diarias");
        mnuVentasDiarias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuVentasDiariasActionPerformed(evt);
            }
        });
        jMenu6.add(mnuVentasDiarias);

        mnuCreditosClientes.setText("Creditos de clientes");
        mnuCreditosClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCreditosClientesActionPerformed(evt);
            }
        });
        jMenu6.add(mnuCreditosClientes);

        jMenuItem13.setText("Consultar Caja");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem13);

        jMenu4.add(jMenu6);

        jMenuItem11.setText("Ventas");
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cerrarSesion();
        iniciarSesion();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int rpta = JOptionPane.showConfirmDialog(rootPane, "¿Seguro que desea Salir?", "SISTEMA", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(rpta==0){
            cerrarSesion();
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int rpta = JOptionPane.showConfirmDialog(rootPane, "¿Seguro que desea Salir?", "SISTEMA", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(rpta==0){
            cerrarSesion();
            System.exit(0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        cerrarSesion();
        iniciarSesion();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        int rpta = JOptionPane.showConfirmDialog(rootPane, "¿Seguro que desea Salir?", "SISTEMA", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(rpta==0){
            cerrarSesion();
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        cambiarContraseña();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cambiarContraseña();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        JDMantenimientoMarca objMantMarca = new JDMantenimientoMarca(this, true);
        objMantMarca.setLocationRelativeTo(this);
        objMantMarca.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        JDMantenimientoCategoria objMantCategoria = new JDMantenimientoCategoria(this, true);
        objMantCategoria.setLocationRelativeTo(this);
        objMantCategoria.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        JDMantenimientoProducto objMantProducto = new JDMantenimientoProducto(this, true);
        objMantProducto.setLocationRelativeTo(this);
        objMantProducto.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        JDMantenimientoUsuario objMantUsuario = new JDMantenimientoUsuario(this, true);
        if (!user.equals("admin")){
            objMantUsuario.modoPerfil(user);
            //objMantUsuario.setSize(objMantUsuario.getWidth(), objMantUsuario.getSizePerfil());
        }
        objMantUsuario.setLocationRelativeTo(this);
        objMantUsuario.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        JDConsultas objConsultas = new JDConsultas(this, true);
        objConsultas.setLocationRelativeTo(this);
        objConsultas.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        JDMantenimientoCliente objMantCliente = new JDMantenimientoCliente(this, true);
        objMantCliente.setLocationRelativeTo(this);
        objMantCliente.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        JDVentas objVentas = new JDVentas(this, true);
        objVentas.setLocationRelativeTo(this);
        objVentas.setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void mnuPagoContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPagoContadoActionPerformed
        // TODO add your handling code here:
        JDPagoContado objPago = new JDPagoContado(this, true);
        objPago.setLocationRelativeTo(this);
        objPago.setVisible(true);
    }//GEN-LAST:event_mnuPagoContadoActionPerformed

    private void mnuPagoCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPagoCreditoActionPerformed
        // TODO add your handling code here:
        JDPagoCreditoD objpago = new JDPagoCreditoD(this,true);
        objpago.setLocationRelativeTo(this);
        objpago.setVisible(true);
    }//GEN-LAST:event_mnuPagoCreditoActionPerformed

    private void mnuVentasDiariasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuVentasDiariasActionPerformed
        // TODO add your handling code here:
        JDVentasDiarias obj = new JDVentasDiarias(this, true);
        obj.setLocationRelativeTo(this);
        obj.setVisible(true);
    }//GEN-LAST:event_mnuVentasDiariasActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        JDConsultasCaja obj = new JDConsultasCaja(this, true);
        obj.setLocationRelativeTo(this);
        obj.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void mnuCreditosClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCreditosClientesActionPerformed
        // TODO add your handling code here:
        JDCreditosCliente obj = new JDCreditosCliente(this, true);
        obj.setLocationRelativeTo(this);
        obj.setVisible(true);
    }//GEN-LAST:event_mnuCreditosClientesActionPerformed

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
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFPrincipal().setVisible(true);
            }
        });
    }

    void iniciarSesion(){
        JDInicioSesion objLogin = new JDInicioSesion(this, rootPaneCheckingEnabled);
        objLogin.setLocationRelativeTo(null);
        objLogin.setVisible(true);
        nomUsuario = objLogin.getNombreUsuario();
        user = objLogin.getUsername();
        sesion = objLogin.getValue();
        String datosSesion = objLogin.getDatosSesion();
        lblDatosSesion.setText(datosSesion);
        
        if(sesion){
            lblUsuario.setText(nomUsuario);
        }else{
            JOptionPane.showMessageDialog(rootPane, "No ha iniciado Sesión, No puede acceder a la Aplicación", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    void cerrarSesion(){
        try {
            objUsuario.registrarCierre(user);
            lblUsuario.setText("");
            lblDatosSesion.setText("");
            sesion = false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void cambiarContraseña(){
        try {
            String clave = JOptionPane.showInputDialog(rootPane, "Necesitamos verificar que eres tú \nEscribe tu Contraseña Actual", "Cambiar contraseña", JOptionPane.PLAIN_MESSAGE);
            if (clave != null){
                String acceso = objUsuario.login(user, clave);
                if(acceso.equals("")){
                    JOptionPane.showMessageDialog(rootPane, "La contraseña es incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                    cambiarContraseña();
                }else{
                    JDCambiarContraseña objCambiarContraseña = new JDCambiarContraseña(this, rootPaneCheckingEnabled);
                    objCambiarContraseña.setLocationRelativeTo(null);
                    objCambiarContraseña.setUser(user);
                    objCambiarContraseña.setNecesario(false);
                    objCambiarContraseña.setVisible(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblDatosSesion;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JMenuItem mnuCreditosClientes;
    private javax.swing.JMenuItem mnuPagoContado;
    private javax.swing.JMenuItem mnuPagoCredito;
    private javax.swing.JMenuItem mnuVentasDiarias;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        while (current == hilo){
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm a");
            lblFecha.setText(formatoFecha.format(new Date()));
            lblHora.setText(formatoHora.format(new Date()));
        }
    }
}
