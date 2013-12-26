// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// GrafoPonderado.java

package Mapa.dominio;


import java.util.Iterator;
import java.util.ArrayList;

public class GrafoPonderado extends Grafo
{
    private String unidades;

    public GrafoPonderado(String unidades)
    {
        super();
        this.unidades = unidades;
    }

    public void addArista(AristaPonderada arista)
    {
        super.addArista(arista);
    }

    public void addArista(Arista arista)
    {
        if(arista instanceof AristaPonderada){
            AristaPonderada a = (AristaPonderada)arista;
            this.addArista(a);
        }
        else
            throw new IllegalArgumentException("Arista no ponderada");
    }

    public Camino getShortestPath(Nodo origen, Nodo destino)
    {
        if(!this.contains(origen) || !this.contains(destino))
            throw new IllegalArgumentException("Nodo no contenido en el grafo");

        ArrayList<Camino> caminos = new ArrayList<Camino>();
        Camino minimo = null;
        double minimaDistancia = 0.0;
        caminos.add(new Camino(origen)); // Incializa los caminos
        // Busqueda

        while(!caminos.isEmpty())
        {
            Camino camino = caminos.remove(0);
            if(minimo == null || this.longitud(camino) < minimaDistancia){

                Nodo ultimo = camino.getFinal();
                Iterator<Nodo> hijos = this.childrenOf(ultimo).iterator();
                while(hijos.hasNext())
                {
                    Nodo siguiente = hijos.next();
                    if(siguiente.equals(destino)){
                        Camino nuevoCamino = camino.clone();
                        nuevoCamino.add(siguiente);
                        if(minimo == null || this.longitud(nuevoCamino) < minimaDistancia)
                        {
                            minimo = nuevoCamino;
                            minimaDistancia = this.longitud(nuevoCamino);
                        }
                    }
                    else if(!camino.contains(siguiente)){
                        Camino nuevoCamino = camino.clone();
                        nuevoCamino.add(siguiente);
                        caminos.add(nuevoCamino);
                    }

                }
            }
        }
        if(minimo == null)
            throw new IllegalArgumentException("Nodos no conectados en el grafo");
        else
            return minimo;
    }

    private double longitud(Camino camino)
    {
        double length = 0.0;
        if(camino.size() > 1){
            
            Iterator<Nodo> itNodos = camino.iterator();
            
            Nodo actual = itNodos.next();
            while(itNodos.hasNext())
            {
                Nodo anterior = actual;
                actual = itNodos.next();
                Iterator<Arista> itAristas = super.aristas.get(anterior).iterator();
                Arista a = new Arista(anterior,actual); 
                Arista b = null;
                while(!a.equals(b))
                    b = itAristas.next();
                length += ((AristaPonderada) b).getPeso();

            }
        }

        return length;
    }

}