package ar.edu.unlu.parade.vistamenuprincipal;

import ar.edu.unlu.parade.online.ClienteParade;
import ar.edu.unlu.parade.online.ServidorParade;

import javax.swing.*;
import java.awt.*;

public class menuParade extends JFrame {
    private JPanel panel1;
    private JLabel titulo;
    private JButton bReglas;
    private JButton bReanudar;
    private JButton bUnirseRed;
    private JButton bIniciarRed;
    private JButton sobreIniciarRed;
    private JButton sobreUnirseRed;
    private JButton sobreReanudar;
    private JButton bTopJugadores;
    private JButton bHistorialPartidas;
    private Image icono;

    public menuParade() {
        // Iniciar y configurar Frame
        initElements();
        setSize(360, 400);
        setContentPane(panel1);
        setIconImage(icono);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Menú principal - Parade");

        // Eventos
        bIniciarRed.addActionListener(e -> {
            dispose();
            new ServidorParade();
        });
        bUnirseRed.addActionListener(e -> {
            dispose();
            new ClienteParade();
        });
        bReanudar.addActionListener(e -> {/*
            ArrayList<PartidaGuardada> partidasGuardadas = Persistencia.cargarPartidasGuardadas();
            if (partidasGuardadas.isEmpty()) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "No se han encontrado partidas guardadas en el ordenador", "¡AVISO!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                dispose();
                new ListaPartidasGuardadas(partidasGuardadas);
            }*/ //TODO ESTA LOGICA HAY QUE APLICARLA, YO LO OMITÍ POR AHORA
            dispose();
            new menuCargarPartida(/*partidasGuardadas*/);
        });
        bHistorialPartidas.addActionListener(e -> {
            /*
                //TODO ACÁ SE DEBE APLICAR LA MISMA LOGICA QUE LOS OTROS 2 BOTONES
            */
            //dispose();
            new menuHistorialPartidas(/**/);
        });
        bTopJugadores.addActionListener(e -> {/*
            ArrayList<Jugador> mejoresJugadores = Persistencia.cargarJugadoresHistorico();
            if (mejoresJugadores.isEmpty()) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "No se han encontrado Jugadores en este ordenador.", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            } else {
                new MejoresJugadoresDialog(null, mejoresJugadores);
            }*/ //TODO ESTA LOGICA HAY QUE APLICARLA, YO LO OMITÍ POR AHORA
            //dispose();
            new menuRankingJugadores(/*null, mejoresJugadores*/);
        });
        bReglas.addActionListener(e -> {
            String[] paginas = {
                    "INTRODUCCION\n" +
                        "-Ha comenzado el carnaval en el Pais de las Maravillas. Todos ya disfrazados, las calles decoradas y la emoción se siente en el aire. Eres uno de los organizadores y necesitas\n" +
                        "encontrar más personas que deseen ingresar al carnaval, por lo tanto, estás haciendo lo imposible para motivarlos y lograr que se unan a la fiesta. Si tan solo la gente que ya\n" +
                        "participa no se fuera tan pronto… pues después de un rato, simplemente pierden todo interés en el carnaval. Y si aparece alguien vistiendo el mismo disfraz o uno mejor, lo\n" +
                        "único que logran, es que más personas dejen inmediatamente el carnaval y vayan a quejarse contigo. Si solo quieres que todos se diviertan, ¿por qué tiene que ser tan difícil?\n" +
                        "\n" +
                        "OBJETIVO DEL JUEGO\n" +
                        "- Los jugadores tratarán de no obtener cartas del carnaval, por lo que jugarán cartas de su mano intentando evitarlo. Nadie quiere puntos en el País de las Maravillas.\n" +
                        "\n" +
                        "COMPONENTES DE JUEGO\n" +
                        "- 66 cartas en 6 colores (VERDE, AMARILLO, ROJO, VIOLETA, AZUL, NEGRO), con valores que varían del 0 al 10)\n" +
                        "\n" +
                        "PREPARACIÓN DEL CARNAVAL\n" +
                        "- El jugador inicial se determina al azar. Comenzando con ese jugador, el juego continúa en dirección de las agujas del reloj. El jugador inicial baraja el mazo y reparte 5 \n" +
                        "cartas boca abajo a cada jugador. Luego coloca 6 cartas boca arriba una tras otra en el centro de la mesa. Estas cartas representan a los participantes iniciales del carnaval.\n" +
                        "La caja del juego se deja en uno de los extremos para simbolizar el frente del carnaval. El otro extremo será el final y el resto de cartas se dejan boca abajo formando un \n" +
                        "mazo de robo al centro de la mesa.\n" +
                        "\n",
                        "SECUENCIA DE JUEGO\n" +
                        "- Durante su turno, un jugador realiza las siguientes acciones en el orden explicado abajo.\n" +
                        "\t1. Elige una de las cartas en tu mano y colócala al final del carnaval.\n" +
                        "\t2. De ser necesario, algunas cartas dejarán el carnaval y quedarán frente al jugador activo.\n" +
                        "\t3. Roba una carta del mazo.\n" +
                        "- El siguiente jugador realiza las mismas acciones durante su turno.\n" +
                        "\n" +
                        "EXPLICACIÓN DE LAS ACCIONES\n" +
                        "- 1. El jugador elige una de las cartas en su mano y la coloca boca arriba al final del carnaval. Esta nueva carta no se considera al contar el total de cartas ya existentes en \n" +
                        "la siguiente acción.\n" +
                        "- 2. Algunas cartas dejarán el carnaval según qué carta se haya jugado.\n" +
                        "Si la cantidad de cartas que ya formaban parte del carnaval es menor o igual al valor de la carta jugada, entonces ninguna carta dejará el carnaval.\n" +
                        "Si la cantidad de cartas que ya formaban parte del carnaval es mayor al valor de la carta jugada, entonces algunas cartas dejarán el carnaval.\n" +
                        "Para determinar qué cartas se retiran del juego, enumera las cartas desde el final del carnaval hacia el frente sin tomar en cuenta la carta recién jugada. Se evaluará cada \n" +
                        "carta que cuente con una posición numérica mayor al valor de la carta jugada para determinar si dejará el carnaval.\n" +
                        "Para decidir que cartas evaluadas tendrán que dejar el juego, siga las siguientes reglas:\n" +
                        "\t- Todas las cartas del mismo color de la carta jugada\n" +
                        "\t- Todas las cartas con un valor menor o igual al valor de la carta jugada\n" +
                        "Las cartas retiradas se separan por colores y se dejan boca arriba frente al jugador.\n" +
                        "El valor de todas las cartas es público así que se aconseja agruparlas y desplazarlas\n" +
                        "ligeramente desde la parte superior formando una escala para que el valor de cada una\n" +
                        "permanezca visible.\n" +
                        "El resto de cartas en el carnaval se mueven para cerrar los espacios vacíos.\n" +
                        "Si se juega una carta de valor 0, se evaluarán todas las cartas.\n" +
                        "- 3.El jugador roba una carta del mazo para reponer su mano y volver a tener 5 cartas.\n" +
                        "\n" +
                        "ÚLTIMA RONDA Y FIN DEL JUEGO\n" +
                        "- La última ronda comienza cuando un jugador ha recogido cartas de los 6 colores o si el mazo de robo se acaba.\n" +
                        "Si un jugador recoge una carta del último color que le faltaba, terminará su turno normalmente. Luego, cada jugador incluyendo al que junto los 6 colores juega un turno extra.\n" +
                        " Sin embargo, en este turno final no robarán una carta del mazo.\n" +
                        "El juego termina luego de esta última ronda.\n" +
                        "Si se acaba el mazo de robo, cada jugador obtiene un turno extra. El juego termina cuando todos quedan con 4 cartas en mano.\n" +
                        "Incluso si otros jugadores obtienen su sexto color durante esta ronda final esto ya no surtirá efecto alguno. El juego termina después de esa ronda.\n" +
                        "\n",
                        "PUNTUACIÓN\n" +
                        "- Luego del final del juego cada jugador elegirá 2 cartas de su mano y descarta el resto. Después añadirán estas 2 últimas cartas a las que ya están boca arriba frente al \n" +
                        "jugador. Nota: cada una de estas 2 cartas deberá añadirse a una pila de un color que ya posea o creará una nueva pila si aún no poseía cartas de ese color.\n" +
                        "Solo las cartas que permanecen frente a un jugador le otorgarán puntaje. Las cartas que todavía tomaban parte en el desfile deben descartarse.\n" +
                        "Por cada color, los jugadores determinarán su puntaje. Cada color resuelve su puntaje por separado de la siguiente forma:\n" +
                        "\t1. Determinar quien posee la mayoría de cartas en cada color. El o los jugadores con la mayoría en cada color pondrán esas cartas boca abajo y cada una de estas cartas \n" +
                        "\tcontará como 1 solo punto (no se contará el valor impreso).\n" +
                        "\t2. Cada jugador sumará los valores de todas sus cartas boca arriba y lo añadirá al total obtenido con las cartas boca abajo.\n" +
                        "\n" +
                        "GANADOR\n" +
                        "- Como el País de las Maravillas siempre está al revés, el ganador será el jugador con menor puntaje. En caso de empate, el jugador empatado con menos cartas\n" +
                        "(se cuentan las boca abajo y las boca arriba) gana la partida.\n" +
                        "\n" +
                        "REGLAS PARA 2 JUGADORES\n" +
                        "- La única regla que cambia en esta modalidad es la que determina las mayorías en la puntuación final. Un jugador obtendrá la mayoría solo si tiene al menos 2 cartas más\n" +
                        "de un color que el otro jugador.\n"
            };

            int indice = 0;
            while (indice < paginas.length) {
                String botonTexto = (indice == paginas.length - 1) ? "Fin" : "Siguiente"; // Cambia el texto en la última página

                int opcion = JOptionPane.showOptionDialog(
                        null, paginas[indice], "Reglas del juego",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new String[]{botonTexto}, botonTexto
                );

                if (opcion == -1) break; // Cerrar si el usuario presiona "X"

                indice++;
            }
            //JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "a");
        });

        sobreIniciarRed.addActionListener(e -> {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "IMPORTANTE: Al iniciar el servidor desde este menu, primero se tiene que unir otra persona desde\notra computadora. Una vez que se haya unido, ahí recién nos podremos unir nosotros,\ncolocando nuestra IP que hayamos seleccionado.", "Sobre... iniciar un servidor", JOptionPane.INFORMATION_MESSAGE);
        });
        sobreUnirseRed.addActionListener(e -> {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Si otra persona desde otra computadora inició el servidor, simplemente iniciamos esta opción y colocamos\nlos datos necesarios para poder unirmos. Si vos creaste el servidor, primero esperá a que otra\npersona se una, y luego te unis vos.", "Sobre... unirse a una partida en red", JOptionPane.INFORMATION_MESSAGE);
        });
        sobreReanudar.addActionListener(e -> {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Si se encuentra una partida guardada en nuestro ordenador, se podrá reanudar.", "Sobre... reaunudar una partida", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void initElements() {
        icono = new ImageIcon(getClass().getResource("/ar/edu/unlu/parade/imagenes/LogoParade.png")).getImage();
        Image originalImage = icono;
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icono = new ImageIcon(scaledImage).getImage();
    }

    /*private static class MejoresJugadoresDialog extends JDialog {
        public MejoresJugadoresDialog(Frame parent, ArrayList<Jugador> mejoresJugadores) {
            super(parent, "Ranking mejores Jugadores", true);
            ordenarListaDeJugadores(mejoresJugadores);
            setSize(800, 300);
            setLocationRelativeTo(parent);
            JTextArea textArea = new JTextArea(10, 50);
            textArea.setEditable(false);
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de mejores jugadores registrados a la fecha  >>> (").append(formatearFecha()).append(")\n");
            int posicion = 1;
            for (Jugador jugador : mejoresJugadores) {
                sb.append("\nPOSICIÓN N°").append(posicion++).append("  >>>  ").append(jugador.getNombre()).append("\n");
                sb.append("     ").append("--- PUNTAJE  >>>  * ").append(jugador.getPuntaje()).append(" *\n");
                sb.append("     ").append("--- VICTORIAS: ").append(jugador.getVictorias());
                sb.append("     ").append("--- EMPATES: ").append(jugador.getEmpates());
                sb.append("     ").append("--- DERROTAS: ").append(jugador.getDerrotas());
                sb.append("\n");
            }
            textArea.setText(sb.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            // Hacer visible el diálogo
            setVisible(true);
        }
    }

    private static void ordenarListaDeJugadores(List<Jugador> jugadores) {
        int n = jugadores.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (jugadores.get(j).getPuntaje() < jugadores.get(j + 1).getPuntaje()) {
                    Jugador temp = jugadores.get(j);
                    jugadores.set(j, jugadores.get(j + 1));
                    jugadores.set(j + 1, temp);
                }
            }
        }
    }

    private static String formatearFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }*/
}
