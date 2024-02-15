import mgmt.TaskManager;
import models.Task;
import models.TaskCategory;

import java.util.*;

public class ConsoleUI {
    private static TaskManager taskManager = new TaskManager();

    public static void start() {

        Scanner scanner = new Scanner(System.in);

        while(true) {
            displayMenu();
            int a = scanner.nextInt();
            scanner.nextLine();

            if (a == 1) {
                addTask(scanner);
            }
            else if (a == 2) {
                updateTask(scanner);
            }
            else if (a == 3) {
                deleteTask(scanner);
            }
            else if (a == 4) {
                viewAllTasks();
            }
            else if (a == 5) {
                System.out.println("BB!");
                System.exit(0);
            }
            else {
                System.out.println("Qate bold");
            }
        }
    }
    private static void displayMenu() {
        System.out.println("----- Task Manager Menu -----");
        System.out.println("1. Add Task");
        System.out.println("2. Update Task");
        System.out.println("3. Delete Task");
        System.out.println("4. View All Tasks");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter Task Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        System.out.print("Enter Deadline: ");
        String deadline = scanner.nextLine();

        System.out.print("Is it a categorized task? (y/n): ");
        String isCategorized = scanner.nextLine().toLowerCase();

        if (isCategorized.equals("y")) {
            System.out.print("Enter Category: ");
            String category = scanner.nextLine();
            TaskCategory task = new TaskCategory(name, description, deadline, category);
            taskManager.addTask(task);
        }
        else {
            Task task = new Task(name, description, deadline);
            taskManager.addTask(task);
        }

        System.out.println("Task added successfully!");
    }

    private static void updateTask(Scanner scanner) {
        System.out.print("Enter ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Task task = taskManager.getTask(id);

        if (task != null) {
            System.out.print("Enter new Task Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new Description: ");
            String description = scanner.nextLine();

            System.out.print("Enter new Deadline: ");
            String deadline = scanner.nextLine();

            if (task instanceof TaskCategory) {
                System.out.print("Enter new Category: ");
                String category = scanner.nextLine();
                ((TaskCategory) task).setCategory(category);
            }

            task.setName(name);
            task.setDescription(description);
            task.setDeadline(deadline);

            taskManager.updateTask(task);

            System.out.println("Task updated successfully!");
        }
        else {
            System.out.println("Task not found.");
        }
    }

    private static void deleteTask(Scanner scanner) {
        System.out.print("Enter Task ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Task task = taskManager.getTask(id);

        if (task != null) {
            taskManager.deleteTask(id);
            System.out.println("Task deleted successfully!");
        }
        else {
            System.out.println("Task not found.");
        }
    }

    private static void viewAllTasks() {
        List<Task> tasks = taskManager.getAllTasks();
        //tasks.sort(Comparator.comparing(Task::getName));
        if (tasks.isEmpty()) {
            System.out.println("Netu taskov");
        }
        else {
            System.out.println("----- All Tasks -----");

            for(Task task : tasks) {
                System.out.println("ID: " + task.getId());
                System.out.println("Name: " + task.getName());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Deadline: " + task.getDeadline());

                if (task instanceof TaskCategory) {
                    System.out.println("Category: " + ((TaskCategory) task).getCategory());
                }

                System.out.println();
            }
        }
    }

}
