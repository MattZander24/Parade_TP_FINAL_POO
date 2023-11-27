package ar.edu.unlu.parade.vistaconsola;

import java.util.Scanner;

public class FuncionesConsola {

    //?
    public static void LimpiarPantalla () {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void PulseEnter () {
        System.out.print("Pulse ENTER para continuar");
        Scanner scannerEnter = new Scanner(System.in);
        if (scannerEnter.hasNextLine()) {
            scannerEnter.nextLine();
        }
        LimpiarPantalla();
        System.out.print("\n");
    }
}
