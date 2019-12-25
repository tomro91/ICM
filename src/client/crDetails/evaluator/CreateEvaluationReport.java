package client.crDetails.evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.ClientController;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private TextField EvaluatedTimeDatePicker;
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
		List<String> list = new ArrayList<String>();
        list.add("MOODLE");
        list.add("LIBRARY");
        list.add("STUDENT_INFO_CENTER");
        list.add("LECTURER_INFO_CENTER");
        list.add("EMPLOYEE_INFO_CENTER");
        list.add("CLASS_COMPUTER");
        list.add("LAB_COMPUTER");
        list.add("COLLEGE_SITE");
        ObservableList<String> obList = FXCollections.observableList(list);
        infoSystemChoiceBox.getItems().clear();
        infoSystemChoiceBox.setItems(obList);
        createButton.setDisable(true);
	}

	@Override
	public void handleMessageFromClientController(ServerService serverService) {
		// TODO Auto-generated method stub
		
	}
}
