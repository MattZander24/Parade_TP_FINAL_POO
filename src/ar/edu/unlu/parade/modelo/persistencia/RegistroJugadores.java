package ar.edu.unlu.parade.modelo.persistencia;

import java.io.Serializable;

public class RegistroJugadores implements Serializable {
    private String definicionJugador;
    private int puntosJugador;
    private int posicionJugador;

    public RegistroJugadores(String definicionJugador, int puntosJugador, int posicionJugador) {
        this.definicionJugador = definicionJugador;
        this.puntosJugador = puntosJugador;
        this.posicionJugador = posicionJugador;
    }

    public String getDefinicionJugador() {
        return definicionJugador;
    }

    public void setDefinicionJugador(String definicionJugador) {
        this.definicionJugador = definicionJugador;
    }

    public int getPuntosJugador() {
        return puntosJugador;
    }

    public void setPuntosJugador(int puntosJugador) {
        this.puntosJugador = puntosJugador;
    }

    public int getPosicionJugador() {
        return posicionJugador;
    }

    public void setPosicionJugador(int posicionJugador) {
        this.posicionJugador = posicionJugador;
    }
}
