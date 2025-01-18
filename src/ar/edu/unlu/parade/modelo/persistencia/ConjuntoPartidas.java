package ar.edu.unlu.parade.modelo.persistencia;

import ar.edu.unlu.parade.modelo.Partida;

import java.io.Serializable;
import java.util.ArrayList;

public class ConjuntoPartidas implements Serializable {
    private ArrayList<Partida> partidas;

    public ConjuntoPartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }
}
