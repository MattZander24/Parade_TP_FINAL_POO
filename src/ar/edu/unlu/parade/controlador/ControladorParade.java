package ar.edu.unlu.parade.controlador;

import ar.edu.unlu.parade.modelo.DestinoCarta;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.ModeloParade;
import ar.edu.unlu.parade.modelo.Partida;

import java.io.IOException;

public class ControladorParade {

    ModeloParade m;

    public ControladorParade(ModeloParade m) {
        this.m = m;
    }

    public void iniciarJuego () {
        m.iniciarJuego();
    }

    public void verReglas () {
        m.verReglas();
    }

    public void verHistorico () {
        try {
            m.verHistorico();
        }
        catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
    }

    public void terminarPartida () {
        m.terminarPartida();
    }

    public void iniciarPartida (int cantidadJugadores, boolean agregarNombre) {
        m.iniciarPartida(cantidadJugadores, agregarNombre);
    }

    public void seleccionarCarta(Jugador j) {
        m.seleccionarCarta(j, DestinoCarta.EVALUAR);
    }

    public void devolverCarta(Jugador j, int opcionCarta, DestinoCarta d) {
        m.devolverCarta(j, opcionCarta, d);
    }

    public void setNombre (Jugador j, String nombre) {
        m.setNombre(j, nombre);
    }

    public void mostrarDesfile () {
        m.mostrarDesfile();
    }

    public void mostrarAreaDeJuego (Jugador j) {
        m.mostrarAreaDeJuego(j);
    }

    public void mostrarJugadores () {
        m.mostrarJugadores();
    }

    public void mostrarMano (Jugador j) {
        m.mostrarMano(j);
    }

    public void cargarPartida () {
        try {
            m.cargarPartida();
        }
        catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
    }

    public void reiniciarPartida (Partida p) {
        m.reiniciarPartida();
    }

    public void top5Historico () {
        try {
            m.top5Historico();
        }
        catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
    }

    public void guardarYSalir () {
        try {
            m.guardarYSalir();
        }
        catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
    }

}
