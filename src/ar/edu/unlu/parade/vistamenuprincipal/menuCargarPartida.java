package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.online.ServidorParade;

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
        // Iniciar y configurar Frame
        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Cargar Partida - Parade");

        // Eventos
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

        /*
        // Agregar margen superior al topPanel
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10)); // top, left, bottom, right

        GroupLayout layout = new GroupLayout(topPanel);
        topPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(lTitulo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSalir)
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lTitulo)
                        .addComponent(bSalir)
        );*/

        //TODO ESTO NO ANDA, ARREGLAR

        // Configuración del topPanel
        topPanel.setLayout(new BorderLayout());

        // Panel para margen superior
        JPanel marginPanel = new JPanel();
        marginPanel.setPreferredSize(new Dimension(0, 10)); // margen arriba
        generalPanel.add(marginPanel, BorderLayout.NORTH);

        // Centro: Título centrado
        lTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(lTitulo);
        topPanel.add(centerPanel, BorderLayout.CENTER);

        // Derecha: Botón Salir
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.add(bSalir);
        topPanel.add(rightPanel, BorderLayout.EAST);
    }
}
