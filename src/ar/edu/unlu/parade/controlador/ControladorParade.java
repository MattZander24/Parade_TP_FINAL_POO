package ar.edu.unlu.parade.controlador;

import ar.edu.unlu.parade.modelo.*;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroPartida;
import ar.edu.unlu.parade.recursos.Opcion;
import ar.edu.unlu.parade.vistaconsola.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorParade implements IControladorRemoto {

    private IModelo m;

    private IVista v;
    private Jugador jugadorLocal;

    public ControladorParade(/*ModeloParade m*/) {
        //this.m = m;
    }

    public IVista getV() {
        return v;
    }

    public void setV(IVista v) {
        this.v = v;
    }

    public void iniciarJuego () {
        try {
            m.iniciarJuego();
        }
        catch (RemoteException e) {
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

    public void iniciarPartida (int cantidadJugadores, boolean agregarNombre) {
        try {
            m.iniciarPartida(cantidadJugadores, agregarNombre);
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
                    v.menuPrincipal(this);
                    break;
                case REGLAS:
                    v.verReglas();
                    break;
                case CREACION_ARCHIVO:
                    v.mensajeCreacionArchivo();
                    break;
                case SETEO_PARTIDA:
                    v.configuracionPartida(this);
                    break;
                case ULTIMO_TURNO:
                    v.ultimoTurno();
                    break;
                case GUARDAR_Y_SALIR:
                    v.mensajeGuardarYSalir();
                    break;
                case MENU_TURNO:
                    v.menuTurno(this, jugadorLocal);
                    break;
                case MENU_TURNO_FINAL:
                    v.menuTurnoFinal(this, jugadorLocal);
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
                    v.agregarNombre(this, jugadorLocal);
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
                        v.cargarPartida(this/*, (ConjuntoPartidas) o*/);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case SELECICON_EVALUAR:
                    v.seleccionCarta(this, jugadorLocal, DestinoCarta.EVALUAR);
                    break;
                case SELECICON_DESCARTAR:
                    v.seleccionCarta(this, jugadorLocal, DestinoCarta.DESCARTAR);
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


}
