package ar.edu.unlu.parade.interfaces;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.modelo.Desfile;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.modelo.Jugador;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IVista {
    ControladorParade getC();
    void setC(ControladorParade c);
    void iniciarVista();
    void menuPrincipal ();
    void verReglas ();
    void mensajeCreacionArchivo ();
    void mensajeGuardarYSalir ();
    void verHistorico () throws IOException;
    void verTop5 () throws IOException;
    void configuracionPartida ();
    void cargarPartida () throws IOException;
    void menuTurno ();
    void menuTurnoFinal ();
    void agregarNombre (Jugador jugador);
    void seleccionCarta (Jugador j, DestinoCarta d);
    void mensajeDescarteFinal (Jugador j);
    void mensajeGuardar (boolean msgOpcionIncorrecta);
    void mensajeGanador(Jugador j);
    void mensajeEmpateEntreJugadores (ArrayList<Jugador> ganadores);
    void mensajeRanking(ArrayList<Jugador> jugadoresPartida);
    void habilitarSalir();
    void mostrarADJ (Jugador j);
    void mostrarADJTodos () throws RemoteException;
    void mostrarD (Desfile d);
    void mostrarM (Jugador j);
    void bienvenidaYEspera(Jugador jugadorLocal);
}
