package pl.coderslab;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        String[] options = {"add", "remove", "list", "exit"};
        printMenu(options);
        handleInput(options);
    }

    public static void printMenu(String[] options) {
        System.out.println("Please select an option");
        for (String option : options) {
            System.out.println(option);
        }
    }

    public static int selectOption(String[] options) {
        while (true) {
            String input = getInput();
            for (int i = 0; i < options.length; i++) {
                if (checkIfSame(options[i], input)) {
                    return i;
                }
            }
            System.out.println("Please select a valid option");
        }
    }

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static boolean checkIfSame(String option, String input) {
        return option.equalsIgnoreCase(input);
    }

    public static void handleInput(String[] options) {
        switch (selectOption(options)) {
            case 0:
                addTaskOption();
                break;
            case 1:
                removeTaskOption();
                break;
            case 2:
                listTasksOption();
                break;
            case 3:
                exitOption();
                break;
        }
    }
    public static void addTaskOption() {

    }
    public static void removeTaskOption() {

    }
    public static void listTasksOption() {

    }
    public static void exitOption() {

    }
}
