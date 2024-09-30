/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package gradesanalyzer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author errol
 */
public class FXMLDocumentController implements Initializable {

    private ArrayList<String> m_gradesFromFile;
    private FileHandler fileHandler;

    @FXML
    private Button calculateGPAButton;

    @FXML
    private Button readGradeFilesButton;

    @FXML
    private Button readGradesButton;

    @FXML
    private Button calcGPAButton;

    @FXML
    private TextField readGradesTextField;

    @FXML
    private TextArea outputArea;

    private GradesAnalyzer gradesAnalyzer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m_gradesFromFile = new ArrayList<>();
        fileHandler = new FileHandler("grades_read.txt", "grades_write.txt");
    }

    @FXML
    private void handleReadGrades(ActionEvent event) {
        try {
            String readFileName = readGradesTextField.getText();
            if (!readFileName.isEmpty()) {
                fileHandler = new FileHandler(readFileName, "grades_write.txt");
                boolean fileOpened = fileHandler.openFileandRead();
                if (fileOpened) {
                    boolean fileRead = fileHandler.readFile(m_gradesFromFile);
                    if (fileRead) {
                        outputArea.appendText("Grades loaded successfully.\n");
                        gradesAnalyzer = new GradesAnalyzer(readFileName, "grades_write.txt");
                        gradesAnalyzer.loadGrades();
                    } else {
                        outputArea.appendText("Error reading file.\n");
                    }
                } else {
                    outputArea.appendText("Error opening file.\n");
                }
            } else {
                outputArea.appendText("Please enter a valid file name.\n");
            }
        } catch (Exception e) {
            outputArea.appendText("Error reading file.\n");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCalculateMean(ActionEvent event) {
        if (gradesAnalyzer != null) {
            double mean = gradesAnalyzer.getMeanGrades();
            outputArea.appendText("Mean of grades: " + mean + "\n");
        } else {
            outputArea.appendText("Please load grades first.\n");
        }
    }

    @FXML
    private void handleCalculateStdDev(ActionEvent event) {
        if (gradesAnalyzer != null) {
            double stdDev = gradesAnalyzer.getStandardDeviation();
            outputArea.appendText("Standard Deviation of grades: " + stdDev + "\n");
        } else {
            outputArea.appendText("Please load grades first.\n");
        }
    }

    @FXML
    private void handleAssignLetterGrades(ActionEvent event) {
        if (gradesAnalyzer != null) {
            outputArea.appendText("Assigning letter grades:\n");

            List<String> letterGrades = gradesAnalyzer.assignLetterGrades();

            letterGrades.forEach(grade -> outputArea.appendText(grade + "\n"));
        } else {
            outputArea.appendText("Please load grades first.\n");
        }
    }
    
    @FXML
    private void handleClearButton(ActionEvent event) {
        outputArea.clear(); 
        readGradesTextField.clear();
        m_gradesFromFile.clear();
        gradesAnalyzer = null;
    }
}
