package ar.edu.unlu.parade.controlador;

import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.enumerados.EstadoPartida;
import ar.edu.unlu.parade.interfaces.IModelo;
import ar.edu.unlu.parade.modelo.*;
import ar.edu.unlu.parade.enumerados.Opcion;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorParade implements IControladorRemoto {

    private IModelo m;
    private IVista v;
    private int idControlador;
    private Jugador jugadorLocal;
    private EstadoPartida estadoActual;

    public ControladorParade(/*ModeloParade m*/) {
        //this.m = m;
    }

    public IVista getV() {
        return v;
    }

    public void setV(IVista v) {
        this.v = v;
    }

    public Jugador getJugadorLocal() {
        return jugadorLocal;
    }

    public void setJugadorLocal(Jugador jugadorLocal) {
        this.jugadorLocal = jugadorLocal;
    }

    private void cambiarEstadoYActualizarVista(EstadoPartida nuevoEstado) throws RemoteException {
        estadoActual = nuevoEstado;
        v.actualizarVistaParaAccion(nuevoEstado);
    }

    public void iniciarJuego () {
        try {
            m.iniciarJuego();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void aplicacionCerrada() throws RemoteException {
        try {
            if (m != null) {
                m.removerObservador(this);
                //m.removerJugador(jugadorLocal);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void verReglas () {
        try {
            m.verReglas();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void verHistorico () {
        try {
            m.verHistorico();
        }
        catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
        /*catch (RemoteException e) {
            e.printStackTrace();
        }*/
    }

    public void terminarPartida () {
        try {
            m.terminarPartida();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void iniciarPartida (/*int cantidadJugadores, boolean agregarNombre*/) {
        try {
            m.iniciarPartida(/*cantidadJugadores, agregarNombre*/);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void seleccionarCarta(Jugador j) {
        try {
            m.evaluarCarta();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void devolverCarta(Jugador j, int opcionCarta, DestinoCarta d) {
        try {
            m.devolverCarta(j, opcionCarta, d);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setNombre (Jugador j, String nombre) {
        try {
            m.setNombre(j, nombre);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void mensajeCreacionArchivo () {
        try {
            m.mensajeCreacionArchivo();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void mostrarDesfile () {
        try {
            m.mostrarDesfile();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void mostrarAreaDeJuego (Jugador j) {
        try {
            m.mostrarAreaDeJuego(j);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void mostrarJugadores () {
        try {
            m.mostrarJugadores();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void mostrarMano (Jugador j) {
        try {
            m.mostrarMano(j);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void cargarPartida () {
        try {
            m.cargarPartida();
        }
        catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
        /*catch (RemoteException e) {
            e.printStackTrace();
        }*/
    }

    public void reiniciarPartida (Partida p) {
        try {
            m.reiniciarPartida(p);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void top5Historico () {
        try {
            m.top5Historico();
        }
        catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
        /*catch (RemoteException e) {
            e.printStackTrace();
        }*/
    }

    public void guardarYSalir () {
        try {
            m.guardarYSalir();
        }
        catch (IOException | ClassNotFoundException e) {
            //nada porque ya lo controla la funcion interna
        }
        /*catch (RemoteException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {
        if (cambio instanceof Opcion) {
            Opcion opcion = (Opcion) cambio;
            switch (opcion) {
                case MENU_PRINCIPAL:
                    v.menuPrincipal();
                    break;
                case REGLAS:
                    v.verReglas();
                    break;
                case CREACION_ARCHIVO:
                    v.mensajeCreacionArchivo();
                    break;
                case SETEO_PARTIDA:
                    v.configuracionPartida();
                    break;
                case ULTIMO_TURNO:
                    v.ultimoTurno();
                    break;
                case GUARDAR_Y_SALIR:
                    v.mensajeGuardarYSalir();
                    break;
                case MENU_TURNO:
                    v.menuTurno(jugadorLocal);
                    break;
                case MENU_TURNO_FINAL:
                    v.menuTurnoFinal(jugadorLocal);
                    break;
                case DESCARTE_Y_FINAL:
                    v.mensajeDescarteFinal(jugadorLocal);
                    break;
                case MOSTRAR_AREA:
                    v.mostrarADJ(jugadorLocal);
                    break;
                case MOSTRAR_MANO:
                    v.mostrarM(jugadorLocal);
                    break;
                case MOSTRAR_DESFILE:
                    v.mostrarD(m.getPartida().getDesfileJuego());
                    break;
                case ADD_NOMBRE:
                    v.agregarNombre(jugadorLocal);
                    break;
                case GANADOR_PARTIDA:
                    v.mensajeGanador(m.getPartida().getGanadores().get(0));
                    break;
                case EMPATE_JUGADORES:
                    v.mensajeEmpateEntreJugadores(m.getPartida().getGanadores());
                    break;
                case RANKING:
                    v.mensajeRanking(m.getPartida().getJugadores());
                    break;
                case HISTORICO:
                    try {
                        v.verHistorico(/*partidas*/);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case TOP5:
                    try {
                        v.verTop5(/*(RegistroConjuntoJugadores) o*/);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case CARGAR_PARTIDA:
                    try {
                        v.cargarPartida(/*, (ConjuntoPartidas) o*/);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case SELECICON_EVALUAR:
                    v.seleccionCarta(jugadorLocal, DestinoCarta.EVALUAR);
                    break;
                case SELECICON_DESCARTAR:
                    v.seleccionCarta(jugadorLocal, DestinoCarta.DESCARTAR);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
        this.m = (IModelo) modeloRemoto;
    }


    public void agregarJugador(String nombre) throws RemoteException {
        jugadorLocal = m.agregarJugador(nombre);
        v.bienvenidaYEspera(jugadorLocal);
    }

    public void checkInicioPartida() throws RemoteException {
        if (m.getPartida().getJugadores().size() == m.getPartida().getCantidadJugadores()) {
            m.iniciarPartida();
        }
    }

    /*public void linkJugadorLocal () throws RemoteException {
        ArrayList <Jugador> jugadores = m.getPartida().getJugadores();
        for (Jugador j : jugadores) {
            if (j.getIdJugador() == idControlador) {
                jugadorLocal = j;
            }
        }
    }*/
}
