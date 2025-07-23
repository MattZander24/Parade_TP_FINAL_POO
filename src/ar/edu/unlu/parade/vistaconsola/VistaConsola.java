package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.modelo.Desfile;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.Partida;
import ar.edu.unlu.parade.modelo.persistencia.*;
import ar.edu.unlu.parade.vistamenuprepartida.menuPrePartida;
import ar.edu.unlu.parade.vistamenuprincipal.menuParade;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

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

        /*JLabel filaLabel = new JLabel("Fila:");
        JTextField fila = new JTextField(5);
        fila.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }
                // Verifica si el texto ingresado es numérico y limita la longitud a 1.
                if (str.matches("\\d") && (getLength() + str.length()) <= 1) {
                    super.insertString(offs, str, a);
                }
            }
        });
        JLabel columnaLabel = new JLabel("Columna:");
        JTextField columna = new JTextField(5);
        PlainDocument doc = (PlainDocument) columna.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Verifica si la inserción excedería el límite de un carácter
                if ((fb.getDocument().getLength() + text.length() - length) <= 1) {
                    super.replace(fb, offset, length, text.toUpperCase(), attrs); // Permitir inserción
                } else {
                    Toolkit.getDefaultToolkit().beep(); // Emite un sonido de error si se intenta exceder el límite
                }
            }
        });
        textFieldsPanel.add(columnaLabel);
        textFieldsPanel.add(columna);
        textFieldsPanel.add(filaLabel);
        textFieldsPanel.add(fila);*/

        JLabel opcionLabel = new JLabel("Opción:");
        JTextField opcion = new JTextField(5);
        opcion.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) {
                    return;
                }
                // Verifica si el texto ingresado es numérico y limita la longitud a 1.
                if (str.matches("\\d") && (getLength() + str.length()) <= 1) {
                    super.insertString(offs, str, a);
                }
            }
        });

        textFieldsPanel.add(opcionLabel);
        textFieldsPanel.add(opcion);

        JButton botonEnviarMovimiento = new JButton("Enviar opcion");
        botonEnviarMovimiento.addActionListener(e -> {
            inputOpcion(Integer.parseInt(opcion.getText()));
            // Reiniciamos el contenido.
            opcion.setText("");
        });
        optionsPanel.add(textFieldsPanel);
        optionsPanel.add(botonEnviarMovimiento);

        bottomPanel.add(optionsPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

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
        setVisible(true);
    }

    //Método interno del modo consola. No pertenece a IVista
    public void inputOpcion (int opcion) {
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
                System.out.println("inputOpcion DEFAULT");
                break;
        }
    }

    /*
    @Override
    public void actualizarVistaParaAccion(EstadoPartida estadoActual) throws RemoteException {
        switch (estadoActual) {
            case ESPERANDO_TURNO -> println("Ahora es el turno de tu oponente.\nEspera hasta que realice su movimiento ;)");

            */

            /*case COLOCAR_FICHA, SELECCIONAR_DESTINO_MOVER_SUPER -> {
                mostrarMensajeEsTuTurno();
                mensajePedirNuevaCasillaLibre();
            }
            case SELECCIONAR_ORIGEN_MOVER, SELECCIONAR_ORIGEN_MOVER_SUPER -> {
                mostrarMensajeEsTuTurno();
                mensajeCasillaFichaAMover();
            }
            case SELECCIONAR_DESTINO_MOVER -> {
                mostrarMensajeEsTuTurno();
                mensajePedirNuevaCasillaLibreAdyacente();
            }
            case SELECCIONAR_FICHA_PARA_ELIMINAR -> {
                mostrarMensajeEsTuTurno();
                mensajeFichaAEliminar();
            }*/

            /*
            case TURNO -> {
                println("Turno de este jugador ");
            }
            case PARTIDA_SUSPENDIDA -> {
                WindowListener[] listeners = getWindowListeners();
                for (WindowListener listener : listeners) {
                    removeWindowListener(listener);
                }
                addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

                println("******** ¡La partida se ha suspendido! ********\n\n");
                println("Tu oponente ha salido de la partida, pero ha guardado el estado del\njuego en el servidor. En caso de querer retomar la partida,\nno olvides que jugador tenía asignado cada uno. Así no\npierden el progreso. Ya puedes cerrar el juego.");
                optionsPanel.setVisible(false);

                JPanel panelBotonesFin = new JPanel(new BorderLayout());

                JButton botonSalir = new JButton();
                botonSalir.setText("Cerrar aplicación...");
                botonSalir.setVisible(true);
                botonSalir.addActionListener(e -> {
                    System.exit(0);
                });
                JButton botonReanudar = new JButton();
                botonReanudar.setText("Volver al menú principal...");
                botonReanudar.setVisible(true);
                botonReanudar.addActionListener(e -> {
                    dispose();
                    new menuParade();
                });
                panelBotonesFin.add(botonSalir, BorderLayout.WEST);
                panelBotonesFin.add(botonReanudar, BorderLayout.EAST);
                bottomPanel.add(panelBotonesFin, BorderLayout.SOUTH);
            }
            case PARTIDA_TERMINADA -> {
                WindowListener[] listeners = getWindowListeners();
                for (WindowListener listener : listeners) {
                    removeWindowListener(listener);
                }
                addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

                //juegoTerminado();
                optionsPanel.setVisible(false);
                JPanel panelBotonesFin = new JPanel(new BorderLayout());

                JButton bSalir = new JButton("Cerrar aplicación...");
                bSalir.setVisible(true);
                bSalir.addActionListener(e -> {
                    System.exit(0);
                });
                JButton bVolver = new JButton("Volver al menú principal...");
                bVolver.setVisible(true);
                bVolver.addActionListener(e -> {
                    dispose();
                    new menuParade();
                });
                panelBotonesFin.add(bSalir, BorderLayout.WEST);
                panelBotonesFin.add(bVolver, BorderLayout.EAST);
                bottomPanel.add(panelBotonesFin, BorderLayout.SOUTH);
            }
        }
    }
    */

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

    //Método privado. No debe pertenecer a IVista
    private void initComponents() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }

    @Override
    public void menuPrincipal () {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            println("PARADE - MODO CONSOLA");
            println("\t1. Nueva Partida");
            println("\t2. Cargar Partida");
            println("\t3. Reglas");
            println("\t4. Historial de partidas");
            println("\t5. Top 5 Histórico");
            println("\t6. Salir");
            println("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    c.iniciarJuego();
                    break;
                case 2:
                    c.cargarPartida();
                    break;
                case 3:
                    c.verReglas();
                    break;
                case 4:
                    c.verHistorico();
                    break;
                case 5:
                    c.top5Historico();
                    break;
                case 6:
                    println("\nSaliendo del juego...\n");
                    c.terminarPartida();
                    break;
                default:
                    println("\nPor favor, seleccione una opción correcta\n");
                    break;
            }

        } while (opcion != 6);

        scanner.close();

    }

    @Override
    public void verReglas () {
        println("\n");

        /*String rutaArchivo = "src\\ar\\edu\\unlu\\parade\\recursos\\reglas.txt";

        File archivo = new File(rutaArchivo);

        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                System.out.println(linea);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.print("\n");*/

        println("INTRODUCCION\n" +
                "-Ha comenzado el carnaval en el Pais de las Maravillas. Todos ya disfrazados, las calles decoradas y la emoción se siente en el aire. Eres uno de los organizadores y necesitas\n" +
                "encontrar más personas que deseen ingresar al carnaval, por lo tanto, estás haciendo lo imposible para motivarlos y lograr que se unan a la fiesta. Si tan solo la gente que ya\n" +
                "participa no se fuera tan pronto… pues después de un rato, simplemente pierden todo interés en el carnaval. Y si aparece alguien vistiendo el mismo disfraz o uno mejor, lo\n" +
                "único que logran, es que más personas dejen inmediatamente el carnaval y vayan a quejarse contigo. Si solo quieres que todos se diviertan, ¿por qué tiene que ser tan difícil?\n" +
                "\n" +
                "OBJETIVO DEL JUEGO\n" +
                "- Los jugadores tratarán de no obtener cartas del carnaval, por lo que jugarán cartas de su mano intentando evitarlo. Nadie quiere puntos en el País de las Maravillas.\n" +
                "\n" +
                "COMPONENTES DE JUEGO\n" +
                "- 66 cartas en 6 colores (VERDE, AMARILLO, ROJO, VIOLETA, AZUL, NEGRO), con valores que varían del 0 al 10)\n" +
                "\n" +
                "PREPARACIÓN DEL CARNAVAL\n" +
                "- El jugador inicial se determina al azar. Comenzando con ese jugador, el juego continúa en dirección de las agujas del reloj. El jugador inicial baraja el mazo y reparte 5 \n" +
                "cartas boca abajo a cada jugador. Luego coloca 6 cartas boca arriba una tras otra en el centro de la mesa. Estas cartas representan a los participantes iniciales del carnaval.\n" +
                "La caja del juego se deja en uno de los extremos para simbolizar el frente del carnaval. El otro extremo será el final y el resto de cartas se dejan boca abajo formando un \n" +
                "mazo de robo al centro de la mesa.\n" +
                "\n" +
                "SECUENCIA DE JUEGO\n" +
                "- Durante su turno, un jugador realiza las siguientes acciones en el orden explicado abajo.\n" +
                "\t1. Elige una de las cartas en tu mano y colócala al final del carnaval.\n" +
                "\t2. De ser necesario, algunas cartas dejarán el carnaval y quedarán frente al jugador activo.\n" +
                "\t3. Roba una carta del mazo.\n" +
                "- El siguiente jugador realiza las mismas acciones durante su turno.\n" +
                "\n" +
                "EXPLICACIÓN DE LAS ACCIONES\n" +
                "- 1. El jugador elige una de las cartas en su mano y la coloca boca arriba al final del carnaval. Esta nueva carta no se considera al contar el total de cartas ya existentes en \n" +
                "la siguiente acción.\n" +
                "- 2. Algunas cartas dejarán el carnaval según qué carta se haya jugado.\n" +
                "Si la cantidad de cartas que ya formaban parte del carnaval es menor o igual al valor de la carta jugada, entonces ninguna carta dejará el carnaval.\n" +
                "Si la cantidad de cartas que ya formaban parte del carnaval es mayor al valor de la carta jugada, entonces algunas cartas dejarán el carnaval.\n" +
                "Para determinar qué cartas se retiran del juego, enumera las cartas desde el final del carnaval hacia el frente sin tomar en cuenta la carta recién jugada. Se evaluará cada \n" +
                "carta que cuente con una posición numérica mayor al valor de la carta jugada para determinar si dejará el carnaval.\n" +
                "Para decidir que cartas evaluadas tendrán que dejar el juego, siga las siguientes reglas:\n" +
                "\t- Todas las cartas del mismo color de la carta jugada\n" +
                "\t- Todas las cartas con un valor menor o igual al valor de la carta jugada\n" +
                "Las cartas retiradas se separan por colores y se dejan boca arriba frente al jugador.\n" +
                "El valor de todas las cartas es público así que se aconseja agruparlas y desplazarlas\n" +
                "ligeramente desde la parte superior formando una escala para que el valor de cada una\n" +
                "permanezca visible.\n" +
                "El resto de cartas en el carnaval se mueven para cerrar los espacios vacíos.\n" +
                "Si se juega una carta de valor 0, se evaluarán todas las cartas.\n" +
                "- 3.El jugador roba una carta del mazo para reponer su mano y volver a tener 5 cartas.\n" +
                "\n" +
                "ÚLTIMA RONDA Y FIN DEL JUEGO\n" +
                "- La última ronda comienza cuando un jugador ha recogido cartas de los 6 colores o si el mazo de robo se acaba.\n" +
                "Si un jugador recoge una carta del último color que le faltaba, terminará su turno normalmente. Luego, cada jugador incluyendo al que junto los 6 colores juega un turno extra.\n" +
                " Sin embargo, en este turno final no robarán una carta del mazo.\n" +
                "El juego termina luego de esta última ronda.\n" +
                "Si se acaba el mazo de robo, cada jugador obtiene un turno extra. El juego termina cuando todos quedan con 4 cartas en mano.\n" +
                "Incluso si otros jugadores obtienen su sexto color durante esta ronda final esto ya no surtirá efecto alguno. El juego termina después de esa ronda.\n" +
                "\n" +
                "PUNTUACIÓN\n" +
                "- Luego del final del juego cada jugador elegirá 2 cartas de su mano y descarta el resto. Después añadirán estas 2 últimas cartas a las que ya están boca arriba frente al \n" +
                "jugador. Nota: cada una de estas 2 cartas deberá añadirse a una pila de un color que ya posea o creará una nueva pila si aún no poseía cartas de ese color.\n" +
                "Solo las cartas que permanecen frente a un jugador le otorgarán puntaje. Las cartas que todavía tomaban parte en el desfile deben descartarse.\n" +
                "Por cada color, los jugadores determinarán su puntaje. Cada color resuelve su puntaje por separado de la siguiente forma:\n" +
                "\t1. Determinar quien posee la mayoría de cartas en cada color. El o los jugadores con la mayoría en cada color pondrán esas cartas boca abajo y cada una de estas cartas \n" +
                "\tcontará como 1 solo punto (no se contará el valor impreso).\n" +
                "\t2. Cada jugador sumará los valores de todas sus cartas boca arriba y lo añadirá al total obtenido con las cartas boca abajo.\n" +
                "\n" +
                "GANADOR\n" +
                "- Como el País de las Maravillas siempre está al revés, el ganador será el jugador con menor puntaje. En caso de empate, el jugador empatado con menos cartas\n" +
                "(se cuentan las boca abajo y las boca arriba) gana la partida.\n" +
                "\n" +
                "REGLAS PARA 2 JUGADORES\n" +
                "- La única regla que cambia en esta modalidad es la que determina las mayorías en la puntuación final. Un jugador obtendrá la mayoría solo si tiene al menos 2 cartas más\n" +
                "de un color que el otro jugador.\n");

        FuncionesConsola.PulseEnter();
    }

    @Override
    public void mensajeCreacionArchivo () {
        println("\n----------------------------------------------");
        println("NO SE ENCONTRO EL ARCHIVO, SE CREO UNO VACÍO");
        println("----------------------------------------------\n");
    }

    @Override
    public void mensajeGuardarYSalir () {
        println("\nPartida guardada correctamente. Volviendo al Menú Principal...\n");
    }

    @Override
    public void verHistorico (/*RegistroConjuntoPartidas r*/) throws IOException {

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        RegistroConjuntoPartidas partidas = null;
        try {
            fileInputStream = new FileInputStream("partidas.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            partidas = (RegistroConjuntoPartidas) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException e) {
            ArrayList<RegistroPartida> registroPartidas = new ArrayList<RegistroPartida>();
            partidas = new RegistroConjuntoPartidas(registroPartidas);
            c.mensajeCreacionArchivo();
            fileOutputStream = new FileOutputStream("partidas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.err.println("Se produjo un error de entrada/salida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            println("ClassNotFoundException");
            throw new RuntimeException(e);
        } finally {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
            String fechaHoraFormateada;
            assert partidas != null;
            if (!(partidas.getPartidas().isEmpty())) {
                for (RegistroPartida partida : partidas.getPartidas()) {
                    fechaHoraFormateada = partida.getFechaYHoraPartida().format(formato);
                    println("\n");
                    println("- Partida jugada el " + fechaHoraFormateada + "...");
                    println("  (jugadores ordenados por puesto)");
                    for (RegistroJugadores jugador : partida.getRankingJugador()) {
                        println("\t" + jugador.getPosicionJugador() + "- " + jugador.getDefinicionJugador() + " (" + jugador.getPuntosJugador() + "pts.)");
                    }
                }
                println("\n");
                FuncionesConsola.PulseEnter();
            }
            else {
                println("\nEl historial de partidas esta vacío...\n");
            }
        }
    }

    @Override
    public void verTop5 (/*RegistroConjuntoJugadores j*/) throws IOException {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        RegistroConjuntoJugadores jugadores = null;
        try {
            fileInputStream = new FileInputStream("jugadores.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            jugadores = (RegistroConjuntoJugadores) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException e) {
            ArrayList<RegistroJugadores> registroJugadores = new ArrayList<RegistroJugadores>();
            jugadores = new RegistroConjuntoJugadores(registroJugadores);
            c.mensajeCreacionArchivo();
            fileOutputStream = new FileOutputStream("jugadores.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(jugadores);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.err.println("Se produjo un error de entrada/salida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            println("ClassNotFoundException");
            throw new RuntimeException(e);
        }
        finally {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
            String fechaHoraFormateada;
            if (!(jugadores.getJugadores().isEmpty())) {
                //Hacer solo el top 5
                for (int i = 0; i < 5; i++) {
                    fechaHoraFormateada = jugadores.getJugadores().get(i).getFechaYHoraPartida().format(formato);
                    println("\n");
                    println(i+1 + "- Partida jugada el " + fechaHoraFormateada + ":");
                    println("\t" + jugadores.getJugadores().get(i).getDefinicionJugador() + " - " + jugadores.getJugadores().get(i).getPuntosJugador() + "pts.");
                    println("\n");

                }
                println("\n");
                FuncionesConsola.PulseEnter();
            }
            else {
                println("\nEl Top 5 de mejores puntajes esta vacío...\n");
            }
        }
    }

    @Override
    public void configuracionPartida () {
        Scanner scanner = new Scanner(System.in);
        int cantidadJugadores;
        int jugadoresConNombre;
        println("\nElija la cantidad de jugadores de la partida (2-6): ");
        cantidadJugadores = scanner.nextInt();
        while (cantidadJugadores < 2 || cantidadJugadores > 6) {
            println("\nOpcion incorrecta, la cantidad de jugadores debe ir de 2 a 6...");
            println("Elija la cantidad de jugadores de la partida: ");
            cantidadJugadores = scanner.nextInt();
        }
        println("\nDesea ponerle nombre a sus jugadores?");
        println("1 - SI");
        println("2 - NO");
        println("Seleccione una opción: ");
        jugadoresConNombre = scanner.nextInt();
        while (jugadoresConNombre < 1 || jugadoresConNombre > 2) {
            println("\nOpcion incorrecta, la cantidad de opciones debe ir de 1 a 2...");
            println("Desea ponerle nombre a sus jugadores?");
            println("1 - SI");
            println("2 - NO");
            println("Seleccione una opción: ");
            jugadoresConNombre = scanner.nextInt();
        }

        boolean agregarNombre;
        if (jugadoresConNombre == 1) {
            agregarNombre = true;
            println("\n");
        }
        else {
            agregarNombre = false;
        }

        c.iniciarPartida();
    }

    @Override
    public void cargarPartida (/*, ConjuntoPartidas cp*/) throws IOException {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        ConjuntoPartidas partidas = null;
        try {
            fileInputStream = new FileInputStream("partidas_guardadas.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            partidas = (ConjuntoPartidas) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException e) {
            ArrayList<Partida> cPartidas = new ArrayList<Partida>();
            partidas = new ConjuntoPartidas(cPartidas);
            c.mensajeCreacionArchivo();
            fileOutputStream = new FileOutputStream("partidas_guardadas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.err.println("Se produjo un error de entrada/salida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            println("ClassNotFoundException");
            throw new RuntimeException(e);
        } finally {
            Scanner scanner = new Scanner(System.in);
            int opcion;

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
            String fechaHoraFormateada;
            int indice = 1;
            int indice2;
            if (!(partidas.getPartidas().isEmpty())) {
                for (Partida partida : partidas.getPartidas()) {
                    fechaHoraFormateada = partida.getFechaYHoraPartida().format(formato);
                    println("\n");
                    println(indice + "- Partida guardada el " + fechaHoraFormateada + "...");
                    println("\tJugadores:");
                    indice2 = 1;
                    for (Jugador j : partida.getJugadores()) {
                        println("\t" + indice2 + ". " + j.definicionJugador("", ""));
                        indice2++;
                    }
                    indice++;
                }
                println("\n");
                println(indice + "- Salir");
                println("\n");

                println("Seleccione una opción: ");
                opcion = scanner.nextInt();

                while (opcion < 1 || opcion > indice) {
                    println("\nOpción incorrecta, por favor ingrese una opción valida...");
                    println("Seleccione una opción: ");
                    opcion = scanner.nextInt();
                }
                if (opcion != indice) {
                    Partida p = partidas.getPartidas().get(opcion-1);
                    c.reiniciarPartida (p);
                }
            }
            else {
                println("\nNo hay ninguna partida guardada para cargar...\n");
            }
        }
    }

    @Override
    public void menuTurno (/*Jugador j*/) {
        limpiarPantalla();
        //System.out.println("Jugador del parametro: " + j.definicionJugador("",""));
        System.out.println("Jugador local: " + c.getJugadorLocal().definicionJugador("","") + ", " + c.getJugadorLocal().isTurnoJugador());
        System.out.println(System.identityHashCode(c.getJugadorLocal()));
        System.out.println("\n");
        if (c.getJugadorLocal().isTurnoJugador()) {
            System.out.println("Es turno de " + c.getJugadorLocal().definicionJugador("", "")); //TODO sacar despues
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
            System.out.println("NO es turno de " + c.getJugadorLocal().definicionJugador("", ""));
            indiceInput = 0;
            println("\n");
            println("Actualmente es turno de otro jugador, espera hasta que sea tu turno");
        }
        /*while (indiceInput != 0) {
            Thread.onSpinWait();
        }*/
    }

    //Método interno del modo consola. No pertenece a IVista
    public void menuTurnoOpcion (int opcion) {
        Jugador j = c.getJugadorLocal();
        switch (opcion) {
            case 1:
                c.seleccionarCarta();
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
                c.mensajeGuardarYSalir();
                break;
            default:
                println("\nPor favor, seleccione una opción correcta. DEFAULT.\n");
                break;
        }
        //indiceInput = 0;  //todo esto debe cambiarse solo cuando cambia el turno
    }

    @Override
    public void menuTurnoFinal () {
        limpiarPantalla();
        if (c.getJugadorLocal().isTurnoJugador()) {
            println("----------------------------------------------------------------------------------------------------");
            println("\n \t - ULTIMO TURNO DE " + c.getJugadorLocal().definicionJugador("L ", " ") + " -\n");
            println("----------------------------------------------------------------------------------------------------");
            indiceInput = 2;
            indiceRetorno = 2;
            println("\n");
            //println("TURNO DE" + j.definicionJugador("L ", " "));
            println("\t1. Seleccionar carta para jugar");
            println("\t2. Ver mano");
            println("\t3. Ver desfile");
            println("\t4. Ver area de juego");
            println("\t5. Ver jugadores y sus areas de juego");
            println("Seleccione una opción: ");
        }
        else {
            //System.out.println("NO es turno de " + c.getJugadorLocal().definicionJugador("", ""));
            indiceInput = 0;
            println("\n");
            println("Actualmente es turno de otro jugador, espera hasta que sea tu turno");
        }
        /*while (indiceInput != 0) {
            Thread.onSpinWait();
        }*/
    }

    //Método interno del modo consola. No pertenece a IVista
    public void menuTurnoFinalOpcion(int opcion) {
        Jugador j = c.getJugadorLocal();
        switch (opcion) {
            case 1:
                c.seleccionarCarta();
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
        //indiceInput = 0;
    }

    @Override
    public void agregarNombre (Jugador jugador) {
        Scanner scanner = new Scanner(System.in);
        println("Ingrese el nombre del jugador n°" + jugador.getIdJugador() + ": ");
        String nombreJugador = scanner.nextLine();

        c.setNombre(jugador, nombreJugador);
    }

    @Override
    public void seleccionCarta (Jugador j, DestinoCarta d) {
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
        /*while (indiceInput != 0) {
            Thread.onSpinWait();
        }*/
    }

    //Método interno del modo consola. No pertenece a IVista
    public void seleccionCartaOpcion (int opcion) {
        Jugador j = c.getJugadorLocal();
        DestinoCarta d = null;
        if (indiceInput == 3) { d = DestinoCarta.EVALUAR; }
        else if (indiceInput == 4) { d = DestinoCarta.DESCARTAR; }
        if (j.isTurnoJugador()) {
            while (opcion < 1 || opcion > j.getManoJugador().cantidadMano()) {
                println("Opcion incorrecta...");
                println("Seleccione la carta a jugar: ");

            /*while (indiceInput != 0) {
                Thread.onSpinWait();
            }*/
            }
            c.devolverCarta(j, --opcion, d); //todo paso jugador local entonces modifico la instancia cliente y no la servidor
            //TODO aca cambiar el índice input y el turno
            System.out.println("Finaliza el turno de " + c.getJugadorLocal().definicionJugador("", "")); //TODO sacar despues

            switch (indiceRetorno) {
            // TODO: ACA NO ENTRA NI A CASE 1 NI 2, ENTONCES NO SIGUE EN ULTIMO TURNO.
                case 1:
                    indiceInput = 0;
                    c.finalizarTurno();
                    break;
                case 2:
                    indiceInput = 0;
                    c.finalizarUltimoTurno();
                    break;
                case 3:
                    indiceInput = 0;
                    c.finalizarDescarte();
                    break;
            }
        }
    }

    /*public void ultimoTurno () {
        println("\n  - ULTIMO TURNO -\n");
    }*/

    @Override
    public void mensajeDescarteFinal (Jugador j) {
        limpiarPantalla();
        if (c.getJugadorLocal().isTurnoJugador()) {
            println("----------------------------------------------------------------------------------------------------");
            println("\n \t - DESCARTE DE " + c.getJugadorLocal().definicionJugador("L ", " ") + " -\n");
            println("----------------------------------------------------------------------------------------------------");
            println(j.definicionJugador("El ", "") + " debe seleccionar 2 cartas para descartar");
            println("(las otras dos se añadirán al area de juego para contarse en la puntuación)");
        }
        else {
            //System.out.println("NO es turno de " + c.getJugadorLocal().definicionJugador("", ""));
            indiceInput = 0;
            println("\n");
            println("Actualmente es turno de otro jugador, espera hasta que sea tu turno");
        }
    }

    @Override
    public void mensajeGuardar (boolean msgOpcionIncorrecta) {
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
            //System.out.println("NO es turno de " + c.getJugadorLocal().definicionJugador("", ""));
            limpiarPantalla();
            indiceInput = 0;
            println("\n");
            println(" !!! Un jugador solicitó guardar la partida y salir");
        }
    }

    //Método interno del modo consola. No pertenece a IVista
    public void guardarPartida (int opcion) {
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
        println("----------------------------------------------------------------------------------------------------");
        println("\n");
        println("Jugadores ordenados por puesto");
        int i = 1;
        for (Jugador j:jugadoresPartida) {
            println("\t" + i + "-> " + j.definicionJugador("","") + ": " + j.getPuntos() + "pts.");
            i++;
        }
        println("\n");
        //FuncionesConsola.PulseEnter();
    }

    @Override
    public void habilitarSalir() {
        indiceInput = 5;
        println("----------------------------------------------------------------------------------------------------");
        println("\n");
        println("Para volver al menú principal ingrese '0'");
        println("Para salir del juego ingrese '1'");
        println("\n");
        println("----------------------------------------------------------------------------------------------------");
    }

    //Método interno del modo consola. No pertenece a IVista
    public void evaluarSalirAlMenu(int opcion) {
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

    /*
    //Método interno del modo consola. No pertenece a IVista
    private String numeroFila(int fila) {
        return String.valueOf(fila + 1);
    }

    //Método interno del modo consola. No pertenece a IVista
    private String formatoCelda(String contenido) {
        return String.format("%1s", contenido);
    }*/

    //Método interno del modo consola. No pertenece a IVista
    private void println(String texto) {
        textArea.append("\n" + texto + "\n");
    }

    //Método interno del modo consola. No pertenece a IVista
    private void print(JTextArea textArea, String texto) {
        textArea.append(texto);
    }

    @Override
    public void bienvenidaYEspera (Jugador jugadorLocal) {
        println("Bienvenido " + jugadorLocal.definicionJugador("", "") + ", estamos esperando a que se unan todos los jugadores para empezar la partida.");
    }

    //Método interno del modo consola. No pertenece a IVista
    private void limpiarPantalla() {
        textArea.setText("");
    }
}