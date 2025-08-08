package ar.edu.unlu.parade.online;

import ar.edu.unlu.parade.modelo.ModeloParade;
import ar.edu.unlu.parade.vistamenuprincipal.menuParade;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServidorParade {

    /**
     * Se inicia un servidor limpio. Es decir, es una partida en 0, sin nada cargado. Un modelo limpio.
     */

    public ServidorParade() {
        ArrayList<String> ips = Util.getIpDisponibles();
        String[] nj = { "2", "3", "4", "5", "6" };
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        String cantidadJugadores = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la cantidad de jugadores para esta partida", "Cantidad de jugadores",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nj,
                null
        );
        ModeloParade modelo = new ModeloParade();

        try {
            Servidor servidor = new Servidor(ip, Integer.parseInt(port));
            try {
                servidor.iniciar(modelo);
                modelo.setJugadoresPartida(Integer.parseInt(cantidadJugadores));
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Se ha iniciado correctamente el servidor.\nPara unirte a la partida, iniciá un nuevo menú principal y seleccioná la opción 'Unirse a un servidor',\ncompletando con los datos de tu computadora/red.'", "Servidor iniciado.", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException | RMIMVCException e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "No se ha podido iniciar correctamente el servidor. Vuelva a intentar.", "Error al iniciar el servidor", JOptionPane.ERROR_MESSAGE);
                new menuParade();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Se detectaron valores incorrectos para iniciar el servidor. Vuelva a intentarlo.", "Error al iniciar el servidor", JOptionPane.ERROR_MESSAGE);
            new menuParade();
        }
    }

    /**
     * Se inicia un servidor en base a un modelo ya creado desde antes.
     * Esto lo hacemos para reanudar una partida.
     *
     * @param modelo Es el Modelo que se va utilizar para reanudar la partida.
     */
    public ServidorParade(ModeloParade modelo) {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        try {
            Servidor servidor = new Servidor(ip, Integer.parseInt(port));
            try {
                servidor.iniciar(modelo);
                modelo.setJugadoresPartida(modelo.getPartida().getJugadores().size());
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Se ha iniciado correctamente el servidor.\nPara unirte a la partida, iniciá un nuevo menú principal y seleccioná la opción 'Unirse a un servidor',\ncompletando con los datos de tu computadora/red.'", "Servidor iniciado.", JOptionPane.INFORMATION_MESSAGE);
            } catch (RMIMVCException | RemoteException e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "No se ha podido iniciar correctamente el servidor. Vuelva a intentar.", "Error al iniciar el servidor", JOptionPane.ERROR_MESSAGE);
                new menuParade();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Se detectaron valores incorrectos para iniciar el servidor. Vuelva a intentarlo.", "Error al iniciar el servidor", JOptionPane.ERROR_MESSAGE);
            new menuParade();
        }
    }

    public static void main(String[] args) {
        new ServidorParade();
    }
}
