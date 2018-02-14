import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        double regParam = scanner.nextDouble();
        int numEdges = scanner.nextInt();
        scanner.nextLine();

        Map<String, Integer> namesMap = new HashMap<>(); // отображение имя -> номер
        Map<Integer, String> namesMapReversed = new HashMap<>(); // отображение номер -> имя
        HashSet<String> namesSet = new HashSet<>(); // список всех имен
        HashMap<String, List<String>> edgesMap = new HashMap<>(); // список смежных вершин

        for (int i = 0; i < numEdges; i++) {
            String[] input = scanner.nextLine().split(" ");
            if (!namesSet.contains(input[0])) {
                namesSet.add(input[0]);
                namesMap.put(input[0], namesMap.size());
                namesMapReversed.put(namesMap.size(), input[0]);
                edgesMap.put(input[0], new ArrayList<String>());

            }
            if (!namesSet.contains(input[1])) {
                namesSet.add(input[1]);
                namesMap.put(input[1], namesMap.size());
                namesMapReversed.put(namesMap.size(), input[1]);
                edgesMap.put(input[1], new ArrayList<String>());
            }
            edgesMap.get(input[0]).add(input[1]);
        }

        int numVertices = namesSet.size(); // число вершин
        double[][] transitionMatrix = new double[numVertices][numVertices];
        for(String left: namesSet) {
            int leftIndex = namesMap.get(left);
            List<String> temp = edgesMap.get(left);
            if (temp.size() != 0) {
                for (String right : edgesMap.get(left)) {
                    int rightIndex = namesMap.get(right);
                    int numRightEdges = edgesMap.get(left).size();
                    transitionMatrix[rightIndex][leftIndex] = 1.0 / numRightEdges;
                }
            }
            else {
                // заполнение в случае отсутствия выходов из вершины
                for (int j = 0; j < numVertices; j ++)
                    transitionMatrix[j][leftIndex] = 1.0 / numVertices;
            }
        }
        double [] vector = rankVector(transitionMatrix, numVertices, regParam);
        for (int i = 0; i < numVertices; i++)
            System.out.println(namesMapReversed.get(i+1) + " " + vector[i]);
    }

    private static double[] rankVector(double[][] transitionMatrix, int size, double regParam) {
        // регурялизуем матрицу
        transitionMatrix = sum(multiplyMatrix(transitionMatrix, size, (1 - regParam)),
                               multiplyMatrix(ones(size), size, regParam),
                               size
                               );
        double[] vector = new double[size];
        for (int i = 0; i < size; i++)
            vector[i] = 1.0 / size;
        double[] nextVector = multiplyMatrixVector(transitionMatrix, vector);
        int counter = 0;
        while (!compareVectors(vector, nextVector)) {
            vector = nextVector;
            nextVector = multiplyMatrixVector(transitionMatrix, nextVector);
            counter++;
        }
        return vector;
    }

    private static boolean compareVectors(double[] v1, double[] v2) {
        int size = v1.length;
        boolean result = true;
        for (int i = 0; i < size; i++)
            if (v1[i] != v2[i]) {
            result = false;
            break;
            }
        return result;
    }

    public static double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        return Arrays.stream(matrix)
                .mapToDouble(row ->
                        IntStream.range(0, row.length)
                                .mapToDouble(col -> row[col] * vector[col])
                                .sum()
                ).toArray();
    }

    public static double[][] multiplyMatrix(double[][] matrix, int size, double num) {
        double[][] result = matrix;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result[i][j] *= num;
        return result;
    }

    public static double[][] sum(double[][] matrix1, double[][] matrix2, int size) {
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result[i][j] = matrix1[i][j] + matrix2[i][j];
        return result;
    }

    public static double[][] ones(int size) {
        double[][] result = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result[i][j] = 1.0 / size;
        return result;
    }

    private static void printMatrix(double[][] matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    private static void printVector(double[] vector) {
        for (int i = 0; i < vector.length; i++)
            System.out.print(vector[i] + " ");
        System.out.println();
    }

}
