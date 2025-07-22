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

public class VistaGrafica  extends JFrame implements IVista {

    private ControladorParade c;
    private Image icono;
    private Image iconoMazo;

    private VistaGraficaAreaDeJuego vga;
    private VistaGraficaGuardarYSalir vggys;

    private JPanel generalPanel;

    private JPanel topPanel;
    private JPanel mazo;
    private JPanel desfile;
    private JButton bGuardarYSalir;

    private JPanel mediumPanel;
    private JButton bVerAreaDeJuego;
    private JPanel areaDeJuego;
    private JButton bVerJugadoresYSusAreasDeJuego;

    private JPanel bottomPanel;
    private JPanel mano;

    /*
    private AreaDeJuego areaDeJuego;
    private Mano mano;
    */

    public VistaGrafica() {
        this.vga = new VistaGraficaAreaDeJuego();
        this.vggys = new VistaGraficaGuardarYSalir();

        initComponents();
        setLocationRelativeTo(null);
        setSize(1080, 720);
        setLayout(null);

        setIconImage(icono);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        setContentPane(generalPanel);
        setVisible(true); //TODO Borrar despues

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

        //setLayout(new BorderLayout());

        setVisible(true);
    }

    private void initComponents() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();

        iconoMazo = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/MAZO.png")).getImage();
        Image iconoMazoEscalado = iconoMazo.getScaledInstance(103, 160, Image.SCALE_SMOOTH);
        JLabel mazoLabel = new JLabel(new ImageIcon(iconoMazoEscalado));

        mazo.setLayout(new BorderLayout());
        mazo.add(mazoLabel, BorderLayout.CENTER);
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
