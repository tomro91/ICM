package client.crDetails.phaseLeader.RequestExtensionTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.ClientController;
import client.ClientUI;
import client.crDetails.CrDetails;
import entities.ChangeRequest;
import entities.Phase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import server.ServerService;
import java.time.LocalDate;


public class RequestExtensionTime implements ClientUI{
	
	@FXML
    private DatePicker RequestedtimeDatePicker;
	@FXML
	private TextArea DescriptionTextAreaDescriptionTextArea;
	@FXML
	private Button submitButton;
	@FXML
	private Button CancelButton;

	private  ChangeRequest currRequest;
	private Phase currPhase;
	private LocalDate datePickerChoice;
	private String comments;
	private ClientController clientController;
    
	public void initialize() {
		 try {
	            clientController = ClientController.getInstance(this);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        List<ChangeRequest> params = new ArrayList<>();
	        currRequest= CrDetails.getCurrRequest();
	        params.add(currRequest);
	        System.out.println(params);

	        ServerService loadPhaseData = new ServerService(ServerService.DatabaseService.Get_Phase_Details, params);
	        clientController.handleMessageFromClientUI(loadPhaseData);
	       
	}
	

    @FXML
    void submitRequestTime(ActionEvent event) {
    	LocalDate currDate = LocalDate.now();     // Create a date object
    	if((currDate.plusDays((long)3) == currPhase.getDeadLine()) && currPhase.isExtensionRequest()== false) {
    		datePickerChoice = RequestedtimeDatePicker.getValue();
    		comments = DescriptionTextAreaDescriptionTextArea.getAccessibleText();
    		System.out.println(comments+datePickerChoice);

        }

    }
    
    @FXML
    void cancelRequestTime(ActionEvent event) {

    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {
    	System.out.println(serverService);
    	   
    	switch (serverService.getDatabaseService()) {

        case Get_Phase_Details:
        	System.out.println("pppp1");
        	List<Phase> phaseList= serverService.getParams();
        	System.out.println("pppp2");
     	   
        	//currPhase= phaseList.get(0);
        
            break;

    }
    	
    }
}
