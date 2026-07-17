/**
 * StudentCourseManagementSystem.java
 * Main application class that provides a console-based menu system for managing courses.
 * This class demonstrates:
 * - ArrayList for dynamic data storage
 * - File handling (serialization/deserialization)
 * - Exception handling for robust error management
 * - Recursion for menu implementation
 * - Input validation and string manipulation
 */

import java.io.*;
import java.util.*;

public class StudentCourseManagementSystem {
    // ArrayList to store all courses
    private static ArrayList<Course> courses = new ArrayList<>();
    private static final String FILE_NAME = "courses.txt";
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main method - Entry point of the application
     * Loads existing courses and displays the main menu.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   STUDENT COURSE MANAGEMENT SYSTEM - COS 201            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        // Load existing courses from file
        loadFromFile();

        // Display menu using recursion
        showMenu();

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         Thank you for using StudX Course Manager        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        scanner.close();
    }

    /**
     * Recursive Menu Display Method
     * Shows the main menu and processes user input.
     * Calls itself recursively after each operation (except Exit).
     * This implements the recursion requirement without using while(true) loops.
     */
    private static void showMenu() {
        try {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     MAIN MENU                          ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Add Course                                          ║");
            System.out.println("║ 2. View All Courses                                    ║");
            System.out.println("║ 3. Search Course by Code                               ║");
            System.out.println("║ 4. Compute Total Units                                 ║");
            System.out.println("║ 5. Save Courses to File                                ║");
            System.out.println("║ 6. Load Courses from File                              ║");
            System.out.println("║ 7. Exit                                                ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.print("Enter your choice (1-7): ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addCourse();
                    showMenu(); // Recursive call
                    break;
                case 2:
                    viewCourses();
                    showMenu(); // Recursive call
                    break;
                case 3:
                    searchCourse();
                    showMenu(); // Recursive call
                    break;
                case 4:
                    computeTotalUnits();
                    showMenu(); // Recursive call
                    break;
                case 5:
                    saveToFile();
                    showMenu(); // Recursive call
                    break;
                case 6:
                    loadFromFile();
                    showMenu(); // Recursive call
                    break;
                case 7:
                    System.out.println("\nExiting application...");
                    return; // Terminate recursion
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 1 and 7.");
                    showMenu(); // Recursive call
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Input error: Please enter a valid number.");
            scanner.nextLine(); // Clear invalid input
            showMenu(); // Recursive call
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            showMenu(); // Recursive call
        }
    }

    /**
     * Add Course Method
     * Prompts the user to enter course details and adds the course to the ArrayList.
     * Features:
     * - Removes unnecessary spaces from inputs
     * - Converts course code to uppercase
     * - Validates that units > 0
     * - Prevents duplicate course codes
     * - Includes comprehensive input validation
     */
    private static void addCourse() {
        try {
            System.out.println("\n┌─ ADD COURSE ──────────��──────────────────────────────┐");

            // Get and validate course code
            System.out.print("Enter Course Code: ");
            String courseCode = scanner.nextLine().trim().toUpperCase();

            if (courseCode.isEmpty()) {
                System.out.println("❌ Course code cannot be empty!");
                return;
            }

            // Check for duplicate course code
            if (courseCodeExists(courseCode)) {
                System.out.println("❌ Error: Course code '" + courseCode + "' already exists!");
                return;
            }

            // Get and validate course title
            System.out.print("Enter Course Title: ");
            String courseTitle = scanner.nextLine().trim();

            if (courseTitle.isEmpty()) {
                System.out.println("❌ Course title cannot be empty!");
                return;
            }

            // Get and validate credit units
            System.out.print("Enter Credit Units: ");
            int unit = getIntInput();

            if (unit <= 0) {
                System.out.println("❌ Credit units must be greater than zero!");
                return;
            }

            // Create and add the course
            Course course = new Course(courseCode, courseTitle, unit);
            courses.add(course);
            System.out.println("✅ Course added successfully!");

        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input! Please ensure all fields are entered correctly.");
            scanner.nextLine(); // Clear invalid input
        } catch (Exception e) {
            System.out.println("❌ Error adding course: " + e.getMessage());
        }
    }

    /**
     * View All Courses Method
     * Displays all registered courses in a formatted table.
     * Shows an appropriate message if no courses are available.
     */
    private static void viewCourses() {
        System.out.println("\n┌─ ALL COURSES ────────────────────────────────────────┐");

        if (courses.isEmpty()) {
            System.out.println("│ No courses available. Please add some courses first.  │");
        } else {
            System.out.println("├───────────────┬─────────────────────────┬───────┤");
            System.out.println("│ Course Code   │ Course Title            │ Units │");
            System.out.println("├───────────────┼─────────────────────────┼───────┤");

            for (Course course : courses) {
                System.out.printf("│ %-13s │ %-23s │ %-5d │\n",
                        course.getCourseCode(),
                        truncateTitle(course.getCourseTitle(), 23),
                        course.getUnit());
            }
            System.out.println("├───────────────┴─────────────────────────┴───────┤");
            System.out.println("│ Total Courses: " + courses.size() + "                           │");
        }
        System.out.println("└──────────────────────────────────────────────────────┘");
    }

    /**
     * Search Course Method
     * Searches for a course by its code using recursive binary search approach.
     * Case-insensitive search implementation.
     */
    private static void searchCourse() {
        try {
            System.out.println("\n┌─ SEARCH COURSE ──────────────────────────────────────┐");
            System.out.print("Enter Course Code to search: ");
            String searchCode = scanner.nextLine().trim().toUpperCase();

            if (searchCode.isEmpty()) {
                System.out.println("❌ Course code cannot be empty!");
                return;
            }

            // Recursive search helper
            Course found = recursiveSearch(searchCode, 0);

            if (found != null) {
                System.out.println("✅ Course found!");
                System.out.println("├─────────────────────────────────────────────────────┤");
                System.out.println("│ Course Code: " + found.getCourseCode());
                System.out.println("│ Course Title: " + found.getCourseTitle());
                System.out.println("│ Credit Units: " + found.getUnit());
                System.out.println("└─────────────────────────────────────────────────────┘");
            } else {
                System.out.println("❌ Course not found.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error during search: " + e.getMessage());
        }
    }

    /**
     * Recursive Search Helper Method
     * Implements recursion to search through the ArrayList.
     * 
     * @param code  The course code to search for
     * @param index The current index in the ArrayList
     * @return The Course object if found, null otherwise
     */
    private static Course recursiveSearch(String code, int index) {
        // Base case: reached the end of the list
        if (index >= courses.size()) {
            return null;
        }

        // Check current element
        if (courses.get(index).getCourseCode().equals(code)) {
            return courses.get(index);
        }

        // Recursive case: search the rest of the list
        return recursiveSearch(code, index + 1);
    }

    /**
     * Compute Total Units Method
     * Calculates and displays the total credit units of all registered courses.
     */
    private static void computeTotalUnits() {
        System.out.println("\n┌─ TOTAL UNITS ────────────────────────────────────────┐");

        if (courses.isEmpty()) {
            System.out.println("│ No courses available.                                │");
        } else {
            int totalUnits = 0;
            for (Course course : courses) {
                totalUnits += course.getUnit();
            }
            System.out.println("│ Total Credit Units: " + totalUnits + "                      │");
        }
        System.out.println("└──────────────────────────────────────────────────────┘");
    }

    /**
     * Save to File Method
     * Saves all course records to courses.txt file.
     * Format: CourseCode,CourseTitle,Units
     * Implements proper exception handling for file operations.
     */
    private static void saveToFile() {
        try {
            if (courses.isEmpty()) {
                System.out.println("\n❌ No courses to save!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Course course : courses) {
                    writer.write(course.toString());
                    writer.newLine();
                }
                System.out.println("\n✅ Courses saved to '" + FILE_NAME + "' successfully!");
            }

        } catch (IOException e) {
            System.out.println("\n❌ IO Error while saving: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n❌ Error saving courses: " + e.getMessage());
        }
    }

    /**
     * Load from File Method
     * Reads all course records from courses.txt file.
     * Clears existing ArrayList before loading.
     * Implements proper exception handling for missing files and parse errors.
     */
    private static void loadFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("ℹ️  Note: No existing course file found. Starting with empty list.\n");
            return;
        }

        try {
            courses.clear(); // Clear existing data before loading
            int loadedCount = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            try {
                                String courseCode = parts[0].trim();
                                String courseTitle = parts[1].trim();
                                int unit = Integer.parseInt(parts[2].trim());

                                Course course = new Course(courseCode, courseTitle, unit);
                                courses.add(course);
                                loadedCount++;
                            } catch (NumberFormatException e) {
                                System.out.println("⚠️  Warning: Invalid unit value in line: " + line);
                            }
                        }
                    }
                }
            }

            if (loadedCount > 0) {
                System.out.println("✅ Successfully loaded " + loadedCount + " course(s) from '" + FILE_NAME + "'!\n");
            } else {
                System.out.println("ℹ️  File is empty or contains no valid course records.\n");
            }

        } catch (FileNotFoundException e) {
            System.out.println("❌ File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("❌ IO Error while reading: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error loading courses: " + e.getMessage());
        }
    }

    /**
     * Helper Method: Check if Course Code Exists
     * Searches through ArrayList to prevent duplicate course codes.
     * 
     * @param courseCode The code to check
     * @return true if course code exists, false otherwise
     */
    private static boolean courseCodeExists(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper Method: Get Integer Input
     * Safely reads and validates integer input from user.
     * 
     * @return The integer input
     * @throws InputMismatchException if input is not a valid integer
     */
    private static int getIntInput() throws InputMismatchException {
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the invalid input
            throw e;
        }
    }

    /**
     * Helper Method: Truncate Title
     * Truncates long course titles to fit table format.
     * 
     * @param title The title to truncate
     * @param maxLength The maximum length
     * @return Truncated title
     */
    private static String truncateTitle(String title, int maxLength) {
        if (title.length() > maxLength - 3) {
            return title.substring(0, maxLength - 3) + "...";
        }
        return title;
    }
}
