package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.enumerados.Color;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroPartida;

import java.io.*;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.*;

public class Partida implements Serializable {

    transient ModeloParade modelo;
    ArrayList<Jugador> jugadoresPartida;
    ArrayList<Jugador> ganadores;
    PilaDeDescarte pilaPartida;
    Mazo mazoJuego;
    Desfile desfileJuego;

    private static int iDPartidaGen = 1;
    private int iDPartida;
    private int indiceTurno;
    private int indiceFinal;
    //private int indiceUltimoTurno;
    private int contadorUltimoTurno;
    private int contadorDescartes;
    private boolean salirAlMenu = false;
    private Jugador jugadorEnTurno;
    private Jugador jugadorFinal;
    private boolean finturnos = false;

    private int cantidadJugadores;

    private LocalDateTime fechaYHoraGuardado;

    public Partida(/*int cantidadJugadores, boolean agregarNombre, */ModeloParade modelo) throws RemoteException {
        this.jugadoresPartida = new ArrayList<Jugador>();
        this.ganadores = new ArrayList<Jugador>();
        //Jugador.resetearIDGen();
        this.modelo = modelo;

        /*for (int i = 0; i < cantidadJugadores; i++) {
            Jugador jugador = new Jugador();
            this.jugadoresPartida.add(jugador);
            if (agregarNombre) { modelo.pedirNombre(jugador); }
        }*/

        this.pilaPartida = new PilaDeDescarte();
        this.mazoJuego = new Mazo();
        this.desfileJuego = new Desfile();

        this.iDPartida = iDPartidaGen;
        iDPartidaGen++;
        this.indiceTurno = 0;
        this.fechaYHoraGuardado = LocalDateTime.now();
    }

    public LocalDateTime getFechaYHoraPartida() {
        return fechaYHoraGuardado;
    }

    public void setFechaYHoraPartida(LocalDateTime fechaYHoraPartida) {
        this.fechaYHoraGuardado = fechaYHoraPartida;
    }

    public int getIdPartida() { return iDPartida; }

    public ArrayList<Jugador> getJugadores() {
        return jugadoresPartida;
    }

    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadoresPartida) {
        this.jugadoresPartida = jugadoresPartida;
    }

    public ArrayList<Jugador> getGanadores() {
        return ganadores;
    }

    public void setGanadores(ArrayList<Jugador> ganadores) {
        this.ganadores = ganadores;
    }

    public Desfile getDesfileJuego() {
        return desfileJuego;
    }

    public void setDesfileJuego(Desfile desfileJuego) {
        this.desfileJuego = desfileJuego;
    }

    public /*Jugador*/ void agregarJugador (Jugador jugador) throws RemoteException {
        /*Jugador jugador = new Jugador();
        if (!Objects.equals(nombre, "")) {
            jugador.setNombre(nombre);
        }*/
        this.jugadoresPartida.add(jugador);
        //return jugador;
    }

    public void inicializar () {
        mazoJuego.generarMazo();
        mazoJuego.mezclarMazo();
        mazoJuego.transferirCartas(desfileJuego, 6);
        for (Jugador j: jugadoresPartida) {
            mazoJuego.transferirCartas(j.manoJugador, 5);
        }
    }

    public void comenzarJuego (ModeloParade m, boolean reasignar) throws IOException, ClassNotFoundException {
        if (reasignar) {
            modelo = m;
        }

        //DEBUG
        for (Jugador j : jugadoresPartida) {
            int indice = jugadoresPartida.indexOf(j)+ 1;
            System.out.println("Jugador n°" + indice + ": " + j.definicionJugador("",""));
        }

        modelo.actualizarJugador();
        iniciarTurno();
    }

    public void iniciarTurno () throws IOException, ClassNotFoundException {

        /*do {
            for (int i = 0; i < jugadoresPartida.size(); i++) {
                Jugador jugadorEnTurno = jugadoresPartida.get(indiceTurno);
                jugadorEnTurno.setTurnoJugador(true);
                modelo.actualizarTurno();

                System.out.println("TURNO de " + jugadorEnTurno.definicionJugador("", "") + ", " + jugadorEnTurno.isTurnoJugador());
                System.out.println(System.identityHashCode(jugadorEnTurno));

                turno();
                while (jugadorEnTurno.isTurnoJugador()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } while (!finturnos);*/

        if (!finturnos) {
            jugadorEnTurno = jugadoresPartida.get(indiceTurno);
            jugadorEnTurno.setTurnoJugador(true);
            //modelo.actualizarTurno();
            modelo.actualizarJugador(); //TODO DE HECHO ESTE METODO OBSOLECE ACTUALIZAR TURNO

            System.out.println("TURNO de " + jugadorEnTurno.definicionJugador("", "") + ", " + jugadorEnTurno.isTurnoJugador());
            System.out.println(System.identityHashCode(jugadorEnTurno));

            turno();
        }
        else {
            iniciarSecuenciaFinal();
        }
    }

    //despues de que el jugador mueve invoca esta funcion (vista)
    public void finalizarTurno () throws IOException, ClassNotFoundException {
        //jugadorFinal = null;

        /*if (salirAlMenu) {
            break;
        }*/

        if (!salirAlMenu) {
            mazoJuego.transferirCartas(jugadorEnTurno.manoJugador, 1);
            if (jugadorEnTurno.areaJugador.tieneSeisColores()) {
                jugadorFinal = jugadorEnTurno;
                finturnos = true;
                //break;
            }
            if (mazoJuego.estaTerminado()) {
                jugadorFinal = jugadorEnTurno;
                finturnos = true;
                //break;
            }

            if (indiceTurno == jugadoresPartida.size() - 1) {
                indiceTurno = 0;
            } else {
                indiceTurno++;
            }

            jugadorEnTurno.setTurnoJugador(false);
            iniciarTurno();
        }
    }

    //} while (!finturnos);

    public void iniciarSecuenciaFinal () throws IOException, ClassNotFoundException {
        if (!salirAlMenu) {
            //modelo.ultimoTurno();
            indiceFinal = jugadoresPartida.indexOf(jugadorFinal);
            //indiceUltimoTurno = indiceFinal;
            contadorUltimoTurno = 0;
            System.out.println("Inicia Secuencia FINAL");
            secuenciaUltimoTurno();
        }
    }


    public void secuenciaUltimoTurno () throws IOException, ClassNotFoundException {

        /*for (int i = 0; i < jugadoresPartida.size(); i++) { //todo acá el flujo no se para en cada turno final
            if (indiceUltimoTurno == jugadoresPartida.size() - 1) {
                indiceUltimoTurno = 0;
            } else {
                indiceUltimoTurno += 1;
            }
            turnoFinal(jugadoresPartida.get(indiceUltimoTurno));
        }*/

        if (contadorUltimoTurno < jugadoresPartida.size()) {
            //jugadorEnTurno = jugadoresPartida.get(indiceUltimoTurno);
            jugadorEnTurno = jugadoresPartida.get(indiceFinal);
            jugadorEnTurno.setTurnoJugador(true);
            //modelo.actualizarTurno();
            modelo.actualizarJugador(); //TODO DE HECHO ESTE METODO OBSOLECE ACTUALIZAR TURNO

            System.out.println("ULTIMO TURNO de " + jugadorEnTurno.definicionJugador("", "") + ", " + jugadorEnTurno.isTurnoJugador());
            System.out.println(System.identityHashCode(jugadorEnTurno));

            turnoFinal(jugadorEnTurno);
        } else {
            contadorUltimoTurno = 0;
            //TODO esto debería no tener que hacerse xq cuando pega la ultima el indice ultimo turno debe ser el mismo
            //indiceUltimoTurno = indiceFinal;
            contadorDescartes = 0;
            secuenciaDescarte();
        }
    }

    public void finalizarSecuenciaUltimoTurno () throws IOException, ClassNotFoundException {
        //if (indiceUltimoTurno == jugadoresPartida.size() - 1) {
        //
        // mazoJuego.transferirCartas(jugadorEnTurno.manoJugador, 1);
        if (indiceFinal == jugadoresPartida.size() - 1) {
            //indiceUltimoTurno = 0;
            indiceFinal = 0;
        } else {
            //indiceUltimoTurno += 1;
            indiceFinal += 1;
        }
        contadorUltimoTurno++;
        jugadorEnTurno.setTurnoJugador(false);
        secuenciaUltimoTurno();
    }

    public void secuenciaDescarte () throws IOException, ClassNotFoundException {

        /*for (int i = 0; i < jugadoresPartida.size(); i++) { //todo acá el flujo no se para en cada descarte final
            if (indiceUltimoTurno == jugadoresPartida.size() - 1) {
                indiceUltimoTurno = 0;
            } else {
                indiceUltimoTurno += 1;
            }
            System.out.println("descarte");
            descarteFinal(jugadoresPartida.get(indiceUltimoTurno));
        }*/

        /*if (contadorUltimoTurno < jugadoresPartida.size()) {
            descarteFinal(jugadoresPartida.get(indiceUltimoTurno));
            if (indiceUltimoTurno == jugadoresPartida.size() - 1) {
                indiceUltimoTurno = 0;
            } else {
                indiceUltimoTurno += 1;
            }
            //System.out.println("descarte");
            contadorUltimoTurno++;
        }*/

        if (contadorUltimoTurno < jugadoresPartida.size()) {
            //jugadorEnTurno = jugadoresPartida.get(indiceUltimoTurno);
            jugadorEnTurno = jugadoresPartida.get(indiceFinal);
            jugadorEnTurno.setTurnoJugador(true);
            //modelo.actualizarTurno();
            modelo.actualizarJugador(); //TODO DE HECHO ESTE METODO OBSOLECE ACTUALIZAR TURNO

            System.out.println("DESCARTE N°" + contadorDescartes + " de " + jugadorEnTurno.definicionJugador("", "") + ", " + jugadorEnTurno.isTurnoJugador());
            System.out.println(System.identityHashCode(jugadorEnTurno));

            descarteFinal(/*jugadorEnTurno*/);
        }
        else{
            puntuacion();
            modelo.finalizarPartida(iDPartida);
            registrarPartida();
            registrarJugadores();
        }
    }

    public void finalizarSecuenciaDescarte () throws IOException, ClassNotFoundException {
        if (contadorDescartes == 1) {
            if (indiceFinal == jugadoresPartida.size() - 1) {
                indiceFinal = 0;
            } else {
                indiceFinal += 1;
            }
            descarteUltimasCartas(jugadorEnTurno);
            contadorDescartes = 0;
            contadorUltimoTurno++;
        }
        else {
            contadorDescartes++;
        }
        //System.out.println("descarte");
        jugadorEnTurno.setTurnoJugador(false);
        secuenciaDescarte();
    }


    public void turno () throws RemoteException {
        modelo.menuTurno();
        //j.setTurnoJugador(false);
    }

    public void turnoFinal (Jugador j) throws RemoteException {
        modelo.menuTurnoFinal(/*j*/);
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

    public void descarteFinal (/*Jugador j*/) throws RemoteException {
        modelo.mensajeDescarteFinal(/*j*/);
        modelo.descartarCarta();
        //contadorDescartes++;
    }

    public void descarteUltimasCartas (Jugador j) throws RemoteException {
        for (Carta c : j.manoJugador.cartas) {
            j.manoJugador.transferirCartas(j.areaJugador, c);
        }
        j.areaJugador.ordenar();
    }

    public void puntuacion () throws RemoteException {
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
        //ArrayList<Jugador> ganadores = new ArrayList<Jugador>();
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

        /*for (Jugador j:jugadoresPartida) {
            j.setEsGanador(true);
        }*/

        if (ganadores.size() > 1) {
            modelo.mensajeEmpate(/*ganadores*/);
        }
        else {
            //Jugador ganadorUnico = ganadores.get(0);
            modelo.mensajeGanador(/*ganadorUnico*/);
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
            assert partidas != null;
            partidas.getPartidas().add(partida);

            fileOutputStream = new FileOutputStream("partidas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();
        }
    }

    public void registrarJugadores() throws IOException, ClassNotFoundException {
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
            //modelo.mensajeCreacionArchivo();
        }
        finally {
            //ArrayList<RegistroJugadores> regJugadoresPartida = new ArrayList<RegistroJugadores>();
            for (Jugador j : jugadoresPartida) {
                //regJugadoresPartida.add(new RegistroJugadores(j.definicionJugador("", ""), j.getPuntos(), j.getPosicion()));
                jugadores.getJugadores().add(new RegistroJugadores(j.definicionJugador("", ""), j.getPuntos(), j.getPosicion()));
            }

            //RegistroPartida partida = new RegistroPartida(regJugadoresPartida);
            //assert partidas != null;
            //partidas.getPartidas().add(partida);

            jugadores.ordenarJugadores();

            fileOutputStream = new FileOutputStream("jugadores.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(jugadores);
            objectOutputStream.close();
        }
    }

    public void guardarFechaYHora() {
        fechaYHoraGuardado = LocalDateTime.now();
    }

    public void volverAlMenu() {
        salirAlMenu = true;
    }
}
