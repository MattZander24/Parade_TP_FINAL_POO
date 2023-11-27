package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.Mano;

public class VistaConsolaMano implements Mostrable {
    @Override
    public void mostrar (Object o) {
        Jugador j = (Jugador) o;
        Mano m = j.getManoJugador();
        System.out.print("\n");
        System.out.println("Mano de" + j.definicionJugador("l ", " ") + ":");
        int i = 1;
        for (Carta c : m.getCartas()) {
            System.out.println("\t" + i + "- " + c.getValor() + " " + c.getColor());
            i++;
        }
    }
}
