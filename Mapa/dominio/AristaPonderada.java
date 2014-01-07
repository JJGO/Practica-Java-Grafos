// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// AristaPonderada.java

// Arista direccional con un valor en coma flotante que define el peso o distancia entre los nodos que une.
// Se uso es necesario para defincion de grafos ponderados

package Mapa.dominio;

public class AristaPonderada extends Arista 
{
    private double peso;

    public AristaPonderada(Nodo origen, Nodo destino, double peso)
    {
        super(origen,destino);
        this.peso = peso;
    }

    public AristaPonderada(Nodo origen, double peso, Nodo destino)
    {
        this(origen,destino,peso);
    }

    public AristaPonderada(Arista arista, double peso)
    {
        this(arista.getOrigen(),arista.getDestino(),peso);
    }

    public double getPeso()
    {
        return this.peso;
    }

    public void setPeso(double peso)
    {
        this.peso = peso;
    }

    public AristaPonderada reverse()
    {
        return new AristaPonderada(super.reverse(),peso);
    }

    @Override 
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(super.toString());
        s.append("  ");
        s.append(peso);
        return s.toString();
    }

    public String representation()
    {
        StringBuilder s = new StringBuilder();
        s.append(super.representation());
        s.append(" ");
        s.append(peso);
        return s.toString();
    }

}