import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Graham extends BaseSolution {
    /**
     * тесты
     *
     * 7
     * 0 0
     * 0 3
     * 3 0
     * 1 2
     * 1 1
     * 2 1
     * 3 3
     *
     * 6
     * 0 0
     * 0 3
     * 3 0
     * 1 2
     * 2 1
     * 3 3
     *
     * 5
     * 0 0
     * 0 3
     * 3 0
     * 3 3
     * 1 1
     *
     * 6
     * 0 0
     * 3 0
     * 0 3
     * 2 2
     * 3 5
     * 5 1
     */
    public static void main(String[] args) {
        int n = in.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.nextInt(), in.nextInt());
        }
        Point start = getStart(points);
        sort(points, start);
        LinkedList<Point> stack = new LinkedList <>();
        stack.push(points[0]);
        stack.push(points[1]);
        stack.push(points[2]);
        for (int i = 3; i < points.length; i++) {
            boolean stop = false;
            while (!stop) {
                Point p = stack.pop();
                Point p1 = stack.peek();
                if (!left(p1, p, points[i])) {
                    stop = true;
                    stack.push(p);
                    stack.push(points[i]);
                }
            }
        }
        System.out.println(stack);

    }

    public static Point getStart(Point[] points) {
        Point start = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i].x < start.x) {
                start = points[i];
            } else if (points[i].x == start.x) {
                if (points[i].y <= start.y) {
                    start = points[i];
                }
            }
        }
        return start;
    }

    public static void sort(Point[] points, Point start) {
        Arrays.sort(points, new AngleComparator(start));
    }

    public static boolean left(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p2.y) - (p2.y - p1.y) * (p3.x - p2.x) >= 0;
    }

    //p1 p2
    //p2 p3
}

class Point {

    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s}", x, y);
    }
}

class AngleComparator implements Comparator<Point> {

    private Point left;

    public AngleComparator(Point left) {
        this.left = left;
    }

    @Override
    public int compare(Point o1, Point o2) {
        int result = 0;
        if (o1 == left) {
            result = -1;
        } else if (o2 == left) {
            result = 1;
        } else {
            result = -Double.compare(
                    Math.atan2(o1.y - left.y, o1.x - left.x),
                    Math.atan2(o2.y - left.y, o2.x - left.x)
            );
        }
        return result;
    }
}
