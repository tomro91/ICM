package client.crDetails.tester;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class TesterButtons implements ClientUI {

    @FXML
    private Button setDecisionButton1;

    @FXML
    void showSetDecisionDialog(ActionEvent event) {

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
