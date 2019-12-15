package client.mainWindow;

import client.ClientController;
import client.ClientMain;
import client.ClientUI;
import server.ServerService;
import client.crSummaryDialog.CRSummaryUIController;
import common.IcmUtils;
import entities.Requirement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class MWUIController implements ClientUI {

    @FXML
    private TableView<Requirement> tableView;
    @FXML
    private TableColumn<Requirement, Integer> idColumn;
    @FXML
    private TableColumn<Requirement, String> infoSystemColumn;
    @FXML
    private TableColumn<Requirement, String> phaseStatusColumn;

    private ClientController clientController;
    private ObservableList<Requirement> allRequests;


    public void initialize() {
        System.out.println("start initialize");
        /* create client controller and use it to get all the requests from the server */
        try {
            // create the client controller
            clientController = ClientController.getInstance(this);

            // prepare service request to pass to server
            ServerService serverService = new ServerService(ServerService.DatabaseService.Get_All_Requests, null);
            // pass to client controller.
            // client controller uses 'handleMessageFromClientController' function to load server answer into the ui
            clientController.handleMessageFromClientUI(serverService);

        } catch (IOException e) {
            e.printStackTrace();
            IcmUtils.displayErrorMsg(e.getMessage());
        }

        // initialize the table columns
        allRequests = FXCollections.observableArrayList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        infoSystemColumn.setCellValueFactory(new PropertyValueFactory<>("infoSystem"));
        phaseStatusColumn.setCellValueFactory(new PropertyValueFactory<>("phaseName"));

        // set event handler to the table rows
        tableView.setRowFactory(tv -> {
            TableRow<Requirement> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    showRequestDialog();
                }
            });
            return row;
        });

        System.out.println("end initialize");
    }

    // the client controller uses this function to insert the data from the server into the ui
    public void insertRequestsToTable(List<Requirement> requirements) {
        System.out.println("start insertRequestsToTable");
        allRequests.setAll(requirements);
        tableView.setItems(allRequests);
        System.out.println(requirements);
        System.out.println("end insertRequestsToTable");
    }

    // when table row is double clicked, this function launched
    @FXML
    void showRequestDialog() {
        // get the selected request
        Requirement requirement;
        requirement = tableView.getSelectionModel().getSelectedItem();

        // load the scene
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/client/crSummaryDialog/CRSummaryDialog.fxml").openStream());
            // get the scene controller. this needed because there is no official way to pass the selected request
            CRSummaryUIController crSummaryUIController = loader.getController();
            // pass the selected request
            crSummaryUIController.setRequirement(requirement);
            // start is kind of initialize function since initialize all ready called when the scene is loaded
            crSummaryUIController.start();

            Scene scene = new Scene(root, 550, 725);
            ClientMain.getPrimaryStage().setTitle("Request Summary");
            ClientMain.getPrimaryStage().setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {
        switch (serverService.getDatabaseService()) {
            case Get_All_Requests:
                insertRequestsToTable(serverService.getParams());
                break;
        }
    }
}
