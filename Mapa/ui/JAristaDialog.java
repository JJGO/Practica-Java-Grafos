// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//JAristaDialog.java

// Dialogo customizado para la obtencion de Aristas y AristasPonderadas
// Necesita de Arista, Arista Ponderada para la creacion de instancias
// Necesita de JVentana para las constantes de operacion que permiten distinguir el tipo de dato a introducir

package Mapa.ui;

import Mapa.ui.JVentana;

import Mapa.dominio.Nodo;
import Mapa.dominio.Arista;
import Mapa.dominio.AristaPonderada;

import java.util.Vector;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Frame;
import java.awt.Dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;





public class JAristaDialog extends JDialog
{
    private JLabel      lblMensaje;
    
    private JLabel      lblOrigen;
    private JLabel      lblDestino;
    private JLabel      lblPeso;
    
    private JComboBox   cbxOrigen;
    private JComboBox   cbxDestino;
    private JTextField  txtPeso;
    
    private JPanel      pnlOrigen;
    private JPanel      pnlDestino;
    private JPanel      pnlPeso;
    private JPanel      pnlBoton;
    
    private JButton     btnOperacion;

    private Arista      arista;

    private int         operation;



    public JAristaDialog(Frame parent,Vector<String> nodos,int operation, String operationName,String message,String magnitud)
    {
        super(parent,operationName + " Arista",Dialog.DEFAULT_MODALITY_TYPE);

        this.operation  = operation;

        lblMensaje      = JCreator.createLabel(message);

        lblOrigen       = JCreator.createLabel("Origen");
        lblDestino      = JCreator.createLabel("Destino");

        cbxOrigen       =  JCreator.createComboBox(nodos);
        cbxDestino      =  JCreator.createComboBox(nodos);

        btnOperacion    = JCreator.createBtn(operationName);
        
        pnlOrigen       = new JPanel(new FlowLayout());
        pnlOrigen.add(lblOrigen);
        pnlOrigen.add(cbxOrigen);

        pnlDestino      = new JPanel(new FlowLayout());
        pnlDestino.add(lblDestino);
        pnlDestino.add(cbxDestino);

        pnlBoton        = new JPanel(new FlowLayout());
        pnlBoton.add(btnOperacion);

        

        if(this.isReducedDialog())
        {
            this.setLayout(new GridLayout(3,1));
            this.add(pnlOrigen);
            this.add(pnlDestino);
            this.add(pnlBoton);
        }else{

            lblPeso = JCreator.createLabel(magnitud);
            txtPeso = JCreator.createTextField();

            pnlPeso = new JPanel(new FlowLayout());
            pnlPeso.add(lblPeso);
            pnlPeso.add(txtPeso);

            this.setLayout(new GridLayout(4,1));
            this.add(pnlOrigen);
            this.add(pnlDestino);
            this.add(pnlPeso);
            this.add(pnlBoton);
            
        }
        
        this.manageEvents();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
    }

    public void activate()
    {
        this.setVisible(true);
    }


    public static Arista showInputDialog(Frame parent, Vector<String> nodos, int operation,String operationName, String message,String magnitud)
    {
        JAristaDialog jad = new JAristaDialog(parent, nodos, operation, operationName, message, magnitud);
        jad.activate();
        return jad.getArista();
    }

    public static Arista showInputDialog(Frame parent, Vector<String> nodos, int operation,String operationName, String message)
    {
        return JAristaDialog.showInputDialog(parent, nodos, operation, operationName, message, "");
    }

    public Arista getArista()
    {
        return this.arista;
    }

    private Arista getDataArista()
    {
        String strOrigen    = (String)cbxOrigen.getSelectedItem();
        String strDestino   = (String)cbxDestino.getSelectedItem();

        Nodo origen         = new Nodo(strOrigen);
        Nodo destino        = new Nodo(strDestino);

        return new Arista(origen,destino);
    }

    private AristaPonderada getDataAristaPonderada()
    {
        Arista arista       = this.getDataArista();
        String strPeso      = txtPeso.getText();
        double peso         = Double.parseDouble(strPeso);    
        

        return new AristaPonderada(arista,peso);
    }

    private boolean isReducedDialog()
    {
        // En estos casos el dialogo no debe mostrar el textField de peso
        return (this.operation == JVentana.SEARCH_DATA || this.operation == JVentana.REMOVE_DATA);
    }

    private void manageEvents()
    {
        //Eventos
        // Botones de Datos
        btnOperacion.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try{
                        if(JAristaDialog.this.isReducedDialog())
                        {
                            JAristaDialog.this.arista = JAristaDialog.this.getDataArista();
                        }else{
                            JAristaDialog.this.arista = JAristaDialog.this.getDataAristaPonderada();
                        }
                        setVisible(false);
                        dispose();
                    }catch(Exception exception)
                    {
                        JOptionPane.showMessageDialog(  null, 
                                                        "Datos invalidos",
                                                        "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
            });

        cbxOrigen.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyPressed(KeyEvent e)
                {
                    Object o = e.getSource();
                    int keyCode = e.getKeyCode();
                    if(keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_TAB)
                    {
                        cbxDestino.requestFocus();
                    }
                    else if(keyCode == KeyEvent.VK_ESCAPE)
                    {
                        cbxOrigen.setSelectedIndex(0);
                    }
                }
            });

        cbxDestino.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyPressed(KeyEvent e)
                {
                    Object o = e.getSource();
                    int keyCode = e.getKeyCode();
                    if(keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_TAB)
                    {
                        if(JAristaDialog.this.isReducedDialog())
                        {
                            btnOperacion.requestFocus();
                        }else{
                            txtPeso.requestFocus();
                        }
                    }
                    else if(keyCode == KeyEvent.VK_ESCAPE)
                    {
                        cbxDestino.setSelectedIndex(0);
                    }
                }
            });

        if(!this.isReducedDialog()){
            txtPeso.addKeyListener(new KeyAdapter()
                {
                    @Override
                    public void keyPressed(KeyEvent e)
                    {
                        Object o = e.getSource();
                        int keyCode = e.getKeyCode();
                        if(keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_TAB)
                        {
                            btnOperacion.requestFocus();
                        }
                        else if(keyCode == KeyEvent.VK_ESCAPE)
                        {
                            txtPeso.setText("");
                        }
                    }
                });
        }
        

    }

}
    