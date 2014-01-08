// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//JVentana.java

// Ventana padre para la Archivo de Grafos de distinto tipo.
// Posee tres secciones. 
//      -Editar:         Para la inclusion, modificacion y borrado de Nodos y Aristas
//      -Ver:  Para el mostrado de informacion y busqueda de caminos minimos
//      -Archivo:       Permite la creacion, carga y salvado de grafos tanto en archivos binarios como en tipo texto.

package Mapa.ui;

import Mapa.dominio.Nodo;
import Mapa.dominio.Arista;
import Mapa.dominio.GrafoPonderado;
import Mapa.dominio.Camino;

import Mapa.io.IOGrafo;

import java.util.Vector;
import java.util.Iterator;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableModel;

public class JVentana extends JFrame
{
    // Atributos de la Ventana

    public final static int ADD_DATA     = 0;
    public final static int REMOVE_DATA  = 1;
    public final static int UPDATE_DATA  = 2;
    public final static int SEARCH_DATA  = 3;

    public final static String[] tipoOperacion = {"Añadir","Eliminar","Actualizar","Buscar"};


    // Menu
    private JPanel              pnlCentral;
    private JPanel              pnlSur;

    private JMenuBar            mbBarra;

    private JMenu               menuArchivo;
    private JMenu               menuEditar;
    private JMenu               menuVer;


    // Botones de Menu

    private JMenuItem           mitNuevoGrafo;
    private JMenuItem           mitAbrirGrafo;
    private JMenuItem           mitGuardar;
    private JMenuItem           mitGuardarComo;
    // 

    private JMenuItem           mitNuevoNodo;
    private JMenuItem           mitBorrarNodo;

    private JMenuItem           mitNuevoArista;
    private JMenuItem           mitEditarArista;
    private JMenuItem           mitBorrarArista;

    private JMenuItem           mitBuscarArista;
    private JMenuItem           mitCalcularDistancia;
    private JMenuItem           mitListarGrafo;
    private JCheckBoxMenuItem   mitToggleDistancia;

    
        // Label    
    
    private JLabel              lblTitulo;
    
    private JTable              tblGrafo;
    private DefaultTableModel   tableModel;

    // variables que no forman parte de la nterfaz grafica

    private GrafoPonderado      grafo;
    private boolean             saved;
    private boolean             allDistances;
    private String              nameFile;



    public static void main(String[] args) {
        new JVentana();
    }

    public JVentana()
    {
        super("Grafos");

        this.grafo = null;
        this.nameFile = null;
        this.allDistances = false;
        this.saved = true;

        this.initComponents();
        this.manageEvents();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //this.setResizable(false);
        this.setVisible(true);

    }

    private void initComponents()
    {
        lblTitulo       =   JCreator.createLabel("Grafos");
        lblTitulo.setFont(new Font("Arial",Font.BOLD,24));
        lblTitulo.setPreferredSize(new Dimension(150,40));
        lblTitulo.setMaximumSize(new Dimension(150,40));
        lblTitulo.setMinimumSize(new Dimension(150,40));

        tableModel              = new DefaultTableModel();
        tblGrafo                = new JTable(tableModel);

        // Menus

        mbBarra                 = new JMenuBar();

        menuArchivo             = new JMenu("Archivo");
        menuEditar              = new JMenu("Editar");
        menuVer                 = new JMenu("Ver");

        mitNuevoGrafo           = new JMenuItem("Nuevo Grafo");
        mitAbrirGrafo           = new JMenuItem("Abrir Grafo");
        mitGuardar              = new JMenuItem("Guardar");
        mitGuardarComo          = new JMenuItem("Guardar Como");

        mitNuevoNodo            = new JMenuItem("Nuevo Nodo");
        mitBorrarNodo           = new JMenuItem("Borrar Nodo");
        mitNuevoArista          = new JMenuItem("Nuevo Arista");
        mitEditarArista         = new JMenuItem("Editar Arista");
        mitBorrarArista         = new JMenuItem("Borrar Arista");

        mitBuscarArista         = new JMenuItem("Buscar Arista");
        mitCalcularDistancia    = new JMenuItem("Calcular Distancia");
        mitListarGrafo          = new JMenuItem("Listar Grafo");
        mitToggleDistancia      = new JCheckBoxMenuItem("Mostar Distancia");

        // Menu Desplegabe Archivo
        menuArchivo.add(mitNuevoGrafo);
        menuArchivo.add(mitAbrirGrafo);
        menuArchivo.add(mitGuardar);
        menuArchivo.add(mitGuardarComo);
        mbBarra.add(menuArchivo);

        // Menu Desplegable Editar
        menuEditar.add(mitNuevoNodo);
        menuEditar.add(mitBorrarNodo);
        menuEditar.addSeparator();
        menuEditar.add(mitNuevoArista);
        menuEditar.add(mitEditarArista);
        menuEditar.add(mitBorrarArista);
        mbBarra.add(menuEditar);

        // Menu Desplegable Ver
        menuVer.add(mitBuscarArista);
        menuVer.add(mitCalcularDistancia);
        menuVer.add(mitListarGrafo);
        menuVer.add(mitToggleDistancia);
        mbBarra.add(menuVer);

        // Panel Norte - Menu

        //pnlNorte = new JPanel(new FlowLayout());
        //pnlNorte.add(mbBarra);

        pnlCentral = new JPanel(new FlowLayout());
        pnlCentral.add(lblTitulo);

        pnlSur = new JPanel(new FlowLayout());
        pnlSur.add(tblGrafo);

        // Ajustes finales
        this.setLayout(new BorderLayout());

        this.add(mbBarra,BorderLayout.NORTH);
        this.add(pnlCentral,BorderLayout.CENTER);
        this.add(pnlSur,BorderLayout.SOUTH);
    }

    private void manageEvents()
    {
        //Eventos

        // Botones de Archivo

        mitNuevoGrafo.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.createGrafo();
                }
            });
        mitAbrirGrafo.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.loadGrafo();
                }
            });
        mitGuardar.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.saveGrafo();
                }
            });
        mitGuardarComo.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.saveGrafoAs();
                }
            });


        // Botones de Editar
        mitNuevoNodo.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.operateNodo(JVentana.this.ADD_DATA);
                }
            });
        mitBorrarNodo.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.operateNodo(JVentana.this.REMOVE_DATA);
                }
            });
        mitNuevoArista.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.operateArista(JVentana.this.ADD_DATA);
                }
            });
        mitEditarArista.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.operateArista(JVentana.this.UPDATE_DATA);
                }
            });
        mitBorrarArista.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.operateArista(JVentana.this.REMOVE_DATA);
                }
            });

        // Botones de Ver

        mitBuscarArista.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.searchArista();
                }
            });
        mitCalcularDistancia.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.getPath();
                }
            });
        mitListarGrafo.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.listData();
                }
            });

        mitToggleDistancia.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JVentana.this.allDistances = mitToggleDistancia.isSelected();
                    JVentana.this.updateTable();
                }
            });

        
        

        this.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowOpened(WindowEvent e)
                {
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
                        if(!saved)
                        {
                            int option = JOptionPane.showConfirmDialog( JVentana.this,
                                                                        "Hay cambios sin guardar ¿Desea guardarlos?",
                                                                        "Archivo",
                                                                        JOptionPane.YES_NO_OPTION,
                                                                        JOptionPane.WARNING_MESSAGE); 
                            if(option == JOptionPane.YES_OPTION){
                                JVentana.this.saveGrafo();
                            }
                        }
                        System.exit(0);
                    }
                        
                }       


            });
    }

    private void operateNodo(int operation)
    {
        if(grafo == null) // Si no se ha definido el grafo
        {
            JOptionPane.showMessageDialog(  this, 
                                            "Grafo no definido, por favor cree o cargue un grafo antes de operar sobre él",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
        }else{
            String nombreNodo = JOptionPane.showInputDialog(this,"Por favor introduzca el nombre del nodo a "+tipoOperacion[operation]);
            if(nombreNodo != null && !nombreNodo.equals(""))
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
                        }else{
                            saved = false;
                            this.updateTable();
                        }
                        break;
                    case REMOVE_DATA:
                        if(!grafo.removeNodo(nodo)){
                            JOptionPane.showMessageDialog(  this, 
                                                            "Nodo no contenido en el grafo",
                                                            "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                        }else{
                            saved = false;
                            this.updateTable();
                        }
                        break;
                    case UPDATE_DATA:
                        // No debe alcanzarse, los nodos no son actualizables ya que son claves de un HashMap
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void operateArista(int operation)
    {
        if(grafo == null) // Si no se ha definido el grafo
        {
            JOptionPane.showMessageDialog(  this, 
                                            "Grafo no definido, por favor cree o cargue un grafo antes de operar sobre él",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
        }else{
            Arista arista = JAristaDialog.showInputDialog(  this,
                                                                    grafo.getNombreNodos(),
                                                                    operation,
                                                                    tipoOperacion[operation],
                                                                    "Por favor introduzca los Editar de la arista a "+tipoOperacion[operation],
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
                            }else{
                                saved = false;
                                this.updateTable();
                            }
                            break;
                        case REMOVE_DATA:
                            if(!grafo.removeArista(arista))
                            {
                                JOptionPane.showMessageDialog(  this, 
                                                                "Arista no contenida en el grafo",
                                                                "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                            }else{
                                saved = false;
                                this.updateTable();
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
                                saved = false;
                                this.updateTable();
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
        }

    }


    private void searchArista()
    {
        Arista arista = JAristaDialog.showInputDialog(  this,
                                                        grafo.getNombreNodos(),
                                                        SEARCH_DATA,
                                                        tipoOperacion[SEARCH_DATA],
                                                        "Por favor introduzca los Editar de la arista a buscar");
        if(arista != null)
        {
            try
            {
                double peso = grafo.getPeso(arista.getOrigen(),arista.getDestino());

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
            }catch(IllegalArgumentException e)
            {
                JOptionPane.showMessageDialog(  this, 
                                                "Arista no contenida en el grafo",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }

    private void getPath()
    {
        Arista arista = JAristaDialog.showInputDialog(  this,
                                                        grafo.getNombreNodos(),
                                                        SEARCH_DATA,
                                                        tipoOperacion[SEARCH_DATA],
                                                        "Por favor introduzca los Editar de los nodos entre los que desea hallar la distancia");
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
        
        JTextArea textArea = new JTextArea(grafo.toString());
        textArea.setColumns(30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setSize(textArea.getPreferredSize().width, 1);
        JOptionPane.showMessageDialog(  this, 
                                        textArea, 
                                        grafo.getNombre(), 
                                        JOptionPane.INFORMATION_MESSAGE);
         
    }


    private void createGrafo()
    {
        if(!saved)
        {
            int option = JOptionPane.showConfirmDialog( JVentana.this,
                                                        "¿Desea guardar los cambios realizados en el grafo actual?",
                                                        "Archivo",
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE); 
            if(option == JOptionPane.YES_OPTION){
                JVentana.this.saveGrafo();
            }
        }

        String nombre = JOptionPane.showInputDialog(  this,
                                                    "Por favor especifique el nombre del grafo",
                                                    "Nombre del Grafo",
                                                    JOptionPane.QUESTION_MESSAGE);
        if(nombre != null && !nombre.equals(""))
        {
            String unidades = JOptionPane.showInputDialog( this,
                                                        "Por favor especifique las unidades del grafo",
                                                        "Unidades del Grafo",
                                                        JOptionPane.QUESTION_MESSAGE);
            if(unidades != null && !unidades.equals(""))
            {
                this.grafo = new GrafoPonderado(nombre,unidades);   
                lblTitulo.setText(grafo.getNombre());
                saved = false;
                nameFile = null;
                this.updateTable();
            } 
        }
        
    }

    private void loadGrafo()
    {
        if(!saved)
        {
            int option = JOptionPane.showConfirmDialog( JVentana.this,
                                                        "¿Desea guardar los cambios realizados en el grafo actual?",
                                                        "Archivo",
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE); 
            if(option == JOptionPane.YES_OPTION){
                JVentana.this.saveGrafo();
            }
        }
        
        JFileChooser j = new JFileChooser(); 
        int rtn = j.showOpenDialog(this);
        if (rtn == JFileChooser.APPROVE_OPTION)
        {
            nameFile = j.getSelectedFile().getPath();
            grafo = (GrafoPonderado)IOGrafo.readFile(nameFile);
            if(grafo != null)
            {
                lblTitulo.setText(grafo.getNombre());  
                saved = true;
                this.updateTable();
            }
        }
    }

    private void saveGrafoAs()
    {
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
            nameFile = j.getSelectedFile().getPath();
            if(opcion == 0)
            {    
                IOGrafo.writeFile(grafo,nameFile);
                saved = true;
                
            }else{
                IOGrafo.writeTextFile(grafo,nameFile);
                saved = true;
            }
        }
        
    }

    private void saveGrafo()
    {
        if(nameFile == null)
        {
            this.saveGrafoAs();
        }else{
            IOGrafo.writeFile(grafo,nameFile);
            saved = true;
        }
    }

    private void updateTable()
    {   
        tableModel = new DefaultTableModel(){
            @Override
                public boolean isCellEditable(int row, int column) {
                   //all cells false
                   return false;
                }
        };

        Iterator<Nodo> itNodos = grafo.iteratorNodos();
        Vector<Nodo> nodos = new Vector<Nodo>();

        tableModel.addColumn(grafo.getUnidades());

        while(itNodos.hasNext())
        {
            Nodo nodo = itNodos.next();
            tableModel.addColumn(nodo.getNombre());
            nodos.add(nodo);
        }
        Vector<String> filaTitulo = new Vector<String>();
        filaTitulo.add(grafo.getUnidades());
        for( int i = 0 ; i < nodos.size() ; i++)
        {
            filaTitulo.add(nodos.get(i).getNombre());
        }
        tableModel.addRow(filaTitulo);

        for( int i = 1 ; i < nodos.size() ; i++)
        {
            Vector<String> fila = new Vector<String>();
            Nodo nodoFila = nodos.get(i);
            fila.add(nodoFila.getNombre());
            
            
            for( int j = 0 ; j < i ; j++)
            {
                Arista arista = new Arista(nodoFila,nodos.get(j));
                try
                {
                    Double peso;
                    if(allDistances)
                    {
                        peso = grafo.longitud(grafo.getShortestPath(arista.getOrigen(),arista.getDestino()));
                    }else
                    {
                        peso = grafo.getPeso(arista.getOrigen(),arista.getDestino());    
                    }
                    
                    fila.add(peso.toString());
                }catch(IllegalArgumentException e)
                {
                    fila.add(" - ");
                }
            }
            for( int j = i ; j < nodos.size() ; j++)
            {
                fila.add("");
            }

            tableModel.addRow(fila);
        }


        tblGrafo.setModel(tableModel);  
        tableModel.fireTableDataChanged();
        this.pack();
    }
}