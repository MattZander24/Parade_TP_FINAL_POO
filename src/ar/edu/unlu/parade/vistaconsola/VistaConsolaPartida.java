package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.modelo.DestinoCarta;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroPartida;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class VistaConsolaPartida {

    private VistaConsolaAreaDeJuego vca;
    private VistaConsolaDesfile vcd;
    private VistaConsolaMano vcm;

    public VistaConsolaPartida(VistaConsolaAreaDeJuego vca, VistaConsolaDesfile vcd, VistaConsolaMano vcm) {
        this.vca = vca;
        this.vcd = vcd;
        this.vcm = vcm;
    }

    public void menuPrincipal (ControladorParade c) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("PARADE - MODO CONSOLA");
            System.out.println("\t1. Nueva Partida");
            System.out.println("\t2. Reglas");
            System.out.println("\t3. Historial");
            System.out.println("\t4. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    c.iniciarJuego();
                    break;
                case 2:
                    c.verReglas();
                    break;
                case 3:
                    c.verHistorico();
                    break;
                case 4:
                    System.out.println("\nSaliendo del juego...\n");
                    c.terminarPartida();
                    break;
                default:
                    System.out.println("\nPor favor, seleccione una opción correcta\n");
                    break;
            }

        } while (opcion != 4);

        scanner.close();

    }

    public void verReglas () {
        System.out.println("\n");

        String rutaArchivo = "src\\ar\\edu\\unlu\\parade\\recursos\\reglas.txt";

        File archivo = new File(rutaArchivo);

        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                System.out.println(linea);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.print("\n");
        FuncionesConsola.PulseEnter();
    }

    public void mensajeCreacionArchivo () {
        System.out.println("\n----------------------------------------------");
        System.out.println("NO SE ENCONTRO EL ARCHIVO, SE CREO UNO VACÍO");
        System.out.println("----------------------------------------------\n");
    }

    public void verHistorico (RegistroConjuntoPartidas r) {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
        String fechaHoraFormateada;
        if (!(r.getPartidas().isEmpty())) {
            for (RegistroPartida partida : r.getPartidas()) {
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
            System.out.println("El historial de partidas esta vacío...\n");
        }
    }

    public void configuracionPartida (ControladorParade c) {
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

        c.iniciarPartida(cantidadJugadores, agregarNombre);
    }

    public void menuTurno (ControladorParade c, Jugador j) {
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

    public void agregarNombre (ControladorParade c, Jugador jugador) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del jugador n°" + jugador.getIdJugador() + ": ");
        String nombreJugador = scanner.nextLine();

        c.setNombre(jugador, nombreJugador);
    }

    public void seleccionCarta (ControladorParade c, Jugador j, DestinoCarta d) {
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
        System.out.println("\n  - ULTIMO TURNO -");
    }

    public void mensajeDescarteFinal (Jugador j) {
        System.out.println(j.definicionJugador("El ", "") + " debe seleccionar 2 cartas para descartar");
        System.out.println("(las otras dos se añadirán al area de juego para contarse en la puntuación)");
    }

    public void mensajeGanador(Jugador j) {
        System.out.println("\nEl ganador de la partida es " + j.definicionJugador("el ", "") + " con " + j.getPuntos() + " puntos...");
    }

    public void mensajeEmpateEntreJugadores (ArrayList<Jugador> ganadores) {
        System.out.println("¡Se ha producido un empate!\n");
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
}
