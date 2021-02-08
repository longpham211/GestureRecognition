/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controll.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Long
 */
public class MiddlePanel extends JPanel {
    private BufferedImage img;
    private int zoom = 0;
    private boolean zoomActive = false;

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
        this.repaint();
    }
    
    public MiddlePanel(int width){        
		
        img = getImage("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\images.png");                         
        
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        Graphics2D g2d = ((Graphics2D) g);
        
        int scaledWidth, scaledHeight = getHeight();        
        scaledWidth = (int)((img.getWidth() * getHeight()/img.getHeight()));
        
        if(scaledWidth > getWidth()){
            scaledWidth = getWidth();
            scaledHeight = (int)(img.getHeight()*getWidth())/img.getWidth();
        }
        
       int leftOffset = Math.abs(getWidth()/2-scaledWidth/2);
       int rightOffset = getWidth()/2 + scaledWidth/2;
       int topOffset = Math.abs(getHeight()/2 - scaledHeight/2);
       int bottomOffset = getHeight()/2 + scaledHeight/2;
       if(!zoomActive)
         g2d.drawImage(img, leftOffset, topOffset , rightOffset, bottomOffset ,0, 0, img.getWidth(), img.getHeight(), null);      
       
       if(zoomActive){
            int width2 = (int) (scaledWidth + scaledWidth * (zoom / 500d));
            int height2 = (int) (scaledHeight + scaledHeight * (zoom / 500d));   
            int x1 = leftOffset - ((width2 - scaledWidth)/2);
            int y1 = topOffset - ((height2 - scaledHeight)/2);
            Image scaledInstance = img.getScaledInstance(width2, height2, Image.SCALE_SMOOTH); 
            g2d.drawImage(scaledInstance, x1, y1, null);
       }
    }
    
    private BufferedImage getImage(String filename) {
        try {

            File inputFile = new File(filename);
            InputStream in = new FileInputStream(inputFile);
            
            return ImageIO.read(in);
        }catch (IOException e) {
            System.out.println("The image was not loaded.");
        }
        return null;
    }   

    public void addZoomImageListener(MouseWheelListener action) {
         this.addMouseWheelListener(action);
    }
    public void activeZoom(int zoom){
        zoomActive = true;
        this.zoom = zoom;
    }
    public void deactiveZoom(){
        zoomActive = false;
        zoom = 0;
    }
        
}
