package client.crDetails.initiator;

import client.ClientUI;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class InitiatorButtons implements ClientUI {

    @FXML
    private Button attachFilesButton;

    @FXML
    private FontAwesomeIconView attachFile;

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
