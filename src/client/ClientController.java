package client;

import common.IcmUtils;
import javafx.scene.control.Alert;
import ocsf.client.AbstractClient;
import server.ServerService;

import java.io.IOException;

public class ClientController extends AbstractClient {
    //Instance variables **********************************************

    /**
     * The interface type variable.  It allows the implementation of
     * the display method in the client.
     */

    private static ClientUI clientUI;
    private static ClientController clientController = null;


    /**
     * Constructs an instance of the chat client.
     *
     * @param host     The server to connect to.
     * @param port     The port number to connect on.
     * @param clientUI The interface type variable.
     */

    private ClientController(String host, int port, ClientUI clientUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        ClientController.clientUI = clientUI;
        openConnection();
    }

    //Constructors ****************************************************

    public static ClientController getInstance(ClientUI clientUI) throws IOException {
        if (clientController == null) {
            return new ClientController(ClientMain.getHost(), ClientMain.getPort(), clientUI);
        } else {
            ClientController.clientUI = clientUI;
            return clientController;
        }
    }


    //Instance methods ************************************************

    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        ServerService serverService = (ServerService) msg;
        clientUI.handleMessageFromClientController(serverService);
        System.out.println("sent to clientUI " + serverService.getDatabaseService());
    }

    /**
     * This method handles all data coming from the UI
     *
     * @param service The message from the UI.
     */
    public void handleMessageFromClientUI(Object service) {
        try {
            sendToServer(service);
        } catch (IOException e) {
            IcmUtils.displayErrorMsg
                    ("Could not send message to server.  Terminating client.");
            quit();
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }
}
//End of ChatClient class
