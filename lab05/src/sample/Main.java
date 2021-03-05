package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


import javax.xml.crypto.Data;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab 5 Solution");
        DataSource data = new DataSource();
        ObservableList<StudentRecord> values = data.getAllMarks();

        TableView DataTable = new TableView<>();
        TableColumn<StudentRecord, String> SIDColumn = new TableColumn<>("SID");
        SIDColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));

        TableColumn<StudentRecord, Double> AssignColumn = new TableColumn<>("Assignments");
        AssignColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentGrade"));

        TableColumn<StudentRecord, Double> midColumn = new TableColumn<>("Midterm");
        midColumn.setCellValueFactory(new PropertyValueFactory<>("midtermGrade"));

        TableColumn<StudentRecord, Double> examColumn = new TableColumn<>("Final Exam");
        examColumn.setCellValueFactory(new PropertyValueFactory<>("finalExamGrade"));

        TableColumn<StudentRecord, Double> markColumn = new TableColumn<>("Final Mark");
        markColumn.setCellValueFactory(new PropertyValueFactory<>("finalGrade"));

        TableColumn<StudentRecord, String> letterColumn = new TableColumn<>("Letter Grade");
        letterColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        DataTable.getColumns().addAll(SIDColumn, AssignColumn, midColumn, examColumn, markColumn, letterColumn);
        DataTable.setItems(values);

        VBox display = new VBox(DataTable);
        primaryStage.setScene(new Scene(display,475 ,255));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class DataSource {
        public ObservableList<StudentRecord> getAllMarks() {
            ObservableList<StudentRecord> marks = FXCollections.observableArrayList();
            // Student ID, Assignments, Midterm, Final exam
            marks.add(new StudentRecord("100100100", 75.0f, 68.0f, 54.25f));
            marks.add(new StudentRecord("100100101", 70.0f, 69.25f, 51.5f));
            marks.add(new StudentRecord("100100102", 100.0f, 97.0f, 92.5f));
            marks.add(new StudentRecord("100100103", 90.0f, 88.5f, 68.75f));
            marks.add(new StudentRecord("100100104", 72.25f, 74.75f, 58.25f));
            marks.add(new StudentRecord("100100105", 85.0f, 56.0f, 62.5f));
            marks.add(new StudentRecord("100100106", 70.0f, 66.5f, 61.75f));
            marks.add(new StudentRecord("100100107", 55.0f, 47.0f, 50.5f));
            marks.add(new StudentRecord("100100109", 82.5f, 77.0f, 74.25f));
            return marks;
        }
    }
    public class StudentRecord {
        private String studentID;
        private double midtermGrade;
        private double assignmentGrade;
        private double finalExamGrade;
        private double finalGrade;
        private String letterGrade;

        public StudentRecord(String sid, double mid, double assign, double finalE) {
            this.studentID = sid;
            this.assignmentGrade = assign;
            this.finalExamGrade = finalE;
            this.midtermGrade = mid;
            this.finalGrade = 0.2 * assignmentGrade + 0.3 * midtermGrade + 0.5 * finalExamGrade;

            if (this.finalGrade >= 50 && this.finalGrade <= 59.99) {
                this.letterGrade = "D";
            } else if (this.finalGrade >= 60 && this.finalGrade <= 69.99) {
                this.letterGrade = "C";
            } else if (this.finalGrade >= 70 && this.finalGrade <= 79.99) {
                this.letterGrade = "B";
            } else if (this.finalGrade >= 80) {
                this.letterGrade = "A";
            } else {
                this.letterGrade = "F";
            }
        }
        public String getStudentID() {
            return studentID;
        }
        public double getMidtermGrade() {
            return midtermGrade;
        }
        public double getFinalExamGrade() {
            return finalExamGrade;
        }
        public double getFinalGrade() {
            return finalGrade;
        }
        public String getLetterGrade() {
            return letterGrade;
        }
        public double getAssignmentGrade() {
            return assignmentGrade;
        }
    }
}

