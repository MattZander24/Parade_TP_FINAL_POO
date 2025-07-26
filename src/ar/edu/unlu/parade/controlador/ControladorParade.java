package ar.edu.unlu.parade.controlador;

import ar.edu.unlu.parade.enumerados.DestinoCarta;
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
    private Jugador jugadorLocal;

    public void setV(IVista v) {
        this.v = v;
    }

    public Jugador getJugadorLocal() {
        return jugadorLocal;
    }

    public ArrayList<Jugador> getJugadores() throws RemoteException {
        return m.getPartida().getJugadores();
    }

    public void setJugadorLocal(Jugador jugadorLocal) {
        this.jugadorLocal = jugadorLocal;
    }

    public Desfile getDesfile () throws RemoteException {
        return m.getPartida().getDesfileJuego();
    }

    public void aplicacionCerrada() throws RemoteException {
        try {
            if (m != null) {
                m.removerObservador(this);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void seleccionarCarta() {
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

    public void finalizarTurno () {
        try {
            m.finalizarTurno();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void finalizarUltimoTurno () {
        try {
            m.finalizarUltimoTurno();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void finalizarDescarte () {
        try {
            m.finalizarDescarte();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void mensajeGuardarYSalir () {
        try {
            m.mensajeGuardarYSalir();
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

    public void mostrarAreaDeJuego () {
        try {
            m.mostrarAreaDeJuego();
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

    public void mostrarMano () {
        try {
            m.mostrarMano();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void guardarYSalir () {
        try {
            m.guardarYSalir();
        }
        catch (IOException | ClassNotFoundException e) {
            //Nada porque ya lo controla la funcion interna
        }
    }

    public void menuTurno () {
        try {
            m.menuTurno();
        }
        catch (IOException e) {
            //Nada porque ya lo controla la funcion interna
        }
    }

    @Override
    public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {
        if (cambio instanceof Opcion) {
            Opcion opcion = (Opcion) cambio;
            switch (opcion) {
                case CREACION_ARCHIVO:
                    v.mensajeCreacionArchivo();
                    break;
                case GUARDAR_Y_SALIR:
                    v.mensajeGuardarYSalir();
                    break;
                case MENU_TURNO:
                    v.menuTurno();
                    break;
                case MENU_TURNO_FINAL:
                    v.menuTurnoFinal();
                    break;
                case DESCARTE_Y_FINAL:
                    v.mensajeDescarteFinal(jugadorLocal);
                    break;
                case MOSTRAR_AREA:
                    v.mostrarADJ(jugadorLocal);
                    break;
                case MOSTRAR_AREA_TODOS:
                    v.mostrarADJTodos();
                    break;
                case MOSTRAR_MANO:
                    v.mostrarM(jugadorLocal);
                    break;
                case MOSTRAR_DESFILE:
                    v.mostrarD(m.getPartida().getDesfileJuego());
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
                case HABILITAR_SALIR:
                    v.habilitarSalir();
                    break;
                case SELECICON_EVALUAR:
                    v.seleccionCarta(jugadorLocal, DestinoCarta.EVALUAR);
                    break;
                case SELECICON_DESCARTAR:
                    v.seleccionCarta(jugadorLocal, DestinoCarta.DESCARTAR);
                    break;
                case ACTUALIZAR_JUGADOR:
                    actualizarJugador();
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

    public void agregarJugador(Jugador jugador) throws RemoteException {
        setJugadorLocal(jugador);
        m.agregarJugador(jugador);
        v.bienvenidaYEspera(jugadorLocal);
    }

    public void checkInicioPartida() throws RemoteException {
        m.iniciarPartida();
    }

    public void actualizarJugador () throws RemoteException {
        for (Jugador j : m.getPartida().getJugadores()) {
            if (jugadorLocal.equals(j)){
                jugadorLocal.setPuntos(j.getPuntos());
                jugadorLocal.setPosicion(j.getPosicion());
                jugadorLocal.setEsGanador(j.esGanador());
                jugadorLocal.setTurnoJugador(j.isTurnoJugador());
                jugadorLocal.getManoJugador().actualizarMano(j.getManoJugador());
                jugadorLocal.getAreaJugador().actualizarArea(j.getAreaJugador());
            }
        }
    }
}
