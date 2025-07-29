package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoJugadores;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroJugadores;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class menuRankingJugadores extends JFrame {
    private JPanel generalPanel;
    private JLabel lTitulo;
    private JLabel lTop1;
    private JLabel lTop2;
    private JLabel lTop3;
    private JLabel lTop4;
    private JLabel lTop5;
    private JButton bSalir;
    private JPanel topPanel;
    private JPanel centerPanel;

    private Image icono;

    private RegistroConjuntoJugadores jugadores;

    public menuRankingJugadores(RegistroConjuntoJugadores jugadores) {
        this.jugadores = jugadores;

        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Top 5 Jugadores - Parade");

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

        //Ver Top 5
        /*FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        RegistroConjuntoJugadores jugadores = null;
        try {
            fileInputStream = new FileInputStream("jugadores.txt");
            objectInputStream = new ObjectInputStream(fileInputStream);
            jugadores = (RegistroConjuntoJugadores) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (FileNotFoundException e) {
            ArrayList<RegistroJugadores> registroJugadores = new ArrayList<RegistroJugadores>();
            jugadores = new RegistroConjuntoJugadores(registroJugadores);
            c.mensajeCreacionArchivo();
            fileOutputStream = new FileOutputStream("jugadores.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(jugadores);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.err.println("Se produjo un error de entrada/salida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            throw new RuntimeException(e);
        }
        finally {*/
            //TODO: MOSTRAR EN FORMATO SWING Y NO CON SOUT
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
            String fechaHoraFormateada;
            if (!(jugadores.getJugadores().isEmpty())) {
                //Hacer solo el top 5
                for (int i = 0; i < 5; i++) {
                    fechaHoraFormateada = jugadores.getJugadores().get(i).getFechaYHoraPartida().format(formato);
                    System.out.print("\n");
                    System.out.println(i+1 + "- Partida jugada el " + fechaHoraFormateada + ":");
                    System.out.println("\t" + jugadores.getJugadores().get(i).getDefinicionJugador() + " - " + jugadores.getJugadores().get(i).getPuntosJugador() + "pts.");
                    System.out.print("\n");

                }
                System.out.print("\n");
                //FuncionesConsola.PulseEnter();
            }
            else {
                System.out.println("\nEl Top 5 de mejores puntajes esta vacío...\n");
            }
        //}
    }
}
