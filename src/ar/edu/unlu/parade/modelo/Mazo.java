package ar.edu.unlu.parade.modelo;

import java.util.Collections;
import java.util.EnumSet;

public class Mazo extends PilaCartas {

    private boolean generado;

    public Mazo() {
        super();
        this.generado = false;
    }

    public void generarMazo () {
        for (int v = 0; v <= 10; v++) {
            for (Color c : EnumSet.allOf(Color.class)) {
                Carta carta = new Carta(v, c);
                cartas.push(carta);
            }
        }
        generado = true;
    }

    public void mezclarMazo () {
        if (generado) {
            Collections.shuffle(cartas);
        }
    }

    public boolean estaTerminado () {
        return cartas.empty();
    }

}
