// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// Arista.java

package Mapa.dominio;

public class Arista implements java.io.Serializable
{
    private Nodo origen;
    private Nodo destino;

    public Arista(Nodo origen, Nodo destino)
    {
        this.setOrigen(origen);
        this.setDestino(destino);
    }

    public Nodo getOrigen()
    {
        return this.origen;
    }

    public void setOrigen(Nodo origen)
    {
        this.origen = origen;
    }

    public Nodo getDestino()
    {
        return this.destino;
    }

    public void setDestino(Nodo destino)
    {
        this.destino = destino;
    }

    public Arista reverse()
    {
        return new Arista(destino,origen);
    }

    @Override 
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(origen.toString());
        s.append(" -> ");
        s.append(destino.toString());
        return s.toString();
    }

    public String representation()
    {
        StringBuilder s = new StringBuilder();
        s.append(origen.toString());
        s.append(" ");
        s.append(destino.toString());
        return s.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if( o instanceof Arista)
        {
            Arista a = (Arista) o;
            Nodo o1 = this.origen;
            Nodo o2 = a.getOrigen();
            Nodo d1 = this.destino;
            Nodo d2 = a.getDestino();
            return o1.equals(o2) && d1.equals(d2);
        }else
            return false;
    }

    @Override
    public int hashCode()
    {
        return this.origen.hashCode();
    }
}