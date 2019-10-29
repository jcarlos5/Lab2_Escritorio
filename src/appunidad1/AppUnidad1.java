/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appunidad1;

import javax.swing.ImageIcon;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */

public class AppUnidad1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LoadScreen screen = new LoadScreen("/src/iconoInicio1.png");
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
        screen.velocidadDeCarga();
        
        JFPrincipal objPrincipal = new JFPrincipal();
        objPrincipal.setLocationRelativeTo(null);
        objPrincipal.setVisible(true);
    }
}