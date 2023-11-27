package ar.edu.unlu.parade.modelo;

import java.util.Objects;

public class Jugador {
    Mano manoJugador;
    AreaDeJuego areaJugador;
    private int puntos;
    private static int idJugadorGen = 1;
    private int idJugador;
    private String nombre;
    private int posicion;

    public Jugador() {
        this.idJugador = idJugadorGen;
        idJugadorGen++;
        this.puntos = 0;
        this.manoJugador = new Mano();
        this.areaJugador = new AreaDeJuego();
        this.nombre = "";
        this.posicion = 0;
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

    public AreaDeJuego getAreaJugador() {
        return areaJugador;
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
