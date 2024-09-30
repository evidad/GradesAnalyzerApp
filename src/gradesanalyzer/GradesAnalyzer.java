/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gradesanalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.OptionalDouble;

/**
 *
 * @author errol
 */
public class GradesAnalyzer {

    private FileHandler fileHandler;
    private ArrayList<String> grades;

    public GradesAnalyzer(String readFileName, String writeFileName) {
        fileHandler = new FileHandler(readFileName, writeFileName);
        grades = new ArrayList<>();
    }

    public List<String> getGrades() {
        return grades;
    }

    // Method to read grades using FileHandler
    public void loadGrades() {
        FileHandler.readGrades(grades, fileHandler); // Use FileHandler to read grades into the ArrayList
//        System.out.println("Grades loaded: " + grades); 
    }

    // You can now add other methods to analyze the grades
    public double getMeanGrades() {
        List<Double> convertedGradesList = grades.stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());

        OptionalDouble mean = convertedGradesList.stream()
                .mapToDouble(Double::doubleValue)
                .average();

        return mean.orElse(0.0);
    }

    public double getStandardDeviation() {
        double mean = getMeanGrades();
        double variance = grades.stream()
                .map(Double::parseDouble)
                .mapToDouble(grade -> Math.pow(grade - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }
    
    public List<String> assignLetterGrades() {
        List<String> letterGrades = new ArrayList<>();
        double mean = getMeanGrades();
        double stdDev = getStandardDeviation();

        grades.stream()
              .map(Double::parseDouble)  // Convert each String to a Double
              .forEach(score -> {
                  String letterGrade = assignLetterGrade(score);  // Correctly pass the score as a double
                  letterGrades.add("Score: " + score + " -> Letter Grade: " + letterGrade);
              });

        return letterGrades;
    }

    public String assignLetterGrade(double score) {
        double mean = getMeanGrades();
        double stdDev = getStandardDeviation();

        if (score > mean + 1.5 * stdDev) {
            return "A";
        } else if (score > mean + 0.5 * stdDev && score <= mean + 1.5 * stdDev) {
            return "B";
        } else if (score > mean - 0.5 * stdDev && score <= mean + 0.5 * stdDev) {
            return "C";
        } else if (score > mean - 1.5 * stdDev && score <= mean - 0.5 * stdDev) {
            return "D";
        } else {
            return "F";
        }
    }
}
