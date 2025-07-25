package ar.edu.unlu.parade.interfaces;

import ar.edu.unlu.parade.modelo.Carta;

public interface ConjuntoCartas {
    void transferirCartas(ConjuntoCartas c, Object o);
    void enviarCarta(Carta carta);
}