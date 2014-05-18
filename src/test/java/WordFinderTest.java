import org.junit.Assert;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Sabishinzou on 18.05.14.
 */
public class WordFinderTest {
    private WordFinder wordFinder;
    private BufferedReader reader;
    private  PrintWriter writer;

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void testWork() throws Exception {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream("data\\input_test.txt")));
        writer = new PrintWriter(new File("data\\output_test.txt"));
        wordFinder = new WordFinder(reader,writer);
        wordFinder.work("word");
        if (!compareFiles(new File("data\\output_test.txt"), new File("data\\output_test_right_word.txt"))) {
            Assert.fail("Ошибка. Выходные даные неверны или неточны.");
        }

        reader = new BufferedReader(new InputStreamReader(new FileInputStream("data\\input_test.txt")));
        writer = new PrintWriter(new File("data\\output_test.txt"));
        wordFinder = new WordFinder(reader,writer);
        wordFinder.work("hell");
        if (!compareFiles(new File("data\\output_test.txt"), new File("data\\output_test_right_hell.txt"))) {
            Assert.fail("Ошибка. Выходные даные неверны или неточны.");
        }

        reader = new BufferedReader(new InputStreamReader(new FileInputStream("data\\input_test.txt")));
        writer = new PrintWriter(new File("data\\output_test.txt"));
        wordFinder = new WordFinder(reader,writer);
        wordFinder.work("pa r");
        if (!compareFiles(new File("data\\output_test.txt"), new File("data\\output_test_right_pa r.txt"))) {
            Assert.fail("Ошибка. Выходные даные неверны или неточны.");
        }
    }

    private static boolean compareFiles(File f1, File f2) throws IOException {
        FileReader fR1 = new FileReader(f1);
        FileReader fR2 = new FileReader(f2);
        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);

        String line1 = null;
        String line2 = null;
        boolean flag = true;
        while ((flag == true) &&((line1 = reader1.readLine()) != null)&&((line2 = reader2.readLine()) != null))
        {
            if (!line1.equalsIgnoreCase(line2))
                flag = false;
            else
                flag = true;
        }
        reader1.close();
        reader2.close();
        return flag;
    }
}
