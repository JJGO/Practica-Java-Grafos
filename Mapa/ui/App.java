// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//App.java

package Mapa.ui;

import java.util.*;
import Mapa.dominio.*;

public class App
{
    public static void main(String[] args) {
        
        Nodo a = new Nodo("a");
        Nodo b = new Nodo("b");
        Nodo c = new Nodo("c");
        Nodo d = new Nodo("d");
        Nodo e = new Nodo("e");

        Arista ab = new AristaPonderada(a,b,1.0); //(int)(Math.random()*10) );
        Arista ac = new AristaPonderada(a,c,2.0); //(int)(Math.random()*10) );
        Arista bd = new AristaPonderada(b,d,2.0); //(int)(Math.random()*10) );
        Arista ce = new AristaPonderada(c,e,2.0); //(int)(Math.random()*10) );
        Arista de = new AristaPonderada(d,e,1.0); //(int)(Math.random()*10) );
        Arista cd = new AristaPonderada(c,d,0.5); //(int)(Math.random()*10) );

        GrafoPonderado g = new GrafoPonderado("Puntos","Unidades");

        g.addArista(ab);
        g.addArista(ac);
        g.addArista(bd);
        g.addArista(ce);
        g.addArista(de);
        g.addArista(cd);

        System.out.println(g.toString());
        Camino minimo = g.getShortestPath(a,e);
        System.out.println(minimo);
        System.out.println(g.getPaths(a,e));
    }
}