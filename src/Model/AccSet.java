/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Long
 */
public class AccSet {
    private int idAccSet;
    private int idData;
    private int x;
    private int y;
    private int z;
    private String time;

    public AccSet() {
    }

    public AccSet(int idAccSet, int idData, int x, int y, int z, String time) {
        this.idAccSet = idAccSet;
        this.idData = idData;
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
    }
    
    public AccSet(int x, int y, int z, String time){
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
    }

    public int getIdAccSet() {
        return idAccSet;
    }

    public void setIdAccSet(int idAccSet) {
        this.idAccSet = idAccSet;
    }

    public int getIdData() {
        return idData;
    }

    public void setIdData(int idData) {
        this.idData = idData;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AccSet{" + "idAccSet=" + idAccSet + ", idData=" + idData + ", x=" + x + ", y=" + y + ", z=" + z + ", time=" + time + '}';
    }
        
}
