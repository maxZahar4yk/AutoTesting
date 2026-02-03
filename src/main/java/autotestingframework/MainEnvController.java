package autotestingframework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.IOException;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class MainEnvController {
    @FXML
    protected ListView<String> fileListView,movedFilesListView;
    @FXML
    protected Button moveRightButton, moveLeftButton;
    @FXML
    protected ProgressBar progress;
    @FXML
    protected TextField siteURL;

    public Boolean isStartTesting=false;

    @FXML
    protected void initialize(){
        moveRightButton.setOnAction(event -> moveFile());
        moveLeftButton.setOnAction(event -> moveBackFile());
        loadFilesFromDirectory("..\\Testing.Framework\\src\\test\\java\\features");
    }


    private void loadFilesFromDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String content = readContentAfterScenario(file);
                        fileListView.getItems().add(content);
                    }
                }
            }
        }
    }


    private String readContentAfterScenario(File file) {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("Scenario:")) {
                    contentBuilder.append(file.getName()).append(": ").append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i=contentBuilder.indexOf("Scenario:");
        contentBuilder.replace(i,i+9,"");
        return contentBuilder.toString();
    }


    private void moveFile() {
        String selectedFileContent = fileListView.getSelectionModel().getSelectedItem();
        if (selectedFileContent != null) {
            fileListView.getItems().remove(selectedFileContent);
            movedFilesListView.getItems().add(selectedFileContent);
        }
    }

    private void moveBackFile() {
        String selectedFileContent = movedFilesListView.getSelectionModel().getSelectedItem();
        if (selectedFileContent != null) {
            movedFilesListView.getItems().remove(selectedFileContent);
            fileListView.getItems().add(selectedFileContent);
        }
    }


    @FXML
    protected void showInstruction(){
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Instruction.fxml")));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Про програму");

            stage.setFullScreen(false);
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void showAbout() {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("about.fxml")));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Про програму");

            stage.setFullScreen(false);
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isA=false;
    private void startProgress() {
        new Thread(() -> {
            try {
                for (double progressValue = 0; progressValue <= 1; progressValue += 0.1) {
                    final double currentProgress = progressValue;
                    javafx.application.Platform.runLater(() -> progress.setProgress(currentProgress));
                    Thread.sleep(500);
                    if (progressValue>0.8) isA=true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setHeight(200);
        alert.showAndWait();
    }

    @FXML
    private void startTesting() throws IOException, InterruptedException {
        if(movedFilesListView.getItems().size()>0)
        {
            isStartTesting=true;
            if (siteURL.getText().equals("")){
                showAlert("Помилка вхідних даних", "Введіть посилання на сайт для тестування");
            }else if (!siteURL.getText().contains("https://")){
                showAlert("Помилка вхідних даних", "Введені данні не вірні.\n Введіть повне посилання на сайт для тестування, що буде містити https://");
            }else {
                startProgress();
                if (isA)showAlert("Тестування завершено", "Для відображення і генерації звіту, виконайте відповідну команду!");
                //TestRunner testRunner=new TestRunner
            }
        }else showAlert("Відсутні обрані тестові сценарії!","Оберіть тестові сценарії для проведення автоматизованого тестування обраного сайту.");

    }

    @FXML
    protected void generateReport() throws IOException, InterruptedException {
        if (isStartTesting){
            String[] command = {"cmd", "/c", "allure", "generate", "allure-results", "--clean", "-o", "allure-report"};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new File("..\\Testing.Framework"));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            String[] command1 = {"cmd", "/c", "allure", "open"};
            ProcessBuilder processBuilder1 = new ProcessBuilder(command1);
            processBuilder1.directory(new File("..\\Testing.Framework"));

            Process process1 = processBuilder1.start();
        }else {
            showAlert("Почніть тестування!","Для генерації звіту свочатку почніть тестування");
        }
    }

    @FXML
    protected void saveReport() {
        if (isStartTesting) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Виберіть директорію для збереження звіту");

            Stage stage = (Stage) moveLeftButton.getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(stage);

            String sourceDirectory = "..\\Testing.Framework\\allure-report";
            String targetDirectory = String.valueOf(selectedDirectory + "\\allure-report");

            try {
                copyDirectory(sourceDirectory, targetDirectory);
                System.out.println("Папку скопійовано успішно!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else showAlert("Почніть тестування!","Для генерації звіту свочатку почніть тестування");
    }

    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        File sourceDirectory = new File(sourceDirectoryLocation);
        File destinationDirectory = new File(destinationDirectoryLocation);
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
    }

    @FXML
    protected void openReport() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Виберіть директорію для відкриття звіту");

        Stage stage = (Stage) moveLeftButton.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        String[] command1 = {"cmd", "/c", "allure", "open"};
        ProcessBuilder processBuilder1 = new ProcessBuilder(command1);
        processBuilder1.directory(new File(selectedDirectory.getAbsolutePath()));
        System.out.println(selectedDirectory.getName());

        Process process1 = processBuilder1.start();
    }
}