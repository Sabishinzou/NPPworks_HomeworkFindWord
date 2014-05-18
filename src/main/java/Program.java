import java.io.*;
import java.util.Scanner;

/**
 * Created by Sabishinzou on 05.05.14.
 */

public class Program {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data\\input.txt")));
        PrintWriter writer = new PrintWriter(new File("data\\output.txt"));

        WordFinder wordFinder = new WordFinder(reader, writer);
        System.out.println("Введите символьную последовательность(слово):");
        Scanner scanner = new Scanner(System.in);
        try {
            wordFinder.work(scanner.nextLine());
        } catch (NullPointerException npe) {
            System.out.println("Введены некорректные данные.");
        }
    }
}
