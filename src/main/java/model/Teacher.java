// src/main/java/model/Teacher.java
// src/main/java/model/Teacher.java
package main.java.model;

// src/main/java/model/Teacher.java


import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {
    private static final long serialVersionUID = 1L;
    private String teacherId;
    private List<String> classesAssigned;
    private String subject;

    public Teacher(String username, String password, String name, String teacherId, String subject) {
        super(username, password, name, Role.TEACHER);
        this.teacherId = teacherId;
        this.subject = subject;
        this.classesAssigned = new ArrayList<>();
    }

    public String getTeacherId() { return teacherId; }
    public String getSubject() { return subject; }
    public void setSubject(String s) { this.subject = s; }
    public List<String> getClassesAssigned() { return classesAssigned; }
    public void addClass(String c) {
        if (!classesAssigned.contains(c)) classesAssigned.add(c);
    }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Teacher: ").append(name).append("\nID: ").append(teacherId).append("\nSubject: ").append(subject).append("\n");
        sb.append("Classes: ").append(String.join(", ", classesAssigned)).append("\n");
        return sb.toString();
    }
}