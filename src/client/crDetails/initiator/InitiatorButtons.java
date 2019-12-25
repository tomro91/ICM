package client.crDetails.initiator;

import client.ClientUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class InitiatorButtons implements ClientUI {

    @FXML
    private Button attachFilesButton;


    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
