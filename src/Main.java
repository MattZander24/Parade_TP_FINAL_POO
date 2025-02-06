import ar.edu.unlu.parade.controlador.ControladorParade;
import ar.edu.unlu.parade.modelo.ModeloParade;
import ar.edu.unlu.parade.vistaconsola.VistaConsolaParade;

public class Main {
    public static void main(String[] args) {

        ModeloParade m = new ModeloParade();
        ControladorParade c = new ControladorParade(/*m*/);

        VistaConsolaParade v1 = new VistaConsolaParade(/*c*/);
        /*m.agregarObserver(v1);
        m.iniciarAplicacion();*/
    }
}