package client.crDetails.itd;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class ITDButtons implements ClientUI {

    @FXML
    private Button thawButton;

    @FXML
    void thawChangeRequest(ActionEvent event) {

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
