import static java.lang.Math.abs;

public class ConvexPolygon extends BaseSolution {

//    4
//    0 0 - true, 3 3 - false
//    0 0 0 2 2 2 2 0
    public static void main(String[] args) {
        int n = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();

        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            y[i] = in.nextInt();
        }
        System.out.println(isIn(a, b, x, y, gaussianSquare(x, y)));
    }

    public static double gaussianSquare(int[] x, int[] y) {
        int n = x.length;
        double sum = x[n - 1] * y[0];
        double sub = (-1) * x[0] * y[n - 1];
        for (int i = 0; i < n - 1; i++) {
            sum += x[i] * y[i + 1];
            sub += x[i + 1] * y[i];
        }
        return 0.5 * abs(sum - sub);
    }

    public static boolean isIn(int a, int b, int[] x, int[] y, double actualSquare) {
        double sq = 0;
        for (int i = 0; i < x.length - 1; i ++) {
            sq += gaussianSquare(new int[]{a, x[i], x[i + 1]}, new int[]{b, y[i], y[i + 1]});
        }
        return sq == actualSquare;
    }
}