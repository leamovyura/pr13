import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    static final int MAX_ENTRIES = 50;
    static LocalDate[] dates = new LocalDate[MAX_ENTRIES];
    static String[] entries = new String[MAX_ENTRIES];
    static int count = 0;

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEntry(scanner);
                    break;
                case "2":
                    deleteEntry(scanner);
                    break;
                case "3":
                    listEntries();
                    break;
                case "4":
                    exit = true;
                    System.out.println("Вихід з програми.");
                    break;
                default:
                    System.out.println("Невірний вибір.");
            }
        }
        scanner.close();
    }

    static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1 Додати запис");
        System.out.println("2 Видалити запис за датою");
        System.out.println("3 Переглянути всі записи");
        System.out.println("4 Вийти");
        System.out.print("Ваш вибір: ");
    }

    static void addEntry(Scanner scanner) {
        if (count >= MAX_ENTRIES) {
            System.out.println("Щоденник заповнений. Неможливо додати новий запис.");
            return;
        }

        LocalDate date = null;
        while (date == null) {
            System.out.print("Введіть дату: ");
            String dateStr = scanner.nextLine();
            try {
                date = LocalDate.parse(dateStr, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Некоректний формат дати.");
            }
        }

        System.out.println("Введіть текст запису (для завершення введіть порожній рядок):");
        String text = "";
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            text += line + "\n";
        }
        text = text.trim();

        dates[count] = date;
        entries[count] = text;
        count++;
        System.out.println("Запис додано");
    }

    static void deleteEntry(Scanner scanner) {
        if (count == 0) {
            System.out.println("Щоденник порожній");
            return;
        }
        LocalDate date = null;
        while (date == null) {
            System.out.print("Введіть дату запису для видалення: ");
            String dateStr = scanner.nextLine();
            try {
                date = LocalDate.parse(dateStr, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Некоректний формат дати.");
            }
        }

        int index = -1;
        for (int i = 0; i < count; i++) {
            if (dates[i].equals(date)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Запис з такою датою не знайдено");
            return;
        }

        for (int i = index; i < count - 1; i++) {
            dates[i] = dates[i + 1];
            entries[i] = entries[i + 1];
        }
        dates[count - 1] = null;
        entries[count - 1] = null;
        count--;
        System.out.println("Запис видалено");
    }

    static void listEntries() {
        if (count == 0) {
            System.out.println("Щоденник порожній");
            return;
        }
        System.out.println("Всі записи:");
        for (int i = 0; i < count; i++) {
            System.out.println("Дата: " + dates[i].format(FORMATTER));
            System.out.println("Текст запису:");
            System.out.println(entries[i]);
            System.out.println("------------------------");
        }
    }
}
