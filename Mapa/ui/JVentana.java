// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//JVentana.java

// Ventana padre para la gestion de Grafos de distinto tipo.
// Posee tres secciones. 
//      -Datos:         Para la inclusion, modificacion y borrado de Nodos y Aristas
//      -Presentacion:  Para el mostrado de informacion y busqueda de caminos minimos
//      -Gestion:       Permite la creacion, carga y salvado de grafos tanto en archivos binarios como en tipo texto.

package Mapa.ui;

import Mapa.dominio.*;
import Mapa.io.IOGrafo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;



public class JVentana extends JFrame
{
    // Atributos de la Ventana

    public final static int ADD_DATA     = 0;
    public final static int REMOVE_DATA  = 1;
    public final static int UPDATE_DATA  = 2;
    public final static int SEARCH_DATA  = 3;

    public final static String[] tipoOperacion = {"Añadir","Eliminar","Actualizar","Buscar"};


    // Paneles

    private JPanel pnlDatos;
    private JPanel pnlPresentacion;
    private JPanel pnlGestion;

    // Label

    private JLabel lblTitulo;

    private JLabel lblDatos;
    private JLabel lblPresentacion;
    private JLabel lblGestion;

    // Botones

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnRemove;

    private JButton btnBuscar;
    private JButton btnDistancia;
    private JButton btnListar;
    // TODO private JButton btnTabla;

    private JButton btnCreate;
    private JButton btnSave;
    private JButton btnLoad;

    private GrafoPonderado grafo = null;


    public static void main(String[] args) {
        new JVentana();
    }

    public JVentana()
    {
        super("Grafos");
        
        this.initComponents();
        this.manageEvents();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

    }

    private void initComponents()
    {
        lblTitulo       =   JCreator.createLabel("Grafos");
        lblTitulo.setFont(new Font("Arial",Font.BOLD,24));
        lblTitulo.setPreferredSize(new Dimension(60,30));
        lblTitulo.setMaximumSize(new Dimension(60,30));
        lblTitulo.setMinimumSize(new Dimension(60,30));

        lblDatos        =   JCreator.createLabel("Datos");
        lblPresentacion =   JCreator.createLabel("Presentacion");
        lblGestion      =   JCreator.createLabel("Gestion");

        btnAdd          =   JCreator.createBtn("Añadir");
        btnUpdate       =   JCreator.createBtn("Actualizar");
        btnRemove       =   JCreator.createBtn("Eliminar");

        btnBuscar       =   JCreator.createBtn("Buscar");
        btnDistancia    =   JCreator.createBtn("Distancia");
        btnListar       =   JCreator.createBtn("Listar");

        btnCreate       =   JCreator.createBtn("Crear");
        btnSave         =   JCreator.createBtn("Guardar");
        btnLoad         =   JCreator.createBtn("Cargar");

        // Panel Datos - Oeste

        pnlDatos = new JPanel(new GridLayout(4,1));
        pnlDatos.add(lblDatos);
        pnlDatos.add(btnAdd);
        pnlDatos.add(btnUpdate);
        pnlDatos.add(btnRemove);

        // Panel Presentacion - Centro

        pnlPresentacion = new JPanel(new GridLayout(4,1));
        pnlPresentacion.add(lblPresentacion);
        pnlPresentacion.add(btnBuscar);
        pnlPresentacion.add(btnDistancia);
        pnlPresentacion.add(btnListar);

        // Panel Gestion - Este

        pnlGestion = new JPanel(new GridLayout(4,1));
        pnlGestion.add(lblGestion);
        pnlGestion.add(btnCreate);
        pnlGestion.add(btnLoad);
        pnlGestion.add(btnSave);


        // gestion de eventos
        

        // Ajustes finales
        this.setLayout(new BorderLayout());

        this.add(lblTitulo,BorderLayout.NORTH);
        this.add(pnlDatos,BorderLayout.WEST);
        this.add(pnlPresentacion,BorderLayout.CENTER);
        this.add(pnlGestion,BorderLayout.EAST);
    }

    private void manageEvents()
    {
        //Eventos
        // Botones de Datos
        btnAdd.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.operateData(JVentana.this.ADD_DATA);
                }
            });
        btnUpdate.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.operateData(JVentana.this.UPDATE_DATA);
                }
            });
        btnRemove.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.operateData(JVentana.this.REMOVE_DATA);
                }
            });

        // Botones de Presentacion

        btnBuscar.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.searchData();
                }
            });
        btnDistancia.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.getPath();
                }
            });
        btnListar.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.listData();
                }
            });

        // Botones de Gestion

        btnCreate.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.createGrafo();
                }
            });
        btnSave.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.saveGrafo();
                }
            });
        btnLoad.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.loadGrafo();
                }
            });

        

        this.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowOpened(WindowEvent e)
                {
                    String nameFile = "alumnos";
                    int option = JOptionPane.showConfirmDialog( JVentana.this,
                                                                "¿Desea cargar un grafo desde archivo?",
                                                                "Archivo",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE); 
                    if(option == JOptionPane.YES_OPTION){
                        JVentana.this.loadGrafo();
                    }
                }   

                @Override
                public void windowClosing(WindowEvent e) 
                {
                    
                    int exit = JOptionPane.showConfirmDialog(   JVentana.this,  
                                                                "¿Desea salir del programa?", 
                                                                "Atención",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.WARNING_MESSAGE);
                    if(exit == JOptionPane.YES_OPTION)
                    {
                        String nameFile = "alumnos";
                        int option = JOptionPane.showConfirmDialog( JVentana.this,
                                                                    "¿Desea guardar los cambios realizados en el grafo?",
                                                                    "Archivo",
                                                                    JOptionPane.YES_NO_OPTION,
                                                                    JOptionPane.QUESTION_MESSAGE); 
                        if(option == JOptionPane.YES_OPTION){
                            JVentana.this.saveGrafo();
                        }
                        System.exit(0);
                    }
                        
                }       


            });
    }

    private void operateData(int operation)
    {
        
        if(grafo == null) // Si no se ha definido el grafo
        {
            JOptionPane.showMessageDialog(  this, 
                                            "Grafo no definido, por favor cree o cargue un grafo antes de operar sobre él",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
        }else{
            int opcionTipoDato;
            String[] tiposDato = {"Nodo","Arista"};
            if(operation == UPDATE_DATA)
            {
                opcionTipoDato = 1; // Solo las aristas son actualizables
            }else{
                opcionTipoDato = JOptionPane.showOptionDialog(  this, 
                                                                "¿Que tipo de dato desea introducir?", 
                                                                "Tipo de Dato", 
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE, 
                                                                null, 
                                                                tiposDato, 
                                                                tiposDato[0]);
            }
            switch(opcionTipoDato)
            {
                case 0:
                    String nombreNodo = JOptionPane.showInputDialog(this,"Por favor introduzca el nombre del nodo a "+tipoOperacion[operation]);
                    if(nombreNodo != null)
                    {
                        Nodo nodo = new Nodo(nombreNodo);
                        switch(operation)
                        {
                            case ADD_DATA:
                                if(!grafo.addNodo(nodo)) // Si ya ha sido añadida
                                {
                                    JOptionPane.showMessageDialog(  this, 
                                                                    "Nodo ya existente en el grafo",
                                                                    "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case REMOVE_DATA:
                                if(!grafo.removeNodo(nodo)){
                                    JOptionPane.showMessageDialog(  this, 
                                                                    "Nodo no contenido en el grafo",
                                                                    "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case UPDATE_DATA:
                                // No debe alcanzarse, los nodos no son actualizables ya que son claves de un HashMap
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                    

                case 1:
                    Arista arista = JAristaDialog.showInputDialog(  this,
                                                                    grafo.getNombreNodos(),
                                                                    operation,
                                                                    tipoOperacion[operation],
                                                                    "Por favor introduzca los datos de la arista a "+tipoOperacion[operation],
                                                                    grafo.getUnidades());
                    
                    if(arista != null)
                    {
                        try{
                            switch(operation)
                            {
                                case ADD_DATA:
                                    if(!grafo.addArista(arista))
                                    {
                                        JOptionPane.showMessageDialog(  this, 
                                                                        "Arista ya existente en el grafo",
                                                                        "Error",
                                                                        JOptionPane.ERROR_MESSAGE);
                                    }
                                    break;
                                case REMOVE_DATA:
                                    if(!grafo.removeArista(arista))
                                    {
                                        JOptionPane.showMessageDialog(  this, 
                                                                        "Arista no contenida en el grafo",
                                                                        "Error",
                                                                        JOptionPane.ERROR_MESSAGE);
                                    }
                                    break;
                                case UPDATE_DATA:
                                    Arista aristaNoPonderada = new Arista(arista.getOrigen(),arista.getDestino());
                                    if(!grafo.removeArista(aristaNoPonderada))
                                    {
                                        JOptionPane.showMessageDialog(  this, 
                                                                        "Arista no contenida en el grafo",
                                                                        "Error",
                                                                        JOptionPane.ERROR_MESSAGE);
                                    }else{
                                        grafo.addArista(arista);
                                    }
                                    
                                    break;
                                default:
                                    break;
                            }
                        }catch(IllegalArgumentException e)
                        {
                            JOptionPane.showMessageDialog(  this, 
                                                            e.getMessage(),
                                                            "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                default: 
                    break;
            }
        }
        
    }


    private void searchData()
    {
        Arista arista = JAristaDialog.showInputDialog(  this,
                                                        grafo.getNombreNodos(),
                                                        SEARCH_DATA,
                                                        tipoOperacion[SEARCH_DATA],
                                                        "Por favor introduzca los datos de la arista a buscar");
        if(grafo.containsArista(arista))
        {
            Iterator<Arista> itAristas = grafo.iteratorAristas(arista.getOrigen());
            Arista i = null;
            while(!arista.equals(i))
            {
                i = itAristas.next();
            }
            double peso = ((AristaPonderada) i).getPeso();

            StringBuilder s = new StringBuilder();
            s.append("La arista que conecta ");
            s.append(arista.getOrigen().toString());
            s.append(" y ");
            s.append(arista.getDestino().toString());
            s.append(" mide ");
            s.append(peso);
            s.append(" ");
            s.append(grafo.getUnidades());

            JOptionPane.showMessageDialog( this, s.toString());
        }else{
            JOptionPane.showMessageDialog(  this, 
                                            "Arista no contenida en el grafo",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getPath()
    {
        Arista arista = JAristaDialog.showInputDialog(  this,
                                                        grafo.getNombreNodos(),
                                                        SEARCH_DATA,
                                                        tipoOperacion[SEARCH_DATA],
                                                        "Por favor introduzca los datos de los nodos entre los que desea hallar la distancia");
        try{
            Nodo origen = arista.getOrigen();
            Nodo destino = arista.getDestino();
            Camino camino = grafo.getShortestPath(origen,destino);  

            StringBuilder s = new StringBuilder();
            s.append("Entre ");
            s.append(origen.toString());
            s.append(" y ");
            s.append(destino.toString());
            s.append(" hay una distancia de ");
            s.append(grafo.longitud(camino));
            s.append(" ");
            s.append(grafo.getUnidades());

            JOptionPane.showMessageDialog( this, s.toString());
            int i = 1;
            while(i < camino.size())
            {
                origen = camino.get(i-1); 
                Nodo siguiente = camino.get(i);

                StringBuilder indicacion = new StringBuilder();
                indicacion.append("Desde ");
                indicacion.append( origen.toString() );
                indicacion.append(" dirijase a ");
                indicacion.append( siguiente.toString() );
                indicacion.append(". Distancia: ");
                indicacion.append( grafo.longitud(new Camino(origen,siguiente)) );
                indicacion.append(" ");
                indicacion.append( grafo.getUnidades() );

                String[] opciones = {"< Anterior", "Siguiente >"};
                int opcion = JOptionPane.showOptionDialog(   this,
                                                        indicacion.toString(),
                                                        "Paso "+i,
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.INFORMATION_MESSAGE,
                                                        null,
                                                        opciones,
                                                        opciones[1]);
                if( opcion == 0 && i > 1)
                {
                    i--;
                }
                else if(opcion == 1)
                {
                    i++;
                }
            }
            JOptionPane.showMessageDialog(this, "Ha llegado al nodo de destino");
        }
        catch(IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(  this,
                                            e.getMessage(),
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
        }
        

    }

    private void listData()
    {
        // TODO Un Text Area o algo asi que imprima el grafo de golpe 
    }


    private void createGrafo()
    {

        String nombre = JOptionPane.showInputDialog(  this,
                                                    "Por favor especifique el nombre del grafo",
                                                    "Nombre del Grafo",
                                                    JOptionPane.QUESTION_MESSAGE);
        if(nombre != null)
        {
            String unidades = JOptionPane.showInputDialog( this,
                                                        "Por favor especifique las unidades del grafo",
                                                        "Unidades del Grafo",
                                                        JOptionPane.QUESTION_MESSAGE);
            if(unidades != null)
            {
                this.grafo = new GrafoPonderado(nombre,unidades);   
                lblTitulo.setText(grafo.getNombre());
            } 
        }
        
    }

    private void loadGrafo()
    {
        //String nameFile = JOptionPane.showInputDialog(this,"Por favor especifique el nombre del archivo","Nombre del Archivo",JOptionPane.QUESTION_MESSAGE);
        JFileChooser j = new JFileChooser(); 
        int rtn = j.showOpenDialog(this);
        if (rtn == JFileChooser.APPROVE_OPTION)
        {
            String nameFile = j.getSelectedFile().getPath();
            grafo = (GrafoPonderado)IOGrafo.readFile(nameFile);
            if(grafo != null)
            {
                lblTitulo.setText(grafo.getNombre());  
            }
        }
    }

    private void saveGrafo()
    {
        // String nameFile = JOptionPane.showInputDialog(this,"Por favor especifique el nombre del archivo","Nombre del Archivo",JOptionPane.QUESTION_MESSAGE);
        String[] tiposArchivo = {"Archivo Binario","Archivo de Texto"};
        int opcion = JOptionPane.showOptionDialog(  this, 
                                                    "¿Que tipo de fichero desea utilizar?", 
                                                    "Tipo de Fichero", 
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.QUESTION_MESSAGE, 
                                                    null, 
                                                    tiposArchivo, 
                                                    tiposArchivo[0]);
        
        JFileChooser j = new JFileChooser(); 
        int rtn = j.showSaveDialog(this);
        if (rtn == JFileChooser.APPROVE_OPTION)
        {
            String nameFile = j.getSelectedFile().getPath();
            if(opcion == 0)
            {    
                IOGrafo.writeFile(grafo,nameFile);
                
            }else{
                // IOGrafo.writeTextFile(grafo,nameFile);
                // TODO Export2TXT
            }
        }
        
        
    }
}