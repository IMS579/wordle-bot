public class Word {
    char[] word;
    boolean commonList;
    int score;
    
    Word(String word, boolean commonList) {
        this.word = word.toCharArray();
        this.commonList = commonList;
    }

    void printWord() {
        for (char c : word)
            System.out.print((char) (c + ((commonList) ? -32 : 0)));
        System.out.println("\t " + score);
    }
}
