/**
 * Course.java
 * Represents an individual course with course code, title, and credit units.
 * This class demonstrates encapsulation with private attributes and public accessors.
 */

public class Course {
    // Private attributes
    private String courseCode;
    private String courseTitle;
    private int unit;

    /**
     * Default Constructor
     * Initializes a course with default values.
     */
    public Course() {
        this.courseCode = "";
        this.courseTitle = "";
        this.unit = 0;
    }

    /**
     * Parameterized Constructor
     * Initializes a course with specified values.
     * 
     * @param courseCode   The unique identifier for the course
     * @param courseTitle  The name of the course
     * @param unit         The credit units for the course
     */
    public Course(String courseCode, String courseTitle, int unit) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.unit = unit;
    }

    // Getters
    /**
     * Gets the course code.
     * 
     * @return The course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Gets the course title.
     * 
     * @return The course title
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Gets the credit units.
     * 
     * @return The number of credit units
     */
    public int getUnit() {
        return unit;
    }

    // Setters
    /**
     * Sets the course code.
     * 
     * @param courseCode The new course code
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Sets the course title.
     * 
     * @param courseTitle The new course title
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Sets the credit units.
     * 
     * @param unit The new number of credit units
     */
    public void setUnit(int unit) {
        this.unit = unit;
    }

    /**
     * Overridden toString() method
     * Returns a string representation of the course in CSV format for file storage.
     * 
     * @return String representation of the course
     */
    @Override
    public String toString() {
        return courseCode + "," + courseTitle + "," + unit;
    }
}
