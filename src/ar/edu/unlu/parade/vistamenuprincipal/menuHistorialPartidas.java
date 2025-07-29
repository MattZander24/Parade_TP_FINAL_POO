package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.modelo.persistencia.ConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroPartida;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class menuHistorialPartidas extends JFrame {
    private JPanel topPanel;
    private JLabel lTitulo;
    private JButton bSalir;
    private JPanel listaPartidas;
    private JPanel generalPanel;

    private Image icono;

    private RegistroConjuntoPartidas partidas;

    public menuHistorialPartidas(RegistroConjuntoPartidas partidas) {
        this.partidas = partidas;

        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Historial de Partidas - Parade");

        //Eventos
        bSalir.addActionListener(e -> {
            dispose();
        });
    }

    private void initElements() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();

        //Ver Historial Partidas
        /*FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        RegistroConjuntoPartidas partidas = null;
        try {
            fileInputStream = new FileInputStream("partidas.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            partidas = (RegistroConjuntoPartidas) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException e) {
            ArrayList<RegistroPartida> registroPartidas = new ArrayList<RegistroPartida>();
            partidas = new RegistroConjuntoPartidas(registroPartidas);
            c.mensajeCreacionArchivo();
            fileOutputStream = new FileOutputStream("partidas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.err.println("Se produjo un error de entrada/salida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            throw new RuntimeException(e);
        } finally {*/
            //TODO: MOSTRAR EN FORMATO SWING Y NO CON SOUT
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
            String fechaHoraFormateada;
            assert partidas != null;
            if (!(partidas.getPartidas().isEmpty())) {
                for (RegistroPartida partida : partidas.getPartidas()) {
                    fechaHoraFormateada = partida.getFechaYHoraPartida().format(formato);
                    System.out.print("\n");
                    System.out.println("- Partida jugada el " + fechaHoraFormateada + "...");
                    System.out.println("  (jugadores ordenados por puesto)");
                    for (RegistroJugadores jugador : partida.getRankingJugador()) {
                        System.out.println("\t" + jugador.getPosicionJugador() + "- " + jugador.getDefinicionJugador() + " (" + jugador.getPuntosJugador() + "pts.)");
                    }
                }
                System.out.print("\n");
                //FuncionesConsola.PulseEnter();
            }
            else {
                System.out.println("\nEl historial de partidas esta vacío...\n");
            }
        //}
    }
}
