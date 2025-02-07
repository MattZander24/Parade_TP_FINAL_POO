package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.interfaces.Mostrable;
import ar.edu.unlu.parade.modelo.Carta;
import ar.edu.unlu.parade.modelo.Desfile;

public class VistaConsolaDesfile implements Mostrable {

    @Override
    public void mostrar(Object o) {
        int i = 1;
        Desfile d = (Desfile) o;
        System.out.print("\n");
        System.out.println("Desfile Actualmente:");
        System.out.print("/ ");
        for (Carta c : d.getCartas()) {
            System.out.print(c.getValor() + " " + c.getColor() + " / ");
            i++;
        }
        System.out.print("\n");
    }
}
