package ar.edu.unlu.parade.modelo;

import ar.edu.unlu.parade.enumerados.DestinoCarta;
import ar.edu.unlu.parade.interfaces.IModelo;
import ar.edu.unlu.parade.modelo.persistencia.*;
import ar.edu.unlu.parade.enumerados.Opcion;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModeloParade extends ObservableRemoto implements IModelo {
    private Partida partida;

    public ModeloParade() {
        try {
            iniciarAplicacion();
        } catch (RemoteException e) {
            throw new RuntimeException();
        }
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public void iniciarAplicacion() throws RemoteException {
        generarPartida();
    }

    public void iniciarJuego () throws RemoteException {
        notificarObservadores(Opcion.SETEO_PARTIDA);
    }

    public void verReglas() throws RemoteException {
        notificarObservadores(Opcion.REGLAS);
    }

    public void verHistorico () throws IOException, ClassNotFoundException, RemoteException {
        notificarObservadores(Opcion.HISTORICO);
    }

    public void mensajeCreacionArchivo () throws RemoteException {
        notificarObservadores(Opcion.CREACION_ARCHIVO);
    }

    public void mensajeGuardarYSalir () throws RemoteException {
        notificarObservadores(Opcion.MENSAJE_GUARDAR_Y_SALIR);
    }


    public void terminarPartida () throws RemoteException {
        System.exit(0);
    }

    public void generarPartida () throws RemoteException {
        partida = new Partida(this);
    }

    public void iniciarPartida () throws RemoteException {
        if (partida.getJugadores().size() == partida.getCantidadJugadores()) {
            partida.inicializar();
            try {
                partida.comenzarJuego(this, false);
            } catch (IOException | ClassNotFoundException e) {
                //Nada porque ya lo controla la funcion interna
            }
        }
    }

    public void menuTurno () throws RemoteException {
        notificarObservadores(Opcion.MENU_TURNO);
    }

    public void menuTurnoFinal () throws RemoteException {
        notificarObservadores(Opcion.MENU_TURNO_FINAL);
    }

    public void evaluarCarta() throws RemoteException {
        notificarObservadores(Opcion.SELECICON_EVALUAR);
    }

    public void descartarCarta() throws RemoteException {
        notificarObservadores(Opcion.SELECICON_DESCARTAR);
    }

    public void devolverCarta(Jugador jugador, int opcionCarta, DestinoCarta d) throws RemoteException {
        for (Jugador j : partida.getJugadores()) {
            if (j.equals(jugador)) {
                Carta cartaSeleccionada = j.manoJugador.cartas.get(opcionCarta);
                switch (d) {
                    case EVALUAR -> partida.evaluarDesfile(cartaSeleccionada, j);
                    case DESCARTAR -> j.manoJugador.transferirCartas(partida.pilaPartida, cartaSeleccionada);
                }
            }
        }
    }

    public void finalizarTurno() throws IOException, ClassNotFoundException {
        partida.finalizarTurno();
    }

    public void finalizarUltimoTurno() throws IOException, ClassNotFoundException {
        partida.finalizarSecuenciaUltimoTurno();
    }

    public void finalizarDescarte() throws IOException, ClassNotFoundException {
        partida.finalizarSecuenciaDescarte();
    }

    public void mensajeDescarteFinal () throws RemoteException {
        notificarObservadores(Opcion.DESCARTE_Y_FINAL);
    }

    public void mensajeGanador () throws RemoteException {
        notificarObservadores(Opcion.GANADOR_PARTIDA);
    }

    public void mensajeEmpate () throws RemoteException {
        notificarObservadores(Opcion.EMPATE_JUGADORES);
    }

    public void mensajeGanadorYRanking () throws RemoteException {
        notificarObservadores(Opcion.RANKING);
    }

    public void habilitarSalir () throws RemoteException {
        notificarObservadores(Opcion.HABILITAR_SALIR);
    }

    public void setNombre (Jugador j, String nombre) throws RemoteException {
        j.setNombre(nombre);
    }

    public void mostrarDesfile () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_DESFILE);
    }

    public void mostrarAreaDeJuego () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_AREA);
    }

    public void mostrarJugadores () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_AREA_TODOS);
    }

    public void mostrarMano () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_MANO);
    }

    public void cargarPartida () throws IOException, ClassNotFoundException, RemoteException {
        notificarObservadores(Opcion.CARGAR_PARTIDA);
    }

    public void reiniciarPartida (Partida p) throws RemoteException {
        partida = p;
        try {
            partida.comenzarJuego(this, true);
        } catch (IOException | ClassNotFoundException e) {
            //Nada porque ya lo controla la funcion interna
        }
    }

    public void top5Historico () throws IOException, ClassNotFoundException, RemoteException {
        notificarObservadores(Opcion.TOP5);
    }

    public void guardarYSalir () throws IOException, ClassNotFoundException, RemoteException {
        partida.guardarFechaYHora();

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        ConjuntoPartidas partidas = null;
        try {
            fileInputStream = new FileInputStream("partidas_guardadas.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            partidas = (ConjuntoPartidas) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException e) {
            ArrayList<Partida> cPartidas = new ArrayList<Partida>();
            partidas = new ConjuntoPartidas(cPartidas);
            notificarObservadores(Opcion.CREACION_ARCHIVO);
        }
        finally {
            assert partidas != null;
            partidas.agregarOActualizarPartida(partida);

            fileOutputStream = new FileOutputStream("partidas_guardadas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();

            partida.volverAlMenu();
            notificarObservadores(Opcion.GUARDAR_Y_SALIR);
        }
    }

    public void finalizarPartida(int idPartida) throws IOException, ClassNotFoundException, RemoteException {

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        ConjuntoPartidas partidas = null;

        try {
            fileInputStream = new FileInputStream("partidas_guardadas.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            partidas = (ConjuntoPartidas) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException e) {
            partidas = new ConjuntoPartidas(new ArrayList<>());
        }

        assert partidas != null;
        partidas.eliminarPartidasTerminadas(idPartida);

        fileOutputStream = new FileOutputStream("partidas_guardadas.txt");
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(partidas);
        objectOutputStream.close();
    }

    public void agregarJugador(Jugador jugador) throws RemoteException {
        partida.agregarJugador(jugador);
    }

    public void setJugadoresPartida(int cantidadJugadores) throws RemoteException {
        partida.setCantidadJugadores(cantidadJugadores);
    }

    public void actualizarTurno() throws RemoteException {
        notificarObservadores(Opcion.ACTUALIZAR_TURNO);
    }

    public void actualizarJugador() throws RemoteException {
        notificarObservadores(Opcion.ACTUALIZAR_JUGADOR);
    }
}
