package ar.edu.unlu.parade.modelo;

import java.util.*;

public class AreaDeJuego extends ListaCartas {

    public AreaDeJuego() {
        super();
    }

    public boolean tieneSeisColores () {
        Set<Color> coloresDeseados = EnumSet.of(
                Color.VERDE, Color.AMARILLO, Color.ROJO, Color.VIOLETA, Color.AZUL, Color.NEGRO
        );
        Set<Color> coloresEncontrados = new HashSet<>();

        for (Carta carta : cartas) {
            coloresEncontrados.add(carta.getColor());
        }

        return coloresEncontrados.containsAll(coloresDeseados);
    }

    public void ordenar () {
        List<Color> ordenColores = List.of(
                Color.VERDE, Color.AMARILLO, Color.ROJO, Color.VIOLETA, Color.AZUL, Color.NEGRO
        );

        EnumMap<Color, Integer> colorOrdenMap = new EnumMap<>(Color.class);
        for (int i = 0; i < ordenColores.size(); i++) {
            colorOrdenMap.put(ordenColores.get(i), i);
        }

        cartas.sort(new Comparator<Carta>() {
            @Override
            public int compare(Carta carta1, Carta carta2) {
                int colorComparison = colorOrdenMap.get(carta1.getColor()).compareTo(colorOrdenMap.get(carta2.getColor()));
                if (colorComparison != 0) {
                    return colorComparison;
                }

                return Integer.compare(carta1.getValor(), carta2.getValor());
            }
        });
    }

    public int sumarArea () {
        int puntaje = 0;
        for (Carta c : cartas) {
             if (c.isAnulada()) {
                 puntaje++;
             }
             else {
                 puntaje += c.getValor();
             }
        }
        return puntaje;
    }
}
