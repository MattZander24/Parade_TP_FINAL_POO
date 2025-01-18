package ar.edu.unlu.parade.vistaconsola;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.modelo.*;
import ar.edu.unlu.parade.modelo.persistencia.ConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.recursos.Observer;
import ar.edu.unlu.parade.recursos.Opcion;

import java.util.ArrayList;

public class VistaConsolaParade implements Observer {

    private ControladorParade c;
    private VistaConsolaAreaDeJuego vca;
    private VistaConsolaPartida vcp;
    private VistaConsolaDesfile vcd;
    private VistaConsolaMano vcm;

    public VistaConsolaParade(ControladorParade c) {
        this.c = c;
        this.vca = new VistaConsolaAreaDeJuego();
        this.vcd = new VistaConsolaDesfile();
        this.vcm = new VistaConsolaMano();
        this.vcp = new VistaConsolaPartida();
    }

    @Override
    public void notificar(Opcion opcion) {
        switch (opcion) {
            case MENU_PRINCIPAL:
                vcp.menuPrincipal(c);
                break;
            case REGLAS:
                vcp.verReglas();
                break;
            case CREACION_ARCHIVO:
                vcp.mensajeCreacionArchivo();
                break;
            case SETEO_PARTIDA:
                vcp.configuracionPartida(c);
                break;
            case ULTIMO_TURNO:
                vcp.ultimoTurno();
            case GUARDAR_Y_SALIR:
                vcp.mensajeGuardarYSalir();
            default:
                break;
        }

    }

    @Override
    public void notificar(Opcion opcion, Object o) {
        switch (opcion) {
            case MENU_TURNO:
                vcp.menuTurno(c, (Jugador) o);
                break;
            case DESCARTE_Y_FINAL:
                vcp.mensajeDescarteFinal((Jugador) o);
                break;
            case MOSTRAR_AREA:
                vca.mostrar((Jugador) o);
                break;
            case MOSTRAR_MANO:
                vcm.mostrar((Jugador) o);
                break;
            case MOSTRAR_DESFILE:
                vcd.mostrar((Desfile) o);
                break;
            case ADD_NOMBRE:
                vcp.agregarNombre(c, (Jugador) o);
                break;
            case GANADOR_PARTIDA:
                vcp.mensajeGanador((Jugador) o);
                break;
            case EMPATE_JUGADORES:
                vcp.mensajeEmpateEntreJugadores((ArrayList<Jugador>) o);
                break;
            case RANKING:
                vcp.mensajeRanking((ArrayList<Jugador>) o);
                break;
            case HISTORICO:
                vcp.verHistorico((RegistroConjuntoPartidas) o);
                break;
            case TOP5:
                vcp.verTop5((RegistroConjuntoJugadores) o);
                break;
            case CARGAR_PARTIDA:
                vcp.cargarPartida(c, (ConjuntoPartidas) o);
                break;
            default:
                break;
        }
    }

    @Override
    public void notificar(Opcion opcion, Object o, Object o2) {
        switch (opcion) {
            case SELECICON_CARTA:
                vcp.seleccionCarta(c, (Jugador) o, (DestinoCarta) o2);
                break;
        }
    }
}
