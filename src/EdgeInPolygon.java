import static java.lang.Math.abs;

public class EdgeInPolygon extends BaseSolution {

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
        System.out.println(isIn(a, b, x, y, PolygonSquare.gaussianSquare(x, y)));
    }

    public static boolean isIn(int a, int b, int[] x, int[] y, double actualSquare) {
        double sq = 0;
        for (int i = 0; i < x.length - 1; i ++) {
            sq += PolygonSquare.gaussianSquare(new int[]{a, x[i], x[i + 1]}, new int[]{b, y[i], y[i + 1]});
        }
        return sq == actualSquare;
    }
}