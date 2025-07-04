package ar.edu.unlu.parade.vistagrafica;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.enumerados.EstadoPartida;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.modelo.Desfile;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.vistaconsola.VistaConsolaAreaDeJuego;
import ar.edu.unlu.parade.vistaconsola.VistaConsolaDesfile;
import ar.edu.unlu.parade.vistaconsola.VistaConsolaMano;
import ar.edu.unlu.parade.vistamenuprincipal.menuParade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VistaGrafica  extends JFrame implements IVista {

    //Atributos

    private ControladorParade c;
    private ImageIcon fichaBlanca;
    private ImageIcon fichaNegra;
    private ImageIcon fondoCasilla;
    private Image icono;
    private JPanel rightPanel;
    private JTextArea textArea;
    private JPanel panelBotonesFin;

    private VistaGraficaAreaDeJuego vga;
    private VistaGraficaGuardarYSalir vggys;

    private JButton btnAreasJuego, btnJugadores, btnGuardarSalir;
    private JLabel mazoLabel;
    private Desfile desfile;
    /*
    private AreaDeJuego areaDeJuego;
    private Mano mano;
    */

    //private final Map<ButtonCoordinates, JButton> botonesPorCoordenada = new HashMap<>();

    public VistaGrafica() {
        this.vga = new VistaGraficaAreaDeJuego();
        this.vggys = new VistaGraficaGuardarYSalir();

        initComponents();
        setLocationRelativeTo(null);
        setSize(1080, 720);
        setLayout(null);

        setIconImage(icono);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
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

        setLayout(new BorderLayout());

        // Botones
        btnAreasJuego = new JButton("1\nVER AREAS DE JUEGO");
        btnAreasJuego.setBounds(20, 220, 100, 80);
        add(btnAreasJuego);

        btnJugadores = new JButton("2\nVER JUGADORES");
        btnJugadores.setBounds(680, 220, 100, 80);
        add(btnJugadores);

        btnGuardarSalir = new JButton("3\nGUARDAR Y SALIR");
        btnGuardarSalir.setBounds(680, 20, 100, 80);
        add(btnGuardarSalir);

        // Mazo
        mazoLabel = new JLabel(new ImageIcon("carta_reverso.png"));
        mazoLabel.setBounds(20, 20, 60, 90);
        add(mazoLabel);

        /*
        // Desfile
        desfile = new Desfile();
        desfile.setBounds(100, 20, 560, 100);
        add(desfile);

        // Área de juego
        areaDeJuego = new AreaDeJuego();
        areaDeJuego.setBounds(140, 200, 500, 100);
        add(areaDeJuego);

        // Mano
        mano = new Mano();
        mano.setBounds(140, 400, 500, 100);
        add(mano);*/

        setVisible(true);
        
        // Tablero
        /*BoardPanel boardPanel = new BoardPanel();
        crearBoton(boardPanel, 0, 0, 29, 29);
        crearBoton(boardPanel, 0, 3, 226, 29);
        crearBoton(boardPanel, 0, 6, 425, 29);
        crearBoton(boardPanel, 1, 1, 95, 95);
        crearBoton(boardPanel, 1, 3, 226, 95);
        crearBoton(boardPanel, 1, 5, 358, 95);
        crearBoton(boardPanel, 2, 2, 157, 157);
        crearBoton(boardPanel, 2, 3, 226, 157);
        crearBoton(boardPanel, 2, 4, 296, 157);
        crearBoton(boardPanel, 3, 0, 29, 226);
        crearBoton(boardPanel, 3, 1, 95, 226);
        crearBoton(boardPanel, 3, 2, 157, 226);
        crearBoton(boardPanel, 3, 4, 296, 226);
        crearBoton(boardPanel, 3, 5, 358, 226);
        crearBoton(boardPanel, 3, 6, 425, 226);
        crearBoton(boardPanel, 4, 2, 157, 298);
        crearBoton(boardPanel, 4, 3, 226, 298);
        crearBoton(boardPanel, 4, 4, 296, 298);
        crearBoton(boardPanel, 5, 1, 95, 360);
        crearBoton(boardPanel, 5, 3, 226, 360);
        crearBoton(boardPanel, 5, 5, 358, 360);
        crearBoton(boardPanel, 6, 0, 29, 425);
        crearBoton(boardPanel, 6, 3, 226, 425);
        crearBoton(boardPanel, 6, 6, 425, 425);
        add(boardPanel, BorderLayout.WEST);*/

        /*rightPanel = new JPanel(new BorderLayout());
        // Texto
        textArea = new JTextArea(10, 30); // Ajusta los valores para que se ajuste a tus necesidades
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea); // Añade el textArea a un JScrollPane
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JButton bSalir = new JButton("Cerrar aplicación...");
        bSalir.addActionListener(e -> {
            System.exit(0);
        });
        panelBotonesFin = new JPanel(new BorderLayout());

        JButton bVolver = new JButton("Volver al menú principal...");
        bVolver.setVisible(true);
        bVolver.addActionListener(e -> {
            dispose();
            new menuParade();
        });

        panelBotonesFin.add(bSalir, BorderLayout.EAST);
        panelBotonesFin.add(bVolver, BorderLayout.WEST);
        rightPanel.add(panelBotonesFin, BorderLayout.SOUTH);
        panelBotonesFin.setVisible(false);
        add(rightPanel, BorderLayout.EAST);*/
    }

    private void initComponents() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
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
