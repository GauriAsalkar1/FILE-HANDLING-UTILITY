import java.io.*;
import java.util.Scanner;

/**
 * FILE HANDLING UTILITY PROGRAM
 * This program provides basic file operations including:
 * - Creating new files
 * - Writing content to files
 * - Reading file contents
 * - Modifying file contents (search and replace)
 * - Deleting files
 * Developed for CODTECH internship requirements
 */
public class FileOperations {
    // Scanner object for user input
    private static final Scanner scanner = new Scanner(System.in);
    // Stores the current working file path
    private static String currentFilePath = "default.txt";

    /**
     * Main method - program entry point
     * Displays menu and processes user choices
     */
    public static void main(String[] args) {
        System.out.println("\nFILE HANDLING UTILITY");
        
        // Main program loop
        while (true) {
            // Display menu options
            System.out.println("\nMAIN MENU:");
            System.out.println("1. Set/Create File");
            System.out.println("2. Write to File");
            System.out.println("3. Read from File");
            System.out.println("4. Modify File Content");
            System.out.println("5. Delete File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            // Get and validate user input
            int choice = getIntInput(1, 6);

            // Process user choice
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

    /**
     * Creates a new file or sets an existing file as current
     * Prompts user for filename and verifies existence
     */
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

    /**
     * Writes content to the current file
     * Overwrites any existing content
     */
    private static void writeToFile() {
        if (!checkFileExists()) return;
        
        System.out.print("\nEnter text to write: ");
        String content = scanner.nextLine();
        
        // Try-with-resources to automatically close FileWriter
        try (FileWriter writer = new FileWriter(currentFilePath)) {
            writer.write(content);
            System.out.println("Successfully wrote to file");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads and displays content from the current file
     * Shows content line by line
     */
    private static void readFromFile() {
        if (!checkFileExists()) return;
        
        System.out.println("\nFile Content:");
        // Try-with-resources to automatically close BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Modifies file content by replacing all occurrences of specified text
     * Performs global search and replace operation
     */
    private static void modifyFile() {
        if (!checkFileExists()) return;
        
        System.out.print("\nEnter text to replace: ");
        String oldText = scanner.nextLine();
        System.out.print("Enter replacement text: ");
        String newText = scanner.nextLine();
        
        StringBuilder content = new StringBuilder();
        // Read file and perform replacements
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line.replace(oldText, newText)).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Write modified content back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
            writer.write(content.toString());
            System.out.println("File modified successfully");
        } catch (IOException e) {
            System.out.println("Error writing changes: " + e.getMessage());
        }
    }

    /**
     * Deletes the current file
     * Resets currentFilePath to default after deletion
     */
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

    /**
     * Checks if current file exists
     * @return true if file exists, false otherwise
     */
    private static boolean checkFileExists() {
        File file = new File(currentFilePath);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Please create/set a file first.");
            return false;
        }
        return true;
    }

    /**
     * Validates integer input within specified range
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Validated integer input
     */
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

    /**
     * Cleanly exits the program
     * Closes resources and displays exit message
     */
    private static void exitProgram() {
        System.out.println("\nThank you for using File Utility!");
        scanner.close();
        System.exit(0);
    }
}
