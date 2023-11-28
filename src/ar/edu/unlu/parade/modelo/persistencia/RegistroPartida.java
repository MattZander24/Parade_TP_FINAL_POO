package ar.edu.unlu.parade.modelo.persistencia;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RegistroPartida implements Serializable {

    private ArrayList <RegistroJugadores> rankingJugador;
    private LocalDateTime fechaYHoraPartida;

    public RegistroPartida(ArrayList<RegistroJugadores> rankingJugador) {
        this.rankingJugador = rankingJugador;
        this.fechaYHoraPartida = LocalDateTime.now();
    }

    public ArrayList<RegistroJugadores> getRankingJugador() {
        return rankingJugador;
    }

    public void setRankingJugador(ArrayList<RegistroJugadores> rankingJugador) {
        this.rankingJugador = rankingJugador;
    }

    public LocalDateTime getFechaYHoraPartida() {
        return fechaYHoraPartida;
    }

    public void setFechaYHoraPartida(LocalDateTime fechaYHoraPartida) {
        this.fechaYHoraPartida = fechaYHoraPartida;
    }
}
