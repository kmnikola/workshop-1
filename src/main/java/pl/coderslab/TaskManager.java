package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        String[][] tasks = loadTasksFromFile();
        while (true) {
            printMenu();
            switch (handleOptionSelectionInput()) {
                case 0:
                    tasks = addTask(tasks);
                    break;
                case 1:
                    tasks = removeTask(tasks);
                    break;
                case 2:
                    listTasks(tasks);
                    break;
                case 3:
                    exitOption(tasks);
                    break;
                default:
                    System.out.println("This menu option does not exist (yet).");
                    break;
            }
        }
    }

    public static String[][] loadTasksFromFile() {
        String[][] tasks = new String[0][];
        try {
            for (String line : Files.readAllLines(getFilePath())) {
                String[] taskData = line.split(", ");
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = taskData;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ConsoleColors.RED + "Error while loading tasks from file. No tasks loaded from file." + ConsoleColors.RESET);
        }
        return tasks;
    }

    public static Path getFilePath() {
        return Paths.get("tasks.csv");
    }

    public static void printMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (String option : loadMenuOptions()) {
            System.out.println(option);
        }
    }

    public static String[] loadMenuOptions() {
        String[] options = {"add", "remove", "list", "exit"};
        return options;
    }

    public static int handleOptionSelectionInput() {
        while (true) {
            String input = getInput();
            for (int i = 0; i < loadMenuOptions().length; i++) {
                if (loadMenuOptions()[i].equalsIgnoreCase(input)) {
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

    public static String[][] addTask(String[][] tasks) {
        return updateTasks(getTaskData(), tasks);
    }

    public static String[] getTaskData() {
        String[][] taskProperties = loadTaskProperties();
        String[] taskData = new String[taskProperties[0].length];
        for (int i = 0; i <= taskProperties.length; i++) {
            System.out.println("Please add task " + taskProperties[0][i] + " (" + taskProperties[1][i] + ")");
            taskData[i] = getInput();
        }
        return taskData;
    }

    public static String[][] loadTaskProperties() {
        String[][] taskProperties = {{"description", "due date", "importance"}, {"text", "date", "true/false"}};
        return taskProperties;
    }

    public static String[][] updateTasks(String[] taskData, String[][] tasks) {
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = taskData;
        return tasks;
    }

    public static String[][] removeTask(String[][] tasks) {
        System.out.println("Please select a number to remove");
        int taskNumber;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0 && input < tasks.length) {
                    taskNumber = input;
                    break;
                } else {
                    System.out.println("Please select a valid task number");
                }
            } else {
                System.out.println("Please enter a number");
            }
        }
        System.out.println(ConsoleColors.RED + "Task number " + taskNumber + " succesfully removed." + ConsoleColors.RESET);
        return ArrayUtils.remove(tasks, taskNumber);
    }

    public static void listTasks(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + ":");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(" " + tasks[i][j]);
            }
            System.out.println();
        }
        if (tasks.length == 0) {
            System.out.println(ConsoleColors.RED + "No tasks to display" + ConsoleColors.RESET);
        }
    }

    public static void exitOption(String[][] tasks) {
        saveTasksToFile(tasks);
        System.out.println(ConsoleColors.RED + "Exiting task manager");
        System.exit(0);
    }

    public static void saveTasksToFile(String[][] tasks) {
        StringBuilder taskList = new StringBuilder();
        try {
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    taskList.append(tasks[i][j]);
                    if (j < tasks[i].length - 1) {
                        taskList.append(", ");
                    } else {
                        taskList.append("\n");
                    }
                }
            }
            Files.writeString(getFilePath(), taskList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ConsoleColors.RED + "Tasks saved to " + getFilePath() + ConsoleColors.RESET);
    }
}