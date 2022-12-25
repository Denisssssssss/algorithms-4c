import java.util.*;

// 15
public class SecondMST extends BaseSolution {

    public static void main(String[] args) {
        Kruskal kruskal = new Kruskal();
        int n = in.nextInt();
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = in.nextInt();
            }
        }
        Integer[][] mst = kruskal.run(graph);
        System.out.printf("First MST length: %s", sum(mst));
        List<Rib> ribs = ribs(mst);
        Map<Integer, Integer[][]> msts = new HashMap<>();
        ribs.forEach(rib -> {
            int len = graph[rib.v1][rib.v2];
            graph[rib.v1][rib.v2] = 0;
            graph[rib.v2][rib.v1] = 0;
            Integer[][] result = kruskal.run(graph);
            graph[rib.v1][rib.v2] = len;
            graph[rib.v2][rib.v1] = len;
            msts.put(sum(result), result);
        });
        msts.keySet().stream().min(Comparator.comparingInt(key -> key)).ifPresent(min -> {
            Integer[][] ans = msts.get(min);
            System.out.println("\nSecond MST:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (ans[i][j] == null) {
                        ans[i][j] = 0;
                    }
                    System.out.printf("%s ", ans[i][j]);
                }
                System.out.println();
            }
            System.out.printf("Second MST length: %s", sum(ans));
        });
    }

    public static int sum(Integer[][] a) {
        int n = a.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (a[i][j] == null) {
                    a[i][j] = 0;
                }
                sum += a[i][j];
            }
        }
        return sum;
    }

    public static List<Rib> ribs(Integer[][] a) {
        List<Rib> ribs = new ArrayList<>();
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (a[i][j] == null || a[i][j] == 0) continue;
                ribs.add(new Rib(i, j, a[i][j]));
            }
        }
        return ribs;
    }
}
