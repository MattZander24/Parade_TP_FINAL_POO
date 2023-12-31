package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroPartida;

import java.io.*;
import java.util.*;

public class Partida {

    ModeloParade modelo;
    ArrayList<Jugador> jugadoresPartida;
    PilaDeDescarte pilaPartida;
    Mazo mazoJuego;
    Desfile desfileJuego;

    public Partida(int cantidadJugadores, boolean agregarNombre, ModeloParade modelo) {
        jugadoresPartida = new ArrayList<Jugador>();
        Jugador.resetearIDGen();
        this.modelo = modelo;

        for (int i = 0; i < cantidadJugadores; i++) {
            Jugador jugador = new Jugador();
            this.jugadoresPartida.add(jugador);
            if (agregarNombre) { modelo.pedirNombre(jugador); }
        }

        this.pilaPartida = new PilaDeDescarte();
        this.mazoJuego = new Mazo();
        this.desfileJuego = new Desfile();
    }

    public void inicializar () {
        mazoJuego.generarMazo();
        mazoJuego.mezclarMazo();
        mazoJuego.transferirCartas(desfileJuego, 6);
        for (Jugador j: jugadoresPartida) {
            mazoJuego.transferirCartas(j.manoJugador, 5);
        }
    }

    public void comenzarJuego () throws IOException, ClassNotFoundException {
        boolean finturnos = false;
        while (!finturnos) {
            for (Jugador j : jugadoresPartida) {
                turno(j);
                mazoJuego.transferirCartas(j.manoJugador, 1);
                if (j.areaJugador.tieneSeisColores()) {
                    finturnos = true;
                    break;
                }
            }
            if (finturnos) {
                break;
            }
            finturnos = (finturnos || mazoJuego.estaTerminado());
        }

        modelo.ultimoTurno();

        for (Jugador j : jugadoresPartida) {
            turno(j);
        }
        for (Jugador j : jugadoresPartida) {
            descarteFinal(j);
        }

        puntuacion();
        registrarPartida();

    }

    public void turno (Jugador j) {
        modelo.menuTurno(j);
    }

    public void evaluarDesfile (Carta cartaSeleccionada, Jugador j) {
        int cartasEliminadas = 0;
        if (cartaSeleccionada.getValor() < desfileJuego.cartas.size()) {
            int topeAEvaluar = desfileJuego.cartas.size() - cartaSeleccionada.getValor();
            for (int i = 0; i < topeAEvaluar; i++) {
                Carta cartaEvaluada = desfileJuego.cartas.get(i- cartasEliminadas);
                if (cartaEvaluada.getValor() <= cartaSeleccionada.getValor() ||
                        cartaEvaluada.getColor().equals(cartaSeleccionada.getColor())) {
                    desfileJuego.transferirCartas(j.areaJugador, cartaEvaluada);
                    j.areaJugador.ordenar();
                    cartasEliminadas++;
                }
            }
        }

        j.manoJugador.transferirCartas(desfileJuego, cartaSeleccionada);
    }

    public void descarteFinal (Jugador j) {
        modelo.mensajeDescarteFinal(j);

        modelo.seleccionarCarta(j, DestinoCarta.DESCARTAR);
        modelo.seleccionarCarta(j, DestinoCarta.DESCARTAR);

        for (Carta c : j.manoJugador.cartas) {
            j.manoJugador.transferirCartas(j.areaJugador, c);
        }
        j.areaJugador.ordenar();

    }

    public void puntuacion () {
        //anular las cartas de x color si el jugador es el que
        // mas cartas tiene del color x en su area de juego:
        anularCartas();

        //recuento de puntos:
        //  -por cada carta anulada +1pt y de las no anuladas +carta.valor
        for (Jugador j:jugadoresPartida) {
            j.setPuntos(j.areaJugador.sumarArea());
        }

        //seleccion de jugador ganador
        //  -el que menos puntos tiene
        ArrayList<Jugador> ganadores = new ArrayList<Jugador>();
        for (Jugador j:jugadoresPartida) {
            if (ganadores.isEmpty()) {
                ganadores.add(j);
            }
            else {
                if (j.getPuntos() < ganadores.get(0).getPuntos()) {
                    ganadores.remove(0);
                    ganadores.add(j);
                }
                else if (j.getPuntos() == ganadores.get(0).getPuntos()) {
                    ganadores.add(j);
                }
            }
        }

        if (ganadores.size() > 1) {
            modelo.mensajeEmpate(ganadores);
        }
        else {
            Jugador ganadorUnico = ganadores.get(0);
            modelo.mensajeGanador(ganadorUnico);
        }

        //ordenar el array jugadroes de < puntuacion a mayor
        // Define un Comparator personalizado para ordenar por puntaje de menor a mayor
        Comparator<Jugador> comparadorPorPuntaje = new Comparator<Jugador>() {
            @Override
            public int compare(Jugador jugador1, Jugador jugador2) {
                return Integer.compare(jugador1.getPuntos(), jugador2.getPuntos());
            }
        };

        // Ordena el arreglo utilizando el comparador
        jugadoresPartida.sort(comparadorPorPuntaje);

        int pos = 1;
        for (Jugador j : jugadoresPartida) {
            j.setPosicion(pos);
            pos++;
        }

        modelo.mensajeGanadorYRanking();
    }

    public void anularCartas () {
        if (jugadoresPartida.size() == 2) {
            Jugador j1 = jugadoresPartida.get(0);
            Jugador j2 = jugadoresPartida.get(1);
            for (Color c : Color.values()) {
                int cantidadActualJ1 = cantidadCartasColorJugador(j1, c);
                int cantidadActualJ2 = cantidadCartasColorJugador(j2, c);
                if (cantidadActualJ1-cantidadActualJ2 >= 2) {
                    anularColor(j1, c);
                }
                if (cantidadActualJ2-cantidadActualJ1 >= 2) {
                    anularColor(j2, c);
                }
            }

        }
        else {
            for (Color c : Color.values()) {
                int maxCantColor = 0;
                for (Jugador j : jugadoresPartida) {
                    j.areaJugador.ordenar();
                    int cantidadActual = cantidadCartasColorJugador(j, c);
                    if (cantidadActual > maxCantColor) {
                        maxCantColor = cantidadActual;
                    }
                }
                for (Jugador j : jugadoresPartida) {
                    int cantidadAComparar = cantidadCartasColorJugador(j, c);
                    if (cantidadAComparar == maxCantColor) {
                        anularColor(j, c);
                    }
                }
            }
        }

    }

    public void anularColor (Jugador j, Color color) {
        for (Carta c : j.areaJugador.cartas) {
            if (c.getColor() == color) {
                c.anularCarta();
            }
        }
    }

    public int cantidadCartasColorJugador(Jugador j, Color color) {
        int cantidad = 0;
        for (Carta c : j.areaJugador.cartas) {
            if (c.getColor() == color) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public void registrarPartida() throws IOException, ClassNotFoundException {
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
            modelo.mensajeCreacionArchivo();
        }
        finally {
            ArrayList<RegistroJugadores> regJugadoresPartida = new ArrayList<RegistroJugadores>();
            for (Jugador j : jugadoresPartida) {
                regJugadoresPartida.add(new RegistroJugadores(j.definicionJugador("", ""), j.getPuntos(), j.getPosicion()));
            }
            RegistroPartida partida = new RegistroPartida(regJugadoresPartida);
            partidas.getPartidas().add(partida);

            fileOutputStream = new FileOutputStream("partidas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();
        }
    }
}
