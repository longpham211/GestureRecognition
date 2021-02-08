/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Model.Gesture;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Long
 */
public class ManageRemotePanel extends JPanel{
    private JButton nextImageButton;
    private JButton previousImageButton;
    private JButton rotateClockwiseButton;
    private JButton rotateAnticlockwiseButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton useUserGesturesButton;
    private JButton useDefaultGesturesButton;
    private JButton createUserGesturesButton;
    private JButton okButton;     
    
    public ManageRemotePanel(){
        setOriginalDefault();
        
    }
    private void setOriginalDefault(){
        
        this.setSize(670, 410);
        this.setLayout(null);

        
        
        nextImageButton = new JButton();
        previousImageButton = new JButton();
        rotateClockwiseButton = new JButton();
        rotateAnticlockwiseButton = new JButton();
        zoomInButton = new JButton();
        zoomOutButton = new JButton();
        useUserGesturesButton = new JButton("Use User's Gestures");
        useDefaultGesturesButton = new JButton("Use Default Gestures");
        createUserGesturesButton = new JButton("Create User's Gestures");       
        okButton = new JButton("OK");
        
        nextImageButton.setBackground(Color.white);
        previousImageButton.setBackground(Color.white);
        rotateClockwiseButton.setBackground(Color.white);
        rotateAnticlockwiseButton   .setBackground(Color.white);       
        zoomInButton.setBackground(Color.white);          
        zoomOutButton.setBackground(Color.white);    
        
                  
        JLabel nextImageLabel = new JLabel("Next Image");
        JLabel previousImageLabel = new JLabel("Previous Image");
        JLabel rotateClockwiseLabel = new JLabel("Rotate Clockwise");
        JLabel rotateAnticlockwiseLabel = new JLabel("Rotate Anticlockwise");
        JLabel zoomInLabel = new JLabel("Zoom In");
        JLabel zoomOutLabel = new JLabel("Zoom Out"); 
        JLabel manageRemoteSetting = new JLabel("Manage Remote Setting");
        
        manageRemoteSetting.setFont(new Font("Comic San MS", Font.BOLD, 20));
        this.add(nextImageButton);
        this.add(previousImageButton);
        this.add(rotateClockwiseButton);
        this.add(rotateAnticlockwiseButton);
        this.add(zoomInButton);
        this.add(zoomOutButton);
        this.add(useUserGesturesButton);
        this.add(useDefaultGesturesButton);
        this.add(createUserGesturesButton);
        this.add(okButton);
        this.add(nextImageLabel);
        this.add(previousImageLabel);
        this.add(rotateClockwiseLabel);
        this.add(rotateAnticlockwiseLabel);
        this.add(zoomInLabel);
        this.add(zoomOutLabel); 
        this.add(manageRemoteSetting);       
        
        nextImageButton.setBounds(150, 70, 110, 40);
        previousImageButton.setBounds(490, 70, 110, 40);
        rotateClockwiseButton.setBounds(150, 123, 110, 40);
        rotateAnticlockwiseButton.setBounds(490, 120, 110, 40);
        zoomInButton.setBounds(150, 170, 110, 40);
        zoomOutButton.setBounds(490, 170, 110, 40);
        useUserGesturesButton.setBounds(110, 260, 170, 40);         
        useDefaultGesturesButton.setBounds(110, 260, 170, 40);          
        createUserGesturesButton.setBounds(400, 260, 170, 40);    
        okButton.setBounds(310, 330, 60, 40);
        nextImageLabel.setBounds(60, 80, 90, 20);          
        previousImageLabel.setBounds(380, 80, 100, 20);          
        rotateClockwiseLabel.setBounds(25, 130, 110, 20);          
        rotateAnticlockwiseLabel.setBounds(350, 130, 130, 20);          
        zoomInLabel.setBounds(80, 180, 70, 20);          
        zoomOutLabel.setBounds(415, 180, 70, 20);    
        manageRemoteSetting.setBounds(240, 10, 260, 30);
        
    }
    
    public void displayComponents(Object input){      
        if(input instanceof String){
            nextImageButton.setText("");
            previousImageButton.setText("");
            rotateClockwiseButton.setText("");
            rotateAnticlockwiseButton.setText("");
            zoomInButton.setText("");
            zoomOutButton.setText("");
            nextImageButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\next image.png"));
            previousImageButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\previous image.png"));
            rotateClockwiseButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\rotate clockwise image.png"));
            rotateAnticlockwiseButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\rotate anticlockwise image.png"));
            zoomInButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\zoom in image.png"));
            zoomOutButton.setIcon(new ImageIcon("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\zoom out image.png"));
            useDefaultGesturesButton.setVisible(false);
            useUserGesturesButton.setVisible(true);
        }
        else {
            nextImageButton.setIcon(null);
            previousImageButton.setIcon(null);
            rotateClockwiseButton.setIcon(null);
            rotateAnticlockwiseButton.setIcon(null);
            zoomInButton.setIcon(null);
            zoomOutButton.setIcon(null);
            
            Gesture[] gestures = (Gesture[]) input;
            nextImageButton.setText(gestures[0].getName());
            previousImageButton.setText(gestures[1].getName());
            rotateClockwiseButton.setText(gestures[2].getName());
            rotateAnticlockwiseButton.setText(gestures[3].getName());
            zoomInButton.setText(gestures[4].getName());
            zoomOutButton.setText(gestures[5].getName());
            useDefaultGesturesButton.setVisible(true);
            useUserGesturesButton.setVisible(false);
            
        }
    }
    
    public void addCreateUserGesturesListener(ActionListener action){
        createUserGesturesButton.addActionListener(action);
    }
    
    public void addUseUserGesturesListener(ActionListener action){
        useUserGesturesButton.addActionListener(action);
    }
    
    public void addUseDefaultGesturesListener(ActionListener action){
        useDefaultGesturesButton.addActionListener(action);
    }
    public void addClickOKListener(ActionListener action){
        okButton.addActionListener(action);
    }
}



