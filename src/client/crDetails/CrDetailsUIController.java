package client.crDetails;


import client.ClientController;
import client.ClientUI;
import common.IcmUtils;
import entities.ChangeRequest;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import server.ServerService;

import java.io.IOException;

public class CrDetailsUIController implements ClientUI {

    @FXML
    private AnchorPane changeRequestIdTextField;
    @FXML
    private TextField changeRequestIDTextField;
    @FXML
    private TextField openingDateTextField;
    @FXML
    private TextField initiatorTextField;
    @FXML
    private TextField infoSystemTextField;
    @FXML
    private TextArea currentStateTextArea;
    @FXML
    private TextArea requestedChangeTextField;
    @FXML
    private TextArea reasonForChangeTextArea;
    @FXML
    private TextArea commentsTextArea;
    @FXML
    private TextField currentPhaseTextField;


    private static ChangeRequest currRequest;
    private ClientController clientController;

    public static ChangeRequest getCurrRequest() {
        return currRequest;
    }

    public static void setCurrRequest(ChangeRequest currRequest) {
        CrDetailsUIController.currRequest = currRequest;
    }

    public void initialize() {
        try {
            clientController = ClientController.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }







    @FXML
    void backToHome() {
        try {
            IcmUtils.loadScene(this, IcmUtils.Scenes.Main_Window_New);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
