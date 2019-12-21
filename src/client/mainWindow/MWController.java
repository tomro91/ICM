package client.mainWindow;

import client.ClientController;
import client.ClientUI;
import common.IcmUtils;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import server.ServerService;

import java.io.IOException;
import java.time.LocalDate;

public class MWController implements ClientUI {

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

    }
}
