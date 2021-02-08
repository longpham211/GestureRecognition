/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.DefaultParameterInterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.PopupMenu;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Long
 */
public class TopPanel extends JPanel{       
    private JMenuBar mainMenu = new JMenuBar();
    
    private JMenu fileMenu = new JMenu("File");
    private JMenu remoteMenu = new JMenu("Remote");
    
    private JMenuItem openFileMenuItem = new JMenuItem("Open Image");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    private JMenuItem activeRemoteMenuItem = new JMenuItem("Active Remote");
    private JMenuItem remoteSettingMenuItem = new JMenuItem("Remote Setting");
    
    public TopPanel(int width){
        setLayout(null);        
        //setBackground(Color.red);    
        setPreferredSize(new Dimension(width, DefaultParameterInterface.TOP_PANEL_HEIGHT));   
        
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        fileMenu.add(openFileMenuItem);        
        fileMenu.add(exitMenuItem);
        
        
        remoteMenu.add(activeRemoteMenuItem);
        remoteMenu.add(remoteSettingMenuItem);
        
        
        mainMenu.add(fileMenu);  
        mainMenu.add(remoteMenu);
        
        mainMenu.setSize(100, 26);        
        
        this.add(mainMenu);
        
        setLocationComponent((int)this.getPreferredSize().getHeight());

        
    }
    
    public void setLocationComponent(int parent_height){
        mainMenu.setLocation(0, (parent_height - mainMenu.getHeight())/2);
    }    
    
    public void addClickOpenFileMenuItemListener(ActionListener action){
        openFileMenuItem.addActionListener(action);
    }
    
    public void addClickExitMenuItemListener(ActionListener action){
        exitMenuItem.addActionListener(action);
    }    
    
    public void addClickRemoteSettingMenuItemListener(ActionListener action){
        remoteSettingMenuItem.addActionListener(action);
    }
    
    public void addClickUseRemoteMenuItemListener(ActionListener action){
        activeRemoteMenuItem.addActionListener(action);
    }
}
