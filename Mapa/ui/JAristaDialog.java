// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//JAristaDialog.java

// Dialogo customizado para la obtencion de Aristas y AristasPonderadas
// Necesita de Arista, Arista Ponderada para la creacion de instancias
// Necesita de JVentana para las constantes de operacion que permiten distinguir el tipo de dato a introducir

package Mapa.ui;

import Mapa.dominio.Arista;
import Mapa.dominio.AristaPonderada;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class JAristaDialog extends JDialog
{
     private JLabel lblTitulo;

     private JLabel lblOrigen;
     private JLabel lblDestino;
     private JLabel lblPeso;

     private JComboBox   cbxOrigen;
     private JComboBox   cbxDestino;
     private JTextField  txtPeso;

     private JButton     btnOperacion;
    // private JButton     btnCancelar;

    private AristaPonderada arista;

    private int operation;


    public JAristaDialog(Frame parent,Vector nodos,int operation, String operationName,String message,String magnitud)
    {
        super(parent,"Arista");     //Ventana padre

        this.operation = operation;

        lblMensaje = JCreator.createLabel(message);

        lblOrigen = JCreator.createLabel("Origen");
        lblDestino = JCreator.createLabel("Destino");

        cbxOrigen = new JComboBox(nodos);
        cbxDestino = new JComboBox(nodos);

        btnOperacion = JCreator.createBtn(operationName);
        
        FlowLayout origenLayout = new FlowLayout();
        origenLayout.add(lblOrigen);
        origenLayout.add(cbxOrigen);

        FlowLayout destinoLayout = new FlowLayout();
        destinoLayout.add(lblDestino);
        destinoLayout.add(cbxDestino);


        FlowLayout btnLayout = new FlowLayout();
        btnLayout.add(btnOperacion);

        if(this.isReducedDialog())
        {
            this.setLayout(new GridLayout(3,1));
            this.add(origenLayout);
            this.add(destinoLayout);
            this.add(btnLayout);
        }else{

            lblPeso = JCreator.createLabel(magnitud);
            txtPeso = JCreator.createTextField();

            FlowLayout pesoLayout = new FlowLayout();
            pesoLayout.add(lblPeso);
            pesoLayout.add(txtPeso);

            this.setLayout(new GridLayout(4,1));
            this.add(origenLayout);
            this.add(destinoLayout);
            this.add(pesoLayout);
            this.add(btnLayout);
        }
        
        this.manageEvents();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public Arista getArista()
    {
        return this.arista;
    }

    private AristaPonderada getDataAristaPonderada()
    {
        Arista arista       = this.getDataArista();
        String strPeso      = txtPeso.getText();
        double peso         = Double.parseDouble(strPeso);

        return new AristaPonderada(arista,peso);
    }

    private Arista getDataArista()
    {
        String strOrigen    = (String)cbxOrigen.getSelectedItem();
        String strDestino   = (String)cbxDestino.getSelectedItem();

        Nodo origen         = new Nodo(strOrigen);
        Nodo destino        = new Nodo(strDestino);

        return new Arista(origen,destino);
    }

    public static Arista showInputDialog(Frame parent, Vector nodos, int operation,String operationName, String message,String magnitud)
    {
        JAristaDialog jad = new JAristaDialog(parent, nodos, operation, operationName, message, magnitud);
        return jad.getArista();

    }

    public static Arista showInputDialog(Frame parent, Vector nodos, int operation,String operationName, String message)
    {
        return this.showInputDialog(parent, nodos, operation, operationName, message, "");
    }

    public boolean isReducedDialog()
    {
        // En estos casos el dialogo no debe mostrar el textField de peso
        return (this.operation == JVentana.SEARCH_DATA || this.operation == JVentana.DELETE_DATA);
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
                    if(this.isReducedDialog())
                    {
                        this.arista = getDataArista();
                    }else{
                        this.arista = getDataAristaPonderada();
                    }
                    setVisible(false);
                    dispose();
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
                        if(this.isReducedDialog())
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
    