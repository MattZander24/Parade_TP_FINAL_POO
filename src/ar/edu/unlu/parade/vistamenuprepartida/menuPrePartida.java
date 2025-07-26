package ar.edu.unlu.parade.vistamenuprepartida;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.modelo.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Objects;

public class menuPrePartida extends JFrame {
    private JPanel panel;
    private JTextArea textArea;
    private JTextField textField1;
    private JButton bSeleccionar;
    private JButton bSeleccionarJugadorPorPos;
    private JButton iniciarPartidaButton;
    private JButton bCrearNuevoJugador;
    private JButton bSeleccionarExistente;
    private Image icono;

    public menuPrePartida(ControladorParade c) {
        initComponents();
        setIconImage(icono);
        setContentPane(panel);
        setTitle("MENU PRE PARTIDA");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        textArea.setSize(10, 50);
        setVisible(true);

        //Eventos
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

        bSeleccionar.addActionListener(e -> {
            try {
                String nombre = textField1.getText();
                if (!Objects.equals(nombre, "")) {
                    Jugador jugador = new Jugador(nombre);
                    c.agregarJugador(jugador);
                }
                else {
                    //TODO Y ESTO?
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
        println("Elegí tu nombre. Si no queres elegir uno, presioná 'Seleccionar' sin escribir nada.\n");
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
