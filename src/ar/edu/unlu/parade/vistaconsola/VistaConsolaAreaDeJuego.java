package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.interfaces.Mostrable;
import ar.edu.unlu.parade.modelo.AreaDeJuego;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.Jugador;

public class VistaConsolaAreaDeJuego implements Mostrable {

    @Override
    public String mostrar(Object o) {
        Jugador j = (Jugador) o;
        AreaDeJuego a = j.getAreaJugador();
        StringBuilder resultado = new StringBuilder();

        resultado.append("\n");
        resultado.append("Area de juego de").append(j.definicionJugador("l ", " ")).append(":\n");

        a.ordenar();
        int i = 1;
        for (Carta c : a.getCartas()) {
            resultado.append("\t").append(i).append("- ")
                    .append(c.getValor()).append(" ")
                    .append(c.getColor()).append("\n");
            i++;
        }

        return resultado.toString();
    }
}
