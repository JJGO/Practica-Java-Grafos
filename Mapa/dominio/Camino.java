// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// Camino.java

// Clase formada por un conjunto de nodos ordenados por el orden de inclusion
// Se utiliza para representar caminos en un grafo.
// Hereda de ArrayList<Nodo> y expande su funcionalidad para el uso en grafos.

package Mapa.dominio;

import java.util.ArrayList;
import java.util.Iterator;

public class Camino extends ArrayList<Nodo>
{
    public Camino()
    {
        super();

    }

    public Camino(Nodo origen)
    {
        this();
        this.add(origen);
    }

    public Camino(Nodo origen,Nodo destino)
    {
        this();
        this.add(origen);
        this.add(destino)
    }

    public Nodo getInicio()
    {
        return super.get(0);
    }

    public Nodo getFinal()
    {
        return super.get(super.size()-1);
    }

    public Camino clone()
    {
        return (Camino)super.clone();
    }

    @Override 
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < super.size()-1; i++)
        {
            s.append(super.get(i).toString());
            s.append(" -> ");
        }
        s.append(super.get(super.size()-1).toString());
        return s.toString();
    }

}