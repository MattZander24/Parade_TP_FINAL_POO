package ar.edu.unlu.parade.modelo.persistencia;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegistroJugadores implements Serializable {
    private String definicionJugador;
    private int puntosJugador;
    private int posicionJugador;
    private LocalDateTime fechaYHoraPartida;

    public RegistroJugadores(String definicionJugador, int puntosJugador, int posicionJugador) {
        this.definicionJugador = definicionJugador;
        this.puntosJugador = puntosJugador;
        this.posicionJugador = posicionJugador;
        this.fechaYHoraPartida = LocalDateTime.now();
    }

    public String getDefinicionJugador() {
        return definicionJugador;
    }

    public int getPuntosJugador() {
        return puntosJugador;
    }

    public int getPosicionJugador() {
        return posicionJugador;
    }

    public LocalDateTime getFechaYHoraPartida() {
        return fechaYHoraPartida;
    }
}
