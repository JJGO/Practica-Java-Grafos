// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//IOGrafo.java

// Clase para el Input / Output de grafos

package Mapa.io;

import java.io.*;
import Mapa.dominio.Digrafo;

public class IOGrafo
{
    private static  String EXTENSION = ".bin";

    public static Digrafo readFile(String nameFile)
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try
        {
            fis = new FileInputStream(nameFile);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            if(o instanceof Digrafo)
            {
                Digrafo digrafo = (Digrafo)o;
                return digrafo;
            }

        }
        catch(EOFException e)
        {
            try{
                if(ois != null)
                    ois.close();
                if(fis != null)
                    fis.close();
            }
            catch(IOException eio)
            {
                e.printStackTrace();
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,"No se ha encontrado el fichero "+nameFile+EXTENSION,"Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }  
        return null; 
                                                                
    }

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
            javax.swing.JOptionPane.showMessageDialog(null,"No se ha encontrado el fichero "+nameFile+EXTENSION,"Error",javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
    /*
    public static void writeTextFile(Digrafo digrafo, String nameFile)
    {
        try{
            Export2TXT textInterface = new Export2TXT(nameFile);

            textInterface.println("-Grafo");
            textInterface.println(grafo.getNombre());

            textInterface.println("-Unidades");
            textInterface.println(grafo.getUnidades());

            textInterface.println("-Nodos");
            Iterator<Nodo> itNodos = digrafo.iteratorNodos();
            while(itNodos.hasNext())
            {
                textInterface.println(itNodos.next().representation());
            }
            textInterface.println("-Aristas");
            Iterator<Arista> itAristas = digrafo.iteratorAristas();
            while(itAristas.hasNext())
            {
                textInterface.println(itAristas.next().representation());
            }
            textInterface.println("-EOF");

            textInterface.close();

        }catch(IOException e)
        {
            javax.swing.JOptionPane.showMessageDialog(null,"Se ha encontrado un problema a la hora de guardar el archivo","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }
    */
    public static String getEXTENSION()
    {
        return EXTENSION;
    }

    public static void setEXTENSION(String extension)
    {
        EXTENSION = extension;
    }
}

// Constructor Detail

// Export2TXT
// public Export2TXT(java.lang.String nombreFichero)
//            throws java.io.IOException
// Throws:
// java.io.IOException
// Method Detail

// println
// public void println(java.lang.String lineaAInsertar)
// close
// public void close()

// Ejemplo de compilación:
// javac -cp .;Export2TXT.jar App.java

// Ejemplo de ejecución:
// java -cp .;Export2TXT.jar App

// El argumento -cp (CLASSPATH) sirve para indicar a la JVM la ubicación de las clases que se utilizarán: las nuestras (.) y las externas (separadas por ;).

// Si la ubicación del fichero JAR externo a utilizar no fuese el 
// directorio actual, se debería escribir la ruta absoluta, por ejemplo:

// javac -cp .;C:\temp\Export2TXT.jar App.java