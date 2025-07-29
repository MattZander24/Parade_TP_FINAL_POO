package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroPartida;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class menuHistorialPartidas extends JFrame {
    private JPanel topPanel;
    private JLabel lTitulo;
    private JButton bSalir;
    private JScrollPane listaPartidasScroll;
    private JPanel listaPartidas;
    private JPanel generalPanel;

    private Image icono;

    private RegistroConjuntoPartidas partidas;

    public menuHistorialPartidas(RegistroConjuntoPartidas partidas) {
        this.partidas = partidas;

        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Historial de Partidas - Parade");

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

        listaPartidas = new JPanel();
        listaPartidas.setLayout(new BoxLayout(listaPartidas, BoxLayout.Y_AXIS));

        listaPartidasScroll.setViewportView(listaPartidas);
        listaPartidasScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listaPartidasScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //Ver Historial Partidas
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
        String fechaHoraFormateada;
        assert partidas != null;

        if (!(partidas.getPartidas().isEmpty())) {
            for (RegistroPartida partida : partidas.getPartidas()) {
                fechaHoraFormateada = partida.getFechaYHoraPartida().format(formato);

                JLabel tituloPartida = new JLabel("- Partida jugada el " + fechaHoraFormateada + "...");
                listaPartidas.add(tituloPartida);

                JLabel subtitulo = new JLabel("  (jugadores ordenados por puesto)");
                listaPartidas.add(subtitulo);

                for (RegistroJugadores jugador : partida.getRankingJugador()) {
                    String info = "\t" + jugador.getPosicionJugador() + "- " +
                            jugador.getDefinicionJugador() + " (" +
                            jugador.getPuntosJugador() + " pts.)";
                    JLabel jugadorLabel = new JLabel(info);
                    listaPartidas.add(jugadorLabel);
                }

                listaPartidas.add(Box.createVerticalStrut(15));
            }
        } else {
            JLabel vacioLabel = new JLabel("El historial de partidas está vacío...");
            listaPartidas.add(vacioLabel);
        }
    }
}
