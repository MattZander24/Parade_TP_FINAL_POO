package ar.edu.unlu.parade.interfaces;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.enumerados.EstadoPartida;
import ar.edu.unlu.parade.modelo.Desfile;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.modelo.Jugador;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IVista {
    /*public void actualizar (Opcion opcion);*/

    public ControladorParade getC();

    public void setC(ControladorParade c);

    public void actualizarVistaParaAccion(EstadoPartida estadoActual) throws RemoteException;

    public void iniciarVista();

    public void menuPrincipal ();

    public void verReglas ();

    public void mensajeCreacionArchivo ();

    public void mensajeGuardarYSalir ();

    public void verHistorico (/*RegistroConjuntoPartidas r*/) throws IOException;

    public void verTop5 (/*RegistroConjuntoJugadores j*/) throws IOException;

    public void configuracionPartida ();

    public void cargarPartida (/*, ConjuntoPartidas cp*/) throws IOException;

    public void menuTurno ();

    public void menuTurnoFinal ();

    public void agregarNombre (Jugador jugador);

    public void seleccionCarta (Jugador j, DestinoCarta d);

    public void ultimoTurno ();

    public void mensajeDescarteFinal (Jugador j);

    public void mensajeGanador(Jugador j);

    public void mensajeEmpateEntreJugadores (ArrayList<Jugador> ganadores);

    public void mensajeRanking(ArrayList<Jugador> jugadoresPartida);

    public void mostrarADJ (Jugador j);

    public void mostrarD (Desfile d);

    public void mostrarM (Jugador j);

    public void bienvenidaYEspera(Jugador jugadorLocal);
}
