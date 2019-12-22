package client.crDetails;


import client.ClientController;
import client.ClientUI;
import common.IcmUtils;
import entities.ChangeRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import server.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrDetailsBase implements ClientUI {

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
    @FXML
    private TextField phaseDeadLineTextField;
    @FXML
    private VBox buttonsVBox;
    @FXML
    private Button downloadFilesButton;

    private static ChangeRequest currRequest;
    private ClientController clientController;

    public static ChangeRequest getCurrRequest() {
        return currRequest;
    }

    public static void setCurrRequest(ChangeRequest currRequest) {
        CrDetailsBase.currRequest = currRequest;
    }

    public void initialize() {
        try {
            clientController = ClientController.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> params = new ArrayList<>();
        params.add(ClientController.getUser().getId());
        params.add(currRequest.getId());

        ServerService loadRequestData = new ServerService(ServerService.DatabaseService.Get_Request_Details, params);

        clientController.handleMessageFromClientUI(loadRequestData);

        Button myBtn = new Button("test");
        myBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        myBtn.styleProperty().setValue(downloadFilesButton.getStyle());
        buttonsVBox.getChildren().add(myBtn);
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
