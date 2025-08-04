package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.modelo.Desfile;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.vistamenuprepartida.menuPrePartida;
import ar.edu.unlu.parade.vistamenuprincipal.menuParade;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VistaConsola extends JFrame implements IVista {
    private JTextArea textArea;
    private JPanel optionsPanel;
    private JPanel bottomPanel;
    private Image icono;

    private ControladorParade c;
    private VistaConsolaAreaDeJuego vca;
    private VistaConsolaDesfile vcd;
    private VistaConsolaMano vcm;

    private volatile int indiceInput = -1;
    private volatile int indiceRetorno = -1;

    public VistaConsola() {
        this.vca = new VistaConsolaAreaDeJuego();
        this.vcd = new VistaConsolaDesfile();
        this.vcm = new VistaConsolaMano();

        initComponents();

        setIconImage(icono);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        bottomPanel = new JPanel(new BorderLayout());
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        JPanel textFieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel opcionLabel = new JLabel("Opción:");
        JTextField opcion = new JTextField(5);
        opcion.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }
                //Verifica si el texto ingresado es numérico y limita la longitud a 1.
                if (str.matches("\\d") && (getLength() + str.length()) <= 1) {
                    super.insertString(offs, str, a);
                }
            }
        });

        textFieldsPanel.add(opcionLabel);
        textFieldsPanel.add(opcion);

        JButton botonEnviarMovimiento = new JButton("Enviar opcion");
        botonEnviarMovimiento.addActionListener(e -> {
            if (!opcion.getText().isEmpty()) {
                inputOpcion(Integer.parseInt(opcion.getText()));
            }
            opcion.setText("");
        });
        optionsPanel.add(textFieldsPanel);
        optionsPanel.add(botonEnviarMovimiento);
        bottomPanel.add(optionsPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void inputOpcion (int opcion) {
        switch (indiceInput) {
            case 1:
                menuTurnoOpcion(opcion);
                break;
            case 2:
                menuTurnoFinalOpcion(opcion);
                break;
            case 3, 4:
                seleccionCartaOpcion(opcion);
                break;
            case 5:
                evaluarSalirAlMenu(opcion);
                break;
            case 6:
                guardarPartida(opcion);
                break;
            case 0:
                println("\nActualmente no es tu turno, espera el tuyo para ingresar opciones.\n");
                break;
            default:
                break;
        }
    }

    @Override
    public ControladorParade getC() {
        return c;
    }

    @Override
    public void setC(ControladorParade c) {
        this.c = c;
    }

    @Override
    public void iniciarVista() throws RemoteException {
        new menuPrePartida(c);
    }

    //Método privado. No pertenece a IVista.
    private void initComponents() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }

    @Override
    public void mensajeCreacionArchivo () {
        println("\n----------------------------------------------------------------------------");
        println("NO SE ENCONTRO EL ARCHIVO, SE CREO UNO VACÍO");
        println("----------------------------------------------------------------------------\n");
    }

    @Override
    public void mensajeGuardarYSalir () {
        println("\nPartida guardada correctamente. Volviendo al Menú Principal...\n");
    }

    @Override
    public void menuTurno () {
        limpiarPantalla();
        if (c.getJugadorLocal().isTurnoJugador()) {
            indiceInput = 1;
            indiceRetorno = 1;

            println("\n");
            println("TURNO DE" + c.getJugadorLocal().definicionJugador("L ", " "));
            println("\t1. Seleccionar carta para jugar");
            println("\t2. Ver mano");
            println("\t3. Ver desfile");
            println("\t4. Ver area de juego");
            println("\t5. Ver jugadores y sus areas de juego");
            println("\t6. Guardar y Salir");
            println("Seleccione una opción: ");
        }
        else {
            indiceInput = 0;
            println("\n");
            println("Actualmente es turno de otro jugador, espera hasta que sea tu turno");
        }
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void menuTurnoOpcion (int opcion) {
        switch (opcion) {
            case 1:
                seleccionCarta(c.getJugadorLocal(), DestinoCarta.EVALUAR);
                break;
            case 2:
                c.mostrarMano();
                break;
            case 3:
                c.mostrarDesfile();
                break;
            case 4:
                c.mostrarAreaDeJuego();
                break;
            case 5:
                c.mostrarJugadores();
                break;
            case 6:
                mensajeGuardar(false);
                break;
            default:
                println("\nPor favor, seleccione una opción correcta.\n");
                break;
        }
    }

    @Override
    public void menuTurnoFinal () {
        limpiarPantalla();
        if (c.getJugadorLocal().isTurnoJugador()) {
            println("----------------------------------------------------------------------------");
            println("\n \t - ULTIMO TURNO DE " + c.getJugadorLocal().definicionJugador("L ", " ") + " -\n");
            println("----------------------------------------------------------------------------");
            indiceInput = 2;
            indiceRetorno = 2;
            println("\n");
            println("\t1. Seleccionar carta para jugar");
            println("\t2. Ver mano");
            println("\t3. Ver desfile");
            println("\t4. Ver area de juego");
            println("\t5. Ver jugadores y sus areas de juego");
            println("Seleccione una opción: ");
        }
        else {
            indiceInput = 0;
            println("\n");
            println("Actualmente es turno de otro jugador, espera hasta que sea tu turno");
        }
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void menuTurnoFinalOpcion(int opcion) {
        switch (opcion) {
            case 1:
                seleccionCarta(c.getJugadorLocal(), DestinoCarta.EVALUAR);
                break;
            case 2:
                c.mostrarMano();
                break;
            case 3:
                c.mostrarDesfile();
                break;
            case 4:
                c.mostrarAreaDeJuego();
                break;
            case 5:
                c.mostrarJugadores();
                break;
            default:
                println("\nPor favor, seleccione una opción correcta\n");
                break;
        }
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void seleccionCarta (Jugador j, DestinoCarta d) {
        String verbo;
        if (d == DestinoCarta.EVALUAR) {
            indiceInput = 3;
            verbo = "jugar";
        }
        else {
            indiceInput = 4;
            indiceRetorno = 3;
            verbo = "descartar";
        }
        if (j.isTurnoJugador()) {
            c.mostrarMano();
            println("Seleccione la carta a " + verbo + ": ");
        }
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void seleccionCartaOpcion (int opcion) {
        Jugador j = c.getJugadorLocal();
        DestinoCarta d = null;
        if (indiceInput == 3) { d = DestinoCarta.EVALUAR; }
        else if (indiceInput == 4) { d = DestinoCarta.DESCARTAR; }
        if (j.isTurnoJugador()) {
            if (opcion < 1 || opcion > j.getManoJugador().cantidadMano()) {
                println("Opcion incorrecta... Seleccione la carta a jugar: ");
            }
            else{
                c.devolverCarta(j, --opcion, d);

                switch (indiceRetorno) {
                    case 1 -> {
                        indiceInput = 0;
                        c.finalizarTurno();
                    }
                    case 2 -> {
                        indiceInput = 0;
                        c.finalizarUltimoTurno();
                    }
                    case 3 -> {
                        indiceInput = 0;
                        c.finalizarDescarte();
                    }
                }
            }
        }
    }

    @Override
    public void mensajeDescarteFinal (Jugador j) {
        limpiarPantalla();
        if (c.getJugadorLocal().isTurnoJugador()) {
            println("----------------------------------------------------------------------------");
            println("\n \t - DESCARTE DE " + c.getJugadorLocal().definicionJugador("L ", " ") + " -\n");
            println("----------------------------------------------------------------------------");
            println(j.definicionJugador("El ", "") + " debe seleccionar 2 cartas para descartar");
            println("(las otras dos se añadirán al area de juego para contarse en la puntuación)");
            seleccionCarta(c.getJugadorLocal(), DestinoCarta.DESCARTAR);
        }
        else {
            indiceInput = 0;
            println("\n");
            println("Actualmente es turno de otro jugador, espera hasta que sea tu turno");
        }
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void mensajeGuardar (boolean msgOpcionIncorrecta) {
        if (c.getJugadorLocal().isTurnoJugador()) {
            limpiarPantalla();
            indiceInput = 6;
            if (msgOpcionIncorrecta) {
                println("\nOpcion incorrecta, la cantidad de opciones debe ir de 1 a 2...\n");
            }
            println("Desea Guardar y Salir?");
            println("1 - SI");
            println("2 - NO");
            println("Seleccione una opción: ");
        }
        else {
            limpiarPantalla();
            indiceInput = 0;
            println("\n");
            println(" !!! Un jugador solicitó guardar la partida y salir");
        }
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void guardarPartida (int opcion) {
        switch (opcion) {
            case 1:
                c.guardarYSalir();
                break;
            case 2:
                c.menuTurno();
                break;
            default:
                mensajeGuardar(true);
                break;
        }
    }

    @Override
    public void mensajeGanador(Jugador j) {
        limpiarPantalla();
        println("\n");
        println("\nEl ganador de la partida es " + j.definicionJugador("el ", "") + " con " + j.getPuntos() + " puntos...");
        println("\n");
    }

    @Override
    public void mensajeEmpateEntreJugadores (ArrayList<Jugador> ganadores) {
        limpiarPantalla();
        println("\n");
        println("\n¡Se ha producido un empate!\n");
        for (Jugador j : ganadores) {
            println("Uno de los ganadores de la partida es " +  j.definicionJugador("el ", "") + " con " + j.getPuntos() + " PUNTOS...");
        }
        println("\n");
    }

    @Override
    public void mensajeRanking(ArrayList<Jugador> jugadoresPartida) {
        println("----------------------------------------------------------------------------");
        println("\n");
        println("Jugadores ordenados por puesto");
        int i = 1;
        for (Jugador j:jugadoresPartida) {
            println("\t" + i + "-> " + j.definicionJugador("","") + ": " + j.getPuntos() + "pts.");
            i++;
        }
        println("\n");
    }

    @Override
    public void habilitarSalir() {
        indiceInput = 5;
        println("----------------------------------------------------------------------------");
        println("\n");
        println("Para volver al menú principal ingrese '0'");
        println("Para salir del juego ingrese '1'");
        println("\n");
        println("----------------------------------------------------------------------------");
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void evaluarSalirAlMenu(int opcion) {
        if (opcion == 0) {
            dispose();
            new menuParade();
        }
        else if (opcion == 1) {
            System.exit(0);
        }
        else {
            println("\n Ingrese una opción correcta...");
        }
    }

    @Override
    public void mostrarADJ (Jugador j) {
        if (c.getJugadorLocal().isTurnoJugador()) {
            println(vca.mostrar(j));
        }
    }

    @Override
    public void mostrarADJTodos () throws RemoteException {
        for (Jugador j : c.getJugadores()) {
            mostrarADJ(j);
        }
    }

    @Override
    public void mostrarD (Desfile d) {
        if (c.getJugadorLocal().isTurnoJugador()) {
            println(vcd.mostrar(d));
        }
    }

    @Override
    public void mostrarM (Jugador j) {
        if (j.isTurnoJugador()) {
            println(vcm.mostrar(j));
        }
    }

    @Override
    public void bienvenidaYEspera (Jugador jugadorLocal) {
        println("Bienvenido " + jugadorLocal.definicionJugador("", "") + ", estamos esperando a que se unan todos los jugadores para empezar la partida.");
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void println(String texto) {
        textArea.append("\n" + texto + "\n");
    }

    //Método interno del modo consola. No pertenece a IVista.
    private void limpiarPantalla() {
        textArea.setText("");
    }
}