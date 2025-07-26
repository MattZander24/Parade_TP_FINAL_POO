package ar.edu.unlu.parade.vistagrafica;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.modelo.*;
import ar.edu.unlu.parade.vistamenuprepartida.menuPrePartida;
import ar.edu.unlu.parade.enumerados.Color;
import ar.edu.unlu.parade.vistamenuprincipal.menuParade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class VistaGrafica extends JFrame implements IVista {
    private ControladorParade c;
    private Image icono;
    private JPanel generalPanel;
    int indiceInput;
    /*
        1 = Turno [EVALUAR],
        2 = Ultimo Turno [EVALUAR],
        3 = Primer Descarte [DESCARTAR],
        4 = Segundo Descarte
    */
    int indiceArea = 0;


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

    public VistaGrafica() {

        initComponents();
        setLocationRelativeTo(null);
        setSize(1080, 720);

        setIconImage(icono);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        setContentPane(generalPanel);

        //Eventos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO QUE PASA CON ESTO?
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
                displayCartasAreaDeJuego(areaDeJuego2, c.getJugadorLocal().getAreaJugador(), 2);
                lNombreJugador2.setText(c.getJugadorLocal().getNombre());
                CardLayout cl = (CardLayout) (generalPanel.getLayout());
                cl.show(generalPanel, "Card2");
            }
            else {
                JOptionPane.showMessageDialog(this, "Actualmente no es tu turno, espera el tuyo para ingresar opciones.", "Atención!", JOptionPane.WARNING_MESSAGE);
            }
        });
        bVerJugadoresYSusAreasDeJuego1.addActionListener(e -> {
            if (c.getJugadorLocal().isTurnoJugador()) {
                try {
                    displayCartasAreaDeJuego(areaDeJuego3, c.getJugadores().get(0).getAreaJugador(), 2);
                    lNombreJugador3.setText(c.getJugadores().get(0).getNombre());
                    indiceArea = 0;
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
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

        bAnterior3.addActionListener(e -> {
            ArrayList<Jugador> jugadores = null;
            try {
                jugadores = c.getJugadores();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            if (indiceArea == 0) {
                indiceArea = jugadores.size()-1;
            } else {
                indiceArea--;
            }
            displayCartasAreaDeJuego(areaDeJuego3, jugadores.get(indiceArea).getAreaJugador(), 2);
            lNombreJugador3.setText(jugadores.get(indiceArea).getNombre());
        });

        bSiguiente3.addActionListener(e -> {
            ArrayList<Jugador> jugadores = null;
            try {
                jugadores = c.getJugadores();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            if (indiceArea == jugadores.size()-1) {
                indiceArea = 0;
            } else {
                indiceArea++;
            }
            displayCartasAreaDeJuego(areaDeJuego3, jugadores.get(indiceArea).getAreaJugador(), 2);
            lNombreJugador3.setText(jugadores.get(indiceArea).getNombre());
        });

        bSalir3.addActionListener(e -> {
            CardLayout cl = (CardLayout)(generalPanel.getLayout());
            cl.show(generalPanel, "Card1");
        });

        bSi4.addActionListener(e -> {
            System.out.println("GUARDAR PARTIDA");
        });

        bNo4.addActionListener(e -> {
            CardLayout cl = (CardLayout)(generalPanel.getLayout());
            cl.show(generalPanel, "Card1");
        });

        setModoTurno(false);
        indiceInput = 1;

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
        //Ponemos un fondo gris si no es el turno
        float r = turno ? 1f : 0.6f;
        float g = turno ? 1f : 0.5f;
        float b = turno ? 1f : 0.5f;
        java.awt.Color filtro = new java.awt.Color(r, g, b);
        generalPanel.setBackground(filtro);
        String debugColor = turno ? "COLOR TRUE" : "COLOR FALSE";
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

    public void ocultarUltimaCarta(JPanel panelCartas) {
        panelCartas.removeAll();
        panelCartas.revalidate();
        panelCartas.repaint();
    }

    public void displayCartasAreaDeJuego(JPanel panelArea, ListaCartas listaCartas, int modo) {
        int anchoCarta = 0;
        int altoCarta = 0;
        int offX = 0;
        int offY = 0;
        int wColumna = 0;
        int hColumna = 0;
        int offsetInicialY = 0;

        if (modo == 1) {
            anchoCarta = 70;
            altoCarta = 110;
            offX = 30;
            offY = 5;
            wColumna = 100;
            hColumna = 600;
        }
        else if (modo == 2) {
            anchoCarta = 85;
            altoCarta = 130;
            offX = 25;
            offY = 5;
            wColumna = 135;
            hColumna = 800;
            offsetInicialY = 50;
        }

        panelArea.removeAll();
        panelArea.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 10));

        for (Color color : Color.values()) {
            JPanel columna = new JPanel(null);
            columna.setPreferredSize(new Dimension(wColumna, hColumna));
            columna.setOpaque(false);

            List<Carta> cartasDelColor = listaCartas.getCartas().stream()
                    .filter(c -> c.getColor() == color)
                    .toList();

            int offsetX = 0;
            int offsetY = offsetInicialY;
            for (int i = cartasDelColor.size() - 1; i >= 0; i--) {
                Carta carta = cartasDelColor.get(i);
                String nombreImagen = carta.nombreImagen();
                ImageIcon icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/" + nombreImagen + ".png"));
                Image imagenEscalada = icono.getImage().getScaledInstance(anchoCarta, altoCarta, Image.SCALE_SMOOTH);
                JLabel cartaLabel = new JLabel(new ImageIcon(imagenEscalada));
                cartaLabel.setBounds(offsetY, offsetX, anchoCarta, altoCarta);
                columna.add(cartaLabel);
                offsetX += offX;
                offsetY += offY;
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

    @Override
    public void iniciarVista() {
        new menuPrePartida(c);
    }

    @Override
    public void mensajeCreacionArchivo() {
        mostrarDialogo("No se encontró el archivo, se creó uno vacío.", "Partida Guardada");
    }

    @Override
    public void mensajeGuardarYSalir() {
        mostrarDialogo("Partida guardada correctamente. Volviendo al Menú Principal...", "Partida Guardada");
    }

    @Override
    public void menuTurno() {
        //Actualizar el display del desfile, el area de juego del jugador local y la mano del jugador local
        c.mostrarMano();
        c.mostrarDesfile();
        c.mostrarAreaDeJuego();
        setModoTurno(c.getJugadorLocal().isTurnoJugador());
    }

    @Override
    public void menuTurnoFinal() {
        if (c.getJugadorLocal().isTurnoJugador()) {
            indiceInput = 2;
            mostrarDialogo("Es tu ÚLTIMO turno!\n" +
                            "Luego deberás descartar 2 de las 4 cartas que te no jugaste.\n" +
                            "Las 2 cartas que no hayas descartado se agregarán a tu área de juego para la puntuación final.",
                    "Atención!");
        }
        menuTurno();
    }

    @Override
    public void seleccionCarta(Jugador j, DestinoCarta d) {
        //TODO Y ESTO?
    }

    public void seleccionCartaOpcion (int indiceCartaJugada) {
        Jugador j = c.getJugadorLocal();
        DestinoCarta d = null;
        if (indiceInput == 1 || indiceInput == 2) { d = DestinoCarta.EVALUAR; }
        else if (indiceInput == 3 || indiceInput == 4) { d = DestinoCarta.DESCARTAR; }
        c.devolverCarta(j, indiceCartaJugada, d);

        switch (indiceInput) {
            case 1 -> {
                c.finalizarTurno();
            }
            case 2 -> {
                c.finalizarUltimoTurno();
            }
            case 3 -> {
                c.finalizarDescarte();
            }
            case 4 -> {
                ocultarUltimaCarta(mano1);
                c.finalizarDescarte();
            }
        }
    }

    @Override
    public void mensajeDescarteFinal(Jugador j) {
        if (c.getJugadorLocal().isTurnoJugador()) {
            if (indiceInput == 2) {
                indiceInput = 3;
                mostrarDialogo(j.definicionJugador("", "") + ", debés seleccionar 2 cartas para descartar.\n" +
                                "(Las otras dos se añadirán al area de juego para contarse en la puntuación)",
                        "Atención!");
            }
            else if (indiceInput == 3) {
                indiceInput = 4;
            }
        }
        menuTurno();
    }

    @Override
    public void mensajeGanador(Jugador j) {
        mostrarDialogo("El ganador de la partida es " + j.definicionJugador("el ", "") + " con " + j.getPuntos() + " puntos...", "Partida terminada");
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
        mostrarDialogo(mensaje.toString(), "Empate");
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
        mostrarDialogo(mensaje.toString(), "Ranking de Jugadores");
    }

    @Override
    public void habilitarSalir() {
        SwingUtilities.invokeLater(() -> {
            String[] opciones = {"Volver al Menú Principal", "Salir del Juego"};

            int eleccion = JOptionPane.showOptionDialog(
                    this,
                    "¿Desea volver al Menú Principal o salir del juego?",
                    "Confirmar salida",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );
            dispose();

            if (eleccion == 0) {
                new menuParade();
            } else if (eleccion == 1) {
                System.exit(0);
            }
        });
    }

    @Override
    public void mostrarADJ(Jugador j) {
        displayCartasAreaDeJuego(areaDeJuego1, j.getAreaJugador(), 1);
    }

    @Override
    public void mostrarADJTodos () {
        //TODO Y ESTO?
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

    public void mostrarDialogo(String mensaje, String titulo) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
        });
    }

}
