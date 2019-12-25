package client.crDetails.evaluator;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.ClientController;
import client.ClientUI;
import common.IcmUtils;
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
        infoSystemChoiceBox.setValue("MOODLE");
        createButton.setDisable(false);
      
        
	}
	public void createEvaluaReport(ActionEvent e) {
		boolean flag=true;
		//checks if one or more fields are empty
		if(requiredChangeTextArea.getText()==null) {
			requiredChangeTextArea.setText("required change text area is empty!!!");
			flag=false;
		}
		if(expectedResultTextArea.getText()==null) {
			expectedResultTextArea.setText("expected result text area is empty!!!");
			flag=false;
		}
		if(risksAndConstraintsTextArea.getText()==null) {
			risksAndConstraintsTextArea.setText("risks and constraints text area is empty!!!");
			flag=false;
		}
		if(EvaluatedTimeDatePicker.getText()==null) {
			EvaluatedTimeDatePicker.setText("evaluated time text field is empty!!!");
			flag=false;
		}
		//if all the fields are full then save to db the report
		if(flag==true) {
			
		}
		else
			IcmUtils.displayErrorMsg("one or more fields are empty");
	}

	@Override
	public void handleMessageFromClientController(ServerService serverService) {
		// TODO Auto-generated method stub
		
	}
}
