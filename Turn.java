public class Turn {
    char[] word;
    char[] pattern;
    Turn(char[] word, char[] pattern) {
        this.word = word;
        this.pattern = pattern;
    }
    Turn() {
        word = new char[0];
        pattern = new char[0];
    }
    int length() {
        return word.length;
    }
    int numYellowGreen(char c) {
        int count = 0;
        for (int i = 0; i < word.length; i++) {
            if (word[i] == c) {
                if (pattern[i] == 'y' || pattern[i] == 'c')
                    count++;
            }
        }
        return count;
    }
}
