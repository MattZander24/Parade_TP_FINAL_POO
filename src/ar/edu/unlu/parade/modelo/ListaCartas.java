package ar.edu.unlu.parade.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaCartas implements ConjuntoCartas, Serializable {
    ArrayList<Carta> cartas;
    public ListaCartas() {
        this.cartas = new ArrayList<Carta>();
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    public void transferirCartas (ConjuntoCartas c, Object o) {
        Carta carta = (Carta) o;
        if (cartas.contains(carta)) {
            cartas.remove(carta);
            c.enviarCarta(carta);
        }
    }

    public void enviarCarta(Carta carta) {
        cartas.add(carta);
    }

}
