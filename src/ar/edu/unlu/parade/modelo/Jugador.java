package ar.edu.unlu.parade.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Jugador implements Serializable {
    Mano manoJugador;
    AreaDeJuego areaJugador;
    private int puntos;
    private final Integer idJugador;
    private String nombre;
    private int posicion;
    private boolean esGanador;
    private boolean turnoJugador;
    private boolean elegido;

    public Jugador(String nombre) {
        this.elegido = false;
        this.idJugador = generarHashID(nombre);
        this.puntos = 0;
        this.manoJugador = new Mano();
        this.areaJugador = new AreaDeJuego();
        this.nombre = nombre;
        this.posicion = 0;
        this.esGanador = false;
        this.turnoJugador = false;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Mano getManoJugador() {
        return manoJugador;
    }

    public boolean esGanador() {
        return esGanador;
    }

    public void setEsGanador(boolean esGanador) {
        this.esGanador = esGanador;
    }

    public AreaDeJuego getAreaJugador() {
        return areaJugador;
    }

    public boolean isTurnoJugador() {
        return turnoJugador;
    }

    public void setTurnoJugador(boolean turnoJugador) {
        this.turnoJugador = turnoJugador;
    }

    public boolean isElegido() {
        return elegido;
    }
    public void setElegido(boolean elegido) {
        this.elegido = elegido;
    }

    //Funcion que retorna el nombre SI EXISTE y sino entonces el ID
    public String definicionJugador(String articuloID, String articuloNombre) {
        if (Objects.equals(this.nombre, "")) {
            return articuloID + "jugador n°" + idJugador;
        }
        else {
            return articuloNombre + nombre;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return idJugador.equals(jugador.idJugador) && nombre.equals(jugador.nombre);
    }

    private int generarHashID(String nombre){
        long timestamp = System.currentTimeMillis();
        String nombreLimpio = nombre.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");
        String input = nombreLimpio + "_" + timestamp;
        return input.hashCode();
    }
}
