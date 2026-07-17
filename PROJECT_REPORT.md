# Student Course Management System - Project Report

## Project Overview

The **Student Course Management System** is a comprehensive Java console application developed for COS 201. It provides a complete solution for managing student course registrations, including adding, viewing, searching, and persisting course data to permanent storage.

### Purpose
The application serves as a practical demonstration of core Java programming concepts including object-oriented design, data structures, file handling, exception management, and recursion. Students can register courses, manage their academic load, and maintain persistent records.

---

## Feature Implementation

### 1. **Add Course**
- **Functionality:** Allows users to register new courses with course code, title, and credit units.
- **Validation Features:**
  - Removes leading/trailing whitespace from all inputs using `.trim()`
  - Automatically converts course codes to uppercase for consistency
  - Validates that credit units are greater than zero
  - Prevents duplicate course code registration
  - Provides meaningful error messages for invalid inputs
- **User Experience:** Clear prompts guide users through the registration process with immediate feedback.

### 2. **View All Courses**
- **Functionality:** Displays all registered courses in a formatted table.
- **Features:**
  - Professional table layout with proper alignment
  - Shows course code, title, and units in columns
  - Displays total course count
  - Handles empty list gracefully with appropriate message
  - Long titles are truncated intelligently to maintain table formatting

### 3. **Search Course by Code**
- **Functionality:** Searches for a specific course using its code (case-insensitive).
- **Implementation:** Uses recursive search algorithm (see Recursion section)
- **Output:** Displays full course details if found, otherwise "Course not found" message
- **User-Friendly:** Course code input is automatically converted to uppercase for consistency

### 4. **Compute Total Units**
- **Functionality:** Calculates the total credit units across all registered courses.
- **Use Case:** Helps students track their academic load and ensure compliance with course load limits.
- **Efficiency:** Uses simple iteration through ArrayList for O(n) time complexity.

### 5. **Save Courses to File**
- **Functionality:** Persists all course data to `courses.txt` file.
- **File Format:** CSV format - `CourseCode,CourseTitle,Units`
- **Example:** `COS201,Programming I,3`
- **Resource Management:** Uses try-with-resources statement for automatic file closure
- **Validation:** Prevents saving when no courses exist

### 6. **Load Courses from File**
- **Functionality:** Restores previously saved course data from file.
- **Process:**
  - Clears existing ArrayList before loading (prevents duplicates)
  - Reads line-by-line using BufferedReader
  - Parses CSV format and validates each entry
  - Creates Course objects and populates ArrayList
- **Error Handling:** Gracefully handles missing files, corrupt data, and parse errors
- **User Feedback:** Reports number of courses successfully loaded

---

## Exception Handling Implementation

The application implements comprehensive exception handling to ensure robustness:

### 1. **NumberFormatException**
- **Caught In:** `getIntInput()` method when parsing menu choice
- **Location:** File loading when parsing unit values
- **Handling:** Clear error message and retry mechanism

### 2. **InputMismatchException**
- **Caught In:** `showMenu()` and `addCourse()` methods
- **Handling:** Clears invalid input buffer and prompts user to retry
- **Recovery:** Allows recursive menu call without crashing

### 3. **IOException**
- **Caught In:** `saveToFile()` and `loadFromFile()` methods
- **Specific Types:**
  - `FileNotFoundException` - when courses.txt doesn't exist during load
  - General `IOException` - for file read/write failures
- **Handling:** Provides specific error messages and continues execution

### 4. **General Exception Handling**
- **Strategy:** Catch-all exception handlers in recursive menu to prevent stack overflow
- **Benefit:** Application never crashes regardless of unexpected errors
- **User Experience:** Graceful degradation with informative error messages

### Implementation Example:
```java
try {
    // File operations
} catch (FileNotFoundException e) {
    System.out.println("File not found: " + e.getMessage());
} catch (IOException e) {
    System.out.println("IO Error: " + e.getMessage());
} catch (Exception e) {
    System.out.println("Unexpected error: " + e.getMessage());
}
```

---

## Recursion Implementation

### Where Recursion is Used

#### 1. **Menu System (Primary Recursion)**
- **Method:** `showMenu()`
- **How It Works:**
  - Displays menu options
  - Reads user choice
  - Executes corresponding operation
  - **Calls itself recursively** to display menu again
  - Base case: User selects option 7 (Exit) which returns without recursive call
- **Why Recursion:** Eliminates need for `while(true)` loop and demonstrates functional programming concepts
- **Call Stack:** Each recursive call adds a new frame until Exit is selected

```java
private static void showMenu() {
    // Display menu and get choice
    switch (choice) {
        case 1: addCourse(); showMenu(); break;    // Recursive call
        case 7: return;                             // Base case - stops recursion
    }
}
```

#### 2. **Search Course (Secondary Recursion)**
- **Method:** `recursiveSearch(String code, int index)`
- **How It Works:**
  - Base case: If index >= ArrayList size, return null
  - Recursive case: Check current element, if not found, call recursively with index+1
  - Traverses ArrayList until course is found or end is reached
- **Purpose:** Demonstrates recursive search algorithm
- **Time Complexity:** O(n) - traverses each element once

```java
private static Course recursiveSearch(String code, int index) {
    if (index >= courses.size()) return null;      // Base case
    if (courses.get(index).getCourseCode().equals(code)) 
        return courses.get(index);
    return recursiveSearch(code, index + 1);       // Recursive case
}
```

### Advantages of Recursion Used
- **Menu System:** Cleaner code structure without explicit loop variable management
- **Search:** Educational demonstration of recursive algorithm
- **Stack Utilization:** Shows proper base case implementation to prevent infinite recursion

---

## Challenges Encountered and Solutions

### Challenge 1: Managing ArrayList State During File Operations
**Problem:** Loading new courses could create duplicates if ArrayList wasn't properly cleared.
**Solution:** Implemented `courses.clear()` at the start of `loadFromFile()` to reset state.

### Challenge 2: Input Validation Complexity
**Problem:** Multiple validation layers needed (empty check, format check, range check, duplicate check).
**Solution:** Broke validation into separate conditions with specific error messages for each case.

### Challenge 3: File Format Parsing
**Problem:** Handling malformed CSV lines and NumberFormatException during unit parsing.
**Solution:** Implemented try-catch within file reading loop to log warnings and skip invalid lines gracefully.

### Challenge 4: Integer Input Handling
**Problem:** `Scanner.nextInt()` leaves newline in buffer, causing subsequent `nextLine()` calls to fail.
**Solution:** Created `getIntInput()` helper method that properly consumes the newline: `scanner.nextLine()`.

### Challenge 5: Table Formatting
**Problem:** Long course titles could break table alignment.
**Solution:** Implemented `truncateTitle()` helper method to intelligently truncate titles while maintaining readability.

### Challenge 6: Preventing Stack Overflow in Recursion
**Problem:** Recursive menu could cause stack overflow if not properly terminated.
**Solution:** Ensured option 7 (Exit) returns cleanly without recursive call, providing proper base case.

---

## Java Concepts Demonstrated

✅ **Classes and Objects** - Course class with private attributes and public methods
✅ **Constructors** - Default and parameterized constructors in Course class
✅ **Encapsulation** - Private fields with getters/setters for controlled access
✅ **Methods** - Multiple specialized methods following single responsibility principle
✅ **ArrayList** - Dynamic collection for storing Course objects
✅ **Loops** - Enhanced for-loops for iteration, while-loops for file reading
✅ **Recursion** - Menu system and course search implemented recursively
✅ **File Handling** - BufferedReader/Writer for file I/O operations
✅ **Exception Handling** - Comprehensive try-catch blocks for error management
✅ **String Manipulation** - `.trim()`, `.toUpperCase()`, `.split()`, `.substring()`
✅ **Input Validation** - Multi-level validation with meaningful error messages

---

## Compilation and Execution

### Compilation
```bash
javac Course.java
javac StudentCourseManagementSystem.java
```

### Execution
```bash
java StudentCourseManagementSystem
```

### First Run
- Creates empty course list
- No `courses.txt` file exists yet
- User can add courses and save them

### Subsequent Runs
- Automatically loads courses from `courses.txt`
- Previous data persists across sessions

---

## Code Quality Features

1. **Comprehensive Comments** - Every method and complex logic is thoroughly documented
2. **Meaningful Variable Names** - Clear naming conventions throughout
3. **Formatted Output** - Professional table layouts with Unicode box-drawing characters
4. **User Feedback** - Visual indicators (✅, ❌, ℹ️) for different message types
5. **Error Recovery** - Application continues running after errors
6. **Resource Management** - Try-with-resources for automatic file closure

---

## Conclusion

This project successfully demonstrates all required Java concepts while providing a functional, user-friendly application. The implementation emphasizes clean code practices, robust error handling, and thoughtful user experience design. The recursive menu system and recursive search algorithm effectively illustrate advanced programming concepts while maintaining code readability and maintainability.
