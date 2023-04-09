import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("""
                Виберіть дію:
                DIR - вивести каталог
                STAT - вивести дані про використання дискової пам'яті
                TIME - встановити поточну дату""");
        switch (in.next()) {
            case "DIR" -> Methods.DIR();
            case "STAT" -> Methods.STAT();
            case "TIME" -> Methods.TIME();
        }
    }
}
