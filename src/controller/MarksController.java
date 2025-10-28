
package controller;

import main.java.model.Student;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;





public class MarksController {
    private static final String MARKS_PATH = "data/marks.ser";
    private Map<String, Map<String, Integer>> marks;
    public static final String[] SUBJECTS = {
            "Discrete Mathematics",
            "Computational Statistics",
            "Economics",
            "Computer Organisation & Architecture",
            "Digital Design"
    };

    public MarksController() {
        marks = loadMarks();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Map<String, Integer>> loadMarks() {
        File f = new File(MARKS_PATH);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (Map<String, Map<String, Integer>>) obj;
        } catch (Exception e) {
            System.err.println("Marks load error: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private void saveMarks() {
        File f = new File(MARKS_PATH);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(marks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMark(String roll, String subject, int mark) {
        marks.putIfAbsent(roll, new HashMap<>());
        marks.get(roll).put(subject, mark);
        saveMarks();
    }

    public Integer getMark(String roll, String subject) {
        if (!marks.containsKey(roll)) return null;
        return marks.get(roll).get(subject);
    }

    public Map<String, Integer> getMarksFor(String roll) {
        return marks.getOrDefault(roll, new HashMap<>());
    }

    public void setMarksFor(String roll, Map<String, Integer> m) {
        marks.put(roll, new HashMap<>(m));
        saveMarks();
    }

    public Map<String, Map<String, Integer>> getAllMarks() { return marks; }

    public void deleteMarks(String roll) {
        marks.remove(roll);
        saveMarks();
    }

    public double subjectAverage(List<String> rolls, String subject) {
        double sum = 0; int cnt = 0;
        for (String r : rolls) {
            Integer m = getMark(r, subject);
            if (m != null) { sum += m; cnt++; }
        }
        return cnt == 0 ? 0.0 : sum / cnt;
    }

    
    private void generateGraphImage(Student s, String graphPath) throws IOException {
        int width = 600;
        int height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(50, 350, 550, 350); 
        g2d.drawLine(50, 50, 50, 350); 

        
        g2d.drawString("Marks", 10, 200);
        g2d.drawString("Subjects", 250, 380);

        
        for (int i = 0; i <= 100; i += 20) {
            g2d.drawString(String.valueOf(i), 20, 350 - (i * 3));
        }

        Map<String, Integer> mm = getMarksFor(s.getRollNumber());
        int barWidth = 80;
        int spacing = 20;
        int x = 70;
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA};

        for (int i = 0; i < SUBJECTS.length; i++) {
            String subj = SUBJECTS[i];
            int mark = mm.getOrDefault(subj, 0);
            int barHeight = mark * 3; // scale to 300 px max
            g2d.setColor(colors[i % colors.length]);
            g2d.fillRect(x, 350 - barHeight, barWidth, barHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, 350 - barHeight, barWidth, barHeight);
            g2d.drawString(subj.substring(0, Math.min(5, subj.length())), x + 10, 365); // abbreviated
            g2d.drawString(String.valueOf(mark), x + 30, 350 - barHeight - 5);
            x += barWidth + spacing;
        }

        g2d.dispose();
        ImageIO.write(image, "png", new File(graphPath));
    }

    public File generateHTMLReport(Student s) throws IOException {
        File dir = new File("reports");
        if (!dir.exists()) dir.mkdirs();
        String filename = "reports/" + s.getRollNumber() + "_report.html";
        String graphname = "reports/" + s.getRollNumber() + "_graph.png";
        generateGraphImage(s, graphname);
        File f = new File(filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write("<!doctype html><html><head><meta charset='utf-8'><title>Report - " + s.getName() + "</title>");
            bw.write("<style>body{font-family:Arial;margin:20px;background:#f0f8ff;}table{border-collapse:collapse;width:80%;}td,th{border:1px solid #444;padding:8px;text-align:left;background:#add8e6;}h1{color:#333}img{width:600px;}</style></head><body>");
            bw.write("<h1 style='color:#ff4500;'>Progress Report</h1>");
            bw.write("<p><b>Name:</b> " + s.getName() + "<br>");
            bw.write("<b>Roll:</b> " + s.getRollNumber() + "<br>");
            bw.write("<b>Class:</b> " + s.getStudentClass() + " | <b>Branch:</b> " + s.getBranch() + "</p>");
            bw.write("<h3 style='color:#228b22;'>Marks</h3>");
            bw.write("<table><tr><th>Subject</th><th>Marks</th></tr>");
            Map<String, Integer> mm = getMarksFor(s.getRollNumber());
            for (String subj : SUBJECTS) {
                Integer m = mm.getOrDefault(subj, 0);
                bw.write("<tr><td>" + subj + "</td><td>" + m + "</td></tr>");
            }
            bw.write("</table>");
            bw.write("<p><b>Average:</b> " + String.format("%.2f", s.average()) + "</p>");
            bw.write("<p><b>Teacher Remark:</b> " + s.getTeacherRemark() + "</p>");
            bw.write("<h3 style='color:#228b22;'>Performance Graph</h3>");
            bw.write("<img src='" + s.getRollNumber() + "_graph.png' alt='Marks Graph'>");
            bw.write("<p style='font-size:12px;color:#666'>Generated by Student Report Card Generator</p>");
            bw.write("</body></html>");
        }
        return f;
    }
}
