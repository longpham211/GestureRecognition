/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.awt.AWTException;
import java.awt.Robot;

/**
 *
 * @author Long
 */
public class TestMouse {
    public static void main(String[] args) throws AWTException{
            Robot robot = new Robot(); 
            robot.mouseMove(900, 550);    
    }
    
}
