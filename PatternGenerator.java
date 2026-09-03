public class PatternGenerator {
    final static char[] chars = new char[] {'g', 'y', 'c'};
    public static void main(String[] args) {
        System.out.print("{ ");
        addLetter(new StringBuilder());
        System.out.println(" }");
    }

    static void addLetter(StringBuilder word) {
        if (word.length() == 5) {
            System.out.print("\"" + word.toString() + "\", ");
            return;
        }
        for (int i = 0; i < 3; i++) {
            word.append(chars[i]);
            addLetter(word);
            word.deleteCharAt(word.length() - 1);
        }
    }
}
