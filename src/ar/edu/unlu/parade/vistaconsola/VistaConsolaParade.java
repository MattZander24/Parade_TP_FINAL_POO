package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.enumerados.EstadoPartida;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.modelo.*;
import ar.edu.unlu.parade.modelo.persistencia.*;

import java.io.*;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

//LE COMENTE LA IMPLEMENTACION A IVISTA PARA QUE NO DE ERROR, SI NECESITO DE VUELTA DESCOMENTARLO
public class VistaConsolaParade /*implements IVista , Observer*/ {

    private ControladorParade c;
    private VistaConsolaAreaDeJuego vca;
    private VistaConsolaDesfile vcd;
    private VistaConsolaMano vcm;

    public VistaConsolaParade () {
        this.vca = new VistaConsolaAreaDeJuego();
        this.vcd = new VistaConsolaDesfile();
        this.vcm = new VistaConsolaMano();
    }

    public ControladorParade getC() {
        return c;
    }

    public void setC(ControladorParade c) {
        this.c = c;
    }

    public void actualizarVistaParaAccion(EstadoPartida estadoActual) throws RemoteException {
        System.out.println("Not Intended");
    }

    public void iniciarVista() {
        System.out.println("Not Intended");
    }

    public void menuPrincipal () {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("PARADE - MODO CONSOLA");
            System.out.println("\t1. Nueva Partida");
            System.out.println("\t2. Cargar Partida");
            System.out.println("\t3. Reglas");
            System.out.println("\t4. Historial de partidas");
            System.out.println("\t5. Top 5 Histórico");
            System.out.println("\t6. Salir");
            System.out.print("Seleccione una opción: ");

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
                    System.out.println("\nSaliendo del juego...\n");
                    c.terminarPartida();
                    break;
                default:
                    System.out.println("\nPor favor, seleccione una opción correcta\n");
                    break;
            }

        } while (opcion != 6);

        scanner.close();

    }

    public void verReglas () {
        System.out.println("\n");

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

        System.out.println("INTRODUCCION\n" +
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

    public void mensajeCreacionArchivo () {
        System.out.println("\n----------------------------------------------");
        System.out.println("NO SE ENCONTRO EL ARCHIVO, SE CREO UNO VACÍO");
        System.out.println("----------------------------------------------\n");
    }

    public void mensajeGuardarYSalir () {
        System.out.println("\nPartida guardada correctamente. Volviendo al Menú Principal...\n");
    }

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
            System.out.println("ClassNotFoundException");
            throw new RuntimeException(e);
        } finally {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
            String fechaHoraFormateada;
            assert partidas != null;
            if (!(partidas.getPartidas().isEmpty())) {
                for (RegistroPartida partida : partidas.getPartidas()) {
                    fechaHoraFormateada = partida.getFechaYHoraPartida().format(formato);
                    System.out.print("\n");
                    System.out.println("- Partida jugada el " + fechaHoraFormateada + "...");
                    System.out.println("  (jugadores ordenados por puesto)");
                    for (RegistroJugadores jugador : partida.getRankingJugador()) {
                        System.out.println("\t" + jugador.getPosicionJugador() + "- " + jugador.getDefinicionJugador() + " (" + jugador.getPuntosJugador() + "pts.)");
                    }
                }
                System.out.print("\n");
                FuncionesConsola.PulseEnter();
            }
            else {
                System.out.println("\nEl historial de partidas esta vacío...\n");
            }
        }
    }

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
            System.out.println("ClassNotFoundException");
            throw new RuntimeException(e);
        }
        finally {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
            String fechaHoraFormateada;
            if (!(jugadores.getJugadores().isEmpty())) {
                //Hacer solo el top 5
                for (int i = 0; i < 5; i++) {
                    fechaHoraFormateada = jugadores.getJugadores().get(i).getFechaYHoraPartida().format(formato);
                    System.out.print("\n");
                    System.out.println(i+1 + "- Partida jugada el " + fechaHoraFormateada + ":");
                    System.out.println("\t" + jugadores.getJugadores().get(i).getDefinicionJugador() + " - " + jugadores.getJugadores().get(i).getPuntosJugador() + "pts.");
                    System.out.print("\n");

                }
                System.out.print("\n");
                FuncionesConsola.PulseEnter();
            }
            else {
                System.out.println("\nEl Top 5 de mejores puntajes esta vacío...\n");
            }
        }
    }

    public void configuracionPartida () {
        Scanner scanner = new Scanner(System.in);
        int cantidadJugadores;
        int jugadoresConNombre;
        System.out.print("\nElija la cantidad de jugadores de la partida (2-6): ");
        cantidadJugadores = scanner.nextInt();
        while (cantidadJugadores < 2 || cantidadJugadores > 6) {
            System.out.print("\nOpcion incorrecta, la cantidad de jugadores debe ir de 2 a 6...");
            System.out.print("Elija la cantidad de jugadores de la partida: ");
            cantidadJugadores = scanner.nextInt();
        }
        System.out.println("\nDesea ponerle nombre a sus jugadores?");
        System.out.println("1 - SI");
        System.out.println("2 - NO");
        System.out.print("Seleccione una opción: ");
        jugadoresConNombre = scanner.nextInt();
        while (jugadoresConNombre < 1 || jugadoresConNombre > 2) {
            System.out.println("\nOpcion incorrecta, la cantidad de opciones debe ir de 1 a 2...");
            System.out.println("Desea ponerle nombre a sus jugadores?");
            System.out.println("1 - SI");
            System.out.println("2 - NO");
            System.out.print("Seleccione una opción: ");
            jugadoresConNombre = scanner.nextInt();
        }

        boolean agregarNombre;
        if (jugadoresConNombre == 1) {
            agregarNombre = true;
            System.out.print("\n");
        }
        else {
            agregarNombre = false;
        }

        //c.iniciarPartida(cantidadJugadores, agregarNombre); ///!
    }

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
            System.out.println("ClassNotFoundException");
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
                    System.out.print("\n");
                    System.out.println(indice + "- Partida guardada el " + fechaHoraFormateada + "...");
                    System.out.println("\tJugadores:");
                    indice2 = 1;
                    for (Jugador j : partida.getJugadores()) {
                        System.out.println("\t" + indice2 + ". " + j.definicionJugador("", ""));
                        indice2++;
                    }
                    indice++;
                }
                System.out.print("\n");
                System.out.println(indice + "- Salir");
                System.out.print("\n");

                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                while (opcion < 1 || opcion > indice) {
                    System.out.print("\nOpción incorrecta, por favor ingrese una opción valida...");
                    System.out.print("Seleccione una opción: ");
                    opcion = scanner.nextInt();
                }
                if (opcion != indice) {
                    Partida p = partidas.getPartidas().get(opcion-1);
                    c.reiniciarPartida (p);
                }
            }
            else {
                System.out.println("\nNo hay ninguna partida guardada para cargar...\n");
            }
        }
    }

    public void menuTurno (Jugador j) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        boolean gys = false;

        do {
            System.out.print("\n");
            System.out.println("TURNO DE" + j.definicionJugador("L ", " "));
            System.out.println("\t1. Seleccionar carta para jugar");
            System.out.println("\t2. Ver mano");
            System.out.println("\t3. Ver desfile");
            System.out.println("\t4. Ver area de juego");
            System.out.println("\t5. Ver jugadores y sus areas de juego");
            System.out.println("\t6. Guardar y Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    c.seleccionarCarta(j);
                    break;
                case 2:
                    c.mostrarMano(j);
                    break;
                case 3:
                    c.mostrarDesfile();
                    break;
                case 4:
                    c.mostrarAreaDeJuego(j);
                    break;
                case 5:
                    c.mostrarJugadores();
                    break;
                case 6:
                    System.out.println("Desea Guardar y Salir?");
                    System.out.println("1 - SI");
                    System.out.println("2 - NO");
                    System.out.print("Seleccione una opción: ");
                    int confirmacion = scanner.nextInt();
                    while (confirmacion < 1 || confirmacion > 2) {
                        System.out.println("\nOpcion incorrecta, la cantidad de opciones debe ir de 1 a 2...");
                        System.out.println("Desea Guardar y Salir?");
                        System.out.println("1 - SI");
                        System.out.println("2 - NO");
                        System.out.print("Seleccione una opción: ");
                        confirmacion = scanner.nextInt();
                    }
                    if (confirmacion == 1) {
                        gys = true;
                        c.guardarYSalir();
                        break;
                    } else if (confirmacion == 2) {
                        System.out.println("\nOperación cancelada. Volviendo al menú...\n");
                        break;
                    }
                default:
                    System.out.println("\nPor favor, seleccione una opción correcta\n");
                    break;
            }
        } while (opcion != 1 && !gys);

    }

    public void menuTurnoFinal (Jugador j) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.print("\n");
            System.out.println("TURNO DE" + j.definicionJugador("L ", " "));
            System.out.println("\t1. Seleccionar carta para jugar");
            System.out.println("\t2. Ver mano");
            System.out.println("\t3. Ver desfile");
            System.out.println("\t4. Ver area de juego");
            System.out.println("\t5. Ver jugadores y sus areas de juego");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    c.seleccionarCarta(j);
                    break;
                case 2:
                    c.mostrarMano(j);
                    break;
                case 3:
                    c.mostrarDesfile();
                    break;
                case 4:
                    c.mostrarAreaDeJuego(j);
                    break;
                case 5:
                    c.mostrarJugadores();
                    break;
                default:
                    System.out.println("\nPor favor, seleccione una opción correcta\n");
                    break;
            }
        } while (opcion != 1);
    }

    public void agregarNombre (Jugador jugador) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del jugador n°" + jugador.getIdJugador() + ": ");
        String nombreJugador = scanner.nextLine();

        c.setNombre(jugador, nombreJugador);
    }

    public void seleccionCarta (Jugador j, DestinoCarta d) {
        c.mostrarMano(j);

        Scanner scanner = new Scanner(System.in);
        int opcion;
        System.out.print("Seleccione la carta a jugar: ");

        opcion = scanner.nextInt();

        while (opcion < 1 || opcion > j.getManoJugador().cantidadMano()) {
            System.out.print("Opcion incorrecta...");
            System.out.print("Seleccione la carta a jugar: ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            }
        }

        c.devolverCarta(j, --opcion, d);
    }

    public void ultimoTurno () {
        System.out.println("\n  - ULTIMO TURNO -\n");
    }

    public void mensajeDescarteFinal (Jugador j) {
        System.out.println(j.definicionJugador("El ", "") + " debe seleccionar 2 cartas para descartar");
        System.out.println("(las otras dos se añadirán al area de juego para contarse en la puntuación)");
    }

    public void mensajeGanador(Jugador j) {
        System.out.println("\nEl ganador de la partida es " + j.definicionJugador("el ", "") + " con " + j.getPuntos() + " puntos...");
    }

    public void mensajeEmpateEntreJugadores (ArrayList<Jugador> ganadores) {
        System.out.println("\n¡Se ha producido un empate!\n");
        for (Jugador j : ganadores) {
            System.out.println("Uno de los ganadores de la partida es " +  j.definicionJugador("el ", "") + " con " + j.getPuntos() + " PUNTOS...");
        }
    }

    public void mensajeRanking(ArrayList<Jugador> jugadoresPartida) {
        System.out.print("\n");
        System.out.println("Jugadores ordenados por puesto");
        int i = 1;
        for (Jugador j:jugadoresPartida) {
            System.out.println("\t" + i + "-> " + j.definicionJugador("","") + ": " + j.getPuntos() + "pts.");
            i++;
        }
        System.out.print("\n");
        FuncionesConsola.PulseEnter();
    }

    public void mostrarADJ (Jugador j) {
        vca.mostrar(j);
    }

    public void mostrarD (Desfile d) {
        vcd.mostrar(d);
    }

    public void mostrarM (Jugador j) {
        vcm.mostrar(j);
    }

}