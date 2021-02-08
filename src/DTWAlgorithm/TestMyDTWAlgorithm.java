/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTWAlgorithm;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Long
 */
public class TestMyDTWAlgorithm {
    public static void main(String[] args){
        double[][] seq1 = new double[][]{
        {2, 3, 4}, 
        {1, 2, 3},
        {4, 5, 6},
        {1, 7, 8}                
        };
        
        double [][] seq2 = new double [][] {
            {1,2,3},
            {10,15,16},
            {7,8,9}
            };
        
        //System.out.println(seq1.length+ " " + seq2.length);
        DTW dtw1 = new DTW(seq1, seq2);
       // marytts.util.math.DTW dtw2 = new marytts.util.math.DTW(seq1, seq2);
 
        
        System.out.println("Distance by MyDTW: " + dtw1.getDistance());
       // System.out.println("Distance by MaryDTW: " + dtw2.getCost());
        
    }
}
