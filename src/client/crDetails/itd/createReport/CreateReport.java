package client.crDetails.itd.createReport;

import client.ClientUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import server.ServerService;

public class CreateReport implements ClientUI {

    @FXML
    private DialogPane createReportDialogPane;

    @FXML
    private DatePicker startDateDatePicker;

    @FXML
    private DatePicker endDateDatePicker;

    @FXML
    private ChoiceBox<?> reportTypeChoiceBox;

    @FXML
    private Button createButton;


    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
