package client.crDetails.phaseLeader;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class PhaseLeaderButtons implements ClientUI {

    @FXML
    private Button requestPhaseTimeButton2;

    @FXML
    void showRequestTimeDialog(ActionEvent event) {

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
