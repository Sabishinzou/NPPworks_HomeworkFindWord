import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Sabishinzou on 05.05.14.
 */
public class TextParser {

    private static final char[] letters = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z',
            '.', ',', '!', '?', ';', ' ','\n','\r',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };

    private BufferedReader reader;
    private PrintWriter writer;
    private LinkedList<Character> filePart;
    private String word;
    private int word_length;
    private int current_position;
    private HashMap<Character, Integer> tableShifts;

    public TextParser(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
        current_position = 1;
    }

    public void work(String s) throws IOException {
        write(findWordPositions(s),s);
        System.out.println("Данные выведены в файл. Работа завершена.");
    }

    private void write(LinkedList<Integer> list, String s) {
        writer.println("Слово <" + s + "> встречается на позициях :");
        while (!list.isEmpty()) {
            writer.println(list.poll() + " ");
        }
        writer.close();
    }

    private LinkedList<Integer> findWordPositions(String s) throws IOException {
        setTableShifts(s);
        filePart = new LinkedList<Character>();
        LinkedList<Integer> positionList = readFind();
        return positionList;
    }

    private LinkedList<Integer> readFind() throws IOException {
        LinkedList<Integer> pos_list= new LinkedList<Integer>();
        int ci = -1;
        for (int i = 0; i < word_length; i++) {
            ci = reader.read();
            filePart.offer((char)ci);
            current_position++;
        }
        while (ci != -1) {
            if (word.charAt(word_length-1) == (char)ci) {
                boolean b = true;
                for (int i = word_length-2; i >= 0; i--) {
                    if (word.charAt(i) != filePart.get(i)) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    pos_list.offer(current_position - word_length);
                    ci = fileShift(word_length);
                }
                else
                    ci = fileShift(1);
            }
            else
                ci = fileShift(tableShifts.get((char)ci));
        }
        reader.close();
        return pos_list;
    }

    private int fileShift(int x) throws IOException {
        int ci = -1;
        for (int i = 0; i < x; i++) {
            filePart.poll();
            ci = reader.read();
            filePart.offer((char)ci);
            current_position++;
        }
        return ci;
    }

    private void setTableShifts(String s) {
        word = s;
        word_length = s.length();
        tableShifts = new HashMap<Character, Integer>();
        for (int i = 0; i < letters.length; i++) {
            tableShifts.put(letters[i],word_length);
        }
        for (int i = word_length-1; i >= 0 ; i--) {
            if (tableShifts.get(s.charAt(i)) == word_length) {
                tableShifts.put(s.charAt(i),word_length-1-i);
            }
        }
    }

}
