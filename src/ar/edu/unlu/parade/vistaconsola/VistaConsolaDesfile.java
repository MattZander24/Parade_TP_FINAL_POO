package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.interfaces.Mostrable;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.Desfile;

public class VistaConsolaDesfile implements Mostrable {

    @Override
    public String mostrar(Object o) {
        int i = 1;
        Desfile d = (Desfile) o;
        StringBuilder resultado = new StringBuilder();

        resultado.append("\n");
        resultado.append("Desfile Actualmente:\n");
        resultado.append("/ ");

        for (Carta c : d.getCartas()) {
            resultado.append(c.getValor()).append(" ").append(c.getColor()).append(" / ");
            i++;
        }

        resultado.append("\n");
        return resultado.toString();
    }
}
