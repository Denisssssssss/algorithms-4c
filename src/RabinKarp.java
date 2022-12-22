public class RabinKarp extends BaseSolution {

    public static void main(String[] args) {
        String[] text = in.nextLine().split("");
        String s = in.nextLine();
        int hash = s.hashCode();

        for (int i = 0; i <= text.length - s.length(); i++) {
            String current = extractString(i, s.length(), text);
            if (current.hashCode() == hash) {
                if (s.equals(current)) {
                    showAnswer(text, i, i + s.length() - 1);
                    return;
                }
            }
        }

        System.out.println("No match");
    }

    public static String extractString(int index, int length, String[] text) {
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < index + length; i++) {
            sb.append(text[i]);
        }
        return sb.toString();
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
