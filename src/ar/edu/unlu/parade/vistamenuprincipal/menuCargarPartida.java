package ar.edu.unlu.parade.vistamenuprincipal;

import javax.swing.*;
import java.awt.*;

public class menuCargarPartida extends JFrame {
    private JPanel generalPanel;
    private JPanel listaPartidas;
    private JPanel topPanel;
    private JButton bSalir;
    private JLabel lTitulo;

    private Image icono;

    public menuCargarPartida() {
        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Cargar Partida - Parade");

        //Eventos
        bSalir.addActionListener(e -> {
            dispose();
            new menuParade();
        });
    }

    private void initElements() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }
}
