import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrapezeTape extends BaseSolution {

    public static void main(String[] args) {
        int n = in.nextInt();
        int b;
        int c;
        int d;
        Map<Integer, List<Integer>> left = new HashMap<>();
        Map<Integer, List<Integer>> right = new HashMap<>();
        boolean hasRightBorder = false;
        boolean hasLeftBorder = false;
        for (int i = 0; i < n; i++) {
            b = in.nextInt();
            c = in.nextInt();
            d = in.nextInt();
            if (!hasLeftBorder) {
                hasLeftBorder = b == 0;
            }
            if (!hasRightBorder) {
                hasRightBorder = c == d;
            }
            if (!left.containsKey(b)) {
                left.put(b, new ArrayList<>(List.of(i)));
            } else {
                left.get(b).add(i);
            }
            if (!right.containsKey(c - d)) {
                right.put(c - d, new ArrayList<>(List.of(i)));
            } else {
                right.get(c - d).add(i);
            }
        }
        if (!(hasLeftBorder && hasRightBorder)) {
            System.out.println(0);
            return;
        }
        left.keySet().forEach(
                key -> {
                    if (right.containsKey(key)) {
                        List<Integer> rnums = right.get(key);
                        List<Integer> lnums = left.get(key);
                        if (lnums.size() == 1 && rnums.size() == 1 && key != 0) {
                            System.out.println(0);
                            return;
                        }

                    }
                }
        );
    }
}
