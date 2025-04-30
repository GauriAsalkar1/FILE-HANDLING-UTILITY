import java.io.*;
import java.util.Scanner;

public class FileOperations {
    private static final Scanner scanner = new Scanner(System.in);
    private static String currentFilePath = "default.txt";

    public static void main(String[] args) {
        System.out.println("\nFILE HANDLING UTILITY");
        
        while (true) {
            System.out.println("\nMAIN MENU:");
            System.out.println("1. Set/Create File");
            System.out.println("2. Write to File");
            System.out.println("3. Read from File");
            System.out.println("4. Modify File Content");
            System.out.println("5. Delete File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1: createOrSetFile(); break;
                case 2: writeToFile(); break;
                case 3: readFromFile(); break;
                case 4: modifyFile(); break;
                case 5: deleteFile(); break;
                case 6: exitProgram();
            }
        }
    }

    private static void createOrSetFile() {
        System.out.print("\nEnter file name (e.g., 'data.txt'): ");
        currentFilePath = scanner.nextLine();
        
        File file = new File(currentFilePath);
        if (file.exists()) {
            System.out.println("Using existing file: " + currentFilePath);
        } else {
            try {
                if (file.createNewFile()) {
                    System.out.println("Created new file: " + currentFilePath);
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }

    private static void writeToFile() {
        if (!checkFileExists()) return;
        
        System.out.print("\nEnter text to write: ");
        String content = scanner.nextLine();
        
        try (FileWriter writer = new FileWriter(currentFilePath)) {
            writer.write(content);
            System.out.println("Successfully wrote to file");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void readFromFile() {
        if (!checkFileExists()) return;
        
        System.out.println("\nFile Content:");
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void modifyFile() {
        if (!checkFileExists()) return;
        
        System.out.print("\nEnter text to replace: ");
        String oldText = scanner.nextLine();
        System.out.print("Enter replacement text: ");
        String newText = scanner.nextLine();
        
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line.replace(oldText, newText)).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
            writer.write(content.toString());
            System.out.println("File modified successfully");
        } catch (IOException e) {
            System.out.println("Error writing changes: " + e.getMessage());
        }
    }

    private static void deleteFile() {
        if (!checkFileExists()) return;
        
        File file = new File(currentFilePath);
        if (file.delete()) {
            System.out.println("File deleted successfully");
            currentFilePath = "default.txt";
        } else {
            System.out.println("Failed to delete file");
        }
    }

    private static boolean checkFileExists() {
        File file = new File(currentFilePath);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Please create/set a file first.");
            return false;
        }
        return true;
    }

    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) return input;
                System.out.print("Please enter a number between " + min + "-" + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static void exitProgram() {
        System.out.println("\nThank you for using File Utility!");
        scanner.close();
        System.exit(0);
    }
}
