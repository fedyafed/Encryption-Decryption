package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author fedyafed 26.04.19 23:22
 */
public class Test {


    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(new File("/home/fedya/Загрузки/dataset_91069.txt"))) {
            scanner.nextLine();
            int year = scanner.nextInt();;
            long prevPopulation = getPopulation(scanner);
            long diff = 0;

            while (scanner.hasNextInt()) {
                int currentYear = scanner.nextInt();
                long currentPopulation = getPopulation(scanner);
                long newDiff = currentPopulation - prevPopulation;
                if (newDiff > diff) {
                    diff = newDiff;
                    year = currentYear;
                }
                prevPopulation = currentPopulation;
            }
            System.out.println(year);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static long getPopulation(Scanner scanner) {
        String s = scanner.next();
        return Long.parseLong(s.replace(",", ""));
    }
}
