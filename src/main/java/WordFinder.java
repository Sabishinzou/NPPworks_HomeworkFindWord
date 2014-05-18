import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Используется для парсинга файла, и обнаружения всех позиций
 * искомого слова.
 * Для поиска символьной последовательности в тексте используется
 * алгоритм Бойера-Мура. С ним необходимо ознакомиться если возникают
 * проблемы с пониманием таких моментов как сдвиг ао файлу, таблица сдвигов.
 * Всю необходимую информацию можно почерпнуть здесь:
 * -link http://delphiworld.narod.ru/base/search_text.html
 *
 * Created by Sabishinzou on 05.05.14.
 */
public class WordFinder {

    /**
     * Алфавит символов текста, которые могут встречаться.
     */
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
    /**
     * @param BufferedReader поток для файла в котором производися поиск
     * @param PrintWriter аналогично вывод в лругой файл списка позиций
     * @param filePart часть файла которая анализируется методами в данный момент времени
     *                 по сути это индексированная очередь символов.
     * @param word слово для поиска
     * @param word_length длина слова для поиска
     * @param current_position текущая позиция при чтении файла
     * @param tableShifts таблица сдвигов(см. описание алгоритма Бойера-Мура)
     */
    private BufferedReader reader;
    private PrintWriter writer;
    private LinkedList<Character> filePart;
    private String word;
    private int word_length;
    private int current_position;
    private HashMap<Character, Integer> tableShifts;

    /**
     * Инициализация начальных параметров для работы с файлами
     * @param reader для чтения из файла
     * @param writer для записи в новый файл
     */
    public WordFinder(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
        current_position = 1;
    }

    /**
     * Основной метод, где в метод write записывает в файл список, полученный методом findWordPositions
     * @param s слово для поиска
     */
    public void work(String s) throws IOException {
        write(findWordPositions(s),s);
        System.out.println("Данные выведены в файл. Работа завершена.");
    }

    /**
     * вывод в файл списка позиций искомого слова
     * @param list тот список который выводится
     * @param s искомое слово
     */
    private void write(LinkedList<Integer> list, String s) {
        writer.println("Слово <" + s + "> встречается на позициях :");
        while (!list.isEmpty()) {
            writer.println(list.poll() + " ");
        }
        writer.close();
    }

    /**
     * построение таблицы сдвигов и запуск реализации алгоритма
     * @param s искомое слово
     * @return искомый список
     */
    private LinkedList<Integer> findWordPositions(String s) throws IOException {
        setTableShifts(s);
        filePart = new LinkedList<Character>();
        LinkedList<Integer> positionList = readFind();
        return positionList;
    }

    /**
     * Реализация алгоритма Бойера-Мура
     * @return список позиций искомого слова
     */
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

    /**
     * Выполняет сдвиг в файле на определенное число позиций, соответственно меняет текущую позицию
     * @param x величина сдвига
     * @return символ текущей позиции
     */
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

    /**
     * строит таблицу сдвигов, записывает искомое слово в соответствующую переменную
     * @param s искомое слово
     */
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
