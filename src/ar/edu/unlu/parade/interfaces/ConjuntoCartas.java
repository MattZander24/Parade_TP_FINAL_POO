package ar.edu.unlu.parade.interfaces;

import ar.edu.unlu.parade.modelo.Carta;

public interface ConjuntoCartas {

    public void transferirCartas (ConjuntoCartas c, Object o);

    public void enviarCarta(Carta carta);

}