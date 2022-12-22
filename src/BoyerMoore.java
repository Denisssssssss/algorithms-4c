import java.util.HashMap;
import java.util.Map;

public class BoyerMoore extends BaseSolution {

    public static void main(String[] args) {
        String[] text = in.nextLine().split("");
        String[] template = in.nextLine().split("");
        Map<String, Integer> letterPlacesMap = letterPlacesMap(template);
        int size = template.length;
        int step = 1;
        boolean matches;
        for (int i = 0; i <= text.length - size; i += step) {
            matches = true;
            for (int j = i + size - 1; j >= i; j--) {
                if (!text[j].equals(template[j - i])) {
                    step = letterPlacesMap.getOrDefault(text[j], size);
                    matches = false;
                    break;
                }
            }
            if (matches) {
                showAnswer(text, i, i + size - 1);
                return;
            }
        }
        System.out.println("No match");
    }

    public static Map<String, Integer> letterPlacesMap(String[] s) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = s.length - 2; i >= 0; i--) {
            if (map.containsKey(s[i])) {
                continue;
            }
            map.put(s[i], s.length - 1 - i);
        }
        if (!map.containsKey(s[s.length - 1])) {
            map.put(s[s.length - 1], s.length);
        }
        return map;
    }

    public static void showAnswer(String[] text, int start, int end) {
        System.out.printf("Matches in [%s, %s]\n", start, end);
        System.out.println(String.join("", text));
        for (int i = 0; i < text.length; i++) {
            if (i < start || i > end) {
                System.out.print(" ");
            } else {
                System.out.print("^");
            }
        }
    }
}
