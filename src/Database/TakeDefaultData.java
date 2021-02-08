/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Model.AccSet;
import Model.Data;
import Model.Gesture;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TakeDefaultData implements WiimoteListener{
    
    private RawAcceleration rawacc;
    private Timer timer = new Timer();
    private double[][] anArray = new double[0][3];
    private String[] timeArray = new String[0];
    private DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static int gestureID;
    
    public static void main(String[] args){
        Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
        Wiimote wiimote = wiimotes[0];
        wiimote.activateMotionSensing();
        wiimote.toString();
        wiimote.addWiiMoteEventListeners(new TakeDefaultData());  
        
        String gestureString = JOptionPane.showInputDialog(null, "Enter the gesture ID: ");
        try{
            gestureID = Integer.parseInt(gestureString);
            System.out.println(gestureID);
        }            
        catch(NumberFormatException e){
            System.out.println(e.getMessage());
        }
        

    }
    
    public double[][] addNumberToArray(double[][] first, RawAcceleration number){
        int length = (first == null || first.length == 0)? 1: (first.length + 1);
        double[][] second = new double[length][3];
        int i,j = 0 ;
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
    
    public void printAnArray(double[][] anArray){
        for(int i = 0 ; i < anArray.length; i++){
            for(int j = 0 ; j < anArray[0].length; j++)
                System.out.print(anArray[i][j] + "   ");                
            System.out.println();
        }
    }
    
    public void printAnArray(String[] anArray){
        for(int i = 0 ; i < anArray.length; i++){
            System.out.println(anArray[i] + "   ");                
        }
    }
    @Override
    public void onButtonsEvent(WiimoteButtonsEvent wbe) {
        TimerTask task = new TimerTask(){
            @Override
            public void run(){
                anArray = addNumberToArray(anArray, rawacc);
                
                Calendar rightNow = Calendar.getInstance();
                long offset = rightNow.get(Calendar.ZONE_OFFSET) +    rightNow.get(Calendar.DST_OFFSET);
                long sinceMidnight = (rightNow.getTimeInMillis() + offset) %    (24 * 60 * 60 * 1000);        
                
                timeArray = addTimeToArray(timeArray, dateFormatter.format(rightNow.getTime())+ "." + sinceMidnight%(60*60*1000)%(60*1000)%(1000));
            }
        };      
        
        if(wbe.isButtonAJustReleased()){
            System.out.println("Button A Just Released");     
            timer.cancel();
            
            //printAnArray(anArray);
            //printAnArray(timeArray);    
            
            Data data = new Data();
            data.setIdData(takeMinimumIDFromTable("Data"));
            data.setNumberOfAcc(anArray.length);     
            Gesture gesture = new Gesture();
            gesture.setIdGesture(gestureID);            
            data.setGesture(gesture);     
            data.setAccSet(new AccSet[anArray.length]);
            //System.out.println(data);
            
            saveDefaultDataIntoDatabase(data);              
            
            AccSet[] accSet = new AccSet[anArray.length];
            for(int i = 0 ; i < accSet.length; i++){
                accSet[i] = new AccSet();
               // System.out.println("i = " + i);
                //System.out.println("Minimum ID From Table ACCSET :" + takeMinimumIDFromTable("AccSet"));
                accSet[i].setIdAccSet(takeMinimumIDFromTable("AccSet"));
                accSet[i].setIdData(data.getIdData());
                accSet[i].setX((int)anArray[i][0]);
                accSet[i].setY((int)anArray[i][1]);
                accSet[i].setZ((int)anArray[i][2]);
                accSet[i].setTime(timeArray[i]);
                saveDefaultAccSetIntoDatabase(accSet[i]);
                
            }           
            System.out.println("done: dataID = " + data.getIdData() + "," + "numberAcc = " + data.getAccSet().length);
       
        }
   
        
        if(wbe.isButtonAJustPressed()){
                System.out.println("Button A Just Pressed");     
                anArray = new double[0][3];
                timeArray = new String[0];                
                
                int period = 100;
                timer = new Timer();
                timer.schedule(task,0, period);                           
                     
        }        

        
        //if(wbe.isButtonBHeld())
           
          //  WiiUseApiManager.shutdown();
        
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
            Statement stmt = (Statement) con.createStatement();
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
    public void saveDefaultDataIntoDatabase(Data data){
        Connection con = dbConnect();
        
        String query = "INSERT INTO Data(idData, gestureID, numberOfAcc) " + "VALUES('"+ data.getIdData() + "','" + data.getGesture().getIdGesture() + "','" + data.getAccSet().length + "')";
        try {
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("SQLException at saveDefaultDataIntoDatabase with dataID = " + data.getIdData());
            System.out.println(ex.getMessage());
        }
    }    
    public void saveDefaultAccSetIntoDatabase(AccSet temp){
        Connection con = dbConnect();
        
        String query = "INSERT INTO AccSet(idAccSet, dataID, x, y, z, time) " + "VALUES('" + temp.getIdAccSet() + "','" + temp.getIdData() + "','" + temp.getX() + "','" + temp.getY() + "','" + temp.getZ() + "','" + temp.getTime() + "')";
        try {
            Statement stmt = (Statement) con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("SQLException at saveDefaultAccSetIntoDatabase with AccSetId = " + temp.getIdAccSet());
            System.out.println(ex.getMessage());
        }        
    }
    
    @Override
    public void onIrEvent(IREvent ire) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMotionSensingEvent(MotionSensingEvent mse) {
        rawacc = mse.getRawAcceleration();
    }

    @Override
    public void onExpansionEvent(ExpansionEvent ee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStatusEvent(StatusEvent se) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDisconnectionEvent(DisconnectionEvent de) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onNunchukInsertedEvent(NunchukInsertedEvent nie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onNunchukRemovedEvent(NunchukRemovedEvent nre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent ghie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent ghre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent ccie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent ccre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
