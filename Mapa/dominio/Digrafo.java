// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// Digrafo.java

package Mapa.dominio;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashSet;


public class Digrafo implements java.io.Serializable

{
    protected Set<Nodo> nodos;
    protected HashMap<Nodo,Set<Arista>> aristas;

    public Digrafo()
    {
        this.nodos = new TreeSet<Nodo>();
        this.aristas = new HashMap<Nodo,Set<Arista>>();
    }

    public boolean addNodo(Nodo nodo)
    {
        boolean anadido = this.nodos.add(nodo);
        if(anadido)
            // Si no se habia añadido, se inciializa la lista de nodos conectados
            aristas.put(nodo, new HashSet<Arista>() );
        return anadido;
    }

    public void addArista(Arista arista)
    {
        Nodo origen = arista.getOrigen();
        Nodo destino = arista.getDestino();
        // Si los nodos no se habian añadido se añaden
        if(origen.equals(destino))
            throw new IllegalArgumentException("Nodo de origen y de destino iguales");
        this.addNodo(origen);
        this.addNodo(destino);
        aristas.get(origen).add(arista);
    }

    public Iterator<Nodo> nodeIterator()
    {
        return this.nodos.iterator();
    }

    public Collection<Nodo> childrenOf(Nodo nodo)
    {
        if(!nodos.contains(nodo))
            throw new IllegalArgumentException("Nodo no contenido en el grafo");
        ArrayList<Nodo> hijos = new ArrayList<Nodo>();
        Iterator<Arista> itAristas = this.aristas.get(nodo).iterator();
        while(itAristas.hasNext())
            hijos.add(itAristas.next().getDestino());
        return hijos;

    }

    // public Nodo[] getNodos()
    // {
    //     // String[] nombreNodos = new String[nodos.size()];
    //     // Iterator<Nodos> itNodos = nodos.iterator();
    //     // int i = 0;
    //     // while(itNodos.hasNext())
    //     // {
    //     //     nombreNodos[i++] = itNodos.next().getNombre();
    //     // }
    //     // return nombreNodos;
    //     return nodos.toArray();

    // }

    public Camino getShortestPath(Nodo origen, Nodo destino)
    {
        if(!this.contains(origen) || !this.contains(destino))
            throw new IllegalArgumentException("Nodo no contenido en el grafo");

        ArrayList<Camino> caminos = new ArrayList<Camino>();
        caminos.add(new Camino(origen)); // Incializa los caminos
        // Busqueda

        while(!caminos.isEmpty())
        {
            Camino camino = caminos.remove(0);
            Nodo ultimo = camino.getFinal();
            Iterator<Nodo> hijos = this.childrenOf(ultimo).iterator();
            while(hijos.hasNext())
            {
                Nodo siguiente = hijos.next();
                if(siguiente.equals(destino)){
                    camino.add(siguiente);
                    return camino;
                }
                else if(!camino.contains(siguiente)){
                    Camino nuevoCamino = camino.clone();
                    nuevoCamino.add(siguiente);
                    caminos.add(nuevoCamino);
                }

            }
        }

        throw new IllegalArgumentException("Nodos no conectados en el grafo");
    }

    public Collection<Camino> getPaths(Nodo origen, Nodo destino)
    {
        if(!this.contains(origen) || !this.contains(destino))
            throw new IllegalArgumentException("Nodo no contenido en el grafo");

        ArrayList<Camino> caminos = new ArrayList<Camino>();
        ArrayList<Camino> caminosValidos = new ArrayList<Camino>();
        caminos.add(new Camino(origen)); // Incializa los caminos
        // Busqueda

        while(!caminos.isEmpty())
        {
            Camino camino = caminos.remove(0);
            Nodo ultimo = camino.getFinal();
            Iterator<Nodo> hijos = this.childrenOf(ultimo).iterator();
            while(hijos.hasNext())
            {
                Nodo siguiente = hijos.next();
                if(siguiente.equals(destino)){
                    Camino caminoValido = camino.clone();
                    caminoValido.add(siguiente);
                    caminosValidos.add(caminoValido);
                    
                }
                else if(!camino.contains(siguiente)){
                    Camino nuevoCamino = camino.clone();
                    nuevoCamino.add(siguiente);
                    caminos.add(nuevoCamino);
                }

            }
        }
        if(caminosValidos.isEmpty())
            throw new IllegalArgumentException("Nodos no conectados en el grafo");
        else
            return caminosValidos;
    }

    private int longitud(Camino camino)
    {
        return camino.size();
    }

    public boolean contains(Nodo nodo)
    {
        return this.nodos.contains(nodo);
    }

    @Override 
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        Iterator<Nodo> itNodos = nodos.iterator();
        while(itNodos.hasNext())
        {
            Nodo padre = itNodos.next();
            Iterator<Arista> itHijos = aristas.get(padre).iterator();
            while(itHijos.hasNext())
            {
                s.append(itHijos.next().toString());
                s.append("\n");
            }
        }
        s.deleteCharAt(s.length()-1);
        return s.toString();
    }
}