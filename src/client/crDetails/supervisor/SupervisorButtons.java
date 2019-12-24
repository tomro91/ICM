package client.crDetails.supervisor;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class SupervisorButtons implements ClientUI {


    @FXML
    private Button phaseTimeDecisionButton;

    @FXML
    private Button assignPhaseLeadersButton;

    @FXML
    private Button assignPhaseWorkersButton;

    @FXML
    private Button freezeRequestButton;

    @FXML
    private Button closeChangeRequestButton;

    @FXML
    void closeChangeRequest(ActionEvent event) {
        System.out.println("closed!");
    }

    @FXML
    void freezeRequest(ActionEvent event) {

    }

    @FXML
    void setTimeDecision(ActionEvent event) {

    }

    @FXML
    void showAssignPhaseLeadersDialog(ActionEvent event) {

    }

    @FXML
    void showAssignPhaseWorkersDialog(ActionEvent event) {

    }











    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
