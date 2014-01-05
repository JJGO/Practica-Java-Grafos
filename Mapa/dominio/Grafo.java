// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// Grafo.java

// Grafo no direccional.
// Toma la definicion de digrafo y redefine los metodos por los que se añade y elimina una arista 
// para incluir los casos reciprocos de destino -> origen

package Mapa.dominio;

public class Grafo extends Digrafo
{


    public Grafo(String nombre)
    {
        super(nombre);
    }

    public boolean addArista(Arista arista)
    {
        boolean a = super.addArista(arista);
        boolean b = super.addArista(arista.reverse());
        return a && b;
    }

    public boolean removeArista(Arista arista)
    {
        boolean a = super.removeArista(arista);
        boolean b = super.removeArista(arista.reverse());
        return a && b;
    }
}