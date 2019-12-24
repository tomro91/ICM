package client.crDetails;


import client.ClientController;
import client.ClientUI;
import common.IcmUtils;
import entities.ChangeRequest;
import entities.IEPhasePosition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import server.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrDetails implements ClientUI {

    @FXML
    private TextField changeRequestIDTextField;
    @FXML
    private TextField openingDateTextField;
    @FXML
    private TextField initiatorTextField;
    @FXML
    private TextField infoSystemTextField;
    @FXML
    private TextArea currentStateTextArea;
    @FXML
    private TextArea requestedChangeTextField;
    @FXML
    private TextArea reasonForChangeTextArea;
    @FXML
    private TextArea commentsTextArea;
    @FXML
    private TextField currentPhaseTextField;
    @FXML
    private TextField phaseDeadLineTextField;
    @FXML
    private Pane supervisorButtonsPane;
    @FXML
    private Button downloadFilesButton;

    private static ChangeRequest currRequest;
    private ClientController clientController;

    public static ChangeRequest getCurrRequest() {
        return currRequest;
    }

    public static void setCurrRequest(ChangeRequest currRequest) {
        CrDetails.currRequest = currRequest;
    }

    public void initialize() {
        try {
            clientController = ClientController.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> params = new ArrayList<>();
        params.add(ClientController.getUser().getId());
        params.add(currRequest.getId());


        ServerService loadRequestData = new ServerService(ServerService.DatabaseService.Get_Request_Details, params);

        clientController.handleMessageFromClientUI(loadRequestData);

    }







    @FXML
    void backToHome() {
        try {
            IcmUtils.loadScene(this, IcmUtils.Scenes.Main_Window_New);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handleMessageFromClientController(ServerService serverService) {
        switch (serverService.getDatabaseService()){
            case Get_Request_Details:
                System.out.println("adding details to screen");
                List<ChangeRequest> crList = serverService.getParams();
                setCurrRequest(crList.get(0));

                changeRequestIDTextField.setText(String.valueOf(currRequest.getId()));
                openingDateTextField.setText(currRequest.getDate().toString());
                initiatorTextField.setText(currRequest.getInitiator().getFirstName() + " " + currRequest.getInitiator().getLastName());
                infoSystemTextField.setText(currRequest.getInfoSystem().toString());
                currentPhaseTextField.setText(currRequest.getCurrPhaseName().toString());
                currentStateTextArea.textProperty().setValue(currRequest.getCurrState());
                reasonForChangeTextArea.textProperty().setValue(currRequest.getReasonForChange());
                requestedChangeTextField.textProperty().setValue(currRequest.getRequestedChange());
                commentsTextArea.textProperty().setValue(currRequest.getComment());
                phaseDeadLineTextField.setText(currRequest.getPhases().get(0).getDeadLine().toString());

                initButtons();

                break;
        }
    }

    private void initButtons() {
        List<IEPhasePosition> iePhasePositionList;
        IEPhasePosition iePhasePosition;
        iePhasePositionList = currRequest.getPhases().get(0).getIePhasePosition();

        // the user does not have any special position in this request
        if(iePhasePositionList.isEmpty())   return;

        // load buttons based on user position
        iePhasePosition = iePhasePositionList.get(0);
        switch (iePhasePosition.getPhasePosition()) {
            case EXECUTIVE_LEADER:
                supervisorButtonsPane.setVisible(true);
                break;
        }
    }
}
