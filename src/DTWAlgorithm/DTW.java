/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTWAlgorithm;

/**
 *
 * @author Long
 */
public class DTW {
    private double seq1[][];
    private double seq2[][];
    
    private int n;
    private int m;
    
    private double distance;
        
    public DTW(double seq1[][], double seq2[][]){
        this.seq1 = seq1;
        this.seq2 = seq2;
        
       
        if(seq1 != null && seq2!= null){
  
            n = seq1.length;
            m = seq2.length;
            distance = 0;

            this.compute();
            
        }
    }

    private void compute(){
        double tempDistance;
        double[][] d = new double[n][m];       
        double[][] D = new double[n][m];  
        
        for (int i = 0; i < n; i++) 
                for (int j = 0; j < m; j++) 
                        d[i][j] = distanceBetween(seq1[i], seq2[j]);
                
        
        D[0][0] = d[0][0];
        
        for(int i = 1 ; i< n; i++){
            D[i][0] = d[i][0] + D[i - 1][0];
        }
        for (int j = 1; j < m; j++) {
            D[0][j] = d[0][j] + D[0][j - 1];
        }
        
        for (int i = 1; i < n; i++) {
                for (int j = 1; j < m; j++) {
                        tempDistance = Math.min(Math.min(D[i-1][j], D[i-1][j-1]), D[i][j-1]);
                        tempDistance += d[i][j];
                        D[i][j] = tempDistance;
                }
        }        
        distance = D[n - 1][m - 1];
    }
    
    private double distanceBetween(double[] seq1, double[] seq2){
        double distance = 0.0;
        double sum = 0.0;
        for(int i = 0 ; i < seq1.length; i++){
            sum += ((seq1[i] - seq2[i]) * (seq1[i] - seq2[i]));
        }
        return Math.sqrt(sum);        
    }
    
    public double getDistance(){
        return distance;
    }


}
