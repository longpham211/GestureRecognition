/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Long
 */
public class Data {
    private int idData;
    private Gesture gesture;
    private AccSet[] accSet;
    private int numberOfAcc;
    

    public Data() {
    }
    
    public Data(int idData){
        this.idData = idData;
    }
    
    public Data(int idData, Gesture gesture, AccSet[] accSet, int numberOfAcc) {
        this.idData = idData;
        this.gesture = gesture;
        this.accSet = accSet;
        this.numberOfAcc = numberOfAcc;
    }

    public int getIdData() {
        return idData;
    }

    public void setIdData(int idData) {
        this.idData = idData;
    }

    public Gesture getGesture() {
        return gesture;
    }

    public void setGesture(Gesture gesture) {
        this.gesture = gesture;
    }

    public AccSet[] getAccSet() {
        return accSet;
    }

    public void setAccSet(AccSet[] accSet) {
        this.accSet = accSet;
    }


    public int getNumberOfAcc() {
        return numberOfAcc;
    }

    public void setNumberOfAcc(int numberOfAcc) {
        this.numberOfAcc = numberOfAcc;
    }

    @Override
    public String toString() {
        return "Data{" + "idData=" + idData + ", gesture=" + gesture + ", accSet=" + accSet  + ", numberOfAcc=" + numberOfAcc + '}';
    }
 
}
