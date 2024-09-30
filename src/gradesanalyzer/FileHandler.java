/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gradesanalyzer;

import java.util.Scanner;
import java.util.Formatter;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author errol
 */

public class FileHandler {
    
 private Formatter writer = null;
    private Scanner reader = null;
    private String readFileName;
    private String writeFileName;

    public FileHandler(String readFileName, String writeFileName) {
        this.readFileName = readFileName;
        this.writeFileName = writeFileName;
    }

    public boolean openFileandRead() {
        try {
            reader = new Scanner(Paths.get(readFileName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean openFileandWrite() {
        try {
            writer = new Formatter(writeFileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeToFile(ArrayList<String> datatoFile) {
        try {
            System.out.println("Writing grades to file: ");
            for (String str : datatoFile) {
                writer.format("%s%n", str); 
                System.out.printf("%s%n", str); 
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                writer.close();
            }

        }
    }

    public boolean readFile(ArrayList<String> gradesFromFile) {
        if (reader == null) { 
            return false;
        }
        
        try {
            while (reader.hasNextLine()) { 
                String grade = reader.nextLine().trim();
                if (!grade.isEmpty()) {
                    gradesFromFile.add(grade); 
                }
            }
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (reader != null) {
                reader.close(); 
            }
        }
    }

    public static void writeGrades(ArrayList<String> gradesToWrite, FileHandler fh) {
        fh.openFileandWrite();
        fh.writeToFile(gradesToWrite);
    }

    public static void readGrades(ArrayList<String> gradesToRead, FileHandler fh) {
        fh.openFileandRead();
        fh.readFile(gradesToRead);
    }
}

