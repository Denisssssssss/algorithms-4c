import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import static java.lang.Math.max;

// 15
public class Strassen extends BaseSolution {

    /*
     * 900
     * 900
     * 900
     * 128
     */
    public static void main(String[] args) {

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int threshold = in.nextInt();
        int[][] a = new int[n][m];
        int[][] b = new int[m][k];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = j % 3;
            }
        }
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                b[i][j] = j % 3;
            }
        }
        int max = maxSize(a, b);
        int size = closestBinary(max);
        long start = System.currentTimeMillis();
        int[][] strassen = multiply(new Matrix(quadify(a, size)), new Matrix(quadify(b, size)), threshold);
        String strassenStat = String.format("Strassen: %s\n", System.currentTimeMillis() - start);
//        print(strassen, a.length, b[0].length);
        System.out.println();
        start = System.currentTimeMillis();
        int[][] ans = multiply(a, b);
        String defaultStat = String.format("Default: %s\n", System.currentTimeMillis() - start);
//        print(ans, a.length, b[0].length);
        System.out.println(strassenStat);
        System.out.println(defaultStat);
        System.out.printf("Arrays are equal: %s", Arrays.deepEquals(strassen, quadify(ans, strassen.length)));
    }

    private static void print(int[][] a, int rows, int columns) {
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                k = String.valueOf(max(k, a[i][j])).length();
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String s = String.valueOf(a[i][j]);
                int len = s.length();
                for (int l = 0; l < k - len; l++) {
                    s = s.concat(" ");
                }
                System.out.printf("%s ", s);
            }
            System.out.println();
        }
    }

    private static int[][] quadify(int[][] a, int k) {
        int[][] m = new int[k][k];
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(a[i], 0, m[i], 0, a[0].length);
        }
        return m;
    }

    private static int maxSize(int[][] a, int[][] b) {
        return max(max(a.length, a[0].length), max(b.length, b[0].length));
    }

    private static int closestBinary(int n) {
        int k = 1;
        while (k < n) {
            k <<= 1;
        }
        return k;
    }

    private static Matrix[][] split(int[][] a) {
        Matrix[][] splitedMatrix = new Matrix[2][2];
        int size = a.length / 2;
        int[][] a11 = new int[size][size];
        int[][] a12 = new int[size][size];
        int[][] a21 = new int[size][size];
        int[][] a22 = new int[size][size];
        for (int i = 0; i < a.length / 2; i++) {
            for (int j = 0; j < a.length / 2; j++) {
                a11[i][j] = a[i][j];
            }
            for (int j = a.length / 2; j < a.length; j++) {
                a12[i][j - a.length / 2] = a[i][j];
            }
        }
        for (int i = a.length / 2; i < a.length; i++) {
            for (int j = 0; j < a.length / 2; j++) {
                a21[i - a.length / 2][j] = a[i][j];
            }
            for (int j = a.length / 2; j < a.length; j++) {
                a22[i - a.length / 2][j - a.length / 2] = a[i][j];
            }
        }
        splitedMatrix[0][0] = new Matrix(a11);
        splitedMatrix[0][1] = new Matrix(a12);
        splitedMatrix[1][0] = new Matrix(a21);
        splitedMatrix[1][1] = new Matrix(a22);

        return splitedMatrix;
    }

    private static int[][] sum(int[][] a, int[][] b) {
        int[][] sum = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                sum[i][j] = a[i][j] + b[i][j];
            }
        }
        return sum;
    }

    private static int[][] sum(Matrix a, Matrix b) {
        return sum(a.values, b.values);
    }

    private static int[][] sub(int[][] a, int[][] b) {
        int[][] sum = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                sum[i][j] = a[i][j] - b[i][j];
            }
        }
        return sum;
    }

    private static int[][] sub(Matrix a, Matrix b) {
        return sub(a.values, b.values);
    }

    public static int[][] multiply(Matrix a, Matrix b, int threshold) {
        if (a.len() <= threshold) {
            return multiply(a.values, b.values);
        }
        Matrix[][] m1 = split(a.values);
        Matrix[][] m2 = split(b.values);
        int[][] p1 = multiply(new Matrix(sum(m1[0][0], m1[1][1])), new Matrix(sum(m2[0][0], m2[1][1])), threshold);
        int[][] p2 = multiply(new Matrix(sum(m1[1][0], m1[1][1])), new Matrix(m2[0][0].values), threshold);
        int[][] p3 = multiply(m1[0][0], new Matrix(sub(m2[0][1], m2[1][1])), threshold);
        int[][] p4 = multiply(m1[1][1], new Matrix(sub(m2[1][0], m2[0][0])), threshold);
        int[][] p5 = multiply(new Matrix(sum(m1[0][0], m1[0][1])), m2[1][1], threshold);
        int[][] p6 = multiply(new Matrix(sub(m1[1][0], m1[0][0])), new Matrix(sum(m2[0][0], m2[0][1])), threshold);
        int[][] p7 = multiply(new Matrix(sub(m1[0][1], m1[1][1])), new Matrix(sum(m2[1][0], m2[1][1])), threshold);
        int[][] c11 = sum(sub(sum(p1, p4), p5), p7);
        int[][] c12 = sum(p3, p5);
        int[][] c21 = sum(p2, p4);
        int[][] c22 = sum(sum(sub(p1, p2), p3), p6);
        return collect(c11, c12, c21, c22);
    }

    private static int[][] multiply(int[][] a, int[][] b) {
        int[][] m = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                m[i][j] = multiply(a[i], b, j);
            }
        }
        return m;
    }

    private static int multiply(int[] a, int[][] b, int column) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i][column];
        }
        return sum;
    }

    private static int[][] collect(int[][] a, int[][] b, int[][] c, int[][] d) {
        int size = a.length;
        int[][] collected = new int[size * 2][size * 2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                collected[i][j] = a[i][j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = size; j < size * 2; j++) {
                collected[i][j] = b[i][j - size];
            }
        }
        for (int i = size; i < size * 2; i++) {
            for (int j = 0; j < size; j++) {
                collected[i][j] = c[i - size][j];
            }
        }
        for (int i = size; i < size * 2; i++) {
            for (int j = size; j < size * 2; j++) {
                collected[i][j] = d[i - size][j - size];
            }
        }
        return collected;
    }
}

class Matrix {
    int[][] values;

    public Matrix(int[][] values) {
        this.values = values;
    }

    public int len() {
        return values.length;
    }
}
