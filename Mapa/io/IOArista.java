// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//IOArista.java

package Mapa.io;

import Mapa.dominio.Arista;
import java.util.Iterator;

public class IOArista
{
    private static  String EXTENSION = ".obj";

    // public static Digrafo readFile(String nameFile)
    // {
    //     Digrafo digrafo;
    //     FileInputStream fis = null;
    //     ObjectInputStream ois = null;
    //     try
    //     {
    //         fis = new FileInputStream(nameFile+EXTENSION);
    //         ois = new ObjectInputStream(fis);
    //         Object o = ois.readObject();
    //         if(o instanceof Digrafo)
    //         {
    //             Digrafo digrafo = (Digrafo)o;
    //             return digrafo;
    //         }

    //     }
    //     catch(EOFException e)
    //     {
    //         try{
    //             if(ois != null)
    //                 ois.close();
    //             if(fis != null)
    //                 fis.close();
    //         }
    //         catch(IOException eio)
    //         {
    //             e.printStackTrace();
    //         }
    //     }
    //     catch(FileNotFoundException e)
    //     {
    //         e.printStackTrace();
    //         javax.swing.JOptionPane.showMessageDialog(null,"No se ha encontrado el fichero "+nameFile+EXTENSION);
    //     }
    //     catch(ClassNotFoundException e)
    //     {
    //         e.printStackTrace();
    //     }
    //     catch(IOException e)
    //     {
    //         e.printStackTrace();
    //     }   
                                                                
    // }

    // public static void writeFile(Digrafo digrafo, String nameFile)
    // {
    //     try
    //     {
    //         FileOutputStream fos = new FileOutputStream(nameFile+EXTENSION);
    //         ObjectOutputStream oos = new ObjectOutputStream(fos);
    //         oos.writeObject(digrafo);
    //         oos.close();
    //         fos.close();
    //     }
    //     catch(FileNotFoundException e)
    //     {
    //         e.printStackTrace();
    //     }
    //     catch(IOException e)
    //     {
    //         e.printStackTrace();
    //     }

    // }

    public static String getEXTENSION()
    {
        return EXTENSION;
    }

    public static void setEXTENSION(String extension)
    {
        EXTENSION = extension;
    }
}