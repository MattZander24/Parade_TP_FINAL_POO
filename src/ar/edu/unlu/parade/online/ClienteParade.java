package ar.edu.unlu.parade.online;

import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.interfaces.IVista;
import ar.edu.unlu.parade.vistaconsola.VistaConsola;
import ar.edu.unlu.parade.vistagrafica.VistaGrafica;
import ar.edu.unlu.parade.vistamenuprincipal.menuParade;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClienteParade {

    public ClienteParade() {
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("Consola");
        opciones.add("Interfáz gráfica");
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        String interfaz = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione como quiere visualizar el juego", "Interfaz gráfica",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones.toArray(),
                null
        );

        try (Socket ignored = new Socket(ipServidor, Integer.parseInt(portServidor))) {
            //Si llega hasta acá la conexión fue exitosa
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor en el puerto " + portServidor + ". Verifique que esté activo.\nVolviendo al menú principal", "Error", JOptionPane.ERROR_MESSAGE);
            new menuParade();
            return;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido para el puerto.\nVolviendo al menú principal", "Error", JOptionPane.ERROR_MESSAGE);
            new menuParade();
            return;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al intentar conectarse.\nVerifique estar iniciando el juego correctamente.\nVolviendo al menú principal", "Error", JOptionPane.ERROR_MESSAGE);
            new menuParade();
            return;
        }

        ControladorParade controlador = new ControladorParade();
        IVista vista;

        if (interfaz.equals("Consola")) {
            vista = new VistaConsola();
            vista.setC(controlador);
            controlador.setV(vista);
        } else {
            vista = new VistaGrafica();
            vista.setC(controlador);
            controlador.setV(vista);
        }

        Cliente cliente = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));

        try {
            cliente.iniciar(controlador);
            vista.iniciarVista();
        } catch (RemoteException | RMIMVCException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Uno de los puertos seleccionados ya se encuentra en uso. Vuelva a intentar unirse con otro.", "Error al unirse el servidor", JOptionPane.ERROR_MESSAGE);
            new menuParade();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClienteParade();
    }
}
