package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroPartida;
import ar.edu.unlu.parade.recursos.Observable;
import ar.edu.unlu.parade.recursos.Observer;
import ar.edu.unlu.parade.recursos.Opcion;

import java.io.*;
import java.util.ArrayList;

public class ModeloParade implements Observable {

    private Partida partida;
    private ArrayList<Observer> observadores;

    public ModeloParade() {
        observadores = new ArrayList<Observer>();
    }

    @Override
    public void agregarObserver(Observer o) {
        observadores.add(o);
    }

    private void notificarObservadores(Opcion opcion) {
        for(Observer o : observadores){
            o.notificar(opcion);
        }
    }

    private void notificarObservadores(Opcion opcion, Object object) {
        for(Observer o : observadores){
            o.notificar(opcion, object);
        }
    }

    private void notificarObservadores(Opcion opcion, Object object, Object object2) {
        for(Observer o : observadores){
            o.notificar(opcion, object, object2);
        }
    }

    public void iniciarAplicacion() {
        notificarObservadores(Opcion.MENU_PRINCIPAL);
    }

    public void iniciarJuego () {
        notificarObservadores(Opcion.SETEO_PARTIDA);
    }

    public void verReglas() {
        notificarObservadores(Opcion.REGLAS);
    }

    public void verHistorico () throws IOException, ClassNotFoundException {
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
            notificarObservadores(Opcion.CREACION_ARCHIVO);
            fileOutputStream = new FileOutputStream("partidas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.err.println("Se produjo un error de entrada/salida: " + e.getMessage());
        }
        finally {
            notificarObservadores(Opcion.HISTORICO, partidas);
        }
    }

    public void mensajeCreacionArchivo () {
        notificarObservadores(Opcion.CREACION_ARCHIVO);
    }

    public void terminarPartida () {
        System.exit(0);
    }

    public void iniciarPartida (int cantidadJugadores, boolean agregarNombre) {
        partida = new Partida(cantidadJugadores, agregarNombre, this);
        partida.inicializar();
        try {
            partida.comenzarJuego();
        } catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
    }

    public void menuTurno (Jugador j) {
        notificarObservadores(Opcion.MENU_TURNO, j);
    }

    public void seleccionarCarta(Jugador j, DestinoCarta d) {
        notificarObservadores(Opcion.SELECICON_CARTA,j, d);
    }

    public void devolverCarta(Jugador j, int opcionCarta, DestinoCarta d) {
        Carta cartaSeleccionada = j.manoJugador.cartas.get(opcionCarta);

        switch (d) {
            case EVALUAR ->   partida.evaluarDesfile(cartaSeleccionada, j);
            case DESCARTAR -> j.manoJugador.transferirCartas(partida.pilaPartida, cartaSeleccionada);
        }
    }

    public void mensajeDescarteFinal (Jugador j) {
        notificarObservadores(Opcion.DESCARTE_Y_FINAL, j);
    }

    public void mensajeGanador (Jugador j) {
        notificarObservadores(Opcion.GANADOR_PARTIDA, j);
    }

    public void mensajeEmpate (ArrayList<Jugador> ganadores) {
        notificarObservadores(Opcion.EMPATE_JUGADORES, ganadores);
    }

    public void mensajeGanadorYRanking () {
        notificarObservadores(Opcion.RANKING, partida.jugadoresPartida);
    }

    public void pedirNombre (Jugador j) {
        notificarObservadores(Opcion.ADD_NOMBRE, j);
    }

    public void setNombre (Jugador j, String nombre) {
        j.setNombre(nombre);
    }

    public void mostrarDesfile () {
        notificarObservadores(Opcion.MOSTRAR_DESFILE, partida.desfileJuego);
    }

    public void mostrarAreaDeJuego (Jugador j) {
        notificarObservadores(Opcion.MOSTRAR_AREA, j);
    }

    public void mostrarJugadores () {
        for (Jugador j : partida.jugadoresPartida) {
            notificarObservadores(Opcion.MOSTRAR_AREA, j);
        }
    }

    public void mostrarMano (Jugador j) {
        notificarObservadores(Opcion.MOSTRAR_MANO, j);
    }

    public void ultimoTurno() {
        notificarObservadores(Opcion.ULTIMO_TURNO);
    }

    public void cargarPartida () {
        /*
        Desplegar la lista de las partidas inconclusas, detallando la fecha y hora en que se guardó
        y la lista de jugadores (nombres) que tiene la partida, con un índice numérico, que será el
        índice por el cual el jugador elegirá qué partida cargar, más una última opción de volver.
        */

        /*
        Partidas Inconclusas:

            1- Partida guardada el dd/mm/aaaa a las hh:mm
                Jugadores:
                    1. J1
                    2. J2
                    3. J3

            2- Partida guardada el dd/mm/aaaa a las hh:mm
                Jugadores:
                    1. J1
                    2. J2

            3- Salir
        */
    }

    public void top5Historico () throws IOException, ClassNotFoundException {
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
            notificarObservadores(Opcion.CREACION_ARCHIVO);
            fileOutputStream = new FileOutputStream("jugadores.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(jugadores);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.err.println("Se produjo un error de entrada/salida: " + e.getMessage());
        }
        finally {
            notificarObservadores(Opcion.TOP5, jugadores);
        }
    }

    public void guardarYSalir () {

    }
}
