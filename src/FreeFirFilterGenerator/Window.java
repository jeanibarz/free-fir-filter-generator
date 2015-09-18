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
public class Window {    
    public static double[] triangular(int n) {
        double[] window = new double[n];
        
        for(int i = 0; i < n; i++) {
            window[i] = 1.0-Math.abs( (i-(n-1.0)/2.0) / ((n+1)/2.0) );
            // System.out.println("W[" + i + "]=" + window[i]);
        }
        
        return window;
    }
    
    public static double[] parzen(int n) {
        double[] window = new double[n];
        
        final double k = (n-1.0)/2.0;
        for(int i = 0; i < n; i++) {
            window[i] = 1 - ( (n-k)*(n-k)/(k*k) );
        }
        
        return window;
    }
    
    public static double[] hanning(int n) {
        double[] window = new double[n];
        
        final double L = n-1.0;
        for(int i = 0; i < n; i++) {
            final double w = 2*Math.PI*i/L;
            window[i] = 0.5 * ( 1.0-Math.cos(w) );
        }
        
        return window;
    }
    
    public static double[] hamming(int n) {
        double[] window = new double[n];
        
        final double a = 0.54, b = 0.46;
        final double L = n-1.0;
        for(int i = 0; i < n; i++) {
            final double w = 2*Math.PI*i/L;
            window[i] = a - b*Math.cos(w);
        }
        
        return window;
    }
    
    public static double[] blackman(int n) {
        double[] window = new double[n];
        
        final double a0 = 0.42659, a1 = 0.49656, a2 = 0.076849;
        final double L = n-1.0;
        for(int i = 0; i < n; i++) {
            final double w = 2*Math.PI*i/L;
            window[i] = a0 - a1*Math.cos(w) + a2*Math.cos(2.0*w);
        }
        
        return window;
    }
    
    public static double[] nuttall(int n) {
        double[] window = new double[n];

        final double a0 = 0.355768, a1 = 0.487396, a2 = 0.144232, a3 = 0.012604;
        final double L = n-1.0;
        for(int i = 0; i < n; i++) {
            final double w = 2*Math.PI*i/L;
            window[i] = a0 - a1*Math.cos(w) + a2*Math.cos(2.0*w) - a3*Math.cos(3.0*w);
        }

        return window;
    }
    
    public static double[] blackman_nuttall(int n) {
        double[] window = new double[n];

        final double a0 = 0.3635819, a1 = 0.4891775, a2 = 0.1365995, a3 = 0.0106411;
        final double L = n-1.0;
        for(int i = 0; i < n; i++) {
            final double w = 2*Math.PI*i/L;
            window[i] = a0 - a1*Math.cos(w) + a2*Math.cos(2.0*w) - a3*Math.cos(3.0*w);
        }

        return window;
    }
    
    public static double[] blackman_harris(int n) {
        double[] window = new double[n];

        final double a0 = 0.35875, a1 = 0.48829, a2 = 0.14128, a3 = 0.01168;
        final double L = n-1.0;
        for(int i = 0; i < n; i++) {
            final double w = 2*Math.PI*i/L;
            window[i] = a0 - a1*Math.cos(w) + a2*Math.cos(2.0*w) - a3*Math.cos(3.0*w);
        }

        return window;
    }
    
    public static double[] flat_top(int n) {
        double[] window = new double[n];

        final double a0 = 1.0, a1 = 1.93, a2 = 1.29, a3 = 0.388, a4 = 0.028;
        final double L = n-1.0;
        for(int i = 0; i < n; i++) {
            final double w = 2*Math.PI*i/L;
            window[i] = a0 - a1*Math.cos(w) + a2*Math.cos(2.0*w) - a3*Math.cos(3.0*w) + a4*Math.cos(4.0*w);
        }

        return window;
    }
    
    public static double[] gaussian(int n, double sigma) {
        assert(sigma <= 0.5);
        
        double[] window = new double[n];
        
        final double L = n-1.0;
        for(int i = 0; i < n; i++) {
            final double k = n-0.5*L/(sigma*0.5*L);
            window[i] = Math.exp(-0.5*k*k);
        }
        
        return window;
    }
    
    public static double[] tukey(int n, double alpha) {
        assert(alpha >= 0.0);
        assert(alpha <= 1.0);
        
        double[] window = new double[n];
        
        final double L = n-1.0;
        final int e1 = (int)(alpha*L/2.0);
        final int e2 = (int)(L*(1.0-alpha/2.0));
        
        for(int i = 0; i < e1; i++) {
            final double k = 2.0*n/(alpha*L);
            window[i] = 0.5*(1.0 + Math.cos(Math.PI*(k-1.0)));
        }
        for(int i = e1; i < e2; i++) {
            window[i] = 1.0;
        }
        for(int i = e2; i < n; i++) {
            final double k = 2.0*n/(alpha*L);
            window[i] = 0.5*(1.0 + Math.cos(Math.PI*(k-0.5*alpha+1.0)));
        }
        
        return window;
    }
}
