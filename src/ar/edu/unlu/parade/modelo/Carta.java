package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.enumerados.Color;

import java.io.Serializable;

public class Carta implements Serializable {
    private int valor; //VALORES POSIBLES SOLO DE 1 a 10
    private Color color;
    private boolean anulada;

    public Carta(int valor, Color color) {
        this.valor = valor;
        this.color = color;
        this.anulada = false; //para la puntuacion al final del juego. una carta anulada vale 1 punto
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void anularCarta () {
        this.anulada = true;
    }

    public boolean isAnulada() {
        return anulada;
    }

    public String nombreImagen () {
        return String.valueOf(this.valor) + String.valueOf(this.color);
    }
}
