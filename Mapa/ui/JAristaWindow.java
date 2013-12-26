// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

//JVentana.java

package Mapa.ui;

import Mapa.dominio.Arista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class JAristaWindow extends JFrame
{

    JLabel lblOrigen;
    JLabel lblDestino;
    JLabel lblPeso;

    JComboBox   cbxOrigen;
    JComboBox   cbxDestino;
    JTextField  txtPeso;

    JButton     btnAceptar;
    JButton     btnCancelar;

    public static Arista showInputDialog(Digrafo digrafo,String magnitud)
    {
        lblOrigen = new JLabel("Origen");
        lblDestino = new JLabel("Destino");
        lblPeso = new JLabel(magnitud);

        cbxOrigen = new JComboBox(digrafo.getNodos());
        cbxDestino = new JComboBox(digrafo.getNodos());
        
        FlowLayout origenLayout = new FlowLayout();
        origenLayout.add(lblOrigen);
        origenLayout.add(cbxOrigen);

        FlowLayout destinoLayout = new FlowLayout();
        destinoLayout.add(lblDestino);
        destinoLayout.add(cbxDestino);

        FlowLayout pesoLayout = new FlowLayout();
        pesoLayout.add(lblPeso);
        pesoLayout.add(txtField);

        FlowLayout btnLayout = new FlowLayout();
        btnLayout.add(btnAceptar);
        btnLayout.add(btnCancelar);

        this.setLayout(new GridLayout(4,1));
        this.add(origenLayout);
        this.add(destinoLayout);
        this.add(pesoLayout);
        this.add(btnLayout);

        




    }
}

String datos[] = { “Uno”, “Dos”, “Tres”, “Cuatro”, “Cinco”};
JComboBox lista = new JComboBox (datos);
lista.setBorder(BorderFactory.createLineBorder(Color.red,4));