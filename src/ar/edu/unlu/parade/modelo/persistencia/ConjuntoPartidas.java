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

    public void agregarOActualizarPartida(Partida partida) {
        for (int i = 0; i < partidas.size(); i++) {
            if (partidas.get(i).getIdPartida() == partida.getIdPartida()) {
                partidas.set(i, partida);
                return;
            }
        }
        partidas.add(partida);
    }

    public void eliminarPartidasTerminadas(int idPartida) {
        partidas.removeIf(partida -> partida.getIdPartida() == idPartida);
    }
}
