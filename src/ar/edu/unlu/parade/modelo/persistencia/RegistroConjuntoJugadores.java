package ar.edu.unlu.parade.modelo.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RegistroConjuntoJugadores implements Serializable {

    private ArrayList <RegistroJugadores> jugadores;

    public RegistroConjuntoJugadores(ArrayList<RegistroJugadores> jugadores) {
        this.jugadores = jugadores;
    }

    public ArrayList<RegistroJugadores> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<RegistroJugadores> jugadores) {
        this.jugadores = jugadores;
    }

    // Función que ordena el ArrayList de jugadores
    public void ordenarJugadores() {
        Collections.sort(jugadores, new Comparator<RegistroJugadores>() {
            @Override
            public int compare(RegistroJugadores j1, RegistroJugadores j2) {
                // Primero comparamos los puntos (puntos más bajos primero)
                int puntosComparison = Integer.compare(j1.getPuntosJugador(), j2.getPuntosJugador());

                // Si los puntos son iguales, comparamos por la fecha (fecha más antigua primero)
                if (puntosComparison == 0) {
                    return j1.getFechaYHoraPartida().compareTo(j2.getFechaYHoraPartida());
                }

                return puntosComparison;
            }
        });
    }
}
