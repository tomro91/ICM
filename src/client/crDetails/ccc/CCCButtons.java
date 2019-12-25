package client.crDetails.ccc;

import java.io.IOException;

import client.ClientController;
import client.ClientUI;
import common.IcmUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import server.ServerService;

public class CCCButtons implements ClientUI {


    // ccc buttons controller
    @FXML
    private VBox CCCButtonsVBox;

    @FXML
    private Button viewEvaluationReportButton;

    @FXML
    private Button setDecisionButton;

    @FXML
    private Button assignTesterButton;

    private ClientController clientController;

    @FXML
    void showAssignTesterDialog(ActionEvent event) {

    }

    @FXML
    void showEvaluationReport(ActionEvent event) throws IOException {

        IcmUtils.loadScene(this, IcmUtils.Scenes.Evaluation_Report);

    }

    @FXML
    void showSetDecisionDialog(ActionEvent event) {

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }

    public void enableChairmanButtons() {
        setDecisionButton.setDisable(false);
        assignTesterButton.setDisable(false);
    }

    public void initialize() {
        try {
            clientController = ClientController.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
