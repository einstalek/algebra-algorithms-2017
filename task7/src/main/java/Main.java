import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int verticesCount = scanner.nextInt();
        HashMap<Integer, List<Integer>> edgesMap = new HashMap<Integer, List<Integer>>();

        int[] verticesWeights = new int[verticesCount];
        for (int i = 0; i < verticesCount; i++)
            verticesWeights[i] = scanner.nextInt();

        int edgesCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < edgesCount; i++) {
            String[] temp = scanner.nextLine().split(" ");
            int v1 = Integer.parseInt(temp[0]);
            int v2 = Integer.parseInt(temp[1]);
            if (!edgesMap.keySet().contains(v1))
                edgesMap.put(v1, new ArrayList<Integer>());
            if (!edgesMap.keySet().contains(v2))
                edgesMap.put(v2, new ArrayList<Integer>());
            edgesMap.get(v1).add(v2);
            edgesMap.get(v2).add(v1);
        }

        int[] p = zeros(verticesCount);

        for (int v1: edgesMap.keySet())
            for (int v2: edgesMap.get(v1)) {
                if (p[v1] < verticesWeights[v1] && p[v2] < verticesWeights[v2]) {
                    String key = v1 + "" + v2;
                    int x = min(verticesWeights[v1] - p[v1], verticesWeights[v2] - p[v2]);
                    p[v1] += x;
                    p[v2] += x;
                }
            }

        for (int i = 0; i < p.length; i++)
            if (p[i] >= verticesWeights[i])
                System.out.print(i + " ");
    }

    private static int[] zeros(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = 0;
        return result;
    }

    private static int min(int x, int y) {
        return x <= y ? x : y;
    }
}
