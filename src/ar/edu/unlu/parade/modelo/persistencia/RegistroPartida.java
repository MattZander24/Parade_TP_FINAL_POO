package ar.edu.unlu.parade.modelo.persistencia;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RegistroPartida implements Serializable {

    private ArrayList <RegistroJugadores> RankingJugador;
    private LocalDateTime fechaYHoraPartida;

    public RegistroPartida(ArrayList<RegistroJugadores> rankingJugador) {
        RankingJugador = rankingJugador;
        this.fechaYHoraPartida = LocalDateTime.now();
    }

    public ArrayList<RegistroJugadores> getRankingJugador() {
        return RankingJugador;
    }

    public void setRankingJugador(ArrayList<RegistroJugadores> rankingJugador) {
        RankingJugador = rankingJugador;
    }

    public LocalDateTime getFechaYHoraPartida() {
        return fechaYHoraPartida;
    }

    public void setFechaYHoraPartida(LocalDateTime fechaYHoraPartida) {
        this.fechaYHoraPartida = fechaYHoraPartida;
    }
}
