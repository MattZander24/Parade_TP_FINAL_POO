package ar.edu.unlu.parade.vistaconsola;

import java.util.Scanner;

//------------------------------------------------------------------//
/// VISTA CONSOLA SIN SWING. PREVIA A LA ENTREGA FINAL. OBSOLETA. ///
//------------------------------------------------------------------//

public class FuncionesConsola {

    public static void PulseEnter () {
        System.out.print("Pulse ENTER para continuar");
        Scanner scannerEnter = new Scanner(System.in);
        if (scannerEnter.hasNextLine()) {
            scannerEnter.nextLine();
        }
        System.out.print("\n");
    }
}
