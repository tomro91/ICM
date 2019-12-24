package client.crDetails.ccc;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import server.ServerService;

public class CCCButtons implements ClientUI {

    @FXML
    private VBox CCCButtonsVBox;

    @FXML
    private Button viewEvaluationReportButton;

    @FXML
    private Button setDecisionButton;

    @FXML
    private Button assignTesterButton;

    @FXML
    void showAssignTesterDialog(ActionEvent event) {

    }

    @FXML
    void showEvaluationReport(ActionEvent event) {

    }

    @FXML
    void showSetDecisionDialog(ActionEvent event) {

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
