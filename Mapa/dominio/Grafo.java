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


    public void addArista(Arista arista)
    {
        super.addArista(arista);
        super.addArista(arista.reverse());
    }

    public boolean removeArista(Arista arista)
    {
        super.removeArista(arista);
        super.removeArista(arista.reverse());
    }
}