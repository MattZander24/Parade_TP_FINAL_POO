package ar.edu.unlu.parade.vistagrafica;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.modelo.*;
import ar.edu.unlu.parade.vistamenuprepartida.menuPrePartida;
import ar.edu.unlu.parade.enumerados.Color;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class VistaGrafica extends JFrame implements IVista {

    private ControladorParade c;
    private Image icono;
    private JPanel generalPanel;

    int indiceInput; //3 = Evaluar carta, 4 = Descartar carta
    int indiceCarta = 0;


    //MENU PANEL MESA
    private JPanel menuPanel1;
    private JPanel topPanel1;
    private JPanel mazo1;
    private JLabel mazoLabel;
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
            if (c.getJugadorLocal().isTurnoJugador()) {
                CardLayout cl = (CardLayout) (generalPanel.getLayout());
                cl.show(generalPanel, "Card2");
            }
            else {
                JOptionPane.showMessageDialog(this, "Actualmente no es tu turno, espera el tuyo para ingresar opciones.", "Atención!", JOptionPane.WARNING_MESSAGE);
            }
        });
        bVerJugadoresYSusAreasDeJuego1.addActionListener(e -> {
            if (c.getJugadorLocal().isTurnoJugador()) {
                CardLayout cl = (CardLayout)(generalPanel.getLayout());
                cl.show(generalPanel, "Card3");
            }
            else {
                JOptionPane.showMessageDialog(this, "Actualmente no es tu turno, espera el tuyo para ingresar opciones.", "Atención!", JOptionPane.WARNING_MESSAGE);
            }
        });
        bGuardarYSalir1.addActionListener(e -> {
            if (c.getJugadorLocal().isTurnoJugador()) {
                CardLayout cl = (CardLayout)(generalPanel.getLayout());
                cl.show(generalPanel, "Card4");
            }
            else {
                JOptionPane.showMessageDialog(this, "Actualmente no es tu turno, espera el tuyo para ingresar opciones.", "Atención!", JOptionPane.WARNING_MESSAGE);
            }
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

        //TEST DISPLAY
        /*Mazo mazoJuego = new Mazo();
        Desfile desfileJuego = new Desfile();
        Mano manoJuego = new Mano();
        AreaDeJuego aJuego = new AreaDeJuego();

        mazoJuego.generarMazo();
        mazoJuego.mezclarMazo();
        mazoJuego.transferirCartas(desfileJuego, 6);
        displayCartasDesfile(desfile1, desfileJuego);

        mazoJuego.transferirCartas(manoJuego, 4);
        displayCartasMano(mano1, manoJuego);

        mazoJuego.transferirCartas(aJuego, 10);
        aJuego.ordenar();
        displayCartasAreaDeJuego(areaDeJuego1, aJuego, 1);*/

        //TEST MODO TURNO
        setModoTurno(false);
        indiceInput = 3;

    }

    private void initComponents() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();

        //MENU PANEL 1
        iconoMazo1 = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/MAZO.png")).getImage();
        Image iconoMazoEscalado = iconoMazo1.getScaledInstance(103, 160, Image.SCALE_SMOOTH);
        mazoLabel = new JLabel(new ImageIcon(iconoMazoEscalado));

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

    public void setModoTurno(boolean turno) {

        // Ponemos un fondo grisáceo si no es el turno
        float r = turno ? 1f : 0.6f;  // rojo un poco más alto
        float g = turno ? 1f : 0.5f;  // verde más bajo
        float b = turno ? 1f : 0.5f;  // azul más bajo
        java.awt.Color filtro = new java.awt.Color(r, g, b);

        generalPanel.setBackground(filtro);

        String debugColor = turno ? "COLOR TRUE" : "COLOR FALSE";
        System.out.println(debugColor);

        /*mazoLabel.setOpaque(true);
        mazoLabel.setBackground(filtro);
        bGuardarYSalir1.setContentAreaFilled(false);
        bGuardarYSalir1.setOpaque(true);
        bGuardarYSalir1.setBackground(filtro);
        bVerAreaDeJuego1.setContentAreaFilled(false);
        bVerAreaDeJuego1.setOpaque(true);
        bVerAreaDeJuego1.setBackground(filtro);
        bVerJugadoresYSusAreasDeJuego1.setContentAreaFilled(false);
        bVerJugadoresYSusAreasDeJuego1.setOpaque(true);
        bVerJugadoresYSusAreasDeJuego1.setBackground(filtro);*/
    }

    public void displayCartasDesfile(JPanel panelCartas, ListaCartas listaCartas) {
        panelCartas.removeAll();
        panelCartas.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        for (Carta carta : listaCartas.getCartas()) {
            String nombreImagen = carta.nombreImagen();
            ImageIcon icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/" + nombreImagen + ".png"));

            Image imagenEscalada = icono.getImage().getScaledInstance(103, 160, Image.SCALE_SMOOTH);
            JLabel cartaLabel = new JLabel(new ImageIcon(imagenEscalada));
            panelCartas.add(cartaLabel);
        }

        panelCartas.revalidate();
        panelCartas.repaint();
    }

    public void displayCartasMano(JPanel panelCartas, ListaCartas listaCartas) {
        panelCartas.removeAll();
        panelCartas.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        List<Carta> cartas = listaCartas.getCartas();

        for (int i = 0; i < cartas.size(); i++) {
            Carta carta = cartas.get(i);
            final int indiceCarta = i;

            String nombreImagen = carta.nombreImagen();
            ImageIcon icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/" + nombreImagen + ".png"));
            Image imagenEscalada = icono.getImage().getScaledInstance(103, 160, Image.SCALE_SMOOTH);
            JButton bCarta = new JButton(new ImageIcon(imagenEscalada));

            bCarta.addActionListener(e -> {
                if (c.getJugadorLocal().isTurnoJugador()) {
                    seleccionCartaOpcion(indiceCarta);
                } else {
                    JOptionPane.showMessageDialog(this, "Actualmente no es tu turno, espera el tuyo para ingresar opciones.", "Atención!", JOptionPane.WARNING_MESSAGE);
                }
            });

            panelCartas.add(bCarta);
        }

        panelCartas.revalidate();
        panelCartas.repaint();
    }

    public void displayCartasAreaDeJuego(JPanel panelArea, ListaCartas listaCartas, int modo) {
        int anchoCarta = 0;
        int altoCarta = 0;
        if (modo == 1) {
            anchoCarta = 70;
            altoCarta = 110;
        }
        else if (modo == 2) {
            anchoCarta = 103;
            altoCarta = 160;
        }

        panelArea.removeAll();
        panelArea.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 10));

        for (Color color : Color.values()) {
            JPanel columna = new JPanel(null);
            columna.setPreferredSize(new Dimension(100, 600));
            columna.setOpaque(false);

            List<Carta> cartasDelColor = (List<Carta>) listaCartas.getCartas().stream()
                    .filter(c -> c.getColor() == color)
                    /*.sorted(Comparator.comparingInt(Carta::getValor)) // orden de abajo hacia arriba*/
                    .toList();

            // Superponer de abajo hacia arriba
            int offsetX = 0;
            int offsetY = 0;
            for (int i = cartasDelColor.size() - 1; i >= 0; i--) {
                Carta carta = cartasDelColor.get(i);
                String nombreImagen = carta.nombreImagen();
                ImageIcon icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/" + nombreImagen + ".png"));
                Image imagenEscalada = icono.getImage().getScaledInstance(anchoCarta, altoCarta, Image.SCALE_SMOOTH);
                JLabel cartaLabel = new JLabel(new ImageIcon(imagenEscalada));
                cartaLabel.setBounds(offsetY, offsetX, anchoCarta, altoCarta); // posición absoluta
                columna.add(cartaLabel);
                offsetX += 30; // superposición: se muestra solo parte de la carta anterior
                offsetY += 5;
            }

            panelArea.add(columna);
        }

        panelArea.revalidate();
        panelArea.repaint();
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
        new menuPrePartida(c);
    }

    @Override
    public void menuPrincipal() {
        //todo no pertenece a ivista! ahora se hace en el menu. BORRAR
    }

    @Override
    public void verReglas() {
        //todo no pertenece a ivista! ahora se hace en el menu. BORRAR
    }

    @Override
    public void mensajeCreacionArchivo() {
        JOptionPane.showMessageDialog(this, "No se encontró el archivo, se creó uno vacío.", "Partida Guardada", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mensajeGuardarYSalir() {
        JOptionPane.showMessageDialog(this, "Partida guardada correctamente. Volviendo al Menú Principal...", "Partida Guardada", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void verHistorico() throws IOException {
        //todo no pertenece a ivista! ahora se hace en el menu. BORRAR
    }

    @Override
    public void verTop5() throws IOException {
        //todo no pertenece a ivista! ahora se hace en el menu. BORRAR
    }

    @Override
    public void configuracionPartida() {
        //todo no pertenece a ivista! ahora se hace en el menuPrePartida. BORRAR

    }

    @Override
    public void cargarPartida() throws IOException {
        //todo no pertenece a ivista! ahora se hace en el menu. BORRAR
    }

    @Override
    public void menuTurno() {
        //Actualizar el display del desfile, el area de juego del jugador local y la mano del jugador local
        if (c.getJugadorLocal().isTurnoJugador()) {
            setModoTurno(true);
            c.mostrarMano();
            c.mostrarDesfile();
            c.mostrarAreaDeJuego();
        }
        else {
            setModoTurno(false);
        }
    }

    @Override
    public void menuTurnoFinal() {
        if (c.getJugadorLocal().isTurnoJugador()) {
            JOptionPane.showMessageDialog(this,
                    "Es tu ÚLTIMO turno!\n" +
                    "Luego deberás descartar 2 de las 4 cartas que te no jugaste.\n" +
                    "Las 2 cartas que no hayas descartado se agregarán a tu área de juego para la puntuación final.",
                    "Atención!", JOptionPane.INFORMATION_MESSAGE);

        }
        menuTurno();
    }

    @Override
    public void agregarNombre(Jugador jugador) {
        //todo no pertenece a ivista! ahora se hace en el menuPrePartida. BORRAR
    }

    @Override
    public void seleccionCarta(Jugador j, DestinoCarta d) {

    }

    public void seleccionCartaOpcion (int indiceCartaJugada) {
        Jugador j = c.getJugadorLocal();
        DestinoCarta d = null;
        if (indiceInput == 3) { d = DestinoCarta.EVALUAR; }
        else if (indiceInput == 4) { d = DestinoCarta.DESCARTAR; }
        c.devolverCarta(j, indiceCartaJugada, d);

        //todo CAMBIAR TURNO? O SE HACE EN OTRO LUGAR
        //setModoTurno(false);
    }

    @Override
    public void mensajeDescarteFinal(Jugador j) {
        if (c.getJugadorLocal().isTurnoJugador()) {
            indiceInput = 4;
            JOptionPane.showMessageDialog(this,
                    j.definicionJugador("El ", "") + " debe seleccionar 2 cartas para descartar\n" +
                    "(las otras dos se añadirán al area de juego para contarse en la puntuación)",
                    "Atención!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void mensajeGuardar(boolean msgOpcionIncorrecta) {
        //TODO esto lo hace un menú aparte. quizas no deba ser interfaz.
    }

    @Override
    public void mensajeGanador(Jugador j) {
        JOptionPane.showMessageDialog(this, "El ganador de la partida es " + j.definicionJugador("el ", "") + " con " + j.getPuntos() + " puntos...", "Partida terminada", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mensajeEmpateEntreJugadores(ArrayList<Jugador> ganadores) {
        StringBuilder mensaje = new StringBuilder("¡Se ha producido un empate!\n\n");
        for (Jugador j : ganadores) {
            mensaje.append("Uno de los ganadores de la partida es ")
                    .append(j.definicionJugador("el ", ""))
                    .append(" con ")
                    .append(j.getPuntos())
                    .append(" PUNTOS...\n");
        }
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Empate", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mensajeRanking(ArrayList<Jugador> jugadoresPartida) {
        StringBuilder mensaje = new StringBuilder("Jugadores ordenados por puesto:\n\n");
        int i = 1;
        for (Jugador j : jugadoresPartida) {
            mensaje.append("\t")
                    .append(i)
                    .append(" -> ")
                    .append(j.definicionJugador("", ""))
                    .append(": ")
                    .append(j.getPuntos())
                    .append(" pts.\n");
            i++;
        }
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Ranking de Jugadores", JOptionPane.INFORMATION_MESSAGE);
    }


    @Override
    public void habilitarSalir() {
        JOptionPane.showMessageDialog(this, "Volviendo al Menú Principal...", "", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mostrarADJ(Jugador j) {
        displayCartasAreaDeJuego(areaDeJuego1, j.getAreaJugador(), 1);
    }

    @Override
    public void mostrarD(Desfile d) {
        displayCartasDesfile(desfile1, d);
    }

    @Override
    public void mostrarM(Jugador j) {
        displayCartasMano(mano1, j.getManoJugador());
    }

    @Override
    public void bienvenidaYEspera(Jugador jugadorLocal) {
        JOptionPane.showMessageDialog(this, "Bienvenido " + jugadorLocal.definicionJugador("", "") + ", estamos esperando a que se unan todos los jugadores para empezar la partida.", "Bienvenida", JOptionPane.INFORMATION_MESSAGE);
    }
}
