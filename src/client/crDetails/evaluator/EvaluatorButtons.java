package client.crDetails.evaluator;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class EvaluatorButtons implements ClientUI {

    @FXML
    private Button requestPhaseTimeButton;

    @FXML
    private Button createEvaluationReportButton;

    @FXML
    void showCreateEvaluationReportDialog(ActionEvent event) {

    }

    @FXML
    void showRequestTimeDialog(ActionEvent event) {

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
