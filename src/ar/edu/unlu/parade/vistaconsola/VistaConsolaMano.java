package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.interfaces.Mostrable;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.Jugador;
import ar.edu.unlu.parade.modelo.Mano;

public class VistaConsolaMano implements Mostrable {
    @Override
    public String mostrar(Object o) {
        Jugador j = (Jugador) o;
        Mano m = j.getManoJugador();
        StringBuilder resultado = new StringBuilder();

        resultado.append("\n");
        resultado.append("Mano de").append(j.definicionJugador("l ", " ")).append(":\n");

        int i = 1;
        for (Carta c : m.getCartas()) {
            resultado.append("\t").append(i).append("- ")
                    .append(c.getValor()).append(" ")
                    .append(c.getColor()).append("\n");
            i++;
        }

        return resultado.toString();
    }
}
