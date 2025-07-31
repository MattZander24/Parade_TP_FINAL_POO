package ar.edu.unlu.parade.vistamenuprepartida;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.modelo.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public class menuPrePartida extends JFrame {
    private JPanel panel;
    private JTextArea textArea;
    private JTextField textField1;
    private JButton bSeleccionar;
    private Image icono;

    public menuPrePartida(ControladorParade c) throws RemoteException {
        initComponents();
        setIconImage(icono);
        setContentPane(panel);
        setTitle("MENU PRE PARTIDA");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        textArea.setSize(10, 50);
        setVisible(true);

        if (c.esNuevaPartida()) {
            println("Elegí tu nombre. Si no queres elegir uno, presioná 'Seleccionar' sin escribir nada.\n");

            bSeleccionar.addActionListener(e -> {
                try {
                    String nombre = textField1.getText();
                    if (!Objects.equals(nombre, "")) {
                        Jugador jugador = new Jugador(nombre);
                        c.agregarJugador(jugador);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                try {
                    c.checkInicioPartida();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            });

        } else {
            panel.remove(textArea);
            panel.remove(textField1);
            panel.remove(bSeleccionar);

            ArrayList<Jugador> jugadores = c.getJugadores();
            int indice = 1;

            for (Jugador jugador : jugadores) {
                if (!jugador.isElegido()) {
                    StringBuilder texto = new StringBuilder();
                    texto.append("<html>");
                    texto.append("  ").append(indice).append(": ").append(jugador.definicionJugador("", ""));
                    texto.append("</html>");

                    JButton botonJugador = new JButton(texto.toString());

                    botonJugador.addActionListener(e -> {
                        try {
                            c.elegirJugador(jugador.getIdJugador());
                            c.reanudarJugador(jugador);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                        try {
                            c.checkInicioPartida();
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    panel.setLayout(new GridBagLayout());

                    JPanel contenedorBoton = new JPanel();
                    contenedorBoton.setOpaque(false);
                    contenedorBoton.add(botonJugador);

                    panel.add(contenedorBoton);

                    indice++;
                }
            }
        }

        // Confirmar salida
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    int confirm = JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                            "¿Estás seguro de que quieres cerrar la aplicación? El juego todavía no ha comenzado.",
                            "Confirmar salida", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (c != null) {
                            c.aplicacionCerrada();
                        }
                        System.exit(0);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void initComponents() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }

    private void println(String texto) {
        textArea.append(texto + "\n");
    }
}
