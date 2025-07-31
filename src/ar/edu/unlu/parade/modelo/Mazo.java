package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.enumerados.Color;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumSet;

public class Mazo extends PilaCartas implements Serializable {
    private boolean generado;

    public Mazo() {
        super();
        this.generado = false;
    }

    public void generarMazo () {
        for (int v = 0; v <= 10; v++) {
            for (Color c : EnumSet.allOf(Color.class)) {
                Carta carta = new Carta(v, c);
                this.getCartas().push(carta);
            }
        }
        generado = true;
    }

    public void mezclarMazo () {
        if (generado) {
            Collections.shuffle(this.getCartas());
        }
    }

    public boolean estaTerminado () {
        return this.getCartas().empty();
    }

}
