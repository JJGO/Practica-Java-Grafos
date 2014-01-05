// Jose Javier Gonzalez Ortiz
// Proyecto Final de Programacion Orientada a Objetos
// 23/12/2013

// JCreator.java

// Clase para la rapida creacion y customizacion de etiquetas, campos de texto y botones predefinidos.

package Mapa.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Vector;

public class JCreator{

    public static JLabel createLabel(String name)
    {
        JLabel lbl = new JLabel(name);
        lbl.setFont(new Font("Arial",Font.BOLD,12));
        //lbl.setHorizontalAlignment(SwingConstants.LEFT);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setPreferredSize(new Dimension(60,20));
        lbl.setMaximumSize(new Dimension(60,20));
        lbl.setMinimumSize(new Dimension(60,20));
        return lbl;

    }

    public static JTextField createTextField()
    {
        JTextField txt = new JTextField();
        txt.setBackground( new Color(46,82,180));
        txt.setForeground(Color.WHITE);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setPreferredSize(new Dimension(100,20));
        txt.setMaximumSize(new Dimension(100,20));
        txt.setMinimumSize(new Dimension(100,20));
        txt.setFont(new Font("Arial",Font.PLAIN,11));
        return txt;

    }

    public static JButton createBtn(String name)
    {
        JButton btn = new JButton(name);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JComboBox createComboBox(Vector<String> vector)
    {
        JComboBox cbx = new JComboBox(vector);
        cbx.setPreferredSize(new Dimension(100,20));
        cbx.setMaximumSize(new Dimension(100,20));
        cbx.setMinimumSize(new Dimension(100,20));
        return cbx;
    }
}