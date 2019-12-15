package client.crSummaryDialog;

import client.ClientController;
import client.ClientUI;
import server.ServerService;
import common.IcmUtils;
import entities.Requirement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CRSummaryUIController implements ClientUI {

    @FXML
    private TextArea currStateTextArea;
    @FXML
    private TextArea requestedChangeTextArea;
    @FXML
    private ChoiceBox<String> phaseChoiceBox;
    @FXML
    private Label idLabel;
    @FXML
    private Label initiatorNameLabel;
    @FXML
    private Label infoSystemLabel;
    @FXML
    private Button updateStatusBtn;
    @FXML
    private Label phaseLeaderLabel;

    private ClientController clientController;
    private Requirement requirement;
    private String requestOriginalStatus = null;

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public void initialize() {
        // create the client controller for this scene
        try {
            clientController = ClientController.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
            IcmUtils.displayErrorMsg(e.getMessage());
        }
        // set the change-event handler for the status (make the button enable/disable)
        phaseChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                if (requestOriginalStatus == null)
                    requestOriginalStatus = oldValue;
                else if (requestOriginalStatus.equals(newValue)) {
                    updateStatusBtn.setDisable(true);
                    return;
                }
                updateStatusBtn.setDisable(false);
            }
        });
    }


    public void start() {
        // load the request details into the fields
        idLabel.setText(requirement.getId().toString());
        infoSystemLabel.setText(requirement.getInfoSystem());
        initiatorNameLabel.setText(requirement.getInitiatorName());
        currStateTextArea.textProperty().setValue(requirement.getCurrState());
        requestedChangeTextArea.textProperty().setValue(requirement.getRequestedChange());
        phaseLeaderLabel.setText(requirement.getIeName());

        // initialize the choice box
        Set<String> phases = new HashSet<>();
        phases.add("Evaluation");
        phases.add("Examination");
        phases.add("Execution");
        phases.add("Validation");
        phases.add("Closer");
        phases.add(requirement.getPhaseName());

        phaseChoiceBox.getItems().setAll(phases);
        phaseChoiceBox.getSelectionModel().select(requirement.getPhaseName());
        updateStatusBtn.setDisable(true);
    }


    public void updateStatus() throws IOException {

        // insert the change request id and the new status into array list
        List<String> requirementList = new ArrayList<>();
        requirementList.add(phaseChoiceBox.getSelectionModel().getSelectedItem());
        requirementList.add(idLabel.getText());

        // create request object to pass to client controller
        ServerService serverService = new ServerService(ServerService.DatabaseService.Update_Request_Status, requirementList);

        // pass to client controller
        clientController.handleMessageFromClientUI(serverService);

        // update the request status on table
        requirement.setPhaseName(phaseChoiceBox.getSelectionModel().getSelectedItem());

        // show confirmation message
        IcmUtils.displayInformationMsg("Status Updated", "Congratulations!", "Request status updated successfully");
        // load the home scene
        backToHomeScene();
    }


    public void backToHomeScene() throws IOException {
        // load the scene
        IcmUtils.loadHomeScene(this);
    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
