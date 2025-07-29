package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.modelo.persistencia.ConjuntoPartidas;
import ar.edu.unlu.parade.modelo.persistencia.RegistroConjuntoPartidas;

import javax.swing.*;
import java.awt.*;

public class menuCargarPartida extends JFrame {
    private JPanel generalPanel;
    private JPanel listaPartidas;
    private JPanel topPanel;
    private JButton bSalir;
    private JLabel lTitulo;

    private Image icono;

    private RegistroConjuntoPartidas partidas;

    public menuCargarPartida(RegistroConjuntoPartidas partidas) {
        this.partidas = partidas;

        initElements();
        setSize(720, 480);
        setContentPane(generalPanel);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Cargar Partida - Parade");

        //Eventos
        bSalir.addActionListener(e -> {
            dispose();
            new menuParade();
        });
    }

    private void initElements() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();

        //Cargar Partida
        //TODO: EMPEZAR UNA VEZ TERMINADO LOS OTROS 2. PARECE MAS COMPLEJO.
        /*FileInputStream fileInputStream;
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
            c.mensajeCreacionArchivo();
            fileOutputStream = new FileOutputStream("partidas_guardadas.txt");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(partidas);
            objectOutputStream.close();
        }
        catch (IOException e) {
            System.err.println("Se produjo un error de entrada/salida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            throw new RuntimeException(e);
        } finally {
            Scanner scanner = new Scanner(System.in);
            int opcion;

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm");
            String fechaHoraFormateada;
            int indice = 1;
            int indice2;
            if (!(partidas.getPartidas().isEmpty())) {
                for (Partida partida : partidas.getPartidas()) {
                    fechaHoraFormateada = partida.getFechaYHoraPartida().format(formato);
                    System.out.print("\n");
                    System.out.println(indice + "- Partida guardada el " + fechaHoraFormateada + "...");
                    System.out.println("\tJugadores:");
                    indice2 = 1;
                    for (Jugador j : partida.getJugadores()) {
                        System.out.println("\t" + indice2 + ". " + j.definicionJugador("", ""));
                        indice2++;
                    }
                    indice++;
                }
                System.out.print("\n");
                System.out.println(indice + "- Salir");
                System.out.print("\n");

                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                while (opcion < 1 || opcion > indice) {
                    System.out.print("\nOpción incorrecta, por favor ingrese una opción valida...");
                    System.out.print("Seleccione una opción: ");
                    opcion = scanner.nextInt();
                }
                if (opcion != indice) {
                    Partida p = partidas.getPartidas().get(opcion-1);
                    c.reiniciarPartida (p);
                }
            }
            else {
                System.out.println("\nNo hay ninguna partida guardada para cargar...\n");
            }
        }*/
    }
}
