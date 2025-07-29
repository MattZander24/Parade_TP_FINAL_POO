package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroJugadores;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class menuRankingJugadores extends JFrame {
    private JPanel generalPanel;
    private JLabel lTitulo;
    private JButton bSalir;
    private JPanel topPanel;
    private JScrollPane top5PanelScroll;
    private JPanel top5Panel;

    private Image icono;

    private RegistroConjuntoJugadores jugadores;

    public menuRankingJugadores(RegistroConjuntoJugadores jugadores) {
        this.jugadores = jugadores;

        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Top 5 Jugadores - Parade");

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

        top5Panel = new JPanel();
        top5Panel.setLayout(new BoxLayout(top5Panel, BoxLayout.Y_AXIS));

        top5PanelScroll.setViewportView(top5Panel);
        top5PanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        top5PanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //Ver Top 5
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
        top5Panel.setLayout(new BoxLayout(top5Panel, BoxLayout.Y_AXIS));

        if (!(jugadores.getJugadores().isEmpty())) {
            int limite = Math.min(5, jugadores.getJugadores().size());
            for (int i = 0; i < limite; i++) {
                RegistroJugadores jugador = jugadores.getJugadores().get(i);
                String fechaHoraFormateada = jugador.getFechaYHoraPartida().format(formato);

                JLabel tituloPartida = new JLabel((i + 1) + "- Partida jugada el " + fechaHoraFormateada + ":");
                top5Panel.add(tituloPartida);

                JLabel infoJugador = new JLabel("   " + jugador.getDefinicionJugador() + " - " + jugador.getPuntosJugador() + " pts.");
                top5Panel.add(infoJugador);

                top5Panel.add(Box.createVerticalStrut(30)); // Espaciado
            }
        } else {
            JLabel mensaje = new JLabel("El Top 5 de mejores puntajes está vacío...");
            top5Panel.add(mensaje);
        }
    }
}
