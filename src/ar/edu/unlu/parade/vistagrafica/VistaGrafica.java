package ar.edu.unlu.parade.vistagrafica;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.enumerados.EstadoPartida;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.modelo.Desfile;
import ar.edu.unlu.parade.modelo.Jugador;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VistaGrafica  extends JFrame implements IVista {
    @Override
    public ControladorParade getC() {
        return null;
    }

    @Override
    public void setC(ControladorParade c) {

    }

    @Override
    public void actualizarVistaParaAccion(EstadoPartida estadoActual) throws RemoteException {

    }

    @Override
    public void iniciarVista() {

    }

    @Override
    public void menuPrincipal() {

    }

    @Override
    public void verReglas() {

    }

    @Override
    public void mensajeCreacionArchivo() {

    }

    @Override
    public void mensajeGuardarYSalir() {

    }

    @Override
    public void verHistorico() throws IOException {

    }

    @Override
    public void verTop5() throws IOException {

    }

    @Override
    public void configuracionPartida() {

    }

    @Override
    public void cargarPartida() throws IOException {

    }

    @Override
    public void menuTurno() {

    }

    @Override
    public void menuTurnoFinal() {

    }

    @Override
    public void agregarNombre(Jugador jugador) {

    }

    @Override
    public void seleccionCarta(Jugador j, DestinoCarta d) {

    }

    @Override
    public void mensajeDescarteFinal(Jugador j) {

    }

    @Override
    public void mensajeGuardar(boolean msgOpcionIncorrecta) {

    }

    @Override
    public void mensajeGanador(Jugador j) {

    }

    @Override
    public void mensajeEmpateEntreJugadores(ArrayList<Jugador> ganadores) {

    }

    @Override
    public void mensajeRanking(ArrayList<Jugador> jugadoresPartida) {

    }

    @Override
    public void habilitarSalir() {

    }

    @Override
    public void mostrarADJ(Jugador j) {

    }

    @Override
    public void mostrarD(Desfile d) {

    }

    @Override
    public void mostrarM(Jugador j) {

    }

    @Override
    public void bienvenidaYEspera(Jugador jugadorLocal) {

    }
}
