/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TestProceudure;

import DTWAlgorithm.DTW;
import Model.AccSet;
import Model.Data;
import Model.Gesture;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Long
 */
public class ConfusionMatrix {
    ArrayList<Data> dataList;
    int numberOfTest;
    int numberOfTrain;
    int[] testList;
    int[] trainList;

    
    public static void main(String[] args){
        new ConfusionMatrix();
    }
    public ConfusionMatrix(){
        int numberOfRun = 50;
        int fold = 10;
        takeAllData();
        numberOfTest = dataList.size()/fold;
        numberOfTrain = dataList.size() - numberOfTest;
            

        double[][] accuracyMatrix = new double[8][8];
        double[][] temp = new double [8][8];

        for(int i = 0 ; i < numberOfRun ; i++){
            temp = getAccuracyMatrix();
            for(int j = 0; j < temp.length;j++)
                for(int k = 0 ; k < temp[0].length; k++)
                    accuracyMatrix[j][k] +=temp[j][k];
        }
        for(int i = 0 ; i < accuracyMatrix.length; i++)
            for(int j = 0 ; j < accuracyMatrix.length; j++)
                accuracyMatrix[i][j]/=numberOfRun;
            
        printArray(accuracyMatrix);
    }
    private double[][] getAccuracyMatrix(){
        testList = new int[numberOfTest];
        trainList = new int[numberOfTrain];        
        ArrayList<Data> dataTestList = new ArrayList<Data>();
        ArrayList<Data> dataTrainList = new ArrayList<Data>();        
        int temp;
        for(int i = 0 ,j = 0 ; i < numberOfTest; i++){
            temp = (int)(dataList.size()* Math.random());
            for(j = 0 ; j< testList.length; j ++)
                if(testList[j] == temp){
                    i--;
                    break;
                }
            if(j == testList.length)
                testList[i] = temp;
        }
        for(int k = 0, i = 0 , j = 0; i < dataList.size(); i++){
            for(j = 0 ; j < numberOfTest; j++)
                if(i == testList[j])
                    break;
            if(j == numberOfTest){
                trainList[k] = i;
                k++;
            }
        }

        
        int[] gesturesTotalNumber = new int[8];
        for(int i = 0 ; i < numberOfTest ; i++)
            dataTestList.add(takeDataWithDataID(testList[i]+1));
        for(int i = 0 ; i < numberOfTrain ; i++)
            dataTrainList.add(takeDataWithDataID(trainList[i]+1));
                            
        for(int i = 0 ; i < numberOfTest; i++)
            gesturesTotalNumber[dataTestList.get(i).getGesture().getIdGesture()-1]++;
        
        int[][] confusionMatrix = new int[8][8];
        DTW dtw ;
        
        double[] costArray;
        for(int i = 0 ; i < numberOfTest; i++){
            costArray = new double[numberOfTrain];
            for(int j = 0 ; j < numberOfTrain ; j++){
                dtw = new DTW(getDTWSignalFromData(dataTestList.get(i)), getDTWSignalFromData(dataTrainList.get(j)));
                costArray[j] = dtw.getDistance();  
            }
            
            double smallestCost  = costArray[0];
            int smallestIndex = 0 ;

            for(int j = 0 ; j < costArray.length; j++)
                if(costArray[j] < smallestCost){
                    smallestCost = costArray[j];
                    smallestIndex = j;
                }            
           confusionMatrix[dataTestList.get(i).getGesture().getIdGesture() - 1][dataTrainList.get(smallestIndex).getGesture().getIdGesture() - 1]++;
        }
        double[][] accuracyMatrix = new double[8][8];
        for(int i = 0 ; i < confusionMatrix.length; i++){
            if(gesturesTotalNumber[i]==0)
                accuracyMatrix[i][i] = 100;                                 
            else
                for(int j = 0 ; j < confusionMatrix[0].length; j++)
                    accuracyMatrix[i][j] = (double)confusionMatrix[i][j]/gesturesTotalNumber[i] * 100;
        }     
        return accuracyMatrix;
    }
    
    private void printArray(double [][] array){
        for(int i = 0 ; i < array.length; i ++){
            for(int j = 0 ; j < array[0].length ; j++)
                System.out.printf("%1$.2f   ", array[i][j]);
            System.out.println();
        }
    }
    private double[][] getDTWSignalFromData(Data data){
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
    private void takeAllData() {
        Connection con = dbConnect();
        dataList = new ArrayList<Data>();
        Data aData;
        AccSet accSet;
        ArrayList<AccSet> accSetList;

        String query = "SELECT * FROM Data";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
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

        } catch (SQLException ex) {
            System.out.println("SQL EXception");
        } finally{
            try { rs.close(); } catch (Exception e) { }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
    }
    private Data takeDataWithDataID(int id){
        Data aData = null;
        Connection con = dbConnect();
        AccSet accSet;
        ArrayList<AccSet> accSetList;
        Statement stmt = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM Data WHERE idData = '" + id + "'";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                aData = new Data();
                aData.setIdData(rs.getInt(1));
                aData.setGesture(new Gesture(rs.getInt(2)));
                aData.setNumberOfAcc(rs.getInt(3));

            }

                query  = "SELECT * FROM AccSet WHERE dataID = '" + id + "'";                    
                rs = stmt.executeQuery(query);
                accSetList = new ArrayList<AccSet>();

                while(rs.next()){
                    accSet = new AccSet(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6));                        
                    accSetList.add(accSet);
                }

                aData.setAccSet(accSetList.toArray(new AccSet[accSetList.size()]));

        } catch (SQLException ex) {
            System.out.println("SQL EXception");     
        } finally{
            try { rs.close(); } catch (Exception e) { }
            try { stmt.close(); } catch (Exception e) {  }
            try { con.close(); } catch (Exception e) {  }
        }
        return aData;
    }
    

    private Connection dbConnect() {
        Connection con = null;
        String driver = "com.mysql.jdbc.Driver";
        String dbName = "Thesis";
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
