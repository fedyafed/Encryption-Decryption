import java.util.Scanner;

class Time {

    int hour;
    int minute;
    int second;

    private Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public static Time noon() {
        // write your code here
        return of(12, 0, 0);
    }

    public static Time midnight() {
        // write your code here
        return of(0, 0, 0);
    }

    public static Time ofSeconds(long seconds) {
        // write your code here
        long truncSec = seconds % (24 * 60 * 60);
        int h = (int) (truncSec / (60 * 60));
        truncSec = truncSec % (60 * 60);
        int m = (int) (truncSec / 60);
        int s = (int) (truncSec % 60);
        return of(h, m, s);
    }

    public static Time of(int hour, int minute, int second) {
        // write your code here
        if (hour < 0 || hour > 23) return null;
        if (minute < 0 || minute > 59) return null;
        if (second < 0 || second > 59) return null;
        return new Time(hour, minute, second);
    }
}

/* Do not change code below */
public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final String type = scanner.next();
        Time time = null;

        switch (type) {
            case "noon":
                time = Time.noon();
                break;
            case "midnight":
                time = Time.midnight();
                break;
            case "hms":
                int h = scanner.nextInt();
                int m = scanner.nextInt();
                int s = scanner.nextInt();
                time = Time.of(h, m, s);
                break;
            case "seconds":
                time = Time.ofSeconds(scanner.nextInt());
                break;
            default:
                time = null;
                break;
        }

        if (time == null) {
            System.out.println(time);
        } else {
            System.out.println(String.format("%s %s %s", time.hour, time.minute, time.second));
        }
    }
}