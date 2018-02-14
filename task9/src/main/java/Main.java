import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Set<Integer> symbols = legendreSymbol(n - 1);

        int[][] hadamarMatrix = zeros(n);
        for (int i = 0; i < n; i++) {
            hadamarMatrix[i][0] = 1;
            hadamarMatrix[0][i] = 1;
        }

        for (int i = 1; i < n; i++)
            for (int j = 1; j < n; j++) {
                if (i == j) hadamarMatrix[i][i] = -1;
                else {
                    if (i - j >= 0) hadamarMatrix[i][j] = symbols.contains(i - j) ? 1: -1;
                    else hadamarMatrix[i][j] = symbols.contains(i - j + n - 1) ? 1 : -1;
                }
            }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (hadamarMatrix[i][j] == -1) hadamarMatrix[i][j] = 0;

        HashSet<String> codes = new HashSet<>();
        for (int[] x: hadamarMatrix) {
            codes.add(vectorToString(x));
            codes.add(vectorToString(orthogonalComplement(x)));
        }

        for (String s: codes)
            System.out.println(s);
    }

    private static int[][] zeros(int size) {
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result[i][j] = 0;
        return result;
    }

    private static Set<Integer> legendreSymbol(int p) {
        Set<Integer> symbolSet = new HashSet<>();
        for (int x = 1; x < p + 1; x++) {
            for (int j = 0; j < p + 1; j++) {
                if (x - j * j % p == 0) {
                    symbolSet.add(x);
                    break;
                }
            }
        }
        return symbolSet;
    }

    private static int[] orthogonalComplement(int[] vector) {
        int[] result = new int[vector.length];
        for (int i = 0; i < vector.length; i++)
            result[i] = vector[i] == 1 ? 0 : 1;
        return result;
    }

    private static String vectorToString(int[] vector) {
        String s = "";
        for (int x: vector)
            s += x;
        return s;
    }

}
