package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.modelo.persistencia.*;
import ar.edu.unlu.parade.recursos.Observable;
import ar.edu.unlu.parade.recursos.Observer;
import ar.edu.unlu.parade.recursos.Opcion;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModeloParade extends ObservableRemoto implements IModelo/*, Observable*/ {

    private Partida partida;

    //private ArrayList<Observer> observadores;

    public ModeloParade() {
        //observadores = new ArrayList<Observer>();
        try {
            iniciarAplicacion();
        } catch (RemoteException e) {
            throw new RuntimeException();
        }
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    /*
    @Override
    public void agregarObserver(Observer o) {
        observadores.add(o);
    }
    */

    /*
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
    */

    public void iniciarAplicacion() throws RemoteException {
        //notificarObservadores(Opcion.MENU_PRINCIPAL);
        iniciarJuego();
    }

    public void iniciarJuego () throws RemoteException {
        notificarObservadores(Opcion.SETEO_PARTIDA);
    }

    public void verReglas() throws RemoteException {
        notificarObservadores(Opcion.REGLAS);
    }

    public void verHistorico () throws IOException, ClassNotFoundException, RemoteException {
        notificarObservadores(Opcion.HISTORICO);
    }

    public void mensajeCreacionArchivo () throws RemoteException {
        notificarObservadores(Opcion.CREACION_ARCHIVO);
    }

    public void terminarPartida () throws RemoteException {
        System.exit(0);
    }

    public void iniciarPartida (int cantidadJugadores, boolean agregarNombre) throws RemoteException {
        partida = new Partida(cantidadJugadores, agregarNombre, this);
        partida.inicializar();
        try {
            partida.comenzarJuego(this, false);
        } catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
    }

    public void menuTurno (Jugador j) throws RemoteException {
        notificarObservadores(Opcion.MENU_TURNO);
    }

    public void menuTurnoFinal (Jugador j) throws RemoteException {
        notificarObservadores(Opcion.MENU_TURNO_FINAL);
    }

    public void evaluarCarta(/*Jugador j, DestinoCarta d*/) throws RemoteException {
        notificarObservadores(Opcion.SELECICON_EVALUAR);
    }

    public void descartarCarta(/*Jugador j, DestinoCarta d*/) throws RemoteException {
        notificarObservadores(Opcion.SELECICON_DESCARTAR);
    }

    public void devolverCarta(Jugador j, int opcionCarta, DestinoCarta d) throws RemoteException {
        Carta cartaSeleccionada = j.manoJugador.cartas.get(opcionCarta);

        switch (d) {
            case EVALUAR ->   partida.evaluarDesfile(cartaSeleccionada, j);
            case DESCARTAR -> j.manoJugador.transferirCartas(partida.pilaPartida, cartaSeleccionada);
        }
    }

    public void mensajeDescarteFinal (Jugador j) throws RemoteException {
        notificarObservadores(Opcion.DESCARTE_Y_FINAL);
    }

    public void mensajeGanador (/*Jugador j*/) throws RemoteException {
        notificarObservadores(Opcion.GANADOR_PARTIDA/*, j*/);
    }

    public void mensajeEmpate (/*ArrayList<Jugador> ganadores*/) throws RemoteException {
        notificarObservadores(Opcion.EMPATE_JUGADORES/*, ganadores*/);
    }

    public void mensajeGanadorYRanking () throws RemoteException {
        notificarObservadores(Opcion.RANKING);
    }

    public void pedirNombre (Jugador j) throws RemoteException {
        notificarObservadores(Opcion.ADD_NOMBRE);
    }

    public void setNombre (Jugador j, String nombre) throws RemoteException {
        j.setNombre(nombre);
    }

    public void mostrarDesfile () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_DESFILE);
    }

    public void mostrarAreaDeJuego (Jugador j) throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_AREA);
    }

    public void mostrarJugadores () throws RemoteException {
        for (Jugador j : partida.jugadoresPartida) {
            notificarObservadores(Opcion.MOSTRAR_AREA);
        }
    }

    public void mostrarMano (Jugador j) throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_MANO);
    }

    public void ultimoTurno() throws RemoteException {
        notificarObservadores(Opcion.ULTIMO_TURNO);
    }

    public void cargarPartida () throws IOException, ClassNotFoundException, RemoteException {
        notificarObservadores(Opcion.CARGAR_PARTIDA);
    }

    public void reiniciarPartida (Partida p) throws RemoteException {
        partida = p;
        try {
            partida.comenzarJuego(this, true);
        } catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
    }

    public void top5Historico () throws IOException, ClassNotFoundException, RemoteException {
        notificarObservadores(Opcion.TOP5);
    }

    public void guardarYSalir () throws IOException, ClassNotFoundException, RemoteException {
        partida.guardarFechaYHora();

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
            notificarObservadores(Opcion.CREACION_ARCHIVO);
        }
        finally {
            assert partidas != null;
            partidas.agregarOActualizarPartida(partida);

            fileOutputStream = new FileOutputStream("partidas_guardadas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();

            partida.volverAlMenu();
            notificarObservadores(Opcion.GUARDAR_Y_SALIR);
        }
    }

    public void finalizarPartida(int idPartida) throws IOException, ClassNotFoundException, RemoteException {

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
            partidas = new ConjuntoPartidas(new ArrayList<>());
        }

        assert partidas != null;
        partidas.eliminarPartidasTerminadas(idPartida);

        fileOutputStream = new FileOutputStream("partidas_guardadas.txt");
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(partidas);
        objectOutputStream.close();
    }
}
