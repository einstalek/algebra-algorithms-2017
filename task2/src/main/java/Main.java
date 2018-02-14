import java.util.*;
import java.lang.Object;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Integer n = Integer.parseInt(sc.nextLine());
        HashMap<Integer, ArrayList<Integer>> vertices = new HashMap<Integer, ArrayList<Integer>>();

        int size = 0;
        for (int i = 0; i < n; i++) {
            String[] s = sc.nextLine().split(" ");
            Integer l = Integer.parseInt(s[0]);
            Integer r = Integer.parseInt(s[1]);

            size = Math.max(size, Math.max(l, r));

            if (!vertices.keySet().contains(l))
                vertices.put(l, new ArrayList<Integer>());
            if (!vertices.keySet().contains(r))
                vertices.put(r, new ArrayList<Integer>());
            vertices.get(l).add(r);
        }
        size++;

        double mat[][] = new double[size][size];
        Random rand = new Random();

        for (Integer l : vertices.keySet()) {
            for (Integer r : vertices.get(l)) {
                mat[l][r] = 1 + Math.abs(rand.nextInt()) % 10000;
            }
        }

        boolean testDet = det(mat, size);

        if (testDet)
            System.out.println("yes");
        else
            System.out.println("no");
    }

    public static boolean det(double mat[][], int size) {

        for (int col = 0; col < size; col++) {

            double maxElement = mat[col][col];
            int rowOfElement = col;

            // В текущем столбце ищем ненулевой элемент и номер его строки
            for (int row = col; row < size; row ++) {
                double v = mat[row][col];
                if (Math.abs(v) > maxElement) {
                    rowOfElement = row;
                    maxElement = mat[row][col];
                }
            }

            // Выносим найденную строку наверх
            if (rowOfElement != col) {
                double temp[] = mat[rowOfElement];
                mat[rowOfElement] = mat[col];
                mat[col] = temp;
            }

            // Вычитаем первую строку из остальных
            for (int row = col + 1; row < size; row++) {
                double coef = mat[row][col] / mat[col][col];
                for (int k = col; k < size; k++)
                    mat[row][k] -= coef * mat[col][k];
            }
        }

        // если на диагонали есть нулевой элемент, то детерминант = 0
        for (int i = 0; i < size; i++) {
            if (Math.abs(mat[i][i]) < 1e-10)
                return  false;
        }
        return true;
    }
}

