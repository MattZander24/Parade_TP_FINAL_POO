package ar.edu.unlu.parade.modelo;

import java.io.Serializable;

public class Mano extends ListaCartas implements Serializable {

    public Mano() {
        super();
    }

    public int cantidadMano () {
        return this.getCartas().size();
    }

    public void actualizarMano(Mano nuevaMano) {
        this.getCartas().clear();
        this.getCartas().addAll(nuevaMano.getCartas());
    }
}
