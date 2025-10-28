package main.java;
import main.java.view.AppFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;



import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        
        try {
            Files.createDirectories(new File("data").toPath());
            Files.createDirectories(new File("reports").toPath());
            Files.createDirectories(new File("exports").toPath());
        } catch (IOException e) { /* ignore */ }
        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.setVisible(true);
        });
    }
}
