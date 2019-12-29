package client.crDetails.phaseLeader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import client.ClientController;
import client.ClientUI;
import common.IcmUtils;
import entities.ChangeInitiator;
import entities.ChangeRequest;
import entities.Phase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import server.ServerService;
import client.crDetails.CrDetails;


public class PhaseLeaderButtons implements ClientUI {

	private ChangeRequest currRequest;
	private static Phase currPhase;
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
    void showExtensionTimeDialog(ActionEvent event) {
    
    	LocalDate currDate = LocalDate.now();     // Create a date object
    	LocalDate deadLine = currPhase.getDeadLine();
    	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	final long  days = (ChronoUnit.DAYS.between(currDate, deadLine))+1;
        System.out.println("Days between: " + days);
    	
    if(!(days< 4 && days > 0)|| days>3) {
    	 IcmUtils.displayErrorMsg("ERROR","Current stage: " + currPhase.getName().toString() + "\n" +
       	    	"Deadline: " + deadLine.format(formatter) + "\n" + "Time left: " + days +" days" + "\n","ERROR :This request can only be submitted if 3 or less days are left to complete this phase!" );	
    	 //IcmUtils.getPopUp().close();
    }
   
    else if(currPhase.isExtensionRequest()== true) {
    	   IcmUtils.displayErrorMsg("ERROR","ERROR","A time extension request for this phase has already been submitted." +"\n" + "extension request can only be submitted once!" );
    	   //IcmUtils.getPopUp().close();
    	   }
    	   
    	
    else {
    	try {
    		 IcmUtils.popUpScene(this, "Extention Time Request", "/client/crDetails/phaseLeader/RequestExtensionTime/RequestExtensionTime.fxml",407 ,381 );
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    	 
    }
    

    @Override
    public void handleMessageFromClientController(ServerService serverService) {
final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	
    	switch (serverService.getDatabaseService()) {

        case Get_Phase_Details:
        	List<Phase> phaseList= serverService.getParams();
        	currPhase= phaseList.get(0);
            break;
}
    }    

public static void setPhase (Phase NewPhase) {
	currPhase=NewPhase;
}

public static Phase getPhase () {
	return currPhase;
}

}
