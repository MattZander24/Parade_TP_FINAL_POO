package ar.edu.unlu.parade.modelo;

public class Mano extends ListaCartas {

    public Mano() {
        super();
    }

    public int cantidadMano () {
        return this.cartas.size();
    }
}
