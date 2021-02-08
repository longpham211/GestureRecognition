/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JFrame;
import Model.DefaultParameterInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

/**
 *
 * @author Long
 */
public class MainFrame extends JFrame {
    private BottomPanel bottomPanel;
    private TopPanel topPanel;
    private MiddlePanel middlePanel;
    private ManageRemotePanel manageRemotePanel;
    private JPanel emptyPanel;
    
    
    
    public MainFrame(){
        super("Remote Image Viewer");
        setOriginalDefault();
        
        addComponentPanel();
        
    }
    
    public void setOriginalDefault(){
        setSize(DefaultParameterInterface.WIDTH, DefaultParameterInterface.HEIGHT);
        setBackground(Color.white);
        setVisible(true);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screensize.width - DefaultParameterInterface.WIDTH)/2, (screensize.height - DefaultParameterInterface.HEIGHT)/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.setMinimumSize(new Dimension(DefaultParameterInterface.FRAME_WIDTH_MIN,DefaultParameterInterface.FRAME_HEIGHT_MIN));
    }    
    
    public void addComponentPanel(){
        manageRemotePanel = new ManageRemotePanel();
        topPanel = new TopPanel(getWidth());
        middlePanel = new MiddlePanel(getWidth());
        bottomPanel = new BottomPanel(getWidth());
        
        emptyPanel = new JPanel(null);
        emptyPanel.add(manageRemotePanel);
       
        this.add(topPanel, BorderLayout.NORTH);             
        this.add(bottomPanel, BorderLayout.SOUTH);  
        this.add(middlePanel, BorderLayout.CENTER); 

       
        pack();
  
    }

    public void setEmptyPanelComponentLocation(){       
        manageRemotePanel.setLocation((this.getWidth()-manageRemotePanel.getWidth())/2, (this.getHeight() - manageRemotePanel.getHeight()-50)/2);        
    }
    public BottomPanel getBottomPanel() {
        return bottomPanel;
    }
    
    public void setBottomPanel(BottomPanel bottomPanel) {
        this.bottomPanel = bottomPanel;
    }

    public TopPanel getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(TopPanel topPanel) {
        this.topPanel = topPanel;
    }   

    public MiddlePanel getMiddlePanel() {
        return middlePanel;
    }

    public void setMiddlePanel(MiddlePanel middlePanel) {
        this.middlePanel = middlePanel;
    }
    
    public ManageRemotePanel getManageRemotePanel(){
        return manageRemotePanel;
    }
    
    public void addResizeComponentListener(ComponentListener frame){
        this.addComponentListener(frame);
    }    

    public void displayRemoteSettingPanel(Object input) {
        this.remove(middlePanel);
        this.manageRemotePanel.displayComponents(input); 
        this.add(emptyPanel, BorderLayout.CENTER);
        setEmptyPanelComponentLocation();
        bottomPanel.setVisible(false);
        topPanel.setVisible(false);
    }
    
    public void displayNormaly(){
        this.remove(emptyPanel);
        this.add(middlePanel);
        bottomPanel.setVisible(true);
        topPanel.setVisible(true);
    }
}
