package org.davidgeorgehope;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter your sentence:");
            String input = scanner.nextLine();
            Main main = new Main();
            int wordCount = main.countWords(input);
            System.out.println("The input contains " + wordCount + " word(s).");
        }
    }
    public int countWords(String input) {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }
}
