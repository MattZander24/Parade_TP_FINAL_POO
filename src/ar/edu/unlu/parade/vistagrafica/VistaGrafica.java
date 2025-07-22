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

        ImageIcon iconoAreaDeJuego = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/BotonVerAreaDeJuego.png"));
        Image imagenEscaladaAreaDeJuego = iconoAreaDeJuego.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscaladoAreaDeJuego = new ImageIcon(imagenEscaladaAreaDeJuego);
        bVerAreaDeJuego1.setIcon(iconoEscaladoAreaDeJuego);
        bVerAreaDeJuego1.setHorizontalTextPosition(SwingConstants.CENTER);
        bVerAreaDeJuego1.setVerticalTextPosition(SwingConstants.BOTTOM);
        bVerAreaDeJuego1.setBorderPainted(false);
        bVerAreaDeJuego1.setContentAreaFilled(false);
        bVerAreaDeJuego1.setFocusPainted(false);
        bVerAreaDeJuego1.setOpaque(false);
        //bVerAreaDeJuego1.setText("");

        ImageIcon iconoAreasDeJuego = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/BotonVerAreasDeJuego.png"));
        Image imagenEscaladaAreasDeJuego = iconoAreasDeJuego.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscaladoAreasDeJuego = new ImageIcon(imagenEscaladaAreasDeJuego);
        bVerJugadoresYSusAreasDeJuego1.setIcon(iconoEscaladoAreasDeJuego);
        bVerJugadoresYSusAreasDeJuego1.setHorizontalTextPosition(SwingConstants.CENTER);
        bVerJugadoresYSusAreasDeJuego1.setVerticalTextPosition(SwingConstants.BOTTOM);
        bVerJugadoresYSusAreasDeJuego1.setBorderPainted(false);
        bVerJugadoresYSusAreasDeJuego1.setContentAreaFilled(false);
        bVerJugadoresYSusAreasDeJuego1.setFocusPainted(false);
        bVerJugadoresYSusAreasDeJuego1.setOpaque(false);
        //bVerJugadoresYSusAreasDeJuego1.setText("");

        ImageIcon iconoGuardarYSalir = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/BotonGuardarYSalir.png"));
        Image imagenEscaladaGuardarYSalir = iconoGuardarYSalir.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscaladoGuardarYSalir = new ImageIcon(imagenEscaladaGuardarYSalir);
        bGuardarYSalir1.setIcon(iconoEscaladoGuardarYSalir);
        bGuardarYSalir1.setHorizontalTextPosition(SwingConstants.CENTER);
        bGuardarYSalir1.setVerticalTextPosition(SwingConstants.BOTTOM);
        bGuardarYSalir1.setBorderPainted(false);
        bGuardarYSalir1.setContentAreaFilled(false);
        bGuardarYSalir1.setFocusPainted(false);
        bGuardarYSalir1.setOpaque(false);
        bGuardarYSalir1.setText("");

        //MENU PANEL 2
        ImageIcon iconoJugador = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/ImagenJugador.png"));
        Image iconoJugadorEscalado = iconoJugador.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel jugadorLabel2 = new JLabel(new ImageIcon(iconoJugadorEscalado));

        iconoJugador2.setLayout(new BorderLayout());
        iconoJugador2.add(jugadorLabel2, BorderLayout.CENTER);

        ImageIcon iconoVolver = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/BotonVolver.png"));
        Image imagenEscaladaVolver = iconoVolver.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscaladoVolver = new ImageIcon(imagenEscaladaVolver);
        bSalir2.setIcon(iconoEscaladoVolver);
        bSalir2.setHorizontalTextPosition(SwingConstants.CENTER);
        bSalir2.setVerticalTextPosition(SwingConstants.BOTTOM);
        bSalir2.setBorderPainted(false);
        bSalir2.setContentAreaFilled(false);
        bSalir2.setFocusPainted(false);
        bSalir2.setOpaque(false);
        bSalir2.setText("");

        //MENU PANEL 3
        JLabel jugadorLabel3 = new JLabel(new ImageIcon(iconoJugadorEscalado));
        iconoJugador3.setLayout(new BorderLayout());
        iconoJugador3.add(jugadorLabel3, BorderLayout.CENTER);

        bSalir3.setIcon(iconoEscaladoVolver);
        bSalir3.setHorizontalTextPosition(SwingConstants.CENTER);
        bSalir3.setVerticalTextPosition(SwingConstants.BOTTOM);
        bSalir3.setBorderPainted(false);
        bSalir3.setContentAreaFilled(false);
        bSalir3.setFocusPainted(false);
        bSalir3.setOpaque(false);
        bSalir3.setText("");

        ImageIcon iconoAnterior = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/BotonAnterior.png"));
        Image imagenEscaladaAnterior = iconoAnterior.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscaladoAnterior = new ImageIcon(imagenEscaladaAnterior);
        bAnterior3.setIcon(iconoEscaladoAnterior);
        bAnterior3.setHorizontalTextPosition(SwingConstants.CENTER);
        bAnterior3.setVerticalTextPosition(SwingConstants.BOTTOM);
        bAnterior3.setBorderPainted(false);
        bAnterior3.setContentAreaFilled(false);
        bAnterior3.setFocusPainted(false);
        bAnterior3.setOpaque(false);
        bAnterior3.setText("");

        ImageIcon iconoSiguiente = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/BotonSiguiente.png"));
        Image imagenEscaladaSiguiente = iconoSiguiente.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscaladoSiguiente = new ImageIcon(imagenEscaladaSiguiente);
        bSiguiente3.setIcon(iconoEscaladoSiguiente);
        bSiguiente3.setHorizontalTextPosition(SwingConstants.CENTER);
        bSiguiente3.setVerticalTextPosition(SwingConstants.BOTTOM);
        bSiguiente3.setBorderPainted(false);
        bSiguiente3.setContentAreaFilled(false);
        bSiguiente3.setFocusPainted(false);
        bSiguiente3.setOpaque(false);
        bSiguiente3.setText("");

        //MENU PANEL 4
        ImageIcon iconoSi = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/BotonSi.png"));
        Image imagenEscaladaSi = iconoSi.getImage().getScaledInstance(125, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscaladoSi = new ImageIcon(imagenEscaladaSi);
        bSi4.setIcon(iconoEscaladoSi);
        bSi4.setHorizontalTextPosition(SwingConstants.CENTER);
        bSi4.setVerticalTextPosition(SwingConstants.BOTTOM);
        bSi4.setBorderPainted(false);
        bSi4.setContentAreaFilled(false);
        bSi4.setFocusPainted(false);
        bSi4.setOpaque(false);
        bSi4.setText("");

        ImageIcon iconoNo = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/BotonNo.png"));
        Image imagenEscaladaNo = iconoNo.getImage().getScaledInstance(125, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoEscaladoNo = new ImageIcon(imagenEscaladaNo);
        bNo4.setIcon(iconoEscaladoNo);
        bNo4.setHorizontalTextPosition(SwingConstants.CENTER);
        bNo4.setVerticalTextPosition(SwingConstants.BOTTOM);
        bNo4.setBorderPainted(false);
        bNo4.setContentAreaFilled(false);
        bNo4.setFocusPainted(false);
        bNo4.setOpaque(false);
        bNo4.setText("");

        //GENERAL
        CardLayout cl = (CardLayout)(generalPanel.getLayout());
        cl.show(generalPanel, "Card1");

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
