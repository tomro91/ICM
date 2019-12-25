package client.crDetails.evaluator;

import java.io.IOException;

import client.ClientController;
import client.ClientUI;
import client.crDetails.CrDetails;
import common.IcmUtils;
import common.IcmUtils.Scenes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.ServerService;

public class EvaluatorButtons implements ClientUI {

    @FXML
    private Button requestPhaseTimeButton;

    @FXML
    private Button createEvaluationReportButton;
    
    private ClientController clientController;
    
    public void initialize() {
    	try {
			clientController=ClientController.getInstance(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    public void showCreateEvaluationReportDialog(ActionEvent event) {
    	try {
			IcmUtils.loadScene(this,IcmUtils.Scenes.Create_Evaluation_Report);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    public void showRequestTimeDialog(ActionEvent event) {
    	

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {

    }
}
