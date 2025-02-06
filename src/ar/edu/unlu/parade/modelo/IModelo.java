package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.modelo.persistencia.*;
import ar.edu.unlu.parade.recursos.Opcion;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IModelo extends IObservableRemoto {

    public Partida getPartida();

    public void setPartida(Partida partida);
    public void iniciarAplicacion() throws RemoteException;

    public void iniciarJuego () throws RemoteException;

    public void verReglas() throws RemoteException;

    public void verHistorico () throws IOException, ClassNotFoundException, RemoteException;

    public void mensajeCreacionArchivo () throws RemoteException;

    public void terminarPartida () throws RemoteException;

    public void iniciarPartida (int cantidadJugadores, boolean agregarNombre) throws RemoteException;

    public void menuTurno (Jugador j) throws RemoteException;

    public void menuTurnoFinal (Jugador j) throws RemoteException;

    public void evaluarCarta(/*Jugador j, DestinoCarta d*/) throws RemoteException;

    public void descartarCarta(/*Jugador j, DestinoCarta d*/) throws RemoteException;

    public void devolverCarta(Jugador j, int opcionCarta, DestinoCarta d) throws RemoteException;

    public void mensajeDescarteFinal (Jugador j) throws RemoteException;

    public void mensajeGanador (/*Jugador j*/) throws RemoteException;

    public void mensajeEmpate (/*ArrayList<Jugador> ganadores*/) throws RemoteException;

    public void mensajeGanadorYRanking () throws RemoteException;

    public void pedirNombre (Jugador j) throws RemoteException;

    public void setNombre (Jugador j, String nombre) throws RemoteException;

    public void mostrarDesfile () throws RemoteException;

    public void mostrarAreaDeJuego (Jugador j) throws RemoteException;

    public void mostrarJugadores () throws RemoteException;

    public void mostrarMano (Jugador j) throws RemoteException;

    public void ultimoTurno() throws RemoteException;

    public void cargarPartida () throws IOException, ClassNotFoundException, RemoteException;

    public void reiniciarPartida (Partida p) throws RemoteException;

    public void top5Historico () throws IOException, ClassNotFoundException, RemoteException;

    public void guardarYSalir () throws IOException, ClassNotFoundException, RemoteException;

    public void finalizarPartida(int idPartida) throws IOException, ClassNotFoundException, RemoteException;
}
