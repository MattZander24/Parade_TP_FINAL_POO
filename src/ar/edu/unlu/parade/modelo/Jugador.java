package ar.edu.unlu.parade.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Jugador implements Serializable {
    Mano manoJugador;
    AreaDeJuego areaJugador;
    private int puntos;
    private static int idJugadorGen = 1;
    private int idJugador;
    private String nombre;
    private int posicion;
    private boolean esGanador;
    private boolean turnoJugador;

    public Jugador() {
        this.idJugador = idJugadorGen;
        idJugadorGen++;
        this.puntos = 0;
        this.manoJugador = new Mano();
        this.areaJugador = new AreaDeJuego();
        this.nombre = "";
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

    //funcion que retorne SI EXISTE el nombre y si no existe el ID
    public String definicionJugador(String articuloID, String articuloNombre) {
        if (Objects.equals(this.nombre, "")) {
            return articuloID + "jugador n°" + idJugador;
        }
        else {
            return articuloNombre + nombre;
        }
    }

    public static void resetearIDGen () {
        idJugadorGen = 1;
    }
}
