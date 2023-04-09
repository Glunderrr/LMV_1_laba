import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
class Methods {
    private final static Scanner in = new Scanner(System.in);

    private static void retreat() {
        System.out.println("\n");
    }

    static void DIR() {
        retreat();
        System.out.println("Введіть шлях до директорії:");
        File[] files = new File(in.nextLine()).listFiles();
        if (files != null) {
            for (File item : files)
                System.out.println(item.getName());
        } else System.out.println("Не вірно вказаний шлях.");
    }

    static void STAT() throws IOException {
        retreat();
        System.out.println("Введіть шлях до директорії:");
        Path filePath = Path.of(in.nextLine());
        BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);

        System.out.println("Ім'я: " + filePath.getFileName() +
                "\nШлях: " + filePath +
                "\nРозмір: " + attributes.size() + " байт" +
                "\nДата створення: " + formatDate(attributes.creationTime().toMillis()) +
                "\nДата модифікації: " + formatDate(attributes.lastModifiedTime().toMillis()) +
                "\nДата доступу: " + formatDate(attributes.lastAccessTime().toMillis()) +
                "\nТип: " + (attributes.isDirectory() ? "Папка" : "Файл"));
    }

    static void TIME() throws InterruptedException, IOException {
        retreat();

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("Поточний час " + currentTime.format(formatter));

        System.out.println("Введіть почергово: години, хвилини, секунди");
        LocalTime newTime = LocalTime.of(in.nextByte(), in.nextByte(), in.nextByte());
        System.out.println("Новий час: " + newTime);

        Process proc = Runtime.getRuntime().exec("cmd /c time " + newTime.toString());
        int exitVal = proc.waitFor();

        if (exitVal == 0) {
            System.out.println("Час успішно змінено на " + newTime);
        } else {
            System.out.println("Помилка при зміні часу");
        }
    }

    private static String formatDate(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(new Date(millis));
    }

    static void REN() {
        System.out.println("Приклад імені файлу: диск:\\папка\\назва.формат(якщо це не папка)");

        System.out.println("Старе ім'я файлу:");
        String oldFileName = in.nextLine();
        System.out.println("Нове ім'я файлу:");
        String newFileName = in.nextLine();

        File oldFile = new File(oldFileName);

        if (!oldFile.exists()) {
            System.out.println("Файлу з таким ім'ям не існує: " + oldFileName);
            return;
        }

        File newFile = new File(newFileName);

        if (newFile.exists()) {
            System.out.println("Файл з таким ім'ям уже існує: " + newFileName);
            return;
        }

        if (oldFile.renameTo(newFile)) {
            System.out.println("Файл було успішно перейменовано.");
        } else {
            System.out.println("Файл не перейменовано.");
        }
    }

    static void DATE() {
        int day, month, year;
        System.out.println("Рік:");
        year = in.nextInt();
        System.out.println("Місяць:");
        month = in.nextInt();
        System.out.println("День:");
        day = in.nextInt();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);


        System.out.println("Дата змінена на: " + calendar.getTime());
        try {
            Runtime.getRuntime().exec("cmd /c date "
                    + (calendar.get(Calendar.DAY_OF_MONTH))
                    + "-" + calendar.get(Calendar.MONTH)
                    + "-" + calendar.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void PIP() {
        String[] path = new String[2];
        System.out.println("Введіть шлях до файлу:");
        path[0] = in.nextLine();
        System.out.println("Введіть шлях куди копіюємо:");
        path[1] = in.nextLine();
        Path source = Path.of(path[0]);
        Path destination = Path.of(path[1]);

        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл успішно скопійовано.");
        } catch (IOException e) {
            System.out.println("Не вдалось скопіювати файл");
        }
    }
}