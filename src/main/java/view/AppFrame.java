
package main.java.view;

import controller.AuthController;
import controller.MarksController;
import main.java.model.Student;
import main.java.model.Teacher;
import main.java.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




public class AppFrame extends JFrame {
    private AuthController auth;
    private MarksController marks;

    public AppFrame() {
        super("Student Report Card Generator");
        auth = new AuthController();
        marks = new MarksController();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        showHome();
    }

    public void showHome() {
        getContentPane().removeAll();
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(173, 216, 230)); 
        JLabel title = new JLabel("<html><center><h1>Student Report Card Generator</h1></center></html>", SwingConstants.CENTER);
        title.setForeground(new Color(0, 100, 0));
        title.setFont(new Font("Serif", Font.BOLD, 32));
        p.add(title, BorderLayout.NORTH);
        JPanel center = new JPanel(new GridLayout(1, 2, 20, 20));
        center.setBackground(new Color(173, 216, 230));
        JPanel teacherPanel = new JPanel(new BorderLayout());
        teacherPanel.setBorder(BorderFactory.createTitledBorder("Teacher"));
        teacherPanel.setBackground(Color.WHITE);
        JLabel tInfo = new JLabel("<html><p>Create account or Login as Teacher to manage marks and generate class reports.</p></html>");
        teacherPanel.add(tInfo, BorderLayout.CENTER);
        JButton tBtn = new JButton("Open Teacher Login/Signup");
        tBtn.setBackground(new Color(50, 205, 50)); 
        tBtn.addActionListener(e -> showLogin(true));
        teacherPanel.add(tBtn, BorderLayout.SOUTH);
        JPanel studentPanel = new JPanel(new BorderLayout());
        studentPanel.setBorder(BorderFactory.createTitledBorder("Student"));
        studentPanel.setBackground(Color.WHITE);
        JLabel sInfo = new JLabel("<html><p>Students can create an account, view marks, compare and export report.</p></html>");
        studentPanel.add(sInfo, BorderLayout.CENTER);
        JButton sBtn = new JButton("Open Student Login/Signup");
        sBtn.setBackground(new Color(135, 206, 250)); 
        sBtn.addActionListener(e -> showLogin(false));
        studentPanel.add(sBtn, BorderLayout.SOUTH);
        center.add(teacherPanel);
        center.add(studentPanel);
        p.add(center, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(173, 216, 230));
        JButton exit = new JButton("Exit");
        exit.setBackground(Color.RED);
        exit.setForeground(Color.WHITE);
        exit.addActionListener(e -> System.exit(0));
        bottom.add(exit);
        p.add(bottom, BorderLayout.SOUTH);
        add(p, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showLogin(boolean teacherMode) {
        getContentPane().removeAll();
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBackground(new Color(240, 248, 255)); 
        JButton back = new JButton("← Back");
        back.setBackground(new Color(255, 165, 0)); 
        back.addActionListener(e -> showHome());
        p.add(back, BorderLayout.NORTH);
        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        loginPanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.gridx = 0; c.gridy = 0;
        loginPanel.add(new JLabel("Username:"), c);
        c.gridx = 1;
        JTextField loginUser = new JTextField(12);
        loginPanel.add(loginUser, c);
        c.gridx = 0; c.gridy++;
        loginPanel.add(new JLabel("Password:"), c);
        c.gridx = 1;
        JPasswordField loginPass = new JPasswordField(12);
        loginPanel.add(loginPass, c);
        c.gridx = 0; c.gridy++;
        c.gridwidth = 2;
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(0, 128, 0)); 
        loginPanel.add(loginBtn, c);
        
        JPanel signupPanel = new JPanel(new GridBagLayout());
        signupPanel.setBorder(BorderFactory.createTitledBorder("Create New Account"));
        signupPanel.setBackground(Color.WHITE);
        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(4, 4, 4, 4);
        s.gridx = 0; s.gridy = 0;
        signupPanel.add(new JLabel("Full Name:"), s);
        s.gridx = 1;
        JTextField nameField = new JTextField(12);
        signupPanel.add(nameField, s);
        s.gridx = 0; s.gridy++;
        signupPanel.add(new JLabel("Username (unique):"), s);
        s.gridx = 1;
        JTextField userField = new JTextField(12);
        signupPanel.add(userField, s);
        s.gridx = 0; s.gridy++;
        signupPanel.add(new JLabel("Password:"), s);
        s.gridx = 1;
        JPasswordField passField = new JPasswordField(12);
        signupPanel.add(passField, s);
        if (!teacherMode) {
            // student additional fields
            s.gridx = 0; s.gridy++;
            signupPanel.add(new JLabel("Roll Number:"), s);
            s.gridx = 1;
            JTextField rollField = new JTextField(12);
            signupPanel.add(rollField, s);
            s.gridx = 0; s.gridy++;
            signupPanel.add(new JLabel("Class:"), s);
            s.gridx = 1;
            JTextField classField = new JTextField(12);
            signupPanel.add(classField, s);
            s.gridx = 0; s.gridy++;
            signupPanel.add(new JLabel("Branch:"), s);
            s.gridx = 1;
            JTextField branchField = new JTextField(12);
            signupPanel.add(branchField, s);
            s.gridx = 0; s.gridy++;
            s.gridwidth = 2;
            JButton createStudent = new JButton("Create Student Account");
            createStudent.setBackground(new Color(70, 130, 180)); 
            signupPanel.add(createStudent, s);
            createStudent.addActionListener(ev -> {
                String name = nameField.getText().trim();
                String user = userField.getText().trim();
                String pass = new String(passField.getPassword());
                String roll = rollField.getText().trim();
                String clazz = classField.getText().trim();
                String branch = branchField.getText().trim();
                if (name.isEmpty() || user.isEmpty() || pass.isEmpty() || roll.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill mandatory fields.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                boolean ok = auth.registerStudent(user, pass, name, roll, clazz.isEmpty() ? "NA" : clazz, branch.isEmpty() ? "NA" : branch);
                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Student account created. You can login now.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText(""); userField.setText(""); passField.setText("");
                    rollField.setText(""); classField.setText(""); branchField.setText("");
                }
            });
        } else {
        
            s.gridx = 0; s.gridy++;
            signupPanel.add(new JLabel("Teacher ID:"), s);
            s.gridx = 1;
            JTextField tidField = new JTextField(12);
            signupPanel.add(tidField, s);
            s.gridx = 0; s.gridy++;
            signupPanel.add(new JLabel("Subject:"), s);
            s.gridx = 1;
            JTextField subjField = new JTextField(12);
            signupPanel.add(subjField, s);
            s.gridx = 0; s.gridy++;
            s.gridwidth = 2;
            JButton createTeacher = new JButton("Create Teacher Account");
            createTeacher.setBackground(new Color(34, 139, 34)); 
            signupPanel.add(createTeacher, s);
            createTeacher.addActionListener(ev -> {
                String name = nameField.getText().trim();
                String user = userField.getText().trim();
                String pass = new String(passField.getPassword());
                String tid = tidField.getText().trim();
                String subj = subjField.getText().trim();
                if (name.isEmpty() || user.isEmpty() || pass.isEmpty() || tid.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill mandatory fields.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                boolean ok = auth.registerTeacher(user, pass, name, tid, subj.isEmpty() ? "General" : subj);
                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Teacher account created. You can login now.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText(""); userField.setText(""); passField.setText("");
                    tidField.setText(""); subjField.setText("");
                }
            });
        }
        loginBtn.addActionListener(ev -> {
            String user = loginUser.getText().trim();
            String pass = new String(loginPass.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter username and password.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            User u = auth.authenticate(user, pass);
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (teacherMode && u instanceof Teacher) {
                showTeacherDashboard((Teacher) u);
            } else if (!teacherMode && u instanceof Student) {
                showStudentDashboard((Student) u);
            } else {
                JOptionPane.showMessageDialog(this, "User type mismatch. Use the correct login panel.", "Type Mismatch", JOptionPane.ERROR_MESSAGE);
            }
        });
        center.add(loginPanel);
        center.add(signupPanel);
        p.add(center, BorderLayout.CENTER);
        add(p, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showTeacherDashboard(Teacher teacher) {
        getContentPane().removeAll();
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(new Color(144, 238, 144)); 
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(144, 238, 144));
        JLabel welcomeLabel = new JLabel("Teacher Dashboard - Welcome " + teacher.getName(), SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        top.add(welcomeLabel, BorderLayout.WEST);
        JButton logout = new JButton("Logout");
        logout.setBackground(Color.RED);
        logout.setForeground(Color.WHITE);
        logout.addActionListener(e -> showHome());
        top.add(logout, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);
        JPanel center = new JPanel(new BorderLayout(8, 8));
        
        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(600, 400));
        left.setBorder(BorderFactory.createTitledBorder("Students"));
        left.setBackground(Color.WHITE);
        List<Student> students = auth.getAllStudents();
        
        String[] cols = new String[4 + MarksController.SUBJECTS.length + 1];
        cols[0] = "Roll";
        cols[1] = "Name";
        cols[2] = "Class";
        cols[3] = "Branch";
        for (int i = 0; i < MarksController.SUBJECTS.length; i++) {
            cols[4 + i] = MarksController.SUBJECTS[i].substring(0, Math.min(20, MarksController.SUBJECTS[i].length()));
        }
        cols[cols.length - 1] = "Average";
        DefaultTableModel tblModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Student s : students) {
            Object[] row = new Object[cols.length];
            row[0] = s.getRollNumber();
            row[1] = s.getName();
            row[2] = s.getStudentClass();
            row[3] = s.getBranch();
            Map<String, Integer> mm = marks.getMarksFor(s.getRollNumber());
            double sum = 0;
            int count = 0;
            for (int i = 0; i < MarksController.SUBJECTS.length; i++) {
                String subj = MarksController.SUBJECTS[i];
                int m = mm.getOrDefault(subj, 0);
                row[4 + i] = m;
                sum += m;
                count++;
            }
            row[cols.length - 1] = count > 0 ? String.format("%.2f", sum / count) : "0.00";
            tblModel.addRow(row);
        }
        JTable table = new JTable(tblModel);
        JScrollPane sp = new JScrollPane(table);
        left.add(sp, BorderLayout.CENTER);
        JPanel leftBottom = new JPanel();
        leftBottom.setBackground(Color.WHITE);
        JButton refresh = new JButton("Refresh");
        refresh.setBackground(new Color(255, 215, 0)); 
        refresh.addActionListener(e -> {
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);
            List<Student> updatedStudents = auth.getAllStudents();
            for (Student s : updatedStudents) {
                Object[] row = new Object[cols.length];
                row[0] = s.getRollNumber();
                row[1] = s.getName();
                row[2] = s.getStudentClass();
                row[3] = s.getBranch();
                Map<String, Integer> mm = marks.getMarksFor(s.getRollNumber());
                double sum = 0;
                int count = 0;
                for (int i = 0; i < MarksController.SUBJECTS.length; i++) {
                    String subj = MarksController.SUBJECTS[i];
                    int mk = mm.getOrDefault(subj, 0);
                    row[4 + i] = mk;
                    sum += mk;
                    count++;
                }
                row[cols.length - 1] = count > 0 ? String.format("%.2f", sum / count) : "0.00";
                m.addRow(row);
            }
        });
        leftBottom.add(refresh);
        JButton addStd = new JButton("Add Student");
        addStd.setBackground(new Color(0, 191, 255)); 
        addStd.addActionListener(e -> {
            JPanel addPanel = new JPanel(new GridLayout(6, 2));
            addPanel.add(new JLabel("Full Name:"));
            JTextField nameF = new JTextField();
            addPanel.add(nameF);
            addPanel.add(new JLabel("Username:"));
            JTextField userF = new JTextField();
            addPanel.add(userF);
            addPanel.add(new JLabel("Password:"));
            JPasswordField passF = new JPasswordField();
            addPanel.add(passF);
            addPanel.add(new JLabel("Roll Number:"));
            JTextField rollF = new JTextField();
            addPanel.add(rollF);
            addPanel.add(new JLabel("Class:"));
            JTextField classF = new JTextField();
            addPanel.add(classF);
            addPanel.add(new JLabel("Branch:"));
            JTextField branchF = new JTextField();
            addPanel.add(branchF);
            int option = JOptionPane.showConfirmDialog(this, addPanel, "Add Student", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String name = nameF.getText().trim();
                String user = userF.getText().trim();
                String pass = new String(passF.getPassword());
                String roll = rollF.getText().trim();
                String clazz = classF.getText().trim();
                String branch = branchF.getText().trim();
                if (name.isEmpty() || user.isEmpty() || pass.isEmpty() || roll.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill mandatory fields.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                boolean ok = auth.registerStudent(user, pass, name, roll, clazz.isEmpty() ? "NA" : clazz, branch.isEmpty() ? "NA" : branch);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Student added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refresh.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        leftBottom.add(addStd);
        JButton editStd = new JButton("Edit Student");
        editStd.setBackground(new Color(255, 165, 0)); 
        editStd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a student to edit.", "Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String roll = (String) table.getValueAt(row, 0);
            Student st = auth.getStudentByRoll(roll);
            if (st == null) return;
            JPanel editPanel = new JPanel(new GridLayout(3, 2));
            editPanel.add(new JLabel("Name:"));
            JTextField nameF = new JTextField(st.getName());
            editPanel.add(nameF);
            editPanel.add(new JLabel("Class:"));
            JTextField classF = new JTextField(st.getStudentClass());
            editPanel.add(classF);
            editPanel.add(new JLabel("Branch:"));
            JTextField branchF = new JTextField(st.getBranch());
            editPanel.add(branchF);
            int option = JOptionPane.showConfirmDialog(this, editPanel, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                st.setName(nameF.getText().trim());
                st.setStudentClass(classF.getText().trim());
                st.setBranch(branchF.getText().trim());
                auth.updateUser(st);
                refresh.doClick();
                JOptionPane.showMessageDialog(this, "Student updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        leftBottom.add(editStd);
        JButton delStd = new JButton("Delete Student");
        delStd.setBackground(Color.RED);
        delStd.setForeground(Color.WHITE);
        delStd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a student to delete.", "Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String roll = (String) table.getValueAt(row, 0);
            Student st = auth.getStudentByRoll(roll);
            if (st == null) return;
            int confirm = JOptionPane.showConfirmDialog(this, "Delete student " + st.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                auth.deleteUser(st.getUsername());
                marks.deleteMarks(roll);
                refresh.doClick();
                JOptionPane.showMessageDialog(this, "Student deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        leftBottom.add(delStd);
        left.add(leftBottom, BorderLayout.SOUTH);
        center.add(left, BorderLayout.CENTER);
        
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Edit Selected Student"));
        right.setBackground(Color.WHITE);
        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topRight.setBackground(Color.WHITE);
        topRight.add(new JLabel("Selected Student Roll:"));
        JTextField selectedRollField = new JTextField(10);
        selectedRollField.setEditable(false);
        topRight.add(selectedRollField);
        topRight.add(new JLabel("Subject:"));
        JComboBox<String> subjCombo = new JComboBox<>(MarksController.SUBJECTS);
        topRight.add(subjCombo);
        topRight.add(new JLabel("Mark:"));
        JTextField markFld = new JTextField(4);
        topRight.add(markFld);
        JButton setMarkBtn = new JButton("Set/Update Mark");
        setMarkBtn.setBackground(new Color(0, 255, 127)); 
        topRight.add(setMarkBtn);
        JButton editAllMarksBtn = new JButton("Edit All Marks");
        editAllMarksBtn.setBackground(new Color(255, 20, 147)); 
        topRight.add(editAllMarksBtn);
        right.add(topRight, BorderLayout.NORTH);
        JPanel midRight = new JPanel(new BorderLayout());
        JTextArea remarkArea = new JTextArea(5, 30);
        remarkArea.setBorder(BorderFactory.createTitledBorder("Teacher Remark (Required for each student)"));
        midRight.add(new JScrollPane(remarkArea), BorderLayout.CENTER);
        right.add(midRight, BorderLayout.CENTER);
        JPanel rightBottom = new JPanel();
        rightBottom.setBackground(Color.WHITE);
        JButton saveRemark = new JButton("Save Remark");
        saveRemark.setBackground(new Color(218, 165, 32)); 
        JButton genReport = new JButton("Generate Student Report (HTML)");
        genReport.setBackground(new Color(255, 69, 0)); 
        JButton genClassReport = new JButton("Generate Class Reports (all HTML)");
        genClassReport.setBackground(new Color(255, 0, 255)); 
        rightBottom.add(saveRemark);
        rightBottom.add(genReport);
        rightBottom.add(genClassReport);
        right.add(rightBottom, BorderLayout.SOUTH);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String roll = (String) table.getValueAt(row, 0);
                    selectedRollField.setText(roll);
                    Student st = auth.getStudentByRoll(roll);
                    if (st != null) {
                        remarkArea.setText(st.getTeacherRemark());
                    }
                }
            }
        });
        
        setMarkBtn.addActionListener(e -> {
            String roll = selectedRollField.getText();
            if (roll.isEmpty()) { JOptionPane.showMessageDialog(this, "No student selected."); return; }
            String subj = (String) subjCombo.getSelectedItem();
            String mk = markFld.getText().trim();
            int mval;
            try {
                mval = Integer.parseInt(mk);
                if (mval < 0 || mval > 100) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid marks 0-100.", "Invalid", JOptionPane.ERROR_MESSAGE);
                return;
            }
            marks.setMark(roll, subj, mval);
            Student st = auth.getStudentByRoll(roll);
            if (st != null) {
                st.setMark(subj, mval);
                auth.updateUser(st);
            }
            JOptionPane.showMessageDialog(this, "Mark saved.", "Saved", JOptionPane.INFORMATION_MESSAGE);
            refresh.doClick();
        });
        editAllMarksBtn.addActionListener(e -> {
            String roll = selectedRollField.getText();
            if (roll.isEmpty()) { JOptionPane.showMessageDialog(this, "No student selected."); return; }
            Student st = auth.getStudentByRoll(roll);
            if (st == null) return;
            JPanel marksPanel = new JPanel(new GridLayout(MarksController.SUBJECTS.length, 2));
            Map<String, JTextField> fields = new HashMap<>();
            for (String subj : MarksController.SUBJECTS) {
                marksPanel.add(new JLabel(subj));
                JTextField mf = new JTextField(String.valueOf(st.getMark(subj)));
                marksPanel.add(mf);
                fields.put(subj, mf);
            }
            int option = JOptionPane.showConfirmDialog(this, marksPanel, "Edit All Marks for " + st.getName(), JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                boolean valid = true;
                for (String subj : MarksController.SUBJECTS) {
                    String mk = fields.get(subj).getText().trim();
                    int mval;
                    try {
                        mval = Integer.parseInt(mk);
                        if (mval < 0 || mval > 100) throw new NumberFormatException();
                    } catch (NumberFormatException ex) {
                        valid = false;
                        break;
                    }
                    marks.setMark(roll, subj, mval);
                    st.setMark(subj, mval);
                }
                if (valid) {
                    auth.updateUser(st);
                    JOptionPane.showMessageDialog(this, "All marks updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refresh.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid marks entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        saveRemark.addActionListener(e -> {
            String roll = selectedRollField.getText();
            if (roll.isEmpty()) { JOptionPane.showMessageDialog(this, "Select student."); return; }
            String remark = remarkArea.getText().trim();
            if (remark.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Remark is required for each student.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Student st = auth.getStudentByRoll(roll);
            if (st == null) { JOptionPane.showMessageDialog(this, "Student not found."); return; }
            st.setTeacherRemark(remark);
            auth.updateUser(st);
            JOptionPane.showMessageDialog(this, "Remark updated on student profile.", "Saved", JOptionPane.INFORMATION_MESSAGE);
        });
        genReport.addActionListener(e -> {
            String roll = selectedRollField.getText();
            if (roll.isEmpty()) { JOptionPane.showMessageDialog(this, "Select student."); return; }
            Student st = auth.getStudentByRoll(roll);
            if (st == null) { JOptionPane.showMessageDialog(this, "Student not found."); return; }
            if (st.getTeacherRemark().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Remark is required before generating report.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Map<String, Integer> mm = marks.getMarksFor(st.getRollNumber());
            for (String subj : MarksController.SUBJECTS) {
                int v = mm.getOrDefault(subj, 0);
                st.setMark(subj, v);
            }
            auth.updateUser(st);
            try {
                File f = marks.generateHTMLReport(st);
                Desktop.getDesktop().browse(f.toURI());
                
                double avg = st.average();
                String msg;
                if (avg >= 90) msg = "Excellent performance! Keep shining! 🌟";
                else if (avg >= 80) msg = "Great job! Well done! 👍";
                else if (avg >= 70) msg = "Good effort! Keep improving! 📈";
                else msg = "Keep working hard! You can do it! 💪";
                JOptionPane.showMessageDialog(this, msg, "Performance Feedback", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Could not generate/open report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        genClassReport.addActionListener(e -> {
            List<Student> studs = auth.getAllStudents();
            boolean allHaveRemarks = true;
            for (Student ss : studs) {
                if (ss.getTeacherRemark().trim().isEmpty()) {
                    allHaveRemarks = false;
                    break;
                }
            }
            if (!allHaveRemarks) {
                JOptionPane.showMessageDialog(this, "All students must have remarks before generating class reports.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int count = 0;
            for (Student ss : studs) {
                Map<String, Integer> mm = marks.getMarksFor(ss.getRollNumber());
                for (String subj : MarksController.SUBJECTS) {
                    ss.setMark(subj, mm.getOrDefault(subj, 0));
                }
                auth.updateUser(ss);
                try {
                    marks.generateHTMLReport(ss);
                    count++;
                } catch (IOException ioe) { ioe.printStackTrace(); }
            }
            JOptionPane.showMessageDialog(this, "Generated " + count + " reports in reports/ directory.", "Done", JOptionPane.INFORMATION_MESSAGE);
        });
        center.add(right, BorderLayout.EAST);
        p.add(center, BorderLayout.CENTER);
        add(p, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showStudentDashboard(Student student) {
        getContentPane().removeAll();
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(new Color(135, 206, 235)); 
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(135, 206, 235));
        JLabel welcomeLabel = new JLabel("Student Dashboard - Welcome " + student.getName(), SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        top.add(welcomeLabel, BorderLayout.WEST);
        JButton logout = new JButton("Logout");
        logout.setBackground(Color.RED);
        logout.setForeground(Color.WHITE);
        logout.addActionListener(e -> showHome());
        top.add(logout, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);
        JPanel center = new JPanel(new BorderLayout(8, 8));
        
        JPanel info = new JPanel(new GridLayout(1, 2));
        JPanel left = new JPanel(new GridLayout(5, 1));
        left.setBorder(BorderFactory.createTitledBorder("Profile"));
        left.setBackground(Color.WHITE);
        left.add(new JLabel("Name: " + student.getName()));
        left.add(new JLabel("Roll: " + student.getRollNumber()));
        left.add(new JLabel("Class: " + student.getStudentClass()));
        left.add(new JLabel("Branch: " + student.getBranch()));
        left.add(new JLabel("Average: " + String.format("%.2f", student.average())));
        info.add(left);
        
        JPanel right = new JPanel(new BorderLayout());
        right.setBorder(BorderFactory.createTitledBorder("Marks"));
        right.setBackground(Color.WHITE);
        String[] col = {"Subject", "Marks", "Class Avg"};
        DefaultTableModel tm = new DefaultTableModel(col, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        Map<String, Integer> mm = marks.getMarksFor(student.getRollNumber());
        
        List<Student> allStuds = auth.getAllStudents();
        List<String> allRolls = allStuds.stream().map(Student::getRollNumber).collect(Collectors.toList());
        for (String subj : MarksController.SUBJECTS) {
            int val = mm.getOrDefault(subj, 0);
            double avg = marks.subjectAverage(allRolls, subj);
            tm.addRow(new Object[]{subj, val, String.format("%.2f", avg)});
        }
        JTable t = new JTable(tm);
        right.add(new JScrollPane(t), BorderLayout.CENTER);
        info.add(right);
        center.add(info, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(135, 206, 235));
        JTextArea remarkArea = new JTextArea(4, 40);
        remarkArea.setText(student.getTeacherRemark());
        remarkArea.setEditable(false);
        bottom.setBorder(BorderFactory.createTitledBorder("Teacher Remark"));
        bottom.add(new JScrollPane(remarkArea));
        center.add(bottom, BorderLayout.SOUTH);
        JPanel actions = new JPanel();
        actions.setBackground(new Color(135, 206, 235));
        JButton genReport = new JButton("Generate My Report (HTML)");
        genReport.setBackground(new Color(255, 140, 0)); 
        JButton exportCSV = new JButton("Export My Report (CSV)");
        exportCSV.setBackground(new Color(75, 0, 130)); 
        exportCSV.setForeground(Color.WHITE);
        actions.add(genReport); actions.add(exportCSV);
        genReport.addActionListener(e -> {
            
            Map<String, Integer> mks = marks.getMarksFor(student.getRollNumber());
            for (String subj : MarksController.SUBJECTS) {
                student.setMark(subj, mks.getOrDefault(subj, 0));
            }
            auth.updateUser(student);
            try {
                File f = marks.generateHTMLReport(student);
                Desktop.getDesktop().browse(f.toURI());
                
                double avg = student.average();
                String msg;
                if (avg >= 90) msg = "Excellent performance! Keep shining! 🌟";
                else if (avg >= 80) msg = "Great job! Well done! 👍";
                else if (avg >= 70) msg = "Good effort! Keep improving! 📈";
                else msg = "Keep working hard! You can do it! 💪";
                JOptionPane.showMessageDialog(this, msg, "Performance Feedback", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
            }
        });
        exportCSV.addActionListener(e -> {
            try {
                File dir = new File("exports");
                if (!dir.exists()) dir.mkdirs();
                File csv = new File(dir, student.getRollNumber() + "_report.csv");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(csv))) {
                    bw.write("Name,Roll,Class,Branch,Average\n");
                    bw.write(String.format("%s,%s,%s,%s,%.2f\n", student.getName(), student.getRollNumber(), student.getStudentClass(), student.getBranch(), student.average()));
                    bw.write("\nSubject,Marks\n");
                    Map<String, Integer> mks = marks.getMarksFor(student.getRollNumber());
                    for (String subj : MarksController.SUBJECTS) {
                        bw.write(subj + "," + mks.getOrDefault(subj, 0) + "\n");
                    }
                }
                Desktop.getDesktop().open(csv);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Could not export CSV: " + ex.getMessage());
            }
        });
        p.add(center, BorderLayout.CENTER);
        p.add(actions, BorderLayout.SOUTH);
        add(p, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
