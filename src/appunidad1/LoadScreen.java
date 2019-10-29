package appunidad1;

import java.awt.*;
import javax.swing.*;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */

// PANTALLA LOAD

public class LoadScreen extends JWindow {
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel imageLabel = new JLabel();
  JPanel southPanel = new JPanel();
  FlowLayout southPanelFlowLayout = new FlowLayout();
  JProgressBar progressBar = new JProgressBar();
  ImageIcon imageIcon;

    public LoadScreen(String ruta) {
        this.imageIcon = new ImageIcon(getClass().getResource(ruta));
        dibujaVentana();
    }

    public void dibujaVentana() {
        imageLabel.setIcon(imageIcon);
        this.getContentPane().setLayout(borderLayout1);
        southPanel.setLayout(southPanelFlowLayout);
        southPanel.setBackground(Color.BLACK);
        this.getContentPane().add(imageLabel, BorderLayout.CENTER);
        this.getContentPane().add(southPanel, BorderLayout.SOUTH);
        southPanel.add(progressBar, null);
        this.pack();
    }

    public void setProgresoMax(int maxProgress){
        progressBar.setMaximum(maxProgress);
    }

    public void setProgreso(int progress){
        final int progreso = progress;
        progressBar.setValue(progreso);
    }

    public void setProgreso(String message, int progress){
        final int progreso = progress;
        final String theMessage = message;
        setProgreso(progress);
        progressBar.setValue(progreso);
        setMessage(theMessage);
    }

    private void setMessage(String message){
        if (message==null){
            message = "";
            progressBar.setStringPainted(false);}
        else{
            progressBar.setStringPainted(true);}

        progressBar.setString(message);
    }

    public void velocidadDeCarga(){
        for (int i = 0; i <= 100; i++)
        {
            for (long j=0; j<100000; ++j)
            {
                String poop = " " + (j + i);
            }
            setProgreso("Cargando..." + i, i);
        }
        dispose();
    }

}
