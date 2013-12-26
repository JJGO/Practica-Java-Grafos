// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// Grafo.java

package Mapa.dominio;

public class Grafo extends Digrafo
{

    public Grafo()
    {
        super();
    }

    public void addArista(Arista arista)
    {
        super.addArista(arista);
        super.addArista(arista.reverse());
    }
}