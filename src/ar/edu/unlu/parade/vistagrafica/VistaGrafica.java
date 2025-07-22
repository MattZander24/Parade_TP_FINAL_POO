package ar.edu.unlu.parade.vistagrafica;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.modelo.Desfile;
import ar.edu.unlu.parade.modelo.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class VistaGrafica extends JFrame implements IVista {

    private ControladorParade c;
    private Image icono;
    private JPanel generalPanel;


    //MENU PANEL MESA
    private JPanel menuPanel1;
    private JPanel topPanel1;
    private JPanel mazo1;
    private Image iconoMazo1;
    private JPanel desfile1;
    private JButton bGuardarYSalir1;
    private JPanel centerPanel1;
    private JButton bVerAreaDeJuego1;
    private JPanel areaDeJuego1;
    private JButton bVerJugadoresYSusAreasDeJuego1;
    private JPanel bottomPanel1;
    private JPanel mano1;


    //MENU PANEL AREA DE JUEGO
    private JPanel menuPanel2;
    private JPanel topPanel2;
    private JPanel jugador2;
    private JPanel iconoJugador2;
    private JLabel lNombreJugador2;
    private JButton bSalir2;
    private JPanel centerPanel2;
    private JPanel areaDeJuego2;


    //MENU PANEL AREAS DE JUEGO
    private JPanel menuPanel3;
    private JPanel topPanel3;
    private JPanel jugador3;
    private JPanel iconoJugador3;
    private JLabel lNombreJugador3;
    private JButton bSalir3;
    private JPanel centerPanel3;
    private JPanel areaDeJuego3;
    private JButton bAnterior3;
    private JButton bSiguiente3;


    //MENU PANEL GUARDAR Y SALIR
    private JPanel menuPanel4;
    private JPanel centerPanel4;
    private JLabel lGuardarYSalir4;
    private JButton bSi4;
    private JButton bNo4;

    /*
    private AreaDeJuego areaDeJuego;
    private Mano mano;
    */

    public VistaGrafica() {

        initComponents();
        setLocationRelativeTo(null);
        setSize(1080, 720);
        //setLayout(null);

        setIconImage(icono);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        setContentPane(generalPanel);
        setVisible(true); //TODO Esto se activa en otro lado

        //Eventos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                /*try {
                    if (controlador.partidaHaComenzado()) {
                        if (controlador.partidaSigueActiva()) {
                            int confirm = JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                                    "¿Quiere guardar la partida para reanudarla en otra ocación?\nNOTA: Si presiona 'NO' se le contará como abandono y perderás la partida :/",
                                    "Confirmar salida", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                controlador.guardarPartida();
                                System.exit(0); // Termina la aplicación
                            }
                            if (confirm == JOptionPane.NO_OPTION) {
                                // Si el usuario confirma, cierra la aplicación.
                                controlador.jugadorAbandona();
                                System.exit(0); // Termina la aplicación
                            }
                        } else {
                            System.exit(0); // Termina la aplicación
                        }
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(),
                                "¿Estás seguro de que quieres cerrar la aplicación? El juego todavía no ha comenzado.",
                                "Confirmar salida", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Si el usuario confirma, cierra la aplicación.
                            controlador.aplicacionCerrada();
                            System.exit(0); // Termina la aplicación
                        }
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    System.exit(0); // Termina la aplicación
                }*/
            }
        });

        bVerAreaDeJuego1.addActionListener(e -> {
            CardLayout cl = (CardLayout)(generalPanel.getLayout());
            cl.show(generalPanel, "Card2");
        });
        bVerJugadoresYSusAreasDeJuego1.addActionListener(e -> {
            CardLayout cl = (CardLayout)(generalPanel.getLayout());
            cl.show(generalPanel, "Card3");
        });
        bGuardarYSalir1.addActionListener(e -> {
            CardLayout cl = (CardLayout)(generalPanel.getLayout());
            cl.show(generalPanel, "Card4");
        });

        bSalir2.addActionListener(e -> {
            CardLayout cl = (CardLayout)(generalPanel.getLayout());
            cl.show(generalPanel, "Card1");
        });

        bSalir3.addActionListener(e -> {
            CardLayout cl = (CardLayout)(generalPanel.getLayout());
            cl.show(generalPanel, "Card1");
        });

        bNo4.addActionListener(e -> {
            CardLayout cl = (CardLayout)(generalPanel.getLayout());
            cl.show(generalPanel, "Card1");
        });

        setVisible(true);
    }

    private void initComponents() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();

        //MENU PANEL 1
        iconoMazo1 = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/MAZO.png")).getImage();
        Image iconoMazoEscalado = iconoMazo1.getScaledInstance(103, 160, Image.SCALE_SMOOTH);
        JLabel mazoLabel = new JLabel(new ImageIcon(iconoMazoEscalado));

        mazo1.setLayout(new BorderLayout());
        mazo1.add(mazoLabel, BorderLayout.CENTER);

        //MENU PANEL 2

        //MENU PANEL 3

        //MENU PANEL 4

        //GENERAL
        CardLayout cl = (CardLayout)(generalPanel.getLayout());
        cl.show(generalPanel, "Card4");

    }

    @Override
    public ControladorParade getC() {
        return c;
    }

    @Override
    public void setC(ControladorParade c) {
        this.c = c;
    }

    /*
    @Override
    public void actualizarVistaParaAccion(EstadoPartida estadoActual) throws RemoteException {

    }*/

    @Override
    public void iniciarVista() {

    }

    @Override
    public void menuPrincipal() {

    }

    @Override
    public void verReglas() {

    }

    @Override
    public void mensajeCreacionArchivo() {

    }

    @Override
    public void mensajeGuardarYSalir() {

    }

    @Override
    public void verHistorico() throws IOException {

    }

    @Override
    public void verTop5() throws IOException {

    }

    @Override
    public void configuracionPartida() {

    }

    @Override
    public void cargarPartida() throws IOException {

    }

    @Override
    public void menuTurno() {

    }

    @Override
    public void menuTurnoFinal() {

    }

    @Override
    public void agregarNombre(Jugador jugador) {

    }

    @Override
    public void seleccionCarta(Jugador j, DestinoCarta d) {

    }

    @Override
    public void mensajeDescarteFinal(Jugador j) {

    }

    @Override
    public void mensajeGuardar(boolean msgOpcionIncorrecta) {

    }

    @Override
    public void mensajeGanador(Jugador j) {

    }

    @Override
    public void mensajeEmpateEntreJugadores(ArrayList<Jugador> ganadores) {

    }

    @Override
    public void mensajeRanking(ArrayList<Jugador> jugadoresPartida) {

    }

    @Override
    public void habilitarSalir() {

    }

    @Override
    public void mostrarADJ(Jugador j) {

    }

    @Override
    public void mostrarD(Desfile d) {

    }

    @Override
    public void mostrarM(Jugador j) {

    }

    @Override
    public void bienvenidaYEspera(Jugador jugadorLocal) {

    }
}
