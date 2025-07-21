package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.online.ServidorParade;

import javax.swing.*;
import java.awt.*;

public class menuHistorialPartidas extends JFrame {
    private JPanel topPanel;
    private JLabel lTitulo;
    private JButton bSalir;
    private JPanel listaPartidas;
    private JPanel generalPanel;

    private Image icono;

    public menuHistorialPartidas() {
        // Iniciar y configurar Frame
        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Historial de Partidas - Parade");

        // Eventos
        bSalir.addActionListener(e -> {
            dispose();
        });
    }

    private void initElements() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();


    }
}
