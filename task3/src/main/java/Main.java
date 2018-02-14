import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        HashMap<Integer, HashSet<Integer>> vertices = new HashMap<>();
        HashMap<Integer, ArrayList<Double>> coordinates = new HashMap<>();

        int size = 0;
        for (int i = 0; i < n; i++) {
            String[] s = sc.nextLine().split(" ");
            Integer l = Integer.parseInt(s[0]);
            Integer r = Integer.parseInt(s[1]);
            size = Math.max(size, Math.max(l, r));

            if (!vertices.keySet().contains(l))
                vertices.put(l, new HashSet<Integer>());
            if (!vertices.keySet().contains(r))
                vertices.put(r, new HashSet<Integer>());
            vertices.get(l).add(r);
            vertices.get(r).add(l);
        }
        size++;

        int minLoopLength = 2;
        for (int i = 2; i < size; i++) {
            if (vertices.get(i).contains(0)) {
                minLoopLength = i + 1;
                break;
            }
        }

        double step = 2 * Math.PI / minLoopLength;
        for (int i = 0; i < minLoopLength; i++) {
            double currentAngle = step * i;
            coordinates.put(i, new ArrayList<Double>());
            double x = Math.cos(currentAngle);
            double y = Math.sin(currentAngle);
            coordinates.get(i).add(x);
            coordinates.get(i).add(y);
        }

        for (int i = minLoopLength; i < size; i++) {
            double x = 0;
            double y = 0;
            int count = 0;
            for (int j: vertices.get(i)) {
                if (j >=  minLoopLength) continue;
                x += coordinates.get(j).get(0);
                y += coordinates.get(j).get(1);
                count += 1;
            }
            x /= count;
            y /= count;
            coordinates.put(i, new ArrayList<Double>());
            coordinates.get(i).add(x);
            coordinates.get(i).add(y);
        }

        for (int i = 0; i < size; i++) {
            System.out.println(i + " " + coordinates.get(i).get(0) + " "
                    + coordinates.get(i).get(1));
        }
    }
}
