package ar.edu.unlu.parade.modelo;

import java.util.Stack;

public class PilaCartas implements ConjuntoCartas {
    Stack<Carta> cartas;
    public PilaCartas() {
        this.cartas = new Stack<>();
    }

    public Stack<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(Stack<Carta> cartas) {
        this.cartas = cartas;
    }

    public void transferirCartas (ConjuntoCartas c, Object o) {
        Integer cantidad = (Integer) o;
        Carta carta;
        for (int i = 0; i < cantidad; i++) {
            carta = cartas.pop();
            c.enviarCarta(carta);
        }
    }

    public void enviarCarta(Carta carta) {
        this.cartas.push(carta);
    }

}
