package client.crDetails.executiveLeader;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class ExecutiveLeaderButtons implements ClientUI {

    @FXML
    private Button requestPhaseTimeButton1;

    @FXML
    private Button confirmExecutionButton;

    @FXML
    void confirmExecution(ActionEvent event) {

    }

    @FXML
    void showRequestTimeDialog(ActionEvent event) {

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
