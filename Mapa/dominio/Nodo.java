// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// Nodo.java

// Clase para definir nodos en un grafo. 
// Cualquier clase heredada de la misma podra ser incluida en un digrafo.

package Mapa.dominio;

public class Nodo implements Comparable,java.io.Serializable
{
    private String nombre;

    //Constructor
    public Nodo(String nombre)
    {
        this.setNombre(nombre);
    }

    //Getters y setters
    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String representation()
    {
        return this.toString();
    }

    // Override
    @Override 
    public String toString()
    {
        return this.nombre;
    }

    @Override
    public boolean equals(Object o)
    {
        if( o instanceof Nodo)
        {
            Nodo n = (Nodo) o;
            String s1 = this.nombre;
            String s2 = n.getNombre();
            return s1.equals(s2);
        }else
            return false;
    }

    @Override
    public int hashCode()
    {
        return this.nombre.hashCode();
    }

    @Override
    public int compareTo(Object o)
    {
        if( o instanceof Nodo)
        {
            Nodo n = (Nodo) o;
            String s1 = this.nombre;
            String s2 = n.getNombre();
            return s1.compareTo(s2);
        }else
            return -1;
    }
}