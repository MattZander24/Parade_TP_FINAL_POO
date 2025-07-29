package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.ModeloParade;
import ar.edu.unlu.parade.modelo.Partida;
import ar.edu.unlu.parade.modelo.persistencia.ConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.online.ServidorParade;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class menuCargarPartida extends JFrame {
    private JPanel generalPanel;
    private JPanel topPanel;
    private JButton bSalir;
    private JLabel lTitulo;
    private JScrollPane listaPartidasScroll;
    private JPanel listaPartidas;

    private Image icono;

    private ConjuntoPartidas partidas;

    public menuCargarPartida(ConjuntoPartidas partidas) {
        this.partidas = partidas;

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

        listaPartidas = new JPanel();
        listaPartidas.setLayout(new BoxLayout(listaPartidas, BoxLayout.Y_AXIS));

        listaPartidasScroll.setViewportView(listaPartidas);
        listaPartidasScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listaPartidasScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //Cargar Partida
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
        String fechaHoraFormateada;
        int indice = 1;

        if (!(partidas.getPartidas().isEmpty())) {
            for (Partida partida : partidas.getPartidas()) {
                fechaHoraFormateada = partida.getFechaYHoraPartida().format(formato);

                // Construir texto descriptivo
                StringBuilder texto = new StringBuilder();
                texto.append("<html>");
                texto.append(indice).append("- Partida guardada el ").append(fechaHoraFormateada).append("<br>");
                texto.append("&nbsp;&nbsp;&nbsp;Jugadores:<br>");
                int indice2 = 1;
                for (Jugador j : partida.getJugadores()) {
                    texto.append("&nbsp;&nbsp;&nbsp;&nbsp;").append(indice2).append(". ")
                            .append(j.definicionJugador("", "")).append("<br>");
                    indice2++;
                }
                texto.append("</html>");

                JButton botonPartida = new JButton(texto.toString());
                botonPartida.setAlignmentX(Component.LEFT_ALIGNMENT);
                botonPartida.setBackground(Color.WHITE);
                botonPartida.setHorizontalAlignment(SwingConstants.LEFT);

                Partida partidaSeleccionada = partida; //Esta es la partida que se va a cargar
                botonPartida.addActionListener(e -> {
                    ModeloParade modelo = new ModeloParade();
                    modelo.setPartida(partidaSeleccionada);

                    dispose();
                    new ServidorParade(modelo);
                });
                listaPartidas.add(Box.createVerticalStrut(20));
                listaPartidas.add(botonPartida);
                indice++;
            }
        } else {
            JLabel vacioLabel = new JLabel("No hay ninguna partida guardada para cargar...");
            vacioLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            listaPartidas.add(vacioLabel);
        }
    }
}
