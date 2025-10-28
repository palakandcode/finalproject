
package main.java.model;




import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String username;
    protected String password;
    protected String name;
    protected Role role;

    public enum Role { STUDENT, TEACHER }

    public User(String username, String password, String name, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public Role getRole() { return role; }
    public void setPassword(String p) { this.password = p; }
    public void setName(String n) { this.name = n; }

    public abstract String generateReport();
}
