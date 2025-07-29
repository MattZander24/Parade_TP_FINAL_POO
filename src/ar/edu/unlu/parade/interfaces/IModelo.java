package ar.edu.unlu.parade.interfaces;

import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.Partida;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.io.*;
import java.rmi.RemoteException;

public interface IModelo extends IObservableRemoto {
    Partida getPartida() throws RemoteException;
    void setPartida(Partida partida) throws RemoteException;
    void iniciarAplicacion() throws RemoteException;
    void mensajeCreacionArchivo () throws RemoteException;
    void mensajeGuardarYSalir () throws RemoteException;
    void generarPartida () throws RemoteException;
    //void terminarPartida () throws RemoteException;
    void iniciarPartida () throws RemoteException;
    void reiniciarPartida() throws RemoteException;
    void elegirJugador(int idJugador) throws RemoteException;
    boolean esNuevaPartida() throws RemoteException;
    void menuTurno () throws RemoteException;
    void menuTurnoFinal () throws RemoteException;
    void evaluarCarta() throws RemoteException;
    void descartarCarta() throws RemoteException;
    void devolverCarta(Jugador j, int opcionCarta, DestinoCarta d) throws RemoteException;
    void finalizarTurno() throws IOException, ClassNotFoundException;
    void finalizarUltimoTurno() throws IOException, ClassNotFoundException;
    void finalizarDescarte() throws IOException, ClassNotFoundException;
    void mensajeDescarteFinal () throws RemoteException;
    void mensajeGanador () throws RemoteException;
    void mensajeEmpate () throws RemoteException;
    void mensajeGanadorYRanking () throws RemoteException;
    void habilitarSalir () throws RemoteException;
    void mostrarDesfile () throws RemoteException;
    void mostrarAreaDeJuego () throws RemoteException;
    void mostrarJugadores () throws RemoteException;
    void mostrarMano () throws RemoteException;
    void guardarYSalir () throws IOException, ClassNotFoundException, RemoteException;
    void finalizarPartida(int idPartida) throws IOException, ClassNotFoundException, RemoteException;
    void agregarJugador(Jugador jugador) throws RemoteException;
    void setJugadoresPartida(int cantidad) throws RemoteException;
    void actualizarJugador() throws RemoteException;
}
