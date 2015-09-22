/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freefirfiltergenerator;

/**
 *
 * @author Jean
 */
public class FreeFIRFilterGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FreeFirFilterGeneratorForm mainForm = new FreeFirFilterGeneratorForm();

        double[] w = freefirfiltergenerator.Window.triangular(10);
        
        for(int i = 0; i < w.length; i++) System.out.println("W[" + i + "]=" + w[i]);
        
        mainForm.setVisible(true);
    }
    
}
