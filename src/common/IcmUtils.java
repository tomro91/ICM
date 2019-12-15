package common;

import client.ClientMain;
import client.ClientUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class IcmUtils {

    public static void displayConfirmationMsg(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void displayConfirmationMsg(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void displayConfirmationMsg(String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void displayInformationMsg(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void displayInformationMsg(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void displayInformationMsg(String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    public static void displayErrorMsg(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
        System.exit(1);
    }

    public static void displayErrorMsg(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
        System.exit(1);
    }

    public static void displayErrorMsg(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(contentText);
        alert.showAndWait();
        System.exit(1);
    }

    public static void loadScene(ClientUI clientUI, String sceneTitle, String fxmlPath, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(clientUI.getClass().getResource(fxmlPath).openStream());
        Scene scene = new Scene(root, width, height);
        Stage primaryStage = ClientMain.getPrimaryStage();
        primaryStage.setScene(scene);
        primaryStage.setTitle(sceneTitle);
        primaryStage.show();
    }

    public static void loadHomeScene(ClientUI clientUI) throws IOException {
        loadScene(clientUI, "ICM prototype", "/client/mainWindow/MainWindowUI.fxml", 590, 565);
    }
}
