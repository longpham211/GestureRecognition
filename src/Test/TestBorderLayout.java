/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Long
 */
public class TestBorderLayout  extends JFrame{
    public TestBorderLayout(){
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.setPreferredSize(new Dimension(300, 200));
        panel3.setPreferredSize(new Dimension(400, 200));
        panel1.setBackground(Color.red);
        panel2.setBackground(Color.green);
        panel3.setBackground(Color.blue);
        
        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);
        this.add(panel3, BorderLayout.SOUTH);
        
        setVisible(true);
        setSize(800,600);
    }
    
    public static void main(String[] args){
        new TestBorderLayout();
    }
}
