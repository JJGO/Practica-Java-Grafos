// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// Digrafo.java

// Estructura matematica formada por nodos y aristas dirigidas
// Su posibles usos son elevados. 
// Posee una implementacion de camino minimo mediante BFS(Breath First Search)
// Necesita de Nodo, Arista y Camino

package Mapa.dominio;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;


public class Digrafo implements java.io.Serializable

{
    private String nombre;
    protected Set<Nodo> nodos;
    protected HashMap<Nodo,Set<Arista>> aristas;

    public Digrafo(String nombre)
    {
        this.setNombre(nombre);
        this.nodos = new TreeSet<Nodo>();
        this.aristas = new HashMap<Nodo,Set<Arista>>();
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public boolean addNodo(Nodo nodo)
    {
        boolean anadido = this.nodos.add(nodo);
        if(anadido)
            // Si no se habia añadido, se inciializa la lista de nodos conectados
            aristas.put(nodo, new HashSet<Arista>() );
        return anadido;
    }

    public boolean addArista(Arista arista)
    {
        Nodo origen = arista.getOrigen();
        Nodo destino = arista.getDestino();
        // Si los nodos no se habian añadido se añaden
        if(origen.equals(destino))
            throw new IllegalArgumentException("Nodo de origen y de destino iguales");
        this.addNodo(origen);
        this.addNodo(destino);
        return aristas.get(origen).add(arista);
    }

    public boolean removeNodo(Nodo nodo)
    {
        boolean contenido = this.nodos.remove(nodo);
        Iterator<Arista> itAristas = this.iteratorAristas(nodo);

        if(contenido)
            while(itAristas.hasNext())
            {
                //this.removeArista(itAristas.next()); // Destruye las aristas y sus reciprocos
            }
            this.aristas.remove(nodo);  // Destruye el HasMap asociado
        return contenido;
    }

    public boolean removeArista(Arista arista)
    {
        Nodo origen = arista.getOrigen();
        return aristas.get(origen).remove(arista);
    }


    public boolean containsNodo(Nodo nodo)
    {
        return this.nodos.contains(nodo);
    }


    public boolean containsArista(Arista arista)
    {
        Nodo origen = arista.getOrigen();
        if(this.containsNodo(origen))
        {
            return this.aristas.get(origen).contains(arista);
        }else{
            return false;
        }
    }

    public Iterator<Nodo> iteratorNodos()
    {
        return this.nodos.iterator();
    }

    public Iterator<Arista> iteratorAristas(Nodo origen)
    {
        if(this.containsNodo(origen))
        {
            return this.aristas.get(origen).iterator();
        }
        else
        {
            throw new IllegalArgumentException("Nodo no contenido en el grafo");
        }
    }


    public Collection<Nodo> childrenOf(Nodo nodo)
    {
        if(!this.containsNodo(nodo))
            throw new IllegalArgumentException("Nodo no contenido en el grafo");

        ArrayList<Nodo> hijos = new ArrayList<Nodo>();
        Iterator<Arista> itAristas = this.aristas.get(nodo).iterator();
        while(itAristas.hasNext())
            hijos.add(itAristas.next().getDestino());
        return hijos;

    }

    public Vector<String> getNombreNodos()
    {
        Vector<String> nombreNodos = new Vector<String>();
        Iterator<Nodo> itNodos = nodos.iterator();
        while(itNodos.hasNext())
        {
            nombreNodos.add(itNodos.next().getNombre());
        }
        return nombreNodos;
    }

    public Camino getShortestPath(Nodo origen, Nodo destino)
    {
        if(!this.containsNodo(origen) || !this.containsNodo(destino))
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
        if(!this.containsNodo(origen) || !this.containsNodo(destino))
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

    public double longitud(Camino camino)
    {
        return (double)camino.size();
    }

    @Override 
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(this.nombre);
        s.append("\n");
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