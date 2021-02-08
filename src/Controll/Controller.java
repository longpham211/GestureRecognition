/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import DTWAlgorithm.DTW;
import Database.TakeDefaultData;
import Model.AccSet;
import Model.Data;
import Model.DefaultParameterInterface;
import Model.Gesture;

import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.values.RawAcceleration;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

/**
 *
 * @author Long
 */
public class Controller {
    private View.MainFrame view ;
    private String fileName = new String("C:\\Users\\Long\\Dropbox\\Thesis\\Thesis\\src\\Image\\images.png");
    private File[] listOfFile;
    private Data[] listOfData;
    private Wiimote wiimote;
    private String wiimoteCommand = DefaultParameterInterface.REMOTE_COMMAND_VIEW_IMAGE;
    private int zoom = 0;
    private String remoteMode;
    private int countCreateGestureData;
    private double[][] anArray = new double[0][3];  
    private String[] timeArray = new String[0];    
    
    
    public Controller(View.MainFrame view){
        this.view = view;
        this.view.getMiddlePanel().setImg(getImage(fileName));
        listOfFile = getAllFileOfCurrentDirectory(fileName);
        
        this.view.getBottomPanel().addClickRotateLeftButtonListener(new ClickRotateLeftButtonListener());
        this.view.getBottomPanel().addClickRotateRightButtonListener(new ClickRotateRightButtonListener());
        this.view.getBottomPanel().addClickNextImageButton(new ClickNextImageButtonListener());
        this.view.getBottomPanel().addClickPreviousImageButton(new ClickPreviousImageButtonListener());
        
        this.view.getTopPanel().addClickOpenFileMenuItemListener(new ClickOpenFileMenuItemListener());
        this.view.getTopPanel().addClickExitMenuItemListener(new ClickExitMenuItemListener());
        this.view.getTopPanel().addClickRemoteSettingMenuItemListener(new ClickRemoteSettingMenuItemListener());
        this.view.getTopPanel().addClickUseRemoteMenuItemListener(new ClickUseRemoteMenuItemListener());
        
        this.view.addResizeComponentListener(new ResizeComponentListener());   
        
        this.view.getMiddlePanel().addZoomImageListener(new ZoomImageListener());
        
        this.view.getManageRemotePanel().addClickOKListener(new ClickManageRemotePanelOKButton());
        this.view.getManageRemotePanel().addUseDefaultGesturesListener(new UseDefaultGesturesListener());
        this.view.getManageRemotePanel().addUseUserGesturesListener(new UseUserGesturesListener());
        this.view.getManageRemotePanel().addCreateUserGesturesListener(new CreateUserGesturesListener());
    }
    
    private File[] getAllFileOfCurrentDirectory(String fileName){
        File folder = new File(fileName).getParentFile();        
        File[] listOfFiles = folder.listFiles(new MyFilter()); 
        for (int i = 0; i < listOfFiles.length; i++) 
            System.out.println(listOfFiles[i].getAbsolutePath());            
        
        return listOfFiles;
    }
    
    private File getNextImage(String currentImageName){
        if(listOfFile!= null && listOfFile.length > 0){
                if(listOfFile[listOfFile.length - 1].getAbsolutePath().equalsIgnoreCase(currentImageName))
                    return listOfFile[0];
                for(int i = 0 ; i  < listOfFile.length - 1 ; i ++)
                    if(listOfFile[i].getAbsolutePath().equalsIgnoreCase(currentImageName))
                        return (listOfFile[i+1]);  
            }         
        return null;
    } 

    private File getPreviousImage(String currentImageName){
        if(listOfFile!= null && listOfFile.length > 0){
                if(listOfFile[0].getAbsolutePath().equalsIgnoreCase(currentImageName))
                    return listOfFile[listOfFile.length - 1];
                for(int i = 1 ; i  < listOfFile.length ; i ++)
                    if(listOfFile[i].getAbsolutePath().equalsIgnoreCase(currentImageName))
                        return (listOfFile[i-1]);  
            }         
        return null;
    } 
    
        
    private BufferedImage rotate90ToLeft( BufferedImage inputImage ){
	int width = inputImage.getWidth();
	int height = inputImage.getHeight();
	BufferedImage returnImage = new BufferedImage( height, width , inputImage.getType()  );

	for( int x = 0; x < width; x++ )
            for( int y = 0; y < height; y++ ) 
                returnImage.setRGB(y, width-x-1, inputImage.getRGB(x, y));              
           
	return returnImage;
    }

    private BufferedImage rotate90ToRight( BufferedImage inputImage ){
	int width = inputImage.getWidth();
	int height = inputImage.getHeight();
	BufferedImage returnImage = new BufferedImage( height, width , inputImage.getType()  );

	for( int x = 0; x < width; x++ )
            for( int y = 0; y < height; y++)
                returnImage.setRGB(height - y - 1, x, inputImage.getRGB(x, y));        
	return returnImage;
    }
    
    private BufferedImage getImage(String filename) {
        try {

            File inputFile = new File(filename);
            InputStream in = new FileInputStream(inputFile);
            
            return ImageIO.read(in);
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null, "The image was not loaded.");
        }
        return null;
    }    
    
    private Connection dbConnect() {
        Connection con = null;
        String driver = "com.mysql.jdbc.Driver";
        String dbName = "Thesis_new";
        String username = "root";
        String password = "123456";
        String url = "jdbc:mysql://127.0.0.1:3306/" + dbName;

        try{
            Class.forName(driver);
            con = (Connection)DriverManager.getConnection(url,username,password);
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }            

        return con;    
    }
    private void takeAllData(String mode) {
        Connection con = dbConnect();
        ArrayList<Data> dataList = new ArrayList<Data>();
        Data aData;
        AccSet accSet;
        Gesture aGesture;
        ArrayList<AccSet> accSetList;
        ArrayList<Gesture> gestureList = new ArrayList<Gesture>();

        String query = "SELECT * FROM GESTURE WHERE mode = '" + mode + "'";   
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                aGesture = new Gesture();
                aGesture.setIdGesture(rs.getInt(1));
                aGesture.setName(rs.getString(2));
                gestureList.add(aGesture);
            }

            for(int j = 0 ; j < gestureList.size(); j ++){
                query = "SELECT * FROM Data WHERE GestureID = '" + gestureList.get(j).getIdGesture() + "'";

                rs = stmt.executeQuery(query);

                while(rs.next()){
                    aData = new Data();
                    aData.setIdData(rs.getInt(1));
                    aData.setGesture(new Gesture(rs.getInt(2)));
                    aData.setNumberOfAcc(rs.getInt(3));

                    dataList.add(aData);
                }

                for(int i = 0; i < dataList.size(); i ++ ){
                    query  = "SELECT * FROM AccSet WHERE dataID = '" + dataList.get(i).getIdData() + "'";                    
                    rs = stmt.executeQuery(query);
                    accSetList = new ArrayList<AccSet>();

                    while(rs.next()){
                        accSet = new AccSet(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6));                        
                        accSetList.add(accSet);
                    }

                    dataList.get(i).setAccSet(accSetList.toArray(new AccSet[accSetList.size()]));
                }

            }

            listOfData = new Data[dataList.size()];
            dataList.toArray(listOfData);
            System.out.println(dataList.size());

        } catch (SQLException ex) {
            System.out.println("SQL EXception");
            System.out.println(ex.getMessage());
        }
    }    
    
    private Gesture[] getGesturesArray(){
        Connection con = dbConnect();
        String query = "SELECT * FROM GESTURE WHERE MODE = 'USER'";
        Gesture[] gesturesArray = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Gesture> gesturesList = new ArrayList<Gesture>();

            while(rs.next())                        
                gesturesList.add(new Gesture(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));

            gesturesArray = new Gesture[gesturesList.size()];
            gesturesList.toArray(gesturesArray);

        } catch (SQLException ex) {
            System.out.println("SQLException at ClickRemoteSettingMenuItemListener");
            System.out.println(ex.getMessage());
        }    
        return gesturesArray;
    }

    public int takeMinimumIDFromTable(String tableName){
        String idName = null ;
        if(tableName.equalsIgnoreCase("Gesture"))
            idName = "idGesture";
        else if(tableName.equalsIgnoreCase("Data"))
            idName = "idData";
        else if(tableName.equalsIgnoreCase("AccSet"))
            idName = "idAccSet";
        Connection con = dbConnect();
        String query = "SELECT " + idName + " FROM " + tableName;        
        try{
            com.mysql.jdbc.Statement stmt = (com.mysql.jdbc.Statement) con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(query);
            
            rs.last();
            int numberOfColumn = rs.getRow();
            rs.beforeFirst();
            if(numberOfColumn == 0)
                return 1;
            
            int[] idTable = new int[numberOfColumn];
            
            for(int i = 0 ; rs.next(); i++)
                idTable[i] = rs.getInt(1);
            int maxID = 1;
            
            for(int i= 0 ; i < idTable.length; i ++)
                if(idTable[i] > maxID)
                    maxID = idTable[i];
            int[] checkArray = new int[maxID+1];
            int i ;
            for(i = 0 ; i < idTable.length; i ++)
                checkArray[idTable[i]] = 1;
            for(i = 1 ; i<= maxID; i ++)
                if(checkArray[i] == 0)
                    return i;
            return i;       
            
        }
        catch(SQLException ex){
            System.out.println("SQL Exception attakeMinimumIDFromTable " + tableName);
            System.out.println(ex.getMessage());
        }
        return -1;        
    }
    
    private class CreateUserGesturesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isUserGesturesExist = false;
            Connection con = dbConnect();
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT idGesture FROM Gesture WHERE mode = 'USER'");
                if(rs.next())
                    isUserGesturesExist = true;
            } catch (SQLException ex) {
                System.out.println("SQLExcpetion in CreateUSerGesturesListener");
                System.out.println(ex.getMessage());
            }
            finally {
                try {con.close();} catch (SQLException ex) {}
            }
            int confirmCreateGesture = -2;
            if(isUserGesturesExist)
                confirmCreateGesture = JOptionPane.showConfirmDialog(null, "When create new user gestures, the old gestures in database will be lost. Do you want to continue?");
            if(!isUserGesturesExist || (confirmCreateGesture == JOptionPane.YES_OPTION)){  
                String[] gesturesName = new String[6];
                ArrayList<ArrayList<AccSet>> accSetListList = new ArrayList<ArrayList<AccSet>>();
                ArrayList<AccSet> accSetOfAData;
                AccSet anAccSet;
                String gestureName;
                countCreateGestureData = 0;
                int countCreateDataTemp = 0;
                wiimoteCommand = DefaultParameterInterface.REMOTE_COMMAND_CREATE_GESTURE;
                
                outerLoop:
                for(int i = 0, k = 0; i < gesturesName.length; i++){
                    gestureName = null;
                    if(i==0)
                        gestureName = JOptionPane.showInputDialog(null, "Enter the name of gesture for functions NEXT IMAGE:");
                    else if (i==1)
                        gestureName = JOptionPane.showInputDialog(null, "Enter the name of gesture for functions PREVIOUS IMAGE:");
                    else if (i==2)
                        gestureName = JOptionPane.showInputDialog(null, "Enter the name of gesture for functions ROTATE CLOCKWISE IMAGE:");
                    else if (i==3)
                        gestureName = JOptionPane.showInputDialog(null, "Enter the name of gesture for functions ROTATE ANTICLOCKWISE IMAGE:");
                    else if (i==4)
                        gestureName = JOptionPane.showInputDialog(null, "Enter the name of gesture for functions ZOOM IN:");
                    else
                        gestureName = JOptionPane.showInputDialog(null, "Enter the name of gesture for functions ZOOM OUT IMAGE:");
                    
                    if(gestureName != null){
                        gesturesName[i] = gestureName;
                        
                        for(int j = 0 ; j < 3 ; j++){
                            JOptionPane.showMessageDialog(null, (j+1) + " training for gesture " + gestureName + "!" );

                            for(k = 0 ; k < 5 && countCreateGestureData != countCreateDataTemp + 1; k++)
                                JOptionPane.showMessageDialog(null, "Trainning failed, please do " + (j+1) + " trainning again!");     

                            if(k == 5){
                                JOptionPane.showMessageDialog(null, "Create user's gestures process failed !!!");
                                wiimoteCommand = DefaultParameterInterface.REMOTE_COMMAND_VIEW_IMAGE;
                                break outerLoop;
                            }
                            accSetOfAData = new ArrayList<AccSet>();
                            for(int t = 0; t < anArray.length ; t ++){
                                anAccSet = new AccSet((int)anArray[t][0], (int)anArray[t][1], (int)anArray[t][2], timeArray[t]);
                                accSetOfAData.add(anAccSet);
                            }
                            accSetListList.add(accSetOfAData);
                            countCreateDataTemp = countCreateGestureData;                        
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "You cancel creating user's gestures process!");
                        wiimoteCommand = DefaultParameterInterface.REMOTE_COMMAND_VIEW_IMAGE;
                        break;                                           
                    }
                }
                if(countCreateGestureData == 18)
                {
                    con = dbConnect();

                    //Delete all of user gesture already exist
                    if(confirmCreateGesture == JOptionPane.YES_OPTION){
                        try{
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT idGesture from Gesture where mode = 'USER'");
                            System.out.println("a");
                            Gesture[] gestures = new Gesture[6];
                            for(int i = 0; rs.next(); i++)
                                gestures[i] = new Gesture(rs.getInt(1));
                            Data[] datas = new Data[18];
                            for(int i = 0, j = 0 ; i< gestures.length; i ++){
                                rs = stmt.executeQuery("SELECT idData from Data where gestureID = '" + gestures[i].getIdGesture() + "'");
                                System.out.println("b");
                                while(rs.next()){
                                    datas[j] = new Data(rs.getInt(1));
                                    j++;
                                }
                            }
                            for(int i = 0; i < datas.length; i++)
                                stmt.executeUpdate("DELETE FROM ACCSET WHERE dataID = '" + datas[i].getIdData() + "'");                                
                            System.out.println("c");
                            for(int i = 0 ; i < gestures.length; i++)
                                stmt.executeUpdate("DELETE FROM Data WHERE gestureID = '" + gestures[i].getIdGesture() + "'");
                            System.out.println("d");
                            stmt.executeUpdate("DELETE FROM Gesture WHERE mode = 'USER'");                                
                            System.out.println("e");
                        }
                        catch(SQLException ex){
                            System.out.println("SQLExcpetion in CreateUSerGesturesListener When remove User gesture");
                            System.out.println(ex.getMessage());                    
                        }
                    }                    
                    
                    Gesture[] gesturesArray = new Gesture[6];
                    Data[] datasArray = new Data[18];
                    AccSet[] accSetArray; 
                    
                    try{
                        Statement stmt = con.createStatement();                      
                        for(int i = 0 ; i < gesturesArray.length; i++){
                            gesturesArray[i] = new Gesture(takeMinimumIDFromTable("Gesture"), gesturesName[i], "USER", null);
                            stmt.executeUpdate("INSERT INTO Gesture (idGesture, name, mode) VALUES ('" + gesturesArray[i].getIdGesture() + "','" + gesturesArray[i].getName() + "','" + gesturesArray[i].getMode() +"')" );
                        }
                        for(int i = 0; i < datasArray.length ; i ++){
                            accSetArray = new AccSet[accSetListList.get(i).size()];
                            accSetListList.get(i).toArray(accSetArray);
                            datasArray[i] = new Data(takeMinimumIDFromTable("Data"), gesturesArray[i/3], accSetArray, accSetArray.length);
                            stmt.executeUpdate("INSERT INTO DATA (idData, gestureID, numberOfAcc) VALUES ('" + datasArray[i].getIdData() + "','" + datasArray[i].getGesture().getIdGesture() + "','" + datasArray[i].getAccSet().length + "')");
                            for(int j = 0 ; j < accSetArray.length ; j++){
                                accSetArray[j].setIdAccSet(takeMinimumIDFromTable("AccSet"));
                                accSetArray[j].setIdData(datasArray[i].getIdData());
                                stmt.executeUpdate("INSERT INTO ACCSET (idAccSet, dataID, x, y, z, time) VALUES ('" + accSetArray[j].getIdAccSet() + "','" + accSetArray[j].getIdData() + "','" + accSetArray[j].getX() + "','" + accSetArray[j].getY() + "','" + accSetArray[j].getZ() + "','" + accSetArray[j].getTime() + "')");
                            }
                        }
                        stmt.executeUpdate("UPDATE SystemInformation SET mode = 'USER' WHERE id = '1'" );
                    }
                    catch(SQLException ex){
                        System.out.println("SQLExcpetion at Successful create User");
                        System.out.print(ex.getMessage());
                    }
                    
                    listOfData = datasArray;
                    remoteMode = "USER";
                    wiimoteCommand = DefaultParameterInterface.REMOTE_COMMAND_VIEW_IMAGE;
                    view.getManageRemotePanel().displayComponents(gesturesArray);
                }
                else
                    System.out.println("False: " + countCreateGestureData  + " , " + accSetListList.size());                
            }          
            
        }
    }

    private class UseUserGesturesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection con = dbConnect();
            String query = "UPDATE SystemInformation SET mode = 'USER' WHERE id = '1'";
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException ex) {
                System.out.println("SQLException at UseDefaultGesturesListener");
                System.out.println(ex.getMessage());
            }
            
            remoteMode = "USER";
            JOptionPane.showMessageDialog(null, "You are using User remote mode!");
            takeAllData(remoteMode);
            view.getManageRemotePanel().displayComponents(getGesturesArray());            

        }

    }

    private class UseDefaultGesturesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Connection con = dbConnect();
            String query = "UPDATE SystemInformation SET mode = 'DEFAULT' WHERE id = '1'";
            try {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException ex) {
                System.out.println("SQLException at UseDefaultGesturesListener");
                System.out.println(ex.getMessage());
            }
            
            remoteMode = "DEFAULT";
            takeAllData(remoteMode);
            view.getManageRemotePanel().displayComponents("DEFAULT");
            JOptionPane.showMessageDialog(null, "You are using Default remote mode!");
        }

    }

    private class ClickManageRemotePanelOKButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.displayNormaly();
        }
    }

    private class ZoomImageListener implements MouseWheelListener{
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {       
            zoom = Math.min(2000, Math.max(0, zoom - e.getUnitsToScroll()*10));
            view.getMiddlePanel().activeZoom(zoom);
            if (e.getSource() instanceof JComponent) {
                ((JComponent) e.getSource()).repaint();
            }
        }
    }

    private class WiiMoteEventListener implements WiimoteListener {
        private RawAcceleration rawAcc;
        private Timer timer = new Timer();
        private DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
        

        public double[][] addNumberToArray(double[][] first, RawAcceleration number){
            int length = (first == null || first.length == 0)? 1: (first.length + 1);
            double[][] second = new double[length][3];
            int i,j ;
            for(i = 0 ; i < first.length; i++)
                for(j = 0 ; j< first[0].length; j++)
                    second[i][j] = first[i][j];
            second[i][0] = number.getX();   
            second[i][1] = number.getY();
            second[i][2] = number.getZ();

            return second;
        }
    
        public String[] addTimeToArray(String[] first, String time){
            int length = (first == null || first.length == 0) ? 1 : (first.length + 1);
            String[] second = new String[length];
            int i = 0;
            for(i = 0 ; i < first.length; i ++)
                second[i]= first[i];        
            second[i] = time;        
            return second;
        }        
        
        public double[][] getDTWSignalFromData(Data data){
            double[][] signal = new double[0][3];
            double[][] second;
            int length, i, j;
            AccSet[] accSet = data.getAccSet();

            for(int k = 0 ; k < accSet.length; k++){
                length = (signal == null || signal.length == 0)? 1: (signal.length + 1); 
                second = new double[length][3];    
                
                for(i = 0 ; i < signal.length; i++)
                    for(j = 0 ; j< signal[0].length; j++)
                        second[i][j] = signal[i][j];
                second[i][0] = accSet[k].getX();   
                second[i][1] = accSet[k].getY();
                second[i][2] = accSet[k].getZ();
                
                signal = second;                     
            }                
            
            return signal;
        }
        
        @Override
        public void onButtonsEvent(WiimoteButtonsEvent wbe) {
            TimerTask task = new TimerTask(){
                @Override
                public void run(){
                    anArray = addNumberToArray(anArray, rawAcc);

                    Calendar rightNow = Calendar.getInstance();
                    long offset = rightNow.get(Calendar.ZONE_OFFSET) +    rightNow.get(Calendar.DST_OFFSET);
                    long sinceMidnight = (rightNow.getTimeInMillis() + offset) %    (24 * 60 * 60 * 1000);        

                    timeArray = addTimeToArray(timeArray, dateFormatter.format(rightNow.getTime())+ "." + sinceMidnight%(60*60*1000)%(60*1000)%(1000));
                }
            };   
            
            
            
            if(wbe.isButtonAJustReleased()){
                System.out.println("Button A Just Released");     
                timer.cancel(); 

                if(wiimoteCommand.equalsIgnoreCase(DefaultParameterInterface.REMOTE_COMMAND_VIEW_IMAGE)){
                    DTW dtw ;                    

                    if(listOfData!= null ){
                        double[] costArray = new double[listOfData.length];
                        for(int i = 0 ; i  < listOfData.length; i ++){
                            System.out.println("i = " + i);
                            dtw = new DTW(anArray, getDTWSignalFromData(listOfData[i]));
                            costArray[i] = dtw.getDistance();
                        }                     
                        double smallestCost  = costArray[0];
                        int smallestIndex = 0 ;
                        
                        for(int i = 0 ; i < costArray.length; i++)
                            if(costArray[i] < smallestCost){
                                smallestCost = costArray[i];
                                smallestIndex = i;
                            }
                        System.out.println("Smallest cost = " + smallestCost);
                        if(listOfData[smallestIndex].getGesture().getIdGesture() == DefaultParameterInterface.DEFAULT_GESTURE_LEFT_ARROW || listOfData[smallestIndex].getGesture().getIdGesture() == 10)
                            view.getBottomPanel().getPreviousImageButton().doClick();
                        else if(listOfData[smallestIndex].getGesture().getIdGesture() == DefaultParameterInterface.DEFAULT_GESTURE_RIGHT_ARROW || listOfData[smallestIndex].getGesture().getIdGesture() == 9)
                            view.getBottomPanel().getNextImageButton().doClick();
                        else if(listOfData[smallestIndex].getGesture().getIdGesture() == DefaultParameterInterface.DEFAULT_GESTURE_LEFT_CIRCLE || listOfData[smallestIndex].getGesture().getIdGesture() == 12)
                            view.getBottomPanel().getRotateLeftButton().doClick();
                        else if(listOfData[smallestIndex].getGesture().getIdGesture() == DefaultParameterInterface.DEFAULT_GESTURE_RIGHT_CIRCLE || listOfData[smallestIndex].getGesture().getIdGesture() == 11)
                            view.getBottomPanel().getRotateRightButton().doClick();
                        else if(listOfData[smallestIndex].getGesture().getIdGesture() == DefaultParameterInterface.DEFAULT_GESTURE_UP_ARROW || listOfData[smallestIndex].getGesture().getIdGesture() == 13){
                            zoom = Math.min(2000, Math.max(0, zoom + 100));
                            view.getMiddlePanel().activeZoom(zoom);
                            view.getMiddlePanel().repaint();
                        }
                        else if(listOfData[smallestIndex].getGesture().getIdGesture() == DefaultParameterInterface.DEFAULT_GESTURE_DOWN_ARROW || listOfData[smallestIndex].getGesture().getIdGesture() == 14){
                            zoom = Math.min(2000, Math.max(0, zoom - 100));
                            view.getMiddlePanel().activeZoom(zoom);
                            view.getMiddlePanel().repaint();
                        }
                    }
                }
                else if (wiimoteCommand.equalsIgnoreCase(DefaultParameterInterface.REMOTE_COMMAND_CREATE_GESTURE)){
                    new TakeDefaultData().printAnArray(anArray);
                    countCreateGestureData ++;
                }

            }            
            
            if(wbe.isButtonAJustPressed()){
                    System.out.println("Button A Just Pressed");     
                    anArray = new double[0][3];
                    timeArray = new String[0];                

                    int period = 200;
                    timer = new Timer();
                    timer.schedule(task,0, period);                          

            }              
        }
        
        

        @Override
        public void onIrEvent(IREvent ire) {
           
        }

        @Override
        public void onMotionSensingEvent(MotionSensingEvent mse) {
            rawAcc = mse.getRawAcceleration();
        }

        @Override
        public void onExpansionEvent(ExpansionEvent ee) {
            
        }

        @Override
        public void onStatusEvent(StatusEvent se) {
            
        }

        @Override
        public void onDisconnectionEvent(DisconnectionEvent de) {
            
        }

        @Override
        public void onNunchukInsertedEvent(NunchukInsertedEvent nie) {
            
        }

        @Override
        public void onNunchukRemovedEvent(NunchukRemovedEvent nre) {
           
        }

        @Override
        public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent ghie) {
           
        }

        @Override
        public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent ghre) {
            
        }

        @Override
        public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent ccie) {
           
        }

        @Override
        public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent ccre) {
         
        }

    }


    private class ClickUseRemoteMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {            
            Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
            if(wiimotes.length < 1)
                JOptionPane.showMessageDialog(view, "There is no remote connect to Computer!");
            else{
                JOptionPane.showMessageDialog(view, "Remote Control is activated!!!");
                wiimote = null;
                wiimote = wiimotes[0];                
                wiimote.activateMotionSensing();
                wiimote.addWiiMoteEventListeners(new WiiMoteEventListener()); 
                if(remoteMode == null ){
                    Connection con = dbConnect();
                    String query = "SELECT mode FROM SystemInformation";
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        rs.next();
                        remoteMode = rs.getString(1);                    
                    } catch (SQLException ex) {
                        System.out.println("SQLExpcetion when CLickUSeRemoteMenuItemListener");
                        System.out.println(ex.getMessage());
                    }
                    takeAllData(remoteMode);                
                }
            }
        }
    }

    private class ClickRemoteSettingMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
          
            if(remoteMode == null ){
                Connection con = dbConnect();
                String query = "SELECT mode FROM SystemInformation";
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    remoteMode = rs.getString(1);                    
                } catch (SQLException ex) {
                    System.out.println("SQLExpcetion when CLickUSeRemoteMenuItemListener");
                    System.out.println(ex.getMessage());
                }
                takeAllData(remoteMode);                
            }   
            if(remoteMode.equalsIgnoreCase("DEFAULT")){
                view.displayRemoteSettingPanel("DEFAULT");
            }
            else{
                view.displayRemoteSettingPanel(getGesturesArray());                
            }
            
        }
    }

    private  class ClickExitMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(1);
        }
    }

    private class ClickOpenFileMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("C:\\");     
            fileChooser.setDialogTitle("Choose an image file to begin:");  
            fileChooser.showOpenDialog(view);
            File selectedFile = fileChooser.getSelectedFile();   
            boolean checkFile = true;
            
            if (selectedFile != null ) { 
                checkFile = new MyFilter().accept(selectedFile);
                if(checkFile){
                    fileName = selectedFile.getAbsolutePath();
                    view.getMiddlePanel().setImg(getImage(fileName));
                    listOfFile = getAllFileOfCurrentDirectory(fileName);
                    view.setTitle(fileName);
                    view.getBottomPanel().setEnableButton();
                    view.getMiddlePanel().deactiveZoom();
                    zoom = 0;
                }
                else if(!checkFile)            
                    JOptionPane.showMessageDialog(null, "This kind of File have been not supported yet!");
            }               
        }        
    }

    private class ClickPreviousImageButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.getMiddlePanel().deactiveZoom(); 
            zoom = 0;
            File nextImage = getPreviousImage(fileName);            
            fileName = nextImage.getAbsolutePath();
            view.getMiddlePanel().setImg(getImage(fileName));
            view.setTitle(fileName);

            
        }        
    }

    private  class ClickNextImageButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.getMiddlePanel().deactiveZoom();
            zoom = 0;            
            File nextImage = getNextImage(fileName);            
            fileName = nextImage.getAbsolutePath();
            view.getMiddlePanel().setImg(getImage(fileName));
            
            view.setTitle(fileName);

        }

    }

    private class ClickRotateRightButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.getMiddlePanel().setImg(rotate90ToRight(view.getMiddlePanel().getImg()));
        }
    }

    private class ClickRotateLeftButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getMiddlePanel().setImg(rotate90ToLeft(view.getMiddlePanel().getImg()));          
        }
    }
    
    private class ResizeComponentListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {            
            view.getBottomPanel().setLocationComponent(view.getBottomPanel().getWidth(), view.getBottomPanel().getHeight());
            view.setEmptyPanelComponentLocation();
        }

        @Override
        public void componentMoved(ComponentEvent e) {            
        }
        @Override
        public void componentShown(ComponentEvent e) {            
        }
        @Override
        public void componentHidden(ComponentEvent e) {            
        }
    }   
    
}
