/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JPanel;
import Model.DefaultParameterInterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Long
 */
public class BottomPanel extends JPanel{
    private JButton rotateLeftButton = new JButton();
    private JButton rotateRightButton = new JButton();
    private JButton nextImageButton = new JButton();
    private JButton previousImageButton = new JButton();
    private JButton fullScreen = new JButton("Full Screen");

    public JButton getRotateLeftButton() {
        return rotateLeftButton;
    }

    public JButton getRotateRightButton() {
        return rotateRightButton;
    }

    public JButton getNextImageButton() {
        return nextImageButton;
    }

    public JButton getPreviousImageButton() {
        return previousImageButton;
    }
    
    
    private JPanel controlPanel = new JPanel(null);
    
    public BottomPanel(int width){
        setLayout(null);        
        setBackground(Color.orange); 
        setPreferredSize(new Dimension(width, DefaultParameterInterface.BOTTOM_PANEL_HEIGHT));
        
        add(controlPanel);
        
        controlPanel.setSize(400, 60);
        setLocationComponent(width, DefaultParameterInterface.BOTTOM_PANEL_HEIGHT);
        //controlPanel.setLocation((width - controlPanel.getWidth())/2, (DefaultParameter.BOTTOM_PANEL_HEIGHT - controlPanel.getHeight())/2);
        controlPanel.setBackground(Color.orange);
        
        controlPanel.setLayout(new FlowLayout());
        rotateLeftButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\rotate anticlockwise image.png"));
        rotateRightButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\rotate clockwise image.png"));
        nextImageButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\next image.png"));
        previousImageButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\previous image.png"));
        controlPanel.add(rotateLeftButton);
        controlPanel.add(previousImageButton);       
        controlPanel.add(nextImageButton);
        controlPanel.add(rotateRightButton);
        
        rotateLeftButton.setBackground(Color.white);
        rotateRightButton.setBackground(Color.white);
        nextImageButton.setBackground(Color.white);
        previousImageButton.setBackground(Color.white);
        rotateLeftButton.setEnabled(false);
        previousImageButton.setEnabled(false);
        nextImageButton.setEnabled(false);
        rotateRightButton.setEnabled(false);      
    }
    
    public void setEnableButton(){
        rotateLeftButton.setEnabled(true);
        previousImageButton.setEnabled(true);
        nextImageButton.setEnabled(true);
        rotateRightButton.setEnabled(true);              
    }
    public void setLocationComponent(int parent_width, int parent_height){
        controlPanel.setLocation((parent_width - controlPanel.getWidth())/2, (parent_height - controlPanel.getHeight())/2);
    }
    
    public void addClickRotateLeftButtonListener(ActionListener action){
        rotateLeftButton.addActionListener(action);
        
    }

    public void addClickRotateRightButtonListener(ActionListener action) {
        rotateRightButton.addActionListener(action);
    }
    
    public void addClickNextImageButton(ActionListener action){
        nextImageButton.addActionListener(action);
    }
    
    public void addClickPreviousImageButton(ActionListener action){
        previousImageButton.addActionListener(action);
    }    
}
