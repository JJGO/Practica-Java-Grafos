// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//IOArista.java

package Mapa.io;

import Mapa.dominio.Arista;
import java.util.Iterator;

public class IOArista
{


    public static void writeFile(Digrafo digrafo, String nameFile)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(nameFile+EXTENSION);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(digrafo);
            oos.close();
            fos.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}