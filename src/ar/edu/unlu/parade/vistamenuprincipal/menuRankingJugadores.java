package ar.edu.unlu.parade.vistamenuprincipal;

import javax.swing.*;
import java.awt.*;

public class menuRankingJugadores extends JFrame {
    private JPanel generalPanel;
    private JLabel lTitulo;
    private JLabel lTop1;
    private JLabel lTop2;
    private JLabel lTop3;
    private JLabel lTop4;
    private JLabel lTop5;
    private JButton bSalir;
    private JPanel topPanel;
    private JPanel centerPanel;

    private Image icono;

    public menuRankingJugadores() {
        // Iniciar y configurar Frame
        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Top 5 Jugadores - Parade");

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
