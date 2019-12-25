package client.crDetails.evaluator;

import java.io.IOException;

import client.ClientController;
import client.ClientUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import server.ServerService;

public class CreateEvaluationReport implements ClientUI {
	private ClientController clientController;
	@FXML
	private ChoiceBox<String>infoSystemChoiceBox;
	@FXML
	private TextArea requiredChangeTextArea;
	@FXML
	private TextArea expectedResultTextArea;
	@FXML
	private TextArea risksAndConstraintsTextArea;
	@FXML
	private DatePicker EvaluatedTimeDatePicker;
	@FXML
	private Button cancelButton;
	@FXML
	private Button createButton;
	
	public void initialize() {
		try {
			clientController=ClientController.getInstance(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void handleMessageFromClientController(ServerService serverService) {
		// TODO Auto-generated method stub
		
	}
}
