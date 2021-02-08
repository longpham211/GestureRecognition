/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Long
 */
public class Gesture {

    private int idGesture;
    private String name;
    private String description;
    private String mode;

    public Gesture() {
    }
    
    public Gesture(int id){
        idGesture = id;
    }

    public Gesture(int idGesture, String name, String mode, String description) {
        this.idGesture = idGesture;
        this.name = name;
        this.description = description;
        this.mode = mode;
    }

    public int getIdGesture() {
        return idGesture;
    }

    public void setIdGesture(int idGesture) {
        this.idGesture = idGesture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
        
}
