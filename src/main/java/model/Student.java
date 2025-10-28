// src/main/java/model/Student.java
// src/main/java/model/Student.java
package main.java.model;

// src/main/java/model/Student.java


import java.util.HashMap;
import java.util.Map;

public class Student extends User {
    private static final long serialVersionUID = 1L;
    private String rollNumber;
    private String studentClass;
    private String branch;
    private Map<String, Integer> marks;
    private String teacherRemark;

    public Student(String username, String password, String name, String rollNumber, String studentClass, String branch) {
        super(username, password, name, Role.STUDENT);
        this.rollNumber = rollNumber;
        this.studentClass = studentClass;
        this.branch = branch;
        this.marks = new HashMap<>();
        this.teacherRemark = "";
    }

    public String getRollNumber() { return rollNumber; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }
    public void setBranch(String branch) { this.branch = branch; }
    public String getStudentClass() { return studentClass; }
    public String getBranch() { return branch; }
    public Map<String, Integer> getMarks() { return marks; }
    public void setMark(String subject, Integer mark) { marks.put(subject, mark); }
    public Integer getMark(String subject) { return marks.getOrDefault(subject, 0); }
    public void setTeacherRemark(String r) { this.teacherRemark = r; }
    public String getTeacherRemark() { return teacherRemark; }

    public double average() {
        if (marks.isEmpty()) return 0.0;
        double sum = 0;
        for (Integer m : marks.values()) sum += m;
        return sum / marks.size();
    }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Report for ").append(name).append("\n");
        sb.append("Roll: ").append(rollNumber).append("\n");
        sb.append("Class: ").append(studentClass).append(" | Branch: ").append(branch).append("\n\n");
        sb.append("Marks:\n");
        for (Map.Entry<String, Integer> e : marks.entrySet()) {
            sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }
        sb.append("\nAverage: ").append(String.format("%.2f", average())).append("\n");
        sb.append("Teacher Remark: ").append(teacherRemark).append("\n");
        return sb.toString();
    }
}