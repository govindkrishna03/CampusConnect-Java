package models;

public class Student {
    private String name;
    private int rollNo;
    private String course;

    // Additional properties for authentication
    private String username;
    private String password;

    public Student(String name, int rollNo, String course, String username, String password) {
        this.name = name;
        this.rollNo = rollNo;
        this.course = course;
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

   
    public boolean authenticate(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public void viewNotifications() {

        System.out.println("Notifications for " + name + ": No new notifications.");
    }

 
    public void viewEvents() {
     
        System.out.println("Events for " + name + ": No upcoming events.");
    }

  
    public void downloadNotes(String courseName) {
   
        System.out.println("Downloading notes for course " + courseName + "...");
    }

    public static void main(String[] args) {
    
        Student student = new Student("John Doe", 12345, "Computer Science", "john", "password");
        student.viewNotifications();
        student.viewEvents();
        student.downloadNotes("Java Programming");
    }
}
