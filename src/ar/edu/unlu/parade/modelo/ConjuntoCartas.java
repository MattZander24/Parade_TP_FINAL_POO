package ar.edu.unlu.parade.modelo;

public interface ConjuntoCartas {

    public void transferirCartas (ConjuntoCartas c, Object o);

    public void enviarCarta(Carta carta);

}