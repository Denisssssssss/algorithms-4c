import static java.lang.Math.abs;

public class PolygonSquare extends BaseSolution {

    /*
     * 5
     * 3 4
     * 5 11
     * 12 8
     * 9 5
     * 5 6
     *
     * ans = 30
     */
    public static void main(String[] args) {
        int n = in.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            y[i] = in.nextInt();
        }
        System.out.println(gaussianSquare(x, y));
    }

    public static double gaussianSquare(int[] x, int[] y) {
        int n = x.length;
        double sum = 0;
        double sub = 0;
        for (int i = 0; i < n; i++) {
            sum += x[i] * y[(i + 1) % n];
            sub -= x[(i + 1) % n] * y[i];
        }
        return 0.5 * abs(sum + sub);
    }
}
