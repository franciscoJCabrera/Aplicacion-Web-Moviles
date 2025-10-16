package es.ujaen.daw.model;

import java.util.List;

public interface generalDAO<T,K> {

    public T buscaId(K id);
    public List<T> buscaTodos();
    public boolean crea(T tel);
    public boolean guarda(T tel);
    public boolean borra(K id);

}
