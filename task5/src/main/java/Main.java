import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final double PI = 3.1415926535897932384626433832795;

    private static class Complex {
        double re, im;

        Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }
        Complex plus(Complex o) {
            return new Complex(re + o.re, im + o.im); }
        Complex minus(Complex o) {
            return new Complex(re - o.re, im - o.im); }
        Complex times(Complex o) {
            return new Complex(re * o.re - im * o.im, im * o.re + re * o.im);
        }

        @Override
        public String toString() {
            return re + "," + im;
        }
    }

    private static List<Complex> fft(double[] coeffs){
        int length = coeffs.length;

        // Base of recursion
        if (length == 1) return new ArrayList<Complex>(Arrays.asList(new Complex(coeffs[0], 0)));

        // Coeffs on even positions
        double[] even = new double[length / 2];
        for (int i = 0; i < length / 2; i++)
            even[i] = coeffs[2*i];
        List<Complex> left = fft(even);

        // Coeffs on odd positions
        double[] odd = new double[length / 2];
        for (int i = 0; i < length / 2; i++)
            odd[i] = coeffs[2*i + 1];
        List<Complex> right = fft(odd);

        // Combine two parts
        Complex[] y = new Complex[length];
        Complex wk = new Complex(1, 0);
        Complex step = new Complex(Math.cos(2 * PI / length), Math.sin(2 * PI / length));
        for (int i = 0; i < length / 2; i++) {
            y[i]              = left.get(i).plus(wk.times(right.get(i)));
            y[i + length / 2] = left.get(i).minus(wk.times(right.get(i)));
            wk = wk.times(step);
        }
        return new ArrayList<Complex>(Arrays.asList(y));
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");
        double coeffs[] = new double[input.length];
        String s = "";
        for (int i = 0; i < input.length; i++) {
            coeffs[i] = Double.parseDouble(input[i]);
            s += coeffs[i] + " ";
        }

        List<Complex> result = fft(coeffs);
        for (int i = 0; i < input.length - 1; i++)
            System.out.print(result.get(i) + " ");
        System.out.print(result.get(input.length - 1));
    }
}
