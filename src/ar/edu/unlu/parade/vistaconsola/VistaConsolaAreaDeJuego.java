package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.interfaces.Mostrable;
import ar.edu.unlu.parade.modelo.AreaDeJuego;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.Jugador;

public class VistaConsolaAreaDeJuego implements Mostrable {

    @Override
    public void mostrar(Object o) {
        Jugador j = (Jugador) o;
        AreaDeJuego a = j.getAreaJugador();
        System.out.print("\n");
        System.out.println("Area de juego de" + j.definicionJugador("l ", " ") + ":");
        a.ordenar();
        int i = 1;
        for (Carta c : a.getCartas()) {
            System.out.println("\t" + i + "- " + c.getValor() + " " + c.getColor());
            i++;
        }
    }
}
