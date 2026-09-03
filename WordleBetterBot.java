import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class WordleBetterBot {
    ArrayList<Word> remainingWords = new ArrayList<>();
    Turn[] progress = new Turn[6];
    int progressCount = 0;
    String[] possiblePatterns = new String[] { "ggggg", "ggggy", "ggggc", "gggyg", "gggyy", "gggyc", "gggcg", "gggcy", "gggcc", "ggygg", "ggygy", "ggygc", "ggyyg", "ggyyy", "ggyyc", "ggycg", "ggycy", "ggycc", "ggcgg", "ggcgy", "ggcgc", "ggcyg", "ggcyy", "ggcyc", "ggccg", "ggccy", "ggccc", "gyggg", "gyggy", "gyggc", "gygyg", "gygyy", "gygyc", "gygcg", "gygcy", "gygcc", "gyygg", "gyygy", "gyygc", "gyyyg", "gyyyy", "gyyyc", "gyycg", "gyycy", "gyycc", "gycgg", "gycgy", "gycgc", "gycyg", "gycyy", "gycyc", "gyccg", "gyccy", "gyccc", "gcggg", "gcggy", "gcggc", "gcgyg", "gcgyy", "gcgyc", "gcgcg", "gcgcy", "gcgcc", "gcygg", "gcygy", "gcygc", "gcyyg", "gcyyy", "gcyyc", "gcycg", "gcycy", "gcycc", "gccgg", "gccgy", "gccgc", "gccyg", "gccyy", "gccyc", "gcccg", "gcccy", "gcccc", "ygggg", "ygggy", "ygggc", "yggyg", "yggyy", "yggyc", "yggcg", "yggcy", "yggcc", "ygygg", "ygygy", "ygygc", "ygyyg", "ygyyy", "ygyyc", "ygycg", "ygycy", "ygycc", "ygcgg", "ygcgy", "ygcgc", "ygcyg", "ygcyy", "ygcyc", "ygccg", "ygccy", "ygccc", "yyggg", "yyggy", "yyggc", "yygyg", "yygyy", "yygyc", "yygcg", "yygcy", "yygcc", "yyygg", "yyygy", "yyygc", "yyyyg", "yyyyy", "yyyyc", "yyycg", "yyycy", "yyycc", "yycgg", "yycgy", "yycgc", "yycyg", "yycyy", "yycyc", "yyccg", "yyccy", "yyccc", "ycggg", "ycggy", "ycggc", "ycgyg", "ycgyy", "ycgyc", "ycgcg", "ycgcy", "ycgcc", "ycygg", "ycygy", "ycygc", "ycyyg", "ycyyy", "ycyyc", "ycycg", "ycycy", "ycycc", "yccgg", "yccgy", "yccgc", "yccyg", "yccyy", "yccyc", "ycccg", "ycccy", "cgggg", "cgggy", "cgggc", "cggyg", "cggyy", "cggyc", "cggcg", "cggcy", "cggcc", "cgygg", "cgygy", "cgygc", "cgyyg", "cgyyy", "cgyyc", "cgycg", "cgycy", "cgycc", "cgcgg", "cgcgy", "cgcgc", "cgcyg", "cgcyy", "cgcyc", "cgccg", "cgccy", "cgccc", "cyggg", "cyggy", "cyggc", "cygyg", "cygyy", "cygyc", "cygcg", "cygcy", "cygcc", "cyygg", "cyygy", "cyygc", "cyyyg", "cyyyy", "cyyyc", "cyycg", "cyycy", "cyycc", "cycgg", "cycgy", "cycgc", "cycyg", "cycyy", "cycyc", "cyccg", "cyccy", "ccggg", "ccggy", "ccggc", "ccgyg", "ccgyy", "ccgyc", "ccgcg", "ccgcy", "ccgcc", "ccygg", "ccygy", "ccygc", "ccyyg", "ccyyy", "ccyyc", "ccycg", "ccycy", "cccgg", "cccgy", "cccgc", "cccyg", "cccyy", "ccccg", "ccccc"};
    static void main() {
        WordleBetterBot wb = new WordleBetterBot();
        wb.wordInput();
        wb.gameLoop();
    }

    void gameLoop() {
        while (true) {
            if (progressCount == 7)
                break;
            // progress[progressCount++] = new Turn("arise".toCharArray(), "cyggg".toCharArray()); //todo fake
            for (int i = 0; i < remainingWords.size(); i++) {
                if (!legal(remainingWords.get(i), new Turn())) {
                    remainingWords.remove(i--); // check to see if i-- is right
                }
            }
            findBestMove();
            printList();
            System.out.print("Enter word >> ");
            char[] word = IO.readln().toCharArray();
            System.out.print("Enter colors >> ");
            char[] pattern = IO.readln().toCharArray();
            progress[progressCount++] = new Turn(word, pattern);
        }
    }

    void printList() {
        for (int i = 0; i < remainingWords.size(); i++) {
            remainingWords.get(i).printWord();
        }
    }

    void findBestMove() {
        int bestScore = Integer.MAX_VALUE;
        for (Word w : remainingWords) {
            int highestPeak = -1;
            for (String s : possiblePatterns) {
                int scoreForPattern = 0;
                for (Word w2 : remainingWords) {
                    if (w2.commonList) {
                        if (legal(w2, new Turn(w.word, s.toCharArray()))) {
                            scoreForPattern++;
                        }
                    }
                }
                highestPeak = Math.max(highestPeak, scoreForPattern);
                if (highestPeak > bestScore * 1.2)
                    break;
            }
            if (w.commonList) highestPeak -= .5;
            w.score = highestPeak;
            bestScore = Math.min(bestScore, highestPeak);
            // System.out.print("finished testing "); w.printWord(); // todo fake
        }
        remainingWords.sort(Comparator.comparingInt(a -> -a.score));
    }

    void wordInput() {
        Scanner reader = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("FullWordList.txt")));
        reader.useDelimiter("\n|\r\n");
        while (reader.hasNextLine()) {
            remainingWords.add(new Word(reader.next(), false));
        }
        reader = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("CommonWords.txt")));
        reader.useDelimiter("\n|\r\n");
        while (reader.hasNextLine()) {
            remainingWords.add(new Word(reader.next(), true));
        }
    }

    boolean legal(Word w, Turn newTurn) {
        progress[progressCount++] = newTurn;
        for (int p = 0; p < progressCount; p++) {
            for (int i = 0; i < progress[p].length(); i++) {
                if (progress[p].pattern[i] == 'g') {
                    if (contains(w, progress[p].word[i])) {
                        if (numLetters(w, progress[p].word[i]) != progress[p].numYellowGreen(progress[p].word[i])) {
                            progress[--progressCount] = null;
                            return false;
                        }
                    }
                }
                else if (progress[p].pattern[i] == 'y') {
                    if (!contains(w, progress[p].word[i])) {
                        progress[--progressCount] = null;
                        return false;
                    }
                    if (w.word[i] == progress[p].word[i]) {
                        progress[--progressCount] = null;
                        return false;
                    }
                }
                else {
                    if (w.word[i] != progress[p].word[i]) {
                        progress[--progressCount] = null;
                        return false;
                    }
                }
                if (numLetters(w, progress[p].word[i]) < progress[p].numYellowGreen(progress[p].word[i])) {
                    progress[--progressCount] = null;
                    return false;
                }
            }
        }
        progress[--progressCount] = null;
        return true;
    }

    boolean contains(Word w, char c) {
        for (char c2 : w.word)
            if (c == c2)
                return true;
        return false;
    }

    int numLetters(Word w, char c) {
        int count = 0;
        for (char c2 : w.word) {
            if (c == c2)
                count++;
        }
        return count;
    }
}