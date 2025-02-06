package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.modelo.Desfile;
import ar.edu.unlu.parade.modelo.DestinoCarta;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.Partida;
import ar.edu.unlu.parade.modelo.persistencia.*;
import ar.edu.unlu.parade.recursos.Opcion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public interface IVista {
    /*public void actualizar (Opcion opcion);*/

    public ControladorParade getC();

    public void setC(ControladorParade c);

    public void menuPrincipal ();

    public void verReglas ();

    public void mensajeCreacionArchivo ();

    public void mensajeGuardarYSalir ();

    public void verHistorico (/*RegistroConjuntoPartidas r*/) throws IOException;

    public void verTop5 (/*RegistroConjuntoJugadores j*/) throws IOException;

    public void configuracionPartida ();

    public void cargarPartida (/*, ConjuntoPartidas cp*/) throws IOException;

    public void menuTurno (Jugador j);

    public void menuTurnoFinal (Jugador j);

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
}
