package ar.edu.unlu.parade.vistamenuprepartida;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.modelo.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class menuPrePartida extends JFrame {
    private JPanel panel;
    private JTextArea textArea;
    private JTextField textField1;
    private JButton bSeleccionar;
    private JButton bSeleccionarJugadorPorPos;
    private JButton iniciarPartidaButton;
    private JButton bCrearNuevoJugador;
    private JButton bSeleccionarExistente;
    //private Jugador jugadorLocal;
    private ControladorParade c;
    private Image icono;

    public menuPrePartida(ControladorParade c) {
        this.c = c;
        initComponents();
        setIconImage(icono);
        setContentPane(panel);
        setTitle("Juego del Molino - Menú principal");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        textArea.setSize(10, 50);
        setVisible(true);

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
                        System.exit(0); // Termina la aplicación
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        bSeleccionar.addActionListener(e -> {
            try {
                //System.out.println(textField1.getText());
                c.agregarJugador(textField1.getText());
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

        /*iniciarPartidaButton.addActionListener(e -> {
            try {
                c.agregarJugador(jugadorLocal);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });*/

        /*bCrearNuevoJugador.addActionListener(e -> {
            println("");
            println("¡Generando Jugador nuevo!");

            // Verificamos que ese nombre no exista
            boolean ocupado;
            String nombre = JOptionPane.showInputDialog(null, "Ingrese nombre del nuevo jugador: ");
            if (nombre.isBlank() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede introducir un nombre vacío. Vuelva a intentar.", "Error con el nombre", JOptionPane.ERROR_MESSAGE);
                println("*** Error creando jugador ***   >>>   Se introdujo un nombre vacío/en blanco-");
            } else {
                try {
                    ocupado = c.esNombreYaRegistrado(nombre);
                    if (ocupado) {
                        JOptionPane.showMessageDialog(null, "Ya existe un jugador con este mismo nombre. Intente con seleccionarlo desde la lista o cree un jugador nuevo con otro nombre.", "Error con el nombre", JOptionPane.ERROR_MESSAGE);
                        println("*** Error creando jugador ***   >>>   El nombre ingresado ya se encuentra registrado. Intente con otro.");
                        return;
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                println(">>> ¡Hecho! Jugador creado con exito. ¡Bienvenido " + nombre + "!");
                jugadorLocal = new Jugador(nombre);
                bCrearNuevoJugador.setVisible(false);
                bSeleccionarJugadorPorPos.setVisible(false);
                iniciarPartidaButton.setVisible(true);
            }
        });*/

        /*bSeleccionarJugadorPorPos.addActionListener(e -> {
            try {
                boolean disponible;
                int pos;
                pos = solicitarPosicion();
                if (pos == -1) {
                    return;
                }
                disponible = c.jugadorRegistradoEstaDisponible(pos);
                if (!disponible) {
                    println(">>> El jugador seleccionado NO está disponible. Intente con otro.");
                    return;
                }
                jugadorLocal = c.obtenerJugadoresRegistrados().get(pos);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            println(">>> Se ha seleccionado el jugador " + jugadorLocal.getNombre());
            bCrearNuevoJugador.setVisible(false);
            bSeleccionarJugadorPorPos.setVisible(false);
            iniciarPartidaButton.setVisible(true);
        });*/

        /*bSeleccionarExistente.addActionListener(e -> {/*
            try {
                boolean disponible;
                int pos;
                pos = solicitarPosicion();
                if (pos == -1) {
                    return;
                }
                disponible = c.jugadorParaReanudarDisponible(pos);
                if (!disponible) {
                    println(">>> El jugador seleccionado ya fue elegido. Intente con el otro :/");
                    return;
                }
                jugadorLocal = c.obtenerJugadoresParaReanudar().get(pos);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            println(">>> Se ha seleccionado el jugador " + jugadorLocal.getNombre());
            bSeleccionarExistente.setVisible(false);
            iniciarPartidaButton.setVisible(true);
        });*/

        println("Elegí tu nombre. Si no queres elegir uno, presioná 'Seleccionar' sin escribir nada.\n");
        /*try {
            if (c.esPartidaNueva()) {
                println("¡Se va a iniciar una nueva partida dentro de muy poco! Comencemos por conocerte, así te registramos.");
                println("NOTA IMPORTANTE: El primer jugador que se una a la sesión mueve primero.\n");
                println("* * * * ... Analizando lista de jugadores registrados en el servidor... * * * *\n");
                if (!c.hayJugadoresRegistrados()) {
                    println("No se ha encontrado registro de jugadores antiguos.");
                    bCrearNuevoJugador.setVisible(true);
                } else {
                    bCrearNuevoJugador.setVisible(true);
                    bSeleccionarJugadorPorPos.setVisible(true);
                    println("¡Se han detectado jugadores dentro del servidor!\n>>> Jugadores detectados:\n");
                    int i = 1;
                    for (Jugador jugador : c.obtenerJugadoresRegistrados()) {
                        println("Jugador N°" + i + "  :");
                        println(jugador.toString());
                        println("");
                        i++;
                    }
                    println("\n");
                    println("En caso de que el jugador se encuentre en la lista, podés seleccionarlo con el número de su posición.");
                    println("Caso contrario, ¡cree un nuevo jugador!");
                }
            } else {
                println("Se ha encontrado una partida que está a punto de reanudarse.");
                println("Seleccioná el jugador que te corresponda, no hagas trampa ;)");
                bSeleccionarExistente.setVisible(true);
                int i = 1;
                for (Jugador jugador : c.obtenerJugadoresParaReanudar()) {
                    println("Jugador N°" + i + "  :");
                    println(jugador.toString());
                    println("");
                    i++;
                }
            }
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }*/
    }

    private void initComponents() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }

    /*private int solicitarPosicion() {
        boolean inputValido = false;
        int i = 0;
        while (!inputValido) {
            String index = JOptionPane.showInputDialog(null, "Ingrese la posicion que identifique a su jugador ('0' para volver al menú): ");
            try {
                int size = c.obtenerJugadoresRegistrados().size();
                i = Integer.parseInt(index);
                if (i >= 0 && i <= size) {
                    inputValido = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El ID ingresado no está dentro del rango válido (1 / " + size + "). Introduzca '0' para volver al menú.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido para el ID.");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return i - 1;
    }*/

    private void println(String texto) {
        textArea.append(texto + "\n");
    }

    private void limpiarPantalla() {
        textArea.setText("");
    }
}
