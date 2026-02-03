package autotestingframework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class GreetingController {
    @FXML
    Button button;

    @FXML
    protected void showAbout() {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("about.fxml")));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Про програму - AutoTesting Web-pages!");

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
    protected void showInstruction(){
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Instruction.fxml")));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Довідка - AutoTesting Web-pages!");

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
    protected void start(){
        try{
            Stage start = (Stage) button.getScene().getWindow(); start.close();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainEnv.fxml")));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("AutoTesting Web-pages");

            stage.setFullScreen(false);
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png")));
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


