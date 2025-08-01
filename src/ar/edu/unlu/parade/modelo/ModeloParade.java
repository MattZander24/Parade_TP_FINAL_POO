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

    @Override
    public Partida getPartida() {
        return partida;
    }

    @Override
    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Override
    public void iniciarAplicacion() throws RemoteException {
        generarPartida();
    }

    @Override
    public void mensajeCreacionArchivo () throws RemoteException {
        notificarObservadores(Opcion.CREACION_ARCHIVO);
    }

    @Override
    public void generarPartida () throws RemoteException {
        partida = new Partida(this);
    }

    @Override
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

    @Override
    public void reiniciarPartida () throws RemoteException {
        if (partida.getJugadores().size() == partida.getCantidadJugadores()) {
            try {
                partida.comenzarJuego(this, true);
            } catch (IOException | ClassNotFoundException e) {
                //Nada porque ya lo controla la funcion interna
            }
        }
    }

    @Override
    public void elegirJugador(int idJugador) throws RemoteException {
        for (Jugador jugador : partida.getJugadores()) {
            if (jugador.getIdJugador() == idJugador) {
                jugador.setElegido(true);
            }
        }
    }

    @Override
    public boolean esNuevaPartida() throws RemoteException {
        return partida.esNueva();
    }

    @Override
    public void menuTurno () throws RemoteException {
        notificarObservadores(Opcion.MENU_TURNO);
    }

    @Override
    public void menuTurnoFinal () throws RemoteException {
        notificarObservadores(Opcion.MENU_TURNO_FINAL);
    }

    @Override
    public void devolverCarta(Jugador jugador, int opcionCarta, DestinoCarta d) throws RemoteException {
        for (Jugador j : partida.getJugadores()) {
            if (j.equals(jugador)) {
                Carta cartaSeleccionada = j.getManoJugador().getCartas().get(opcionCarta);
                switch (d) {
                    case EVALUAR -> partida.evaluarDesfile(cartaSeleccionada, j);
                    case DESCARTAR -> j.getManoJugador().transferirCartas(partida.getPilaJuego(), cartaSeleccionada);
                }
            }
        }
    }

    @Override
    public void finalizarTurno() throws IOException, ClassNotFoundException {
        partida.finalizarTurno();
    }

    @Override
    public void finalizarUltimoTurno() throws IOException, ClassNotFoundException {
        partida.finalizarSecuenciaUltimoTurno();
    }

    @Override
    public void finalizarDescarte() throws IOException, ClassNotFoundException {
        partida.finalizarSecuenciaDescarte();
    }

    @Override
    public void mensajeDescarteFinal () throws RemoteException {
        notificarObservadores(Opcion.DESCARTE_Y_FINAL);
    }

    @Override
    public void mensajeGanador () throws RemoteException {
        notificarObservadores(Opcion.GANADOR_PARTIDA);
    }

    @Override
    public void mensajeEmpate () throws RemoteException {
        notificarObservadores(Opcion.EMPATE_JUGADORES);
    }

    @Override
    public void mensajeGanadorYRanking () throws RemoteException {
        notificarObservadores(Opcion.RANKING);
    }

    @Override
    public void habilitarSalir () throws RemoteException {
        notificarObservadores(Opcion.HABILITAR_SALIR);
    }

    @Override
    public void mostrarDesfile () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_DESFILE);
    }

    @Override
    public void mostrarAreaDeJuego () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_AREA);
    }

    @Override
    public void mostrarJugadores () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_AREA_TODOS);
    }

    @Override
    public void mostrarMano () throws RemoteException {
        notificarObservadores(Opcion.MOSTRAR_MANO);
    }

    @Override
    public void guardarYSalir () throws IOException, ClassNotFoundException, RemoteException {
        for (Jugador j : partida.getJugadores()) {
            j.setElegido(false);
        }
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
            notificarObservadores(Opcion.MENSAJE_GUARDAR_Y_SALIR);
            notificarObservadores(Opcion.HABILITAR_SALIR);
        }
    }

    @Override
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

    @Override
    public void agregarJugador(Jugador jugador) throws RemoteException {
        partida.agregarJugador(jugador);
    }

    @Override
    public void setJugadoresPartida(int cantidadJugadores) throws RemoteException {
        partida.setCantidadJugadores(cantidadJugadores);
    }

    @Override
    public void actualizarJugador() throws RemoteException {
        notificarObservadores(Opcion.ACTUALIZAR_JUGADOR);
    }
}
