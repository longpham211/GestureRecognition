/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Long
 */
public interface DefaultParameterInterface {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 550;
    public static final int BOTTOM_PANEL_HEIGHT = 60;
    public static final int TOP_PANEL_HEIGHT = 30;
    public static final int FRAME_WIDTH_MIN = 670;
    public static final int FRAME_HEIGHT_MIN = 500;
    public static final int DEFAULT_GESTURE_GREATER = 1;
    public static final int DEFAULT_GESTURE_SQUARE = 2;
    public static final int DEFAULT_GESTURE_RIGHT_ARROW = 3;
    public static final int DEFAULT_GESTURE_LEFT_ARROW = 4;
    public static final int DEFAULT_GESTURE_UP_ARROW = 5;
    public static final int DEFAULT_GESTURE_DOWN_ARROW = 6;
    public static final int DEFAULT_GESTURE_RIGHT_CIRCLE = 7;
    public static final int DEFAULT_GESTURE_LEFT_CIRCLE = 8;
    
            
    public static final String REMOTE_COMMAND_VIEW_IMAGE = "VIEW";
    public static final String[] imageType= new String[]{".jpg", ".png"};
    public static final String REMOTE_COMMAND_CREATE_GESTURE = "CREATE";
      
}
