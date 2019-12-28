package client.crDetails.phaseLeader.RequestExtensionTime;

import java.io.IOException;
import java.security.cert.Extension;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import client.ClientController;
import client.ClientUI;
import client.crDetails.CrDetails;
import common.IcmUtils;
import entities.ChangeRequest;
import entities.Phase;
import entities.Phase.PhaseStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import server.ServerService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class RequestExtensionTime implements ClientUI{
	
	@FXML
    private DatePicker RequestedtimeDatePicker;
	@FXML
	private TextArea DescriptionTextAreaDescriptionTextArea;
	@FXML
	private Button submitButton;
	@FXML
	private Button CancelButton;

	private ChangeRequest currRequest;
	private Phase currPhase;
	private Phase newCurrPhase;
	private LocalDate datePickerChoice;
	private String description;
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
	        System.out.println(loadPhaseData);
	        clientController.handleMessageFromClientUI(loadPhaseData);
	       
	}
	

    @FXML
    void submitRequestTime(ActionEvent event) {
    	
    	LocalDate currDate = LocalDate.now();     // Create a date object
    	LocalDate deadLine = currPhase.getDeadLine();
    	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	final long days = ChronoUnit.DAYS.between(currDate, deadLine);
        System.out.println("Days between: " + days);
    	
    if(!(days< 4 && days > 0)|| days>3) {
    	 IcmUtils.displayErrorMsg("ERROR","Current stage: " + currPhase.getName().toString() + "\n" +
       	    	"Deadline: " + deadLine.format(formatter) + "\n" + "Time left: " + days +" days" + "\n","ERROR :This request can only be submitted if 3 or less days are left to complete this phase!" );	
    	 IcmUtils.getPopUp().close();
    }
   
    else if(currPhase.isExtensionRequest()== true) {
    	   IcmUtils.displayErrorMsg("ERROR","ERROR","A time extension request for this step has already been submitted." +"\n" + "extension request can only be submitted once!" );
    	   IcmUtils.getPopUp().close();
    	   }
    	   
    	
    	else {
    		datePickerChoice = RequestedtimeDatePicker.getValue();
    		description = DescriptionTextAreaDescriptionTextArea.getText();
    		System.out.println(description+datePickerChoice.format(formatter));
    		
    		newCurrPhase.setExceptionTime(datePickerChoice);
    		newCurrPhase.setExtensionRequest(true);
    		newCurrPhase.setPhaseStatus(PhaseStatus.EXTENSION_TIME_REQUESTED);
    		newCurrPhase.setDescription(description);
    		
            List<Phase> phaseList = new ArrayList<>();
            phaseList.add(newCurrPhase);
            System.out.printf("%s\n",phaseList);
            ServerService updatePhaseExtension = new ServerService(ServerService.DatabaseService.Update_Phase_Extension, phaseList);
            System.out.println(updatePhaseExtension);
            clientController.handleMessageFromClientUI(updatePhaseExtension);
            }
    		
          
    }
    
    @FXML
    void cancelRequestTime(ActionEvent event) {
    	IcmUtils.getPopUp().close();
    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {
     
    	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	
    	switch (serverService.getDatabaseService()) {

        case Get_Phase_Details:
        	List<Phase> phaseList= serverService.getParams();
        	currPhase= phaseList.get(0);
        	newCurrPhase=phaseList.get(0);
            break;
        case Update_Phase_Extension:
        	List<Boolean> update=serverService.getParams();
        	boolean checkUpdate= update.get(0);
        	if(checkUpdate== true) {
        		IcmUtils.displayInformationMsg("Time Extension Request Submited", "Time extension request has been successfully submited", "Time extension request: " + currPhase.getExceptionTime().format(formatter));
        	    IcmUtils.getPopUp().close();
        	}
        	
        	break;
    }
    	
    }
}
