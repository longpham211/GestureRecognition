/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Run;

import Controll.Controller;
import View.MainFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author Long
 */
public class Run {
    public static void main(String args[]){
        MainFrame view = new MainFrame();
        Controller controller = new Controller(view);        
    }   
}
