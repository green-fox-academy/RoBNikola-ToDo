import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoApp {
    static Path filePath = Paths.get("src/text.txt");
    static List<String> lines = new ArrayList<String>();

    //  Command line arguments:
//    -l   Lists all the tasks from list
//    -a   + task (adds new task to list)
//    -r   + numbered (Removes the task from the line numbered)
////    -c   Completes an task (not done)

    public static void main(String[] args) throws IOException {
        try {
            lines = Files.readAllLines(filePath);
            if (args[0].equals("-a") && args.length == 1) {
                System.out.println("Unable to add: no task provided");
            } else if (args[0].equals("-a") && args.length == 2) {
                System.out.println(args[1]);
                String theArg = "\n" + "[ ] " + args[1];
                Files.write(filePath, theArg.getBytes(), StandardOpenOption.APPEND);
            } else if (args[0].equals("-a") && args.length >= 2) {
                System.out.println("Unable to add: more than 1 task provided - try in/between qoute marks");
            } else if (args[0].equals("-l") && lines.size() >= 1) {
                listMyTasks(args);
            } else if (args[0].equals("-l")) {
                System.out.println("No todos for today! :)");
            } else if (args[0].equals("-r") && args.length == 2) {
                try {
                    int i = Integer.parseInt(args[1]);
                    removeThisLine(i - 1);
                } catch (NumberFormatException e) {
                    System.out.println("Unable to remove: index is not a number");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("There is no " + args[1] + ". line. Unable to remove: index is out of bound");
                }
            } else if (args[0].equals("-r")) {
                System.out.println("Unable to remove: no index provided");
            } else if (args[0].equals("-c") && lines.size() >= 2 && args.length == 2) {
                int i = Integer.parseInt(args[1]);
                checkTask(i - 1);
            } else {
                System.out.println("Unsupported argument");
                usageInformation();
            }
        } catch (NoSuchFileException e) {
            System.out.println("Uh-oh, could not read the file!");
        } catch (IndexOutOfBoundsException e) {
            usageInformation();
        }
    }

    public static void checkTask(int i) throws IOException {
//        lines.set(i, "[x] " + lines);
//        lines.add(i, "[x] ");
        Files.write(filePath, lines);
    }

    public static void removeThisLine(int i) throws IOException {
        lines.remove(i);
        Files.write(filePath, lines);
    }

    public static void listMyTasks(String[] args) {
        int index = 0;
        for (String tasks : lines) {
            System.out.print(++index + ". ");
            System.out.println(tasks);
        }
    }

    public static void usageInformation() {
        System.out.println();
        System.out.println("Command Line Todo application");
        System.out.println("=============================");
        System.out.println();
        System.out.println("Command line arguments:");
        System.out.println("    -l   Lists all the tasks");
        System.out.println("    -a   Adds a new task");
        System.out.println("    -r   Removes a task");
        System.out.println("    -c   Completes a task");
    }
}
