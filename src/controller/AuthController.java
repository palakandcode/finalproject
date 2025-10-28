
package controller;

import main.java.model.Student;
import main.java.model.Teacher;
import main.java.model.User;




import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthController {
    private static final String USERS_PATH = "data/users.ser";
    private Map<String, User> users;

    public AuthController() {
        users = loadUsers();
    }

    @SuppressWarnings("unchecked")
    private Map<String, User> loadUsers() {
        File f = new File(USERS_PATH);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (Map<String, User>) obj;
        } catch (Exception e) {
            System.err.println("Users load error: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private void saveUsers() {
        File f = new File(USERS_PATH);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerStudent(String username, String password, String name, String roll, String studClass, String branch) {
        if (users.containsKey(username)) return false;
        Student s = new Student(username, password, name, roll, studClass, branch);
        users.put(username, s);
        saveUsers();
        return true;
    }

    public boolean registerTeacher(String username, String password, String name, String teacherId, String subject) {
        if (users.containsKey(username)) return false;
        Teacher t = new Teacher(username, password, name, teacherId, subject);
        users.put(username, t);
        saveUsers();
        return true;
    }

    public User authenticate(String username, String password) {
        User u = users.get(username);
        if (u == null) return null;
        if (u.getPassword().equals(password)) return u;
        return null;
    }

    public User getUser(String username) { return users.get(username); }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        for (User u : users.values()) {
            if (u instanceof Student) list.add((Student) u);
        }
        return list;
    }

    public Student getStudentByRoll(String roll) {
        for (Student s : getAllStudents()) {
            if (s.getRollNumber().equals(roll)) return s;
        }
        return null;
    }

    public void updateUser(User u) {
        users.put(u.getUsername(), u);
        saveUsers();
    }

    public void deleteUser(String username) {
        users.remove(username);
        saveUsers();
    }
}
