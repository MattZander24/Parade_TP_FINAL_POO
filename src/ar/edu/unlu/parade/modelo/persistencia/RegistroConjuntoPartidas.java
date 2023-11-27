package ar.edu.unlu.parade.modelo.persistencia;

import java.io.Serializable;
import java.util.ArrayList;

public class RegistroConjuntoPartidas implements Serializable {
    private ArrayList<RegistroPartida> partidas;

    public RegistroConjuntoPartidas(ArrayList<RegistroPartida> partidas) {
        this.partidas = partidas;
    }

    public ArrayList<RegistroPartida> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<RegistroPartida> partidas) {
        this.partidas = partidas;
    }
}
