import java.util.*;

// 12
public class Kruskal extends BaseSolution {

    /*
     * 5
     * 0 1 2 1 0
     * 1 0 3 0 5
     * 2 3 0 4 5
     * 1 0 4 0 1
     * 0 5 5 1 0
     */
    public static void main(String[] args) {
        Integer[][] a = new Kruskal().run();
        System.out.println("\nMST: ");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] == null) {
                    a[i][j] = 0;
                }
                System.out.printf("%s ", a[i][j]);
            }
            System.out.println();
        }
    }

    public Integer[][] run() {
        int n = in.nextInt();
        Integer[] group = new Integer[n];
        List<Rib> ribs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            group[i] = i;
            for (int j = 0; j < n; j++) {
                int len = in.nextInt();
                if (len == 0) continue;
                ribs.add(new Rib(i, j, len));
            }
        }
        return mst(group, ribs, n);
    }

    public Integer[][] run(int[][] graph) {
        int n = graph.length;
        Integer[] group = new Integer[n];
        List<Rib> ribs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            group[i] = i;
            for (int j = 0; j < n; j++) {
                int len = graph[i][j];
                if (len == 0) continue;
                ribs.add(new Rib(i, j, len));
            }
        }
        return mst(group, ribs, n);
    }

    private Integer[][] mst(Integer[] group, List<Rib> ribs, int totalGroups) {
        Collections.sort(ribs);
        Integer[][] a = new Integer[group.length][group.length];
        for (Rib rib : ribs) {
            if (group[rib.v1] != group[rib.v2]) {
                group[rib.v2] = group[rib.v1];
                a[rib.v1][rib.v2] = rib.length;
                a[rib.v2][rib.v1] = rib.length;
                totalGroups--;
            }
            if (totalGroups == 1) {
                break;
            }
        }
        return a;
    }
}
class Rib implements Comparable<Rib> {
    int v1;
    int v2;

    int length;

    public Rib(int v1, int v2, int length) {
        this.v1 = v1;
        this.v2 = v2;
        this.length = length;
    }

    @Override
    public int compareTo(Rib o) {
        return Integer.compare(this.length, o.length);
    }
}
