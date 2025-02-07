package ar.edu.unlu.parade.interfaces;

import ar.edu.unlu.parade.enumerados.Opcion;

//Debe ser implementada por las vistas
public interface Observer {
    public void notificar (Opcion opcion);
    public void notificar (Opcion opcion, Object o);
    public void notificar (Opcion opcion, Object o, Object o2);
}
