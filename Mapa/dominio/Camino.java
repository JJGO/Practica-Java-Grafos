// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// Camino.java

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