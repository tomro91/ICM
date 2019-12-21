package client.login;

import client.ClientController;
import client.ClientMain;
import client.ClientUI;
import com.mysql.cj.xdevapi.Client;
import common.IcmUtils;
import entities.ChangeInitiator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginUIController implements ClientUI {

    @FXML
    private TextField userName;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField password;

    private ClientController clientController = null;

    public void initialize() {
        try {
            clientController = ClientController.getInstance(this);
            System.out.println("login UI initialized");
        } catch (IOException e) {
            e.printStackTrace();
            IcmUtils.displayErrorMsg(e.getMessage());
        }
    }

    @FXML
    void login(ActionEvent event) {
        List<String> params = new ArrayList<>();
        params.add(userName.getText());
        params.add(password.getText());
        ServerService loginService = new ServerService(ServerService.DatabaseService.Login, params);

        clientController.handleMessageFromClientUI(loginService);


    }

    @Override
    public void handleMessageFromClientController(ServerService serverService) {
            System.out.println("login return from server");
            if(serverService.getParams() == null) {
                IcmUtils.displayErrorMsg("Wrong user name or password");
            }
            else {
                List<ChangeInitiator> userList = serverService.getParams();
                ClientController.setUser(userList.get(0));
                try {
                    IcmUtils.loadHomeScene(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}
