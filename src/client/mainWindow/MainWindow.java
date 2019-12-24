package client.mainWindow;

import client.ClientController;
import client.ClientUI;
import client.crDetails.CrDetails;
import common.IcmUtils;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import server.ServerService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainWindow implements ClientUI {

    @FXML
    private TextField searchChangeRequestTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button logoutButton;

    // my change requests tab
    @FXML
    private TableView<ChangeRequest> myTableView;
    @FXML
    private TableColumn<ChangeRequest, Integer> idColumn;
    @FXML
    private TableColumn<ChangeRequest, InfoSystem> infoSystemColumn;
    @FXML
    private TableColumn<ChangeRequest, LocalDate> dateColumn;
    @FXML
    private TableColumn<ChangeRequest, Phase.PhaseName> currPhaseColumn;
    @FXML
    private TableColumn<ChangeRequest, Phase.PhaseStatus> phaseStatusColumn;
    @FXML
    private TableColumn<ChangeRequest, String> phaseLeaderColumn;

    // in my treatment tab
    @FXML
    private TableView<ChangeRequest> workTableView;
    @FXML
    private TableColumn<ChangeRequest, Integer> idColumn1;
    @FXML
    private TableColumn<ChangeRequest, InfoSystem> infoSystemColumn1;
    @FXML
    private TableColumn<ChangeRequest, LocalDate> dateColumn1;
    @FXML
    private TableColumn<ChangeRequest, Phase.PhaseName> currPhaseColumn1;
    @FXML
    private TableColumn<ChangeRequest, Phase.PhaseStatus> phaseStatusColumn1;
    @FXML
    private TableColumn<ChangeRequest, String> phaseLeaderColumn1;

    // buttons VBox
    @FXML
    private VBox sideVBox;
    @FXML
    private Button addNewRequestButton;
    @FXML
    private Button itdManagerAssignPermissionsButton;
    @FXML
    private MaterialDesignIconView itdManagerAssignPermissionsIcon;
    @FXML
    private Button createReportButton;
    @FXML
    private MaterialDesignIconView createReportIcon;

    // class local variables
    private ClientController clientController;
    private ObservableList<ChangeRequest> myRequests;
    private ObservableList<ChangeRequest> inMyTreatmentRequests;

    public void initialize() {
        try {
            clientController = ClientController.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChangeInitiator currUser = ClientController.getUser();

        // show assign permissions and create report buttons only if the user is the ITD Manager
        if (currUser.getPosition() != Position.ITD_MANAGER) {
            itdManagerAssignPermissionsButton.setVisible(false);
            itdManagerAssignPermissionsIcon.setVisible(false);
            createReportButton.setVisible(false);
            createReportIcon.setVisible(false);
        }

        // init request lists
        myRequests = FXCollections.observableArrayList();
        inMyTreatmentRequests = FXCollections.observableArrayList();

        // init tables columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        infoSystemColumn.setCellValueFactory(new PropertyValueFactory<>("infoSystem"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        currPhaseColumn.setCellValueFactory(new PropertyValueFactory<>("currPhase"));
        phaseStatusColumn.setCellValueFactory(new PropertyValueFactory<>("currPhaseStatus"));
        phaseLeaderColumn.setCellValueFactory(new PropertyValueFactory<>("currPhasePhaseLeaderName"));

        idColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        infoSystemColumn1.setCellValueFactory(new PropertyValueFactory<>("infoSystem"));
        dateColumn1.setCellValueFactory(new PropertyValueFactory<>("date"));
        currPhaseColumn1.setCellValueFactory(new PropertyValueFactory<>("currPhase"));
        phaseStatusColumn1.setCellValueFactory(new PropertyValueFactory<>("currPhaseStatus"));
        phaseLeaderColumn1.setCellValueFactory(new PropertyValueFactory<>("currPhasePhaseLeaderName"));

        // init tables double clicks to open change request
        myTableView.setRowFactory(tv -> {
            TableRow<ChangeRequest> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CrDetails.setCurrRequest(myTableView.getSelectionModel().getSelectedItem());
                    showRequestDialog();
                }
            });
            return row;
        });

        workTableView.setRowFactory(tv -> {
            TableRow<ChangeRequest> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CrDetails.setCurrRequest(workTableView.getSelectionModel().getSelectedItem());
                    showRequestDialog();
                }
            });
            return row;
        });

        // load data into tables
        // prepare service request to pass to server
        List<ChangeInitiator> userList = new ArrayList<>();
        userList.add(ClientController.getUser());
        ServerService serverService = new ServerService(ServerService.DatabaseService.Get_All_Requests_New, userList);
        // pass to client controller.
        // client controller uses 'handleMessageFromClientController' function to load server answer into the ui
        clientController.handleMessageFromClientUI(serverService);
    }

    private void showRequestDialog() {
        try {
            IcmUtils.loadScene(this, IcmUtils.Scenes.Change_Request_Summary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void logout(ActionEvent event) throws IOException {
        ClientController.setUser(null);
        IcmUtils.loadScene(this, IcmUtils.Scenes.Login);
    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void showCreateReportDialog(ActionEvent event) {

    }

    @FXML
    void showItdManagerAssignPermissionsDialog(ActionEvent event) {

    }

    @FXML
    void showNewRequestDialog(ActionEvent event) {
    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {
        List<List<ChangeRequest>> allRequests = serverService.getParams();
        myRequests.setAll(allRequests.get(0));
        myTableView.setItems(myRequests);
        inMyTreatmentRequests.setAll(allRequests.get(1));
        workTableView.setItems(inMyTreatmentRequests);
    }
}
