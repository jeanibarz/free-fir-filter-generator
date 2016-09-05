/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FreeFirFilterGenerator.Filter;
import edu.emory.mathcs.jtransforms.fft.*;

/**
 *
 * @author Jean
 */
public class LowPassFilter extends Filter {
    public LowPassFilter(int size, int samplingRate, double lpFc, int lpOrder, FilterTypeEnum lpFilterType) {
        this.size = size;
        this.lpFc = 2.0*lpFc/samplingRate;
        this.lpOrder = lpOrder;
        this.lpFilterType = lpFilterType;
    }
    
    public double[] getImpulse() {
        // This function returns the filter impulse given the filter characteristics
        
        // I have tested both odd and even signals, and it seems that inverse FFT
        // given an even signal is not correct (because when I delay it by 1.5 sample
        // the response is not symetric). I consider in this case an n+1 signal and
        // then I can perform the IFFT with this odd signal. I truncate the last sample
        // to get the temporal signal I should have obtained normally.
        DoubleFFT_1D fft;
        double mag[], arg[], fftBuffer[];
        int n = this.size;
        boolean even = (n%2 == 0);
        if(even) n += 1;
        
        fft = new DoubleFFT_1D(n);
        
        mag = new double[(n/2)+1];
        arg = new double[(n/2)+1];
        fftBuffer = new double[n];
        
        // We define the frequency amplitude between 0 and size/2
        switch(this.lpFilterType) {
            case BUTTERWORTH:
                for(int i = 0; i < (n/2)+1; ++i) {
                    double nFreq = 2.0*(double)i/(double)n/(double)lpFc;
                    mag[i] = Math.sqrt(1.0/(1.0 + Math.pow(nFreq,2.0*this.lpOrder)));
                }
                break;
            case LINKWITZ_RILEY:
                for(int i = 0; i < (n/2)+1; ++i) {
                    double nFreq = 2.0*(double)i/(double)n/(double)lpFc;
                    mag[i] = 1.0/(1.0 + Math.pow(nFreq,2.0*this.lpOrder));
                }
                break;
        }
        
        // Calculate the delay to add to the impulse to have a centered peak
        double delay = (this.size-1)/2.0;

        // Calculate the phase to add to the frequency bins
        for(int k = 0; k < (n+1)/2; ++k) {
            arg[k] = -2.0*Math.PI*delay*(double)k/(double)n; // Phase to add to delay signal by "delay" samples
        }
        
        // Calculate some particular values of fftBuffer that can't be
        // include in a simple loop and without any additional conditional test...
        fftBuffer[0]=mag[0];
        fftBuffer[1]=mag[(n-1)/2]*Math.sin(arg[(n-1)/2]);
        fftBuffer[n-1]=mag[(n-1)/2]*Math.cos(arg[(n-1)/2]);
        
        // Calculate other fftBuffer values
        int k = 1;
        for(int i = 2; i+1 < n; i+=2) {
            fftBuffer[i]=mag[k]*Math.cos(arg[k]);
            fftBuffer[i+1]=mag[k]*Math.sin(arg[k]);
            k++;
        }
        
        // Do a backward fft transform to get the time domain impulse response
        fft.realInverse(fftBuffer, true);
  
        // If signal was originally even, we need to resize the calculated impulse 
        // to the of original size.
        if(even) fftBuffer = java.util.Arrays.copyOf(fftBuffer, this.size);
        
        return fftBuffer;
    }
}
